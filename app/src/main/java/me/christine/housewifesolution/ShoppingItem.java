package me.christine.housewifesolution;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by christine on 15-5-12.
 */
public class ShoppingItem {
    int _id;
    String _name;
    String _store;

    public ShoppingItem() {

    }
    public ShoppingItem(String name) {
        this._name = name;
    }

    public ShoppingItem(int id, String name) {
        this._id = id;
        this._name = name;
    }

    public ShoppingItem(int id, String name, String store) {
        this._id = id;
        this._name = name;
        this._store = store;
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

    public String getStore() {
        return this._store;
    }

    public void setStore(String store) {
        this._store = store;
    }

    public String toString() {
        return "Item name is: " + this._name + ". Store name is: " + this._store;
    }
}
