package me.christine.housewifesolution;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.TextView;

/**
 * Created by christine on 15-5-29.
 */
public class ItemInfoAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public ItemInfoAdapter (Context context, String[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.item_info_layout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.item_info);
        textView.setText(values[position]);
        return rowView;
    }
}
