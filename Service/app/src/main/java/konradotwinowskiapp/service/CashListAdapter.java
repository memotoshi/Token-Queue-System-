package konradotwinowskiapp.service;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konrad on 2017-07-06.
 */

public class CashListAdapter extends ArrayAdapter<Cash> {

    private static final String TAG = "CashListAdapter";
    private Context mContext;
    int mResource;

    public CashListAdapter(Context context, int resource, ArrayList<Cash> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        int id = getItem(position).getId();
        String cashh = getItem(position).getCash();
        String number = getItem(position).getNumber();

        Cash cash = new Cash(id ,cashh, number);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView CashTxt = (TextView) convertView.findViewById(R.id.CashTxt);
        TextView NumberTxt = (TextView) convertView.findViewById(R.id.NumberTxt);

        CashTxt.setText(cashh);
        NumberTxt.setText(number);

        return convertView;
    }
}
