package me.christine.housewifesolution;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by christine on 15-5-7.
 */
public class RecipeFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the Layout for this fragment;
        return inflater.inflate(R.layout.recipetag_layout, container, false);
    }
}
