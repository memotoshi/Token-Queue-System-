package konradotwinowskiapp.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ViewActivity extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;
    private SharedPreferences sharedPref;
    private static final String TAG = MainActivity.class.getName();

    Server server;
    Context context = this;
    DatabaseHelper myDb;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;

    TextView QueueView;
    TextView KasaView;
    Button testButton;
    Button DbButton;
    Button CheckButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        myDb = new DatabaseHelper(this);

        QueueView = (TextView) findViewById(R.id.QueueView);
        testButton = (Button) findViewById(R.id.testButton);
        DbButton = (Button) findViewById(R.id.DbButton);
        KasaView = (TextView) findViewById(R.id.KasaView);
        CheckButton = (Button) findViewById(R.id.CheckButton);

        final Handler ha=new Handler();
        ha.postDelayed(new Runnable() {

            @Override
            public void run() {
                nextQueue();

                ha.postDelayed(this, 30000);
            }
        }, 30000);

        this.sharedPref = getSharedPreferences("IPInfo", Context.MODE_PRIVATE);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextQueue();
            }
        });

        CheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddKasa();
            }
        });

        DbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                myDb = new DatabaseHelper(getApplicationContext());
//                sqLiteDatabase = myDb.getReadableDatabase();
//                cursor = myDb.getAllData(sqLiteDatabase);
//                if(cursor.moveToFirst()) {
//                    do {
//                        String kasa;
//                        kasa = cursor.getString(0);
//                        DataProvider dataProvider = new DataProvider(kasa);
//                    }while (cursor.moveToNext());

                Cursor res = myDb.getAllData();
                if(res.getCount() == 0) {
                    showMessage("Error", "Not data found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()) {
                    //buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Kasa :" + res.getString(0) + "\n");
                    //buffer.append("CurrentNumber :" + res.getString(2) + "\n");
                }
                showMessage("Data", buffer.toString());
            }
        });
    }

    private void nextQueue(){

        mRequestQueue = Volley.newRequestQueue(this);

        String IP = this.sharedPref.getString("IP", "");

        stringRequest = new StringRequest(Request.Method.GET, "http://" + "192.168.1.2:8080" + "/add/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i(TAG, "Response : " + response.toString());
                QueueView.setText(response.toString());

//                final Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
//                intent.putExtra("Kasa", targetName);
//                //startActivity(new Intent(MainActivity.this, SecondActivity.class));
//                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error : " + error.toString());
            }
        });

        mRequestQueue.add(stringRequest);
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


    private void AddKasa(){

        //String kasa = server.kasa.toString();
        String kasa = QueueView.getText().toString();
        myDb = new DatabaseHelper(context);
        sqLiteDatabase = myDb.getWritableDatabase();
        myDb.insertData(kasa, sqLiteDatabase);
        myDb.close();
        Toast.makeText(getBaseContext(), "Data saved", Toast.LENGTH_LONG).show();
        myDb.close();

    }
}

