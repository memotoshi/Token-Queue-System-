package konradotwinowskiapp.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.icu.text.IDNA;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    DatabaseHelper mDatabaseHelper;
    //Server server;
    //Context context = this;
    //DatabaseHelper myDb;
    //String Kasa = (server.cashh).toString();
    TextView InfoView;
    TextView QueueView;
    TextView KasaView;
    Button testButton;
    Button DbButton;
    ListView cashListView;
    String opencash = Server.openCash;
    String closecash = Server.closeCash;
    String number = "1";

    //ArrayAdapter<String> adapter;
    //List<String> listItems = new ArrayList<String>();

//    private List<Cash> myCash = new ArrayList<>();
//    ArrayAdapter<Cash> adapter;

    ArrayList<Cash> myCash = new ArrayList<>();
    CashListAdapter adapter;
    Cash cash1 = new Cash(1 ,opencash, number);
    Cash cash2 = new Cash(2 ,"zielono", "66");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        QueueView = (TextView) findViewById(R.id.QueueView);
        testButton = (Button) findViewById(R.id.testButton);
        DbButton = (Button) findViewById(R.id.DbButton);
        KasaView = (TextView) findViewById(R.id.KasaTxt);
        InfoView = (TextView) findViewById(R.id.InfoTextView);

        controller = new DB_Controller(this);
        //mDatabaseHelper = new DatabaseHelper(this);


        //addData(Server.openCash, String.valueOf(Server.number));
        //ArrayList<Cash> myCash = new ArrayList<>();
        //myCash.add(cash1);
        //myCash.add(cash2);

        //adapter = new CashListAdapter(this, R.layout.item_view, myCash);
        //cashListView.setAdapter(adapter);

//        adapter = new MyListAdapter();
//        myCash.add(new Cash("kupa", "hi"));
//        cashListView.setAdapter(adapter);
        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);

        final Handler ha=new Handler();
        ha.postDelayed(new Runnable() {

            @Override
            public void run() {
                addQueue();

                ha.postDelayed(this, 30000);
            }
        }, 30000);

        this.sharedPref = getSharedPreferences("IPInfo", Context.MODE_PRIVATE);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addQueue();
                //controller.delete_all();

            }
        });


        DbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test();
                //addCash();
            }
        });
    }

    private void addQueue(){

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

//    public void addData(String dbcash, String dbnumber) {
//        boolean insertData = mDatabaseHelper.addData(dbcash, dbnumber);
//
//        if (insertData) {
//            toastMessage("Data successfully inserted!");
//        } else {
//            toastMessage("Something went wrong");
//        }
//
//    }


//*****************************************************
//    private class MyListAdapter extends ArrayAdapter<Cash> {
//        public MyListAdapter(){
//            super(ViewActivity.this, R.layout.item_view, myCash);
//        }
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View itemView = convertView;
//            if (itemView == null) {
//                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
//            }
//            Cash currentCash = myCash.get(position);
//
//            TextView CashTxt = (TextView) itemView.findViewById(R.id.CashTxt);
//            CashTxt.setText(currentCash.getCash());
//
//            TextView NumberTxt = (TextView) itemView.findViewById(R.id.NumberTxt);
//            NumberTxt.setText(currentCash.getNumber());
//
//            return itemView;
//        }
//    }
//
//    private void addCash() {
//        String opencash = Server.openCash;
//        String closecash = Server.closeCash;
//        Integer number = (Server.number);
//        //String numberCash = (Cash.number);
//
//
//        //myCash.add(new Cash(opencash, closecash));
//        ArrayAdapter<Cash> adapter = new MyListAdapter();
//
//        if (opencash.equals("NotData")) {
//            KasaView.setText(("Not data found"));
//        } else {
//            if (myCash.contains(opencash)) {
//                cashListView.setAdapter(adapter);
//                KasaView.setText("");
//            } else {
//                myCash.add(new Cash(opencash, number.toString()));
//                cashListView.setAdapter(adapter);
//                KasaView.setText(myCash.toString());
//                Server.openCash = "NotData";
//            }
//        }
//        if (closecash.equals("NotData")) {
//                KasaView.setText(("Not data found"));
//            } else {
//            if (myCash.contains(new Cash(closecash, "0"))) {
//                myCash.remove(new Cash(closecash, "0"));
//                //adapter.remove(kaska.cash);
//                //adapter.remove(myCash.get());
//                //addIt();
//                //adapter.notifyDataSetChanged();
//                cashListView.setAdapter(adapter);
//                KasaView.setText(myCash.toString());
//                Server.closeCash = "NotData";
//            }
//        }
//    }
//***********************************************************
    private void test() {
        //if (adapter.getPosition(ca))
        //if (myCash.equals(("zielono"), cashListView.getAdapter().get)){

        switch (Server.openCash) {
            case ("NotData"):
                InfoView.setText("Nothing");
                break;
            default:
                try {
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
                //Server.ppl = (Server.ppl - 1);
                controller.update_cash(Server.nextCash, Server.numberCash);
                Server.numberCash = "NotData";
                Server.nextCash = "NotData";
                Server.openCash = "NotData";
                break;

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



        controller.list_all_cashes(KasaView);
    }

//    private void populateListViewFromDB() {
//        Cursor cursor = controller.get_all_cashes();
//
//        String[] fromFieldNames = new String[]
//                {controller.
//                }
//    }

    //-------------------
//        if(Server.openCash.equals("NotData")){
//
//        } else {
//            try {
//                controller.insert_cash(Server.openCash,String.valueOf(Server.number));
//                Server.openCash = "NotData";
//            } catch(SQLiteException e) {
//                InfoView.setText("Already exist");
//                //Toast.makeText(this, "ALREADY EXISTS", Toast.LENGTH_LONG).show();
//            }
//        }


        //populateListView();

//        int id=0;
//        if (Server.openCash.equals("NotData")){
//            KasaView.setText("Nic do dodania");
//
//        } else {
//            ++id;
//            Cash cash3;
//            adapter.add(cash3 = new Cash(id, Server.openCash, String.valueOf(Server.number)));
//            Server.openCash = "NotData";
//        }
//
////
////        ++id;
//        if (Server.closeCash.equals("NotData")){
//            KasaView.setText("Nic nie ma do zamkniecia");
//        } else {
//            adapter.remove(myCash.get(id));
//            Server.closeCash = "NotData";
//        }



//        cash2.setNumber(String.valueOf((Server.number)));
//        KasaView.setText(cash2.getCash());
        //String kasy = getString(myCash.toString();
        //KasaView.setText(kasy);
//        if (adapter.toString().equals(("zielono"))){
//            KasaView.setText("JEST KURWA");
//        }

//    private void toastMessage(String message){
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//    }
//
//    private void populateListView(){
//        Log.d(TAG, "populateListView: Displaying data in the ListView.");
//
//        Cursor data = mDatabaseHelper.getData();
//        ArrayList<String> listData = new ArrayList<>();
//        if(data.getCount() == 0){
//            display("Error", "Not data found");
//        }
//        //StringBuffer buffer = new StringBuffer();
//        while(data.moveToNext()){
//            listData.add(data.getString(1));
//            //listData.add(data.getString(2));
//        }
//        //display("All stored data:", buffer.toString());
//
//        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
//        cashListView.setAdapter(adapter);
//    }
//
//    public void display(String title, String message){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setCancelable(true);
//        builder.setTitle(title);
//        builder.setMessage(message);
//        builder.show();
//    }





    private void nextPerson(){

    }


}

