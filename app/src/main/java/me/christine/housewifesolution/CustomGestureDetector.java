package me.christine.housewifesolution;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.content.Context;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

/**
 * Created by christine on 15-6-3.
 */
public class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    Context context;
    @Override
    public boolean onFling(MotionEvent mEvent1, MotionEvent mEvent2, float velocityX, float velocityY) {
        try {
            if (Math.abs(mEvent1.getY() - mEvent2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;
            // right to left swipe
            if(mEvent1.getX() - mEvent2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Toast.makeText(context.getApplicationContext(), "Left Swipe", Toast.LENGTH_SHORT).show();
            }  else if (mEvent2.getX() - mEvent1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Toast.makeText(context.getApplicationContext(), "Right Swipe", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

        }
        return false;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }
}
