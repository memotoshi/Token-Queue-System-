package konradotwinowskiapp.service;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.icu.text.IDNA;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;
    private SharedPreferences sharedPref;
    private static final String TAG = MainActivity.class.getName();

    DB_Controller controller;
    IntentFilter intentFilter;

    TextView InfoView, QueueView, KasaView, NumberView, MsgView;
    Button testButton, DbButton, SMSButton;

    public static int nextNumber = 10;
    public static int queueCount = 0;
    public static List<String> lista = new ArrayList<>();

//    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            //display the msg in txtview
//            MsgView = (TextView) findViewById(R.id.MsgTxt);
//            MsgView.setText(intent.getExtras().getString("message"));
//            number++;
//        }
//    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        //intent to filter for SMS msg received
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");

        QueueView = (TextView) findViewById(R.id.QueueView);
        testButton = (Button) findViewById(R.id.testButton);
        DbButton = (Button) findViewById(R.id.DbButton);
        KasaView = (TextView) findViewById(R.id.KasaTxt);
        InfoView = (TextView) findViewById(R.id.InfoTextView);
        NumberView = (TextView) findViewById(R.id.NumberTxt);
        SMSButton = (Button) findViewById(R.id.SMSButton);

        controller = new DB_Controller(this);


        final Handler ha = new Handler();
        ha.postDelayed(new Runnable() {

            @Override
            public void run() {
                QueueView.setText(String.valueOf(queueCount));
                //addQueue();

                ha.postDelayed(this, 1000);
            }
        }, 1000);

        this.sharedPref = getSharedPreferences("IPInfo", Context.MODE_PRIVATE);

        /**
         * Buttons under.
         */
        SMSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String theMsg = "kolejka";
                String theNumber = "502025548";
                sendMsg (theNumber, theMsg);
            }
        });

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addQueue();
            }
        });


        DbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCash();
            }
        });
    }

    protected void sendMsg (String theNumber, String theMsg) {
        String SENT = "Msg sent";
        String DELIVERED = "Msg delivered";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(theNumber, null, theMsg, sentPI, deliveredPI);
    }

//    @Override
//    protected void onResume() {
//        registerReceiver(intentReceiver, intentFilter);
//        super.onResume();
//    }
//    @Override
//    protected void onPause() {
//        unregisterReceiver(intentReceiver);
//        super.onPause();
//    }

    private void addQueue() {

        mRequestQueue = Volley.newRequestQueue(this);

        String IP = this.sharedPref.getString("IP", "");

        stringRequest = new StringRequest(Request.Method.GET, "http://" + "192.168.1.2:8080" + "/add/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i(TAG, "Response : " + response.toString());
                QueueView.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error : " + error.toString());
            }
        });

        mRequestQueue.add(stringRequest);
    }


    private void addCash() {

        switch (Server.openCash) {
            case ("NotData"):
                InfoView.setText("Nothing");
                break;
            default:
                try {
                    Server.number = 0;
                    controller.insert_cash(Server.openCash, String.valueOf(Server.number));
                    Server.openCash = "NotData";
                } catch (SQLiteException e) {
                    InfoView.setText("Already exist");
                    //Toast.makeText(this, "ALREADY EXISTS", Toast.LENGTH_LONG).show();
                }
                break;
        }

        switch (Server.numberCash) {
            case ("NotData"):
                InfoView.setText("No number");
                break;
            default:
                if (queueCount>0) {
                    //Server.ppl = (Server.ppl - 1);
                    controller.update_cash(Server.nextCash, Server.numberCash);
                    Server.numberCash = "NotData";
                    Server.nextCash = "NotData";
                    Server.openCash = "NotData";
                    queueCount--;
                    lista.remove(0);
                    break;
                }

        }

        switch (Server.closeCash) {
            case ("NotData"):
                InfoView.setText("Nothing123");
                break;
            default:
                controller.delete_cash(Server.closeCash);
                Server.closeCash = "NotData";
                break;
        }
        controller.list_all_cashes(KasaView, NumberView);
    }

}

