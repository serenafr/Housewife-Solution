package me.christine.housewifesolution;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.view.View;
import android.content.Context;
import android.widget.GridView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by christine on 15-6-9.
 */
public class CardsAdapter extends CursorAdapter {

    public CardsAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View gridView;
        gridView = (LayoutInflater.from(context)).inflate(R.layout.grid_card_layout, parent, false);
        bindView(gridView, context, cursor);
        gridView.setBackgroundColor(context.getResources().getColor(R.color.grid_view_background));
        return  gridView;
    }

    public void bindView(View view, Context context, Cursor cursor) {
        final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        final float screenWidthInDp = displayMetrics.widthPixels;
        final int gridWidth = (int) (screenWidthInDp - 40) / 2;
        final int gridHeight = gridWidth;
        view.setLayoutParams(new GridView.LayoutParams(gridWidth, gridHeight));
        int position = cursor.getPosition();
        String storeName = cursor.getString(cursor.getColumnIndexOrThrow("store_name"));
        TextView textView = (TextView) view.findViewById(R.id.card_name);
        textView.setText(storeName);
    }
}
