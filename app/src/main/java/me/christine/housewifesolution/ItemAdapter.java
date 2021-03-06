package me.christine.housewifesolution;

import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.TextView;
import android.database.Cursor;
import android.util.Log;

import java.util.List;

import me.christine.sqlite.DatabaseHandler.DatabaseHandler;

/**
 * Created by christine on 15-5-12.
 * Reference: http://kmansoft.com/2010/11/16/adding-group-headers-to-listview/
 */
public class ItemAdapter extends CursorAdapter {
    //Item with store name on top of it.
    private static final int VIEW_TYPE_WITH_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    //Two kinds of view: one with a header, the other one not.
    private static final int VIEW_TYPE_COUNT = 2;

    public ItemAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        Cursor cursor = getCursor();
        cursor.moveToPosition(position);
        boolean newGroup = isNewGroup(cursor, position);
        if(newGroup) {
            return VIEW_TYPE_WITH_HEADER;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    @Override
    public View newView(final Context context, Cursor cursor, ViewGroup parent) {
        final int position = cursor.getPosition();
        int nViewType;
        boolean newGroup = isNewGroup(cursor, position);
        if(newGroup) {
            nViewType = VIEW_TYPE_WITH_HEADER;
        } else {
            nViewType = VIEW_TYPE_ITEM;
        }
        View view;
        if(nViewType == VIEW_TYPE_WITH_HEADER) {
            view = LayoutInflater.from(context).inflate(R.layout.store_separator, parent, false);
            View header = view.findViewById(R.id.store_separator);
            header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.shopping_item_layout, parent, false);
        }
        return view;
    }

    @Override
    public void bindView(final View view, final Context context, final Cursor cursor) {
        TextView tv;
        tv = (TextView) view.findViewById(R.id.itemName);
        final int itemId = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("item_id")));
        String name = cursor.getString(cursor.getColumnIndexOrThrow("item_name"));
        String store = cursor.getString(cursor.getColumnIndexOrThrow("store_name"));
        if (store == null) {
            store = "";
        }
        tv.setText(name);
        tv = (TextView) view.findViewById(R.id.store_separator);
        if(tv != null) {
            tv.setText(store);
        }
        final ImageView imageView = (ImageView) view.findViewById(R.id.delete_item);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler dh = new DatabaseHandler(context);
                ShoppingListOperations shoppingListOperations = new ShoppingListOperations(dh);
                ShoppingItem shoppingItem = dh.getShoppingItem(itemId);
                shoppingListOperations.deleteShoppingItem(shoppingItem);
                changeCursor(shoppingListOperations.getNewCursor());
            }
        });
    }

    //return true if a new store is found
    private boolean isNewGroup(Cursor cursor, int position) {
        if ( position == 0) {
            String store = cursor.getString(cursor.getColumnIndexOrThrow("store_name"));
            if (store == null) {
                return false;
            } else {
                return true;
            }
        } else {
            String store_now = cursor.getString(cursor.getColumnIndexOrThrow("store_name"));
            cursor.moveToPosition(position - 1);
            String store_pre = cursor.getString(cursor.getColumnIndexOrThrow("store_name"));
            cursor.moveToPosition(position);
            if (store_now == null && store_pre == null) {
                return false;
            } else if (store_pre == null || store_now == null) {
                return true;
            } else if (store_pre.equals(store_now)) {
                return false;
            } else {
                return true;
            }
        }
    }
}
