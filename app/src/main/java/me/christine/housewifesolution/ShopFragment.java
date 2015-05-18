package me.christine.housewifesolution;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;

/**
 * Created by christine on 15-5-7.
 */

public class ShopFragment extends Fragment {

    private ArrayList<ShoppingItem> shoppingItems = new ArrayList<ShoppingItem>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the Layout for this fragment;
        View v = inflater.inflate(R.layout.shoptag_layout, container, false);

        return v;
    }


}

