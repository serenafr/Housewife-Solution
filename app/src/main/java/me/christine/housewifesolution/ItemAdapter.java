package me.christine.housewifesolution;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import java.util.ArrayList;
import android.view.View;
import android.widget.TextView;
import android.graphics.Color;

/**
 * Created by christine on 15-5-12.
 */
public class ItemAdapter extends ArrayAdapter<ShoppingItem> {
    private static class ViewHolder {
        TextView name;
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
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /*convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(Color.RED);
            }
        });*/
        viewHolder.name.setText(shoppingItem.getName());
        return convertView;
    }
}
