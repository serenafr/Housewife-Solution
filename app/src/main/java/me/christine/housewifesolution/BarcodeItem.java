package me.christine.housewifesolution;

/**
 * Created by christine on 15-6-15.
 */
public class BarcodeItem {
    int _id;
    String _storeName;
    String _barcodeFormat;
    String _barcodeContent;

    public BarcodeItem() {

    }

    public BarcodeItem(String storeName) {
        this._storeName = storeName;
    }

    public BarcodeItem(String format, String content) {
        this._barcodeContent = content;
        this._barcodeFormat = format;
    }

    public BarcodeItem(String storeName, String format, String content) {
        this._storeName = storeName;
        this._barcodeFormat = format;
        this._barcodeContent = content;
    }

    public int getId() {
        return this._id;
    }

    public String getStoreName() {
        return  this._storeName;
    }

    public String getBarcodeFormat() {
        return this._barcodeFormat;
    }

    public String getBarcodeContent() {
        return this._barcodeContent;
    }

    public void setId(int id) {
        this._id = id;
    }

    public void setStoreName(String name) {
        this._storeName = name;
    }

    public void addBarcode(String format, String content) {
        this._barcodeFormat = format;
        this._barcodeContent = content;
    }
}


