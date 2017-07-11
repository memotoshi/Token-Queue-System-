package konradotwinowskiapp.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

/**
 * Created by Konrad on 2017-07-11.
 */

public class SmsReceiver extends BroadcastReceiver {


    String body, number;
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] obj =(Object[])bundle.get("pdus");
            if (obj != null) {
                for(int i=0; i<obj.length; i++) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[])obj[i]);
                    body = smsMessage.getMessageBody().toString();
                    number = smsMessage.getOriginatingAddress().toString();
                }
                SmsManager sms = SmsManager.getDefault();
                if (body.contains("kolejka")) {
                    ViewActivity.queueCount++; //liczba osob w kolejce
                    ViewActivity.nextNumber++; //wyslanie numerka do klienta
                    ViewActivity.lista.add(String.valueOf(ViewActivity.nextNumber)); //wyswietlenie numerka klienta
                    sms.sendTextMessage(number, null, "Your number is " + String.valueOf(ViewActivity.nextNumber), null, null);
                }
            }
        }
    }
}
