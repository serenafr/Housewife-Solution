package me.christine.sqlite.DatabaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import me.christine.housewifesolution.BarcodeItem;
import me.christine.housewifesolution.ShoppingItem;

/**
 * Created by christine on 15-5-22.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "shoppingManager";
    private static final String TABLE_SHOPPING_LIST = "shoppinglist";
    private static final String TABLE_MEMBERSHIP_CARD = "membershipcard";

    private static final String SHOPPING_LIST_ID = "item_id";
    private static final String SHOPPING_LIST_NAME = "item_name";

    private static final String STORE = "store_name";

    private static final String CARD_ID = "rowid";
    private static final String BARCODE_FORMAT = "barcode_format";
    private static final String BARCODE_CONTENT = "barcode_content";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_SHOPPINGLIST_TABLE = "CREATE TABLE " + TABLE_SHOPPING_LIST +
                "(" + SHOPPING_LIST_ID +
                " INTEGER PRIMARY KEY," +
                SHOPPING_LIST_NAME + " TEXT," +
                STORE + " TEXT" + ")";

        String CREATE_MEMBERSHIP_CARD_TABLE = "CREATE TABLE " + TABLE_MEMBERSHIP_CARD +
                "(" + CARD_ID +
                " INTEGER PRIMARY KEY," +
                STORE + " TEXT," +
                BARCODE_FORMAT + " TEXT," +
                BARCODE_CONTENT + " TEXT" + ")";

        db.execSQL(CREATE_SHOPPINGLIST_TABLE);
        db.execSQL(CREATE_MEMBERSHIP_CARD_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("CREATE_TABLE_IF_EXISTS " + TABLE_SHOPPING_LIST);
        db.execSQL("CREATE_TABLE_IF_EXISTS " + TABLE_MEMBERSHIP_CARD);
        onCreate(db);
        /*if(newVersion > oldVersion) {
            db.execSQL("ALTER TABLE TABLE_SHOPPING_LIST ADD COLUMN KEY_STORE TEXT DEFAULT NULL");
        }*/
    }

    //*************************Shoppinglist table methods**********************************//
    public void addShoppingItem(ShoppingItem shoppingItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SHOPPING_LIST_NAME, shoppingItem.getName());
        long itemId = db.insert(TABLE_SHOPPING_LIST, null, values);
        shoppingItem.setId((int) itemId);
        db.close();
    }

    public ShoppingItem getShoppingItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ShoppingItem shoppingItem;
        Cursor cursor = db.query(TABLE_SHOPPING_LIST,
                new String[]{SHOPPING_LIST_ID, SHOPPING_LIST_NAME, STORE},
                SHOPPING_LIST_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor.moveToFirst()) {
            shoppingItem = new ShoppingItem(
                    cursor.getInt(cursor.getColumnIndexOrThrow(SHOPPING_LIST_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(SHOPPING_LIST_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(STORE)));
            return shoppingItem;
        }
        return null;
    }

    public List<ShoppingItem> getShoppingList() {
        List<ShoppingItem> shoppingList = new ArrayList<ShoppingItem>();
        String selectQuery = "SELECT * FROM " + TABLE_SHOPPING_LIST;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                ShoppingItem shoppingItem = new ShoppingItem();
                shoppingItem.setId(cursor.getInt(cursor.getColumnIndexOrThrow(SHOPPING_LIST_ID)));
                shoppingItem.setName(cursor.getString(cursor.getColumnIndexOrThrow(SHOPPING_LIST_NAME)));
                shoppingItem.setStore(cursor.getString(cursor.getColumnIndexOrThrow(STORE)));
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
        values.put(SHOPPING_LIST_NAME, shoppingItem.getName());
        values.put(STORE, shoppingItem.getStore());
        return db.update(TABLE_SHOPPING_LIST, values, SHOPPING_LIST_ID + " = ? ",
                new String[]{String.valueOf(shoppingItem.getId())});
    }

    public void deleteShoppingItem(ShoppingItem shoppingItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SHOPPING_LIST, SHOPPING_LIST_ID + " = ? ",
                new String[]{String.valueOf(shoppingItem.getId())});
        db.close();
    }

    //****************************mebershipcard table methods***********************************//
    public void createBarcodeItem(BarcodeItem barcodeItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(STORE, barcodeItem.getStoreName());
        if (barcodeItem.getBarcodeFormat() != null && barcodeItem.getBarcodeContent() != null) {
            values.put(BARCODE_FORMAT, barcodeItem.getBarcodeFormat());
            values.put(BARCODE_CONTENT, barcodeItem.getBarcodeContent());
        }
        long barcodeId = db.insert(TABLE_MEMBERSHIP_CARD, null, values);
        barcodeItem.setId((int) barcodeId);
        db.close();
    }

    public BarcodeItem getBarcodeItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MEMBERSHIP_CARD,
                new String[] {CARD_ID, STORE, BARCODE_FORMAT, BARCODE_CONTENT},
                CARD_ID + "=?",
                new String[] {String.valueOf(id)},
                null, null, null, null);
        if (cursor.moveToNext()) {
            BarcodeItem barcodeItem = new BarcodeItem(
                    cursor.getInt(cursor.getColumnIndexOrThrow(CARD_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(STORE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(BARCODE_FORMAT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(BARCODE_CONTENT)));
            return barcodeItem;
        }
        if (cursor.getCount() == 0) {
            Log.d("Ah oh", "cursor is null");
        }
        return null;
    }

    public List<BarcodeItem> getAllCards() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<BarcodeItem> cards = new ArrayList<BarcodeItem>();
        String selectQuery = "SELECT * FROM " + TABLE_MEMBERSHIP_CARD;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                BarcodeItem barcodeItem = new BarcodeItem();
                barcodeItem.setId(cursor.getColumnIndexOrThrow(CARD_ID));
                barcodeItem.setStoreName(cursor.getString(cursor.getColumnIndexOrThrow(STORE)));
                barcodeItem.addBarcode(cursor.getString(cursor.getColumnIndexOrThrow(BARCODE_FORMAT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(BARCODE_CONTENT)));
                cards.add(barcodeItem);
            } while (cursor.moveToNext());
        }
        return cards;
    }

    public int updateMembershipCard(BarcodeItem barcodeItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STORE, barcodeItem.getStoreName());
        if (barcodeItem.getBarcodeFormat() != null && barcodeItem.getBarcodeContent() != null) {
            values.put(BARCODE_FORMAT, barcodeItem.getBarcodeFormat());
            values.put(BARCODE_CONTENT, barcodeItem.getBarcodeContent());
        }
        return db.update(TABLE_MEMBERSHIP_CARD, values, CARD_ID + " = ? ",
                new String[] {String.valueOf(barcodeItem.getId())});
    }

    public void deleteBarcodeItem(BarcodeItem barcodeItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("Deleting item", barcodeItem.getId() + "");
        db.delete(TABLE_MEMBERSHIP_CARD, CARD_ID + " = ? ",
                new String[] {String.valueOf(barcodeItem.getId())});
        db.close();
    }

    public void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST);
    }
}
