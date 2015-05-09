package me.christine.housewifesolution;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.widget.Adapter;

/**
 * Created by christine on 15-5-7.
 */
public class RecipeFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the Layout for this fragment;
        View v = inflater.inflate(R.layout.recipetag_layout, container, false);
        return v;
    }
}
