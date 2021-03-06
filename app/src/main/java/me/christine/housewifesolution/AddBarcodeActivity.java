package me.christine.housewifesolution;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;

import me.christine.sqlite.DatabaseHandler.DatabaseHandler;

/**
 * Created by christine on 15-6-15.
 */
public class AddBarcodeActivity extends Activity {
    private int barcodeWidth = 800;
    private int barcodeHeight = 200;
    private String CARDID;
    private String STORE;
    private String FORMAT = "Barcode Format";
    private String CONTENT = "Barcode Content";
    DatabaseHandler dh = new DatabaseHandler(this);
    BarcodeGridOperations barcodeGridOperations = new BarcodeGridOperations(dh);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_barcode);

        final EditText editText = (EditText) findViewById(R.id.add_store_for_card);
        setOnKeyListenerToEidtText(editText);

        ImageView accept = (ImageView) findViewById(R.id.save_input);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStoreName(editText.getText().toString());
            }
        });

        ImageView delete = (ImageView) findViewById(R.id.delete_input_store);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });

        ImageView scanBarcode = (ImageView) findViewById(R.id.scan_barcode);
        setOnClickListenerToBarcodeScanner(scanBarcode);

        Button saveButton = (Button) findViewById(R.id.save_store_and_barcode);
        setOnClickListenerToSaveButton(saveButton);

        setInfoOnIntentReceived();
    }

    private void setOnKeyListenerToEidtText(final EditText editText) {
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event != null && event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    String str = editText.getText().toString();
                    if (str.length() > 0) {
                        showStoreName(str);
                        editText.selectAll();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    //**************start scanning*************************//
    private void setOnClickListenerToBarcodeScanner(ImageView imageView) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "ONE_D_MODE");
                intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
                intent.putExtra("SCAN_MODE", "UPC_E");
                intent.putExtra("SCAN_MODE", "CODE_39");
                intent.putExtra("SCAN_MODE", "CODABAR");
                startActivityForResult(intent, 0);
            }
        });
    }

    private void setOnClickListenerToSaveButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                TextView storeNameText = (TextView) findViewById(R.id.input_store_name);
                String storeName = storeNameText.getText().toString();
                TextView barcodeFormatText = (TextView) findViewById(R.id.barcode_type);
                String barcodeFormat = barcodeFormatText.getText().toString();
                TextView barcodeContentText = (TextView) findViewById(R.id.barcode_content);
                String barcodeContent = barcodeContentText.getText().toString();
                if (barcodeContent.length() > 0 && barcodeFormat.length() > 0) {
                    resultIntent.putExtra("Card Id", CARDID);
                    resultIntent.putExtra("Store Name", storeName);
                    resultIntent.putExtra("Barcode Format", barcodeFormat);
                    resultIntent.putExtra("Barcode Content", barcodeContent);
                    setResult(RESULT_OK, resultIntent);
                }
                finish();
            }
        });
    }

    private void showStoreName(String storeName) {
        TextView textView = (TextView) findViewById(R.id.input_store_name);
        textView.setText("Store: " + storeName);
    }

    //**********click on already stored card******************//
    private void setInfoOnIntentReceived() {
        Intent intentFromMainActivity = getIntent();
        CARDID = intentFromMainActivity.getStringExtra("Card Id");
        if (CARDID != null) {
            int card_id = Integer.parseInt(CARDID);
            BarcodeItem barcodeItem = barcodeGridOperations.getBarcodeItemById(card_id);
            STORE = barcodeItem.getStoreName();
            FORMAT = barcodeItem.getBarcodeFormat();
            CONTENT = barcodeItem.getBarcodeContent();
            if (STORE != null) {
                TextView textView = (TextView) findViewById(R.id.input_store_name);
                textView.setText(STORE);
            }
            if (FORMAT != null && CONTENT != null) {
                showBarcode(FORMAT, CONTENT);
            }
        }
    }

    //Get scanned barcode from scan activity
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                CONTENT = intent.getStringExtra("SCAN_RESULT");
                FORMAT = intent.getStringExtra("SCAN_RESULT_FORMAT");
                showBarcode(FORMAT, CONTENT);
            }
        } else if (resultCode == RESULT_CANCELED) {
            Log.i("App", "Scan unsuccessful");
        }
    }

    private void showBarcode(String format, String content) {
        TextView barcode_type = (TextView) findViewById(R.id.barcode_type);
        barcode_type.setText(format);
        TextView barcode_content = (TextView) findViewById(R.id.barcode_content);
        barcode_content.setText(content);
        ImageView code_image = (ImageView) findViewById(R.id.scanned_barcode);
        MultiFormatWriter writer = new MultiFormatWriter();
        Bitmap bitmap;
        try {
            BitMatrix bm = writer.encode(content, BarcodeFormat.valueOf(format), barcodeWidth, barcodeHeight);
            bitmap = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);

            for (int i = 0; i < bm.getWidth(); i++) {
                for (int j = 0; j < bm.getHeight(); j++) {
                    bitmap.setPixel(i, j, bm.get(i, j) ? Color.BLACK: Color.WHITE);
                }
            }
            code_image.setImageBitmap(bitmap);
        } catch (com.google.zxing.WriterException e) {
            e.printStackTrace();
        }
    }
}
