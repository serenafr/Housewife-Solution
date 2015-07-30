package me.christine.housewifesolution;

import me.christine.sqlite.DatabaseHandler.DatabaseHandler;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by christine on 15-7-15.
 */
public class BarcodeGridOperations {
    private final DatabaseHandler dh;

    public BarcodeGridOperations(DatabaseHandler dh) {
        this.dh = dh;
    }

    public void addBarcodeItem(BarcodeItem barcodeItem) {
        dh.createBarcodeItem(barcodeItem);
    }

    public BarcodeItem getBarcodeItemById(int id) {
        BarcodeItem barcodeItem = dh.getBarcodeItem(id);
        return barcodeItem;
    }
    public Cursor getNewCursor() {
        SQLiteDatabase db = dh.getWritableDatabase();
        Cursor newCursor = db.rawQuery("SELECT rowid _id,* FROM membershipcard", null);
        return newCursor;
    }

    public void refreshGridView(CardsAdapter cardsAdapter) {
        cardsAdapter.changeCursor(getNewCursor());
    }

    public void editBarcodeItem(BarcodeItem barcodeItem) {
        dh.updateMembershipCard(barcodeItem);
    }

    public void deleteBarcodeItem(BarcodeItem barcodeItem) {
        dh.deleteBarcodeItem(barcodeItem);
    }
}
