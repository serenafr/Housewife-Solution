package me.christine.sqlite.DatabaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import me.christine.housewifesolution.ShoppingItem;

/**
 * Created by christine on 15-5-22.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "shoppingListManager";
    private static final String TABLE_SHOPPING_LIST = "shoppinglist";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_SHOPPINGLIST_TABLE = "CREATE TABLE " + TABLE_SHOPPING_LIST + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT" + ")";
        db.execSQL(CREATE_SHOPPINGLIST_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("CREATE_TABLE_IF_EXISTS " + TABLE_SHOPPING_LIST);
        onCreate(db);
    }

    public void addShoppingItem(ShoppingItem shoppingItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, shoppingItem.getName());
        db.insert(TABLE_SHOPPING_LIST, null, values);
        db.close();
    }

    public ShoppingItem getShoppingItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SHOPPING_LIST, new String[] {KEY_ID, KEY_NAME},
                KEY_ID + "=?", new String[] {String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        ShoppingItem shoppingItem = new ShoppingItem(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
        return shoppingItem;
    }

    public List<ShoppingItem> getShoppingList() {
        List<ShoppingItem> shoppingList = new ArrayList<ShoppingItem>();
        String selectQuery = "SELECT  * FROM " + TABLE_SHOPPING_LIST;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do {
                ShoppingItem shoppingItem = new ShoppingItem();
                shoppingItem.setId(Integer.parseInt(cursor.getString(0)));
                shoppingItem.setName(cursor.getString(1));
                shoppingList.add(shoppingItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return shoppingList;
    }

    public int getItemCount() {
        String selectQuery = "SELECT  * FROM " + TABLE_SHOPPING_LIST;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    public int updateShoppingItem(ShoppingItem shoppingItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, shoppingItem.getName());
        return db.update(TABLE_SHOPPING_LIST, values, KEY_ID + " = ? ",
                new String[] { String.valueOf(shoppingItem.getId()) });
    }

    public void deleteShoppingItem(ShoppingItem shoppingItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SHOPPING_LIST, KEY_ID + " = ? ",
                new String[] { String.valueOf(shoppingItem.getId()) });
        db.close();
    }
}
