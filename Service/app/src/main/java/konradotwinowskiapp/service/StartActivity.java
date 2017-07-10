package konradotwinowskiapp.service;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {


    DB_Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button StartButton = (Button) findViewById(R.id.StartButton);
        Button RestartButton = (Button) findViewById(R.id.RestartButton);

        controller = new DB_Controller(this);

        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //controller.delete()
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        RestartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.delete_all();
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
