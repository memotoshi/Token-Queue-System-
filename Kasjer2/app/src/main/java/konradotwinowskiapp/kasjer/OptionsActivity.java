package konradotwinowskiapp.kasjer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.id.message;

public class OptionsActivity extends AppCompatActivity {

    public static Intent makeIntent(Context context) {
        return new Intent(context, OptionsActivity.class);
    }


    EditText IPText;
    TextView IPView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

         IPText = (EditText) findViewById(R.id.IPText);
         IPView = (TextView) findViewById(R.id.IPView);
        SharedPreferences sharedPref = getSharedPreferences("IPInfo", Context.MODE_PRIVATE);

        String IP = sharedPref.getString("IP", "");
        IPText.setText(IP);
        IPView.setText(IP);

    }


    public void saveIP(View view) {
        SharedPreferences sharedPref = getSharedPreferences("IPInfo", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("IP", IPText.getText().toString());
        editor.apply();

        Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();

        String IP = sharedPref.getString("IP", "");
        IPView.setText(IP);
        //sharedPref.edit().putString("IP", IPText.getText().toString()).commit();
    }



}
