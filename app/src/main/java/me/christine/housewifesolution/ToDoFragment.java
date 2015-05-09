package me.christine.housewifesolution;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
/**
 * Created by christine on 15-5-7.
 */
public class ToDoFragment extends Fragment{
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the Layout for this fragment;
        View v = inflater.inflate(R.layout.todotag_layout, container, false);
        return v;
    }
}
