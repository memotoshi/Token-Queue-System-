package konradotwinowskiapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Konrad on 2017-05-12.
 */

public class MyServices extends Service {

    private Server server;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, /*@IntDef(value = {Service.START_FLAG_REDELIVERY, Service.START_FLAG_RETRY}, flag = true)*/ int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
        try {
            if(server == null) {
                server = new Server();
            }
            server.start();
            Toast.makeText(this, "Service started IP: " + Server.getLocalIpAddress(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Service error", Toast.LENGTH_LONG).show();
        }
        return START_STICKY;
    }

    public void onDestroy(){
        super.onDestroy();
        if(server != null) {
            server.stop();
        }
        Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();
    }
}
