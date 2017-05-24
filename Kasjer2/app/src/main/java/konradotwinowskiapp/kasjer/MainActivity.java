package konradotwinowskiapp.kasjer;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private Button LoginButton;
    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;
    private SharedPreferences sharedPref;
    EditText KasaEditText;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.settings_id) {

            startActivity(new Intent(MainActivity.this, OptionsActivity.class));

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KasaEditText = (EditText) findViewById(R.id.KasaEditText);
        LoginButton = (Button) findViewById(R.id.LoginButton);


        this.sharedPref = getSharedPreferences("IPInfo", Context.MODE_PRIVATE);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendRequestAndPrintResponse();
            }
        });
    }

    private void sendRequestAndPrintResponse() {

        final String targetName = KasaEditText.getText().toString();
        mRequestQueue = Volley.newRequestQueue(this);

        String IP = this.sharedPref.getString("IP", "");

        stringRequest = new StringRequest(Request.Method.GET, "http://" + IP + "/open/" + targetName, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i(TAG, "Response : " + response.toString());
                final Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("Kasa", targetName);
                //startActivity(new Intent(MainActivity.this, SecondActivity.class));
                startActivity(intent);
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
