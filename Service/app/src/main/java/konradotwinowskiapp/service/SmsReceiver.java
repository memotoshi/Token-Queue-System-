package konradotwinowskiapp.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

/**
 * Created by Konrad on 2017-07-11.
 */

public class SmsReceiver extends BroadcastReceiver {

    public static String sms_telephone="NotData";
    public static String sms_number="NotData";
    DB_Controller_sms controller_sms;
    public static SmsManager sms;

    String body, number;
    @Override
    public void onReceive(Context context, final Intent intent) {

        controller_sms = new DB_Controller_sms(context);
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] obj =(Object[])bundle.get("pdus");
            if (obj != null) {
                for(int i=0; i<obj.length; i++) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[])obj[i]);
                    body = smsMessage.getMessageBody().toString();
                    number = smsMessage.getOriginatingAddress().toString();
                }

                sms = SmsManager.getDefault();
                if (body.contains("kolejka") || body.contains("Kolejka") || body.contains("KOLEJKA") ) {
                    try {
                        ViewActivity.nextNumber++;
                        controller_sms.insert_sms(number, String.valueOf(ViewActivity.nextNumber));
                        ViewActivity.lista.add(String.valueOf(ViewActivity.nextNumber)); //wyswietlenie numerka klienta
                        sms.sendTextMessage(number, null, "Twoj numer to " + String.valueOf(ViewActivity.nextNumber) + "\n" + "Liczba kas otwartych: " + String.valueOf(ViewActivity.numberOfCash) +
                                "\n" + "Liczba osob oczekujacych przed Toba: " + String.valueOf(ViewActivity.queueCount), null, null);
                        ViewActivity.queueCount++; //liczba osob w kolejce
                        sms_telephone = number;
                        sms_number = String.valueOf(ViewActivity.queueCount);

                    } catch(SQLiteException e) {
                        sms.sendTextMessage(number, null, "Juz jestes w kolejce!", null, null);
                    }
                    //wyslanie numerka do klienta

                }
            }
        }
    }
}
