package me.christine.housewifesolution;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by christine on 15-5-7.
 */

public class ShopFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the Layout for this fragment;
        View v = inflater.inflate(R.layout.shoptag_layout, container, false);
        ArrayList<ShoppingItem> shoppingItems = new ArrayList<ShoppingItem>();
        ItemAdapter adapter = new ItemAdapter(super.getActivity(), shoppingItems);
        ListView listView = (ListView) v.findViewById(R.id.shopping_list);
        listView.setAdapter(adapter);
        ShoppingItem shoppingItem = new ShoppingItem("5 apples");
        adapter.add(shoppingItem);
        return v;
    }
}
