package konradotwinowskiapp.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

    TextView QueueView;
    Button testButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        QueueView = (TextView) findViewById(R.id.QueueView);
        testButton = (Button) findViewById(R.id.testButton);

        final Handler ha=new Handler();
        ha.postDelayed(new Runnable() {

            @Override
            public void run() {
                nextQueue();

                ha.postDelayed(this, 1000);
            }
        }, 1000);

        this.sharedPref = getSharedPreferences("IPInfo", Context.MODE_PRIVATE);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextQueue();
            }
        });




//


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
}

