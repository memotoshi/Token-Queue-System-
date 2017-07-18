package konradotwinowskiapp.klient;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konrad on 2017-07-17.
 */

public class ListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Hours> mHoursList;
    //static int i;
    //static int k=0;
    public static String message;
    public static TextView tvHour, tvFree;
    public static List<String> listka = new ArrayList<>();
    int index = 0;

    public ListAdapter(Context mContext, List<Hours> mHoursList) {
        this.mContext = mContext;
        this.mHoursList = mHoursList;
    }

    @Override
    public int getCount() {
        return mHoursList.size();
    }

    @Override
    public Object getItem(int position) {
        return mHoursList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.item_view, null);
        tvFree = (TextView)v.findViewById(R.id.FreeSlotsTxt);
        tvHour = (TextView)v.findViewById(R.id.HoursTxt);

        tvFree.setText(mHoursList.get(position).getFree());
        //tvHour.setText("8:00-9:00");
        //mHoursList.get(position).getHour();
        int size = mHoursList.size();
        message = (String.valueOf(SmsReceiver.ip) + ":00" + "-" + String.valueOf(SmsReceiver.kp) + ":00");
            //for(int index=0; index < mHoursList.size(); index++) {
                tvHour.setText(message);
                SmsReceiver.ip++;
                SmsReceiver.kp++;
            //}


        listka.add(index, message);
        index++;
        //tvHour.setText(mHoursList.get(position).getHour());

        v.setTag(mHoursList.get(position).getId());

        return v;
    }
}
