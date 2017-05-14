package konradotwinowskiapp.service;

import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Konrad on 2017-05-12.
 */

public class Server extends NanoHTTPD {
    public Server() throws IOException  {
        super(8080);
    }




    static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration < InetAddress > enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("Server", ex.toString());
        }
        return null;
    }

    @Override
    public Response serve(IHTTPSession session) {
        Log.e("Server", "Gor request!!!!!!!!!!!!!!!!!!");
//        String msg = "<html><body><h1>Hello server</h1>\n";
//        Map<String, String> parms = session.getParms();
//        if (parms.get("username") == null) {
//            msg += "<form action='?' method='get'>\n  <p>Your name: <input type='text' name='username'></p>\n" + "</form>\n";
//        } else {
//            msg += "<p>Hello, " + parms.get("username") + "!</p>";
//        }
//        return newFixedLengthResponse(msg + "</body></html>\n");
        String uri = session.getUri(); // "/open/5"
        String[] arr = uri.split("/"); // ["","open","5"]
        Log.e("Server", Arrays.toString(arr));
        int arrayLength = Array.getLength(arr);

        if (arrayLength > 2 && arrayLength < 4) { // arrayLength = 3
            if (arr[1].equals("open") || arr[1].equals("close")) {
                //if (Arrays.asList(arr).contains("open")){
                return newFixedLengthResponse(arr[2]);
            } else if (arr[1].equals("next")) {
                Random gen = new Random();
                int number = gen.nextInt(99) + 1;
                return newFixedLengthResponse(String.valueOf(number));

            }
        }

        return newFixedLengthResponse(Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "Error 404, file not found.");
        //return newFixedLengthResponse(uri);
}


}
