package konradotwinowskiapp.kasjer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView KasaTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        KasaTextView = (TextView) findViewById(R.id.KasaTextView);
        KasaTextView.setText(getIntent().getStringExtra("Kasa") + " kurwa" );

    }
}
