package konradotwinowskiapp.klient;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static konradotwinowskiapp.klient.ListAdapter.listka;
import static konradotwinowskiapp.klient.ListAdapter.tvHour;
import static konradotwinowskiapp.klient.SmsReceiver.lista;

public class MainActivity extends AppCompatActivity {

    static TextView textView, textView2;
    Button sendButton;
    static ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView2 = (TextView) findViewById(R.id.textView2);
        textView = (TextView) findViewById(R.id.textView);
        sendButton = (Button) findViewById(R.id.sendButton);
        listView = (ListView) findViewById(R.id.listView);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String theMsg = "##-8-/A12/B3/C5/D1/E6/F1";
                String theNumber = "502025548";
                sendMsg(theNumber, theMsg);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //sendMsg("502025548", "$$add:"+ id);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                mBuilder.setMessage("Czy napewno chcesz się zapisać na godzinę: " + listka.get(position) + "?")
                        .setCancelable(false)
                .setPositiveButton("TAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendMsg("502025548", "$$add: " + lista.get(position).getHour());
                    }
                })
                .setNegativeButton("NIE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alert = mBuilder.create();
                alert.setTitle("Alert!");
                alert.show();

                Toast.makeText(getApplicationContext(), "Clicked on: " + lista.get(position).getHour() , Toast.LENGTH_SHORT).show();
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


}
