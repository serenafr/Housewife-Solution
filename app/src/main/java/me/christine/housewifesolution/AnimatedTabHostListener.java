package me.christine.housewifesolution;

import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TabHost;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by christine on 15-6-8.
 */
public class AnimatedTabHostListener implements TabHost.OnTabChangeListener {
    private TabHost tabHost;
    private static final int ANIMATION_TIME = 240;
    private View currentView;
    private View previousView;
    private int currentTab;

    public AnimatedTabHostListener(TabHost tabHost) {
        this.tabHost = tabHost;
        this.previousView = tabHost.getCurrentView();
    }

    @Override
    public void onTabChanged(String tabId) {
        currentView = tabHost.getCurrentView();
        if (tabHost.getCurrentTab() > currentTab) {
            previousView.setAnimation(outToLeftAnimation());
            currentView.setAnimation(inFromRightAnimation());
        }
        else
        {
            previousView.setAnimation(outToRightAnimation());
            currentView.setAnimation(inFromLeftAnimation());
        }
        previousView = currentView;
        currentTab = tabHost.getCurrentTab();
    }

    private Animation outToLeftAnimation() {
        Animation outtoLeft = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
        return setProperties(outtoLeft);
    }

    private Animation inFromRightAnimation() {
        Animation inFromRight = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
        return setProperties(inFromRight);
    }

    private Animation outToRightAnimation() {
        Animation outToRight = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
        return setProperties(outToRight);
    }

    private Animation inFromLeftAnimation() {
        Animation inFromLeft = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
        return setProperties(inFromLeft);
    }

    private Animation setProperties(Animation animation) {
        animation.setDuration(this.ANIMATION_TIME);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        return animation;
    }
}
