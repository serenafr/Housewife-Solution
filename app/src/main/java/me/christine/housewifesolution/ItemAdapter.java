package me.christine.housewifesolution;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import java.util.ArrayList;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by christine on 15-5-12.
 */
public class ItemAdapter extends ArrayAdapter<ShoppingItem> {
    private static class ViewHolder {
        TextView name;
        ImageView imageView;
    }

    public ItemAdapter(Context context, ArrayList<ShoppingItem> shoppingItems) {
        super(context, R.layout.shopping_item_layout, shoppingItems);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ShoppingItem shoppingItem = getItem(position);
        final ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.shopping_item_layout, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.itemName);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.deleteItem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(shoppingItem.getName());
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) v.getContext();
                mainActivity.deleteShoppingItem(v, viewHolder.imageView);
            }
        });
        return convertView;
    }
}
