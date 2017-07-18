package konradotwinowskiapp.klient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static konradotwinowskiapp.klient.MainActivity.listView;
import static konradotwinowskiapp.klient.MainActivity.textView;
import static konradotwinowskiapp.klient.MainActivity.textView2;

/**
 * Created by Konrad on 2017-07-16.
 */

public class SmsReceiver extends BroadcastReceiver {

    private ListAdapter adapter;
    static List<Hours> lista;
    public static SmsManager sms;
    String body, number;
    int i=1;
    static public int ip=0;
    static public int kp=0;
    @Override
    public void onReceive(Context context, final Intent intent) {

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] obj = (Object[]) bundle.get("pdus");
            if (obj != null) {
                for (int i = 0; i < obj.length; i++) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj[i]);
                    body = smsMessage.getMessageBody().toString();
                    number = smsMessage.getOriginatingAddress().toString();
                }
                lista = new ArrayList<>();
                List<String> lista2 = new ArrayList<String>();
                sms = SmsManager.getDefault();
                if (body.contains("##")) {
                    //sms.sendTextMessage(number, null, "Test", null, null);
                    textView2.setText("");
                    MainActivity.textView.setText(""); //"##/A%12/B%3/C%5/D%1/E%6"
                    String[] separated = body.split("-"); //(##,A%12,B%3,C%5,D%1,E%6)
                    ip = Integer.parseInt(separated[1]);
                    kp = ip;
                    kp++;
                    //String[] separated2 = Arrays.copyOfRange(separated, 1, separated.length);
                    //String separated3 = Arrays.toString(separated2);
                    //String[] separated4 = separated3.split("%"); //[A,12,B,3,C,5,D,1,E,6]
                    //MainActivity.textView.append(separated[2] + "\n");
                    Pattern p = Pattern.compile("[0-9]+");
                    Matcher m = p.matcher(separated[2]);
                    Pattern pa = Pattern.compile("[A-Z]+");
                    Matcher ma = pa.matcher(body);
                    while (m.find() & ma.find()) {
                        String separated2 = m.group();
                        lista.add(new Hours(i, m.group(), ma.group()));
                        i++;
                        textView.append(separated2);
                    }
//                    while (ma.find()) {
//                        lista2.add(ma.group());
//                        String separated3 = ma.group() + "\n";
//                        textView2.append(separated3);
//                    }

                    //textView.setText(Arrays.toString(separated4));
                    //lista.set(1, new Hours(1, "8:00-9:00"));

                    //for (int index = 0; index < lista.size(); index++) {
                        //textView2.setText((CharSequence) lista.get(1));
                    //lista.set(0, new Hours(0, "123", "33"));
                    textView.setText(body);
                        //lista.set(0, new Hours(0, "333", "55"));


//                        lista2.set(index, lista2.get(index).replace("A", "8:00-9:00"));
//                        lista2.set(index, lista2.get(index).replace("B", "9:00-10:00"));
//                        lista2.set(index, lista2.get(index).replace("C", "10:00-11:00"));
//                        lista2.set(index, lista2.get(index).replace("D", "11:00-12:00"));
//                        lista2.set(index, lista2.get(index).replace("E", "12:00-13:00"));
                    //}
                    //String[] myArray = Arrays.copyOfRange(separated, 1, separated.length);
                    //List<String> list = new ArrayList<String>();
                    //list.add(separated.toString());

                    //ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, lista2);


                    //adapter.remove(adapter.getItem(0));


                    //for (int i = 0; i < separated.length; i++) {
                        //MainActivity.textView.append(separated4[i] + "\n");

                    //}
                }
                adapter = new ListAdapter(context, lista);
                listView.setAdapter(adapter);
            }
        }
    }
}
