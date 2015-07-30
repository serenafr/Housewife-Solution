package me.christine.housewifesolution;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import me.christine.sqlite.DatabaseHandler.DatabaseHandler;

/**
 * Created by christine on 15-6-20.
 */
public class ShoppingListOperations {
    private final DatabaseHandler dh;

    public ShoppingListOperations(DatabaseHandler dh) {
        this.dh = dh;
    }

    //******************* The following codes are for operations in tab shopping***********************//

    public void addShoppingItems(String itemName) {
        if (itemName.length() > 0) {
            ShoppingItem newItem = new ShoppingItem(itemName);
            dh.addShoppingItem(newItem);
        }
    }

    public ShoppingItem getShoppingItemByID(int id) {
        return dh.getShoppingItem(id);
    }

    //ItemAdapter can use the returned cursor to change to a new cursor, so that the listview is refreshed
    public Cursor getNewCursor() {
        SQLiteDatabase db = dh.getWritableDatabase();
        Cursor newCursor = db.rawQuery("SELECT rowid _id,* FROM shoppinglist ORDER BY store_name", null);
        return newCursor;
    }

    public void deleteShoppingItem(ShoppingItem shoppingItem) {
        dh.deleteShoppingItem(shoppingItem);
    }

    public void editStoreName(ShoppingItem shoppingItem) {
        dh.updateShoppingItem(shoppingItem);
    }
}
