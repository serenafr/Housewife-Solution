package me.christine.housewifesolution;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.database.Cursor;
import android.util.Log;

/**
 * Created by christine on 15-5-12.
 */
public class ItemAdapter extends CursorAdapter {

    public ItemAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.shopping_item_layout, parent, false);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvName = (TextView) view.findViewById(R.id.itemName);
        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String store = cursor.getString(cursor.getColumnIndexOrThrow("store"));
        String nameStore = name + " " + store;
        tvName.setText(nameStore);
        final String itemIdString = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
        final int itemId = Integer.parseInt(itemIdString);
        final String itemName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        final String itemStore = cursor.getString(cursor.getColumnIndexOrThrow("store"));
        final ShoppingItem shoppingItem = new ShoppingItem(itemId, itemName, itemStore);
        final ImageView imageView = (ImageView) view.findViewById(R.id.delete_item);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) v.getContext();
                mainActivity.deleteShoppingItem(v, shoppingItem);
            }
        });
    }
}
