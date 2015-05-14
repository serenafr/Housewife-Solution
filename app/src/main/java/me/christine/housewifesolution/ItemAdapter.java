package me.christine.housewifesolution;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import java.util.ArrayList;
import android.view.View;
import android.widget.TextView;

/**
 * Created by christine on 15-5-12.
 */
public class ItemAdapter extends ArrayAdapter<ShoppingItem> {
    private static class ViewHolder {
        TextView name;
        TextView number;
    }

    public ItemAdapter(Context context, ArrayList<ShoppingItem> shoppingItems) {
        super(context, R.layout.shopping_item_layout, shoppingItems);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ShoppingItem shoppingItem = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.shopping_item_layout, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.itemName);
            viewHolder.number = (TextView) convertView.findViewById(R.id.itemNumber);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(shoppingItem.name);
        viewHolder.number.setText(Integer.toString(shoppingItem.number));

        return convertView;
    }
}
