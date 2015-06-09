package me.christine.housewifesolution;

import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.view.View;
import android.content.Context;
import android.widget.GridView;
import android.widget.ImageView;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by christine on 15-6-9.
 */
public class CardsAdapter extends BaseAdapter {
    private Context context;
    private String cards[] = {"Costco", "CVS", "WholeFoods", "Safeway"};

    public CardsAdapter(Context ctx) {
        context = ctx;
    }

    public int getCount() {
        return 6;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            final float screenWidthInDp = displayMetrics.widthPixels;
            final int gridWidth = (int) (screenWidthInDp - 40)/2;
            final int gridHeight = gridWidth;
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(gridWidth, gridHeight));
            imageView.setScaleType(ImageView.ScaleType.CENTER);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setBackgroundColor(context.getResources().getColor(R.color.grid_view_background));
        return imageView;
    }
}
