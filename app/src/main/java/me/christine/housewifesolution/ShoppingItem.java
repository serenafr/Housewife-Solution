package me.christine.housewifesolution;

import android.widget.ImageView;

/**
 * Created by christine on 15-5-12.
 */
public class ShoppingItem {
    int _id;
    String _name;

    public ShoppingItem() {

    }
    public ShoppingItem(String name) {
        this._name = name;
    }

    public ShoppingItem(int id, String name) {
        this._id = id;
        this._name = name;
    }

    public int getId() {
        return this._id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getName() {
        return this._name;
    }

    public void setName(String name) {
        this._name = name;
    }
}
