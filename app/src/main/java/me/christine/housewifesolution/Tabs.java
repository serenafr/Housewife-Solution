package me.christine.housewifesolution;

import android.view.GestureDetector;
import android.widget.TabHost;
import android.view.View;

/**
 * Created by christine on 15-6-20.
 */
public class Tabs {
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    public void setupTabs(View view) {
        TabHost tabHost = (TabHost) view.findViewById(android.R.id.tabhost);

        tabHost.setup();

        TabHost.TabSpec tab1, tab2, tab3;
        tab1 = tabHost.
                newTabSpec("First Tab").
                setIndicator("Shopping").
                setContent(R.id.shop_content_layout);


        tab2 = tabHost.
                newTabSpec("Second Tab").
                setIndicator("Cards").
                setContent(R.id.cards_content_layout);

        tab3 = tabHost.
                newTabSpec("Third Tab").
                setIndicator("Recipe").
                setContent(R.id.recipe_content_layout);

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);

        setAnimationEffect(tabHost);
    }

    public void setAnimationEffect(TabHost tabHost) {
        //setting animation features for tabs, so that new tabs slide in when clicked
        AnimatedTabHostListener animatedTabHostListener = new AnimatedTabHostListener(tabHost);
        tabHost.setOnTabChangedListener(animatedTabHostListener);
    }


        /*gestureDetector = new GestureDetector(this, new CustomGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        tabHost.setOnTouchListener(new OnSwipeTouchListener(getBaseContext()) {
            public void onSwipeTop() {
                Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {
                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeBottom() {
                Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }


            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });*/

}
