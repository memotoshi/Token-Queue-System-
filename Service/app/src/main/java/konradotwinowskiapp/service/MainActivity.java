package konradotwinowskiapp.service;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;




public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button StartButton = (Button) findViewById(R.id.StartButton);
        final Button StopButton = (Button) findViewById(R.id.StopButton);
        StopButton.setEnabled(false);


        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(getBaseContext(), MyServices.class));
                StartButton.setEnabled(false);
                StopButton.setEnabled(true);
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                startActivity(intent);

            }
        });


        StopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(getBaseContext(), MyServices.class));
                StopButton.setEnabled(false);
                StartButton.setEnabled(true);

            }
        });


    }

}


