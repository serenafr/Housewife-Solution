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
        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ItemAdapter adapter = new ItemAdapter(super.getActivity(), shoppingItems);
        ListView listView = (ListView) v.findViewById(R.id.shopping_list);
        listView.setAdapter(adapter);
        Button button = (Button) v.findViewById(R.id.add_shopping_item_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addShoppingItems();
            }
        });
        return v;
    }

    public void addShoppingItems() {
        View v = getView();
        final EditText nameText = (EditText) v.findViewById(R.id.get_shopping_item_edit_text);
        nameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameText.setHint(" ");
            }
        });
        nameText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                nameText.setHint(R.string.add_items_to_buy);
            }
        });
        String name = nameText.getText().toString();
        EditText quantityText = (EditText) v.findViewById(R.id.get_shopping_item_quantity_edit_text);
        if(!TextUtils.isDigitsOnly(quantityText.getText())) {
            //return false;
        }
        int number = Integer.parseInt(quantityText.getText().toString());
        ShoppingItem newItem = new ShoppingItem(name, number);
        ListView listView = (ListView) v.findViewById(R.id.shopping_list);
        ItemAdapter adapter = (ItemAdapter) listView.getAdapter();
        shoppingItems.add(newItem);
        adapter.add(newItem);
        adapter.notifyDataSetChanged();
    }
}

