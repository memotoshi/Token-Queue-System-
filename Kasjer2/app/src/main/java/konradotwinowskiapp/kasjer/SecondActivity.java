package konradotwinowskiapp.kasjer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class SecondActivity extends AppCompatActivity {

    TextView NumberTextView;
    TextView KasaTextView;
    Button LogoutButton;
    Button NextButton;

    private static final String TAG = MainActivity.class.getName();
    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;
    private SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        NextButton = (Button) findViewById(R.id.NextButton);
        LogoutButton = (Button) findViewById(R.id.LogoutButton);
        KasaTextView = (TextView) findViewById(R.id.KasaTextView);
        NumberTextView = (TextView) findViewById(R.id.NumberTextView);

        KasaTextView.setText(getIntent().getStringExtra("Kasa"));

        this.sharedPref = getSharedPreferences("IPInfo", Context.MODE_PRIVATE);

        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextPerson();
            }
        });

        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });

    }


    private void nextPerson() {

        mRequestQueue = Volley.newRequestQueue(this);

        String targetName = getIntent().getStringExtra("Kasa");
        String IP = this.sharedPref.getString("IP", "");

        stringRequest = new StringRequest(Request.Method.POST, "http://" + IP + "/next/" + targetName, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i(TAG, "Response : " + response.toString());
                NumberTextView.setText(response.toString());

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

    private void Logout() {

        mRequestQueue = Volley.newRequestQueue(this);

        String getName = getIntent().getStringExtra("Kasa");
        String IP = this.sharedPref.getString("IP", "");

        stringRequest = new StringRequest(Request.Method.GET, "http://" + IP + "/next/" + getName, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i(TAG, "Response : " + response.toString());
                finish();
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
