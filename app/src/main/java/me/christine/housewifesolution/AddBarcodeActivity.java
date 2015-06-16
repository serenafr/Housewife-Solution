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
import android.widget.TabHost;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_barcode);

        final EditText editText = (EditText) findViewById(R.id.add_store_for_card);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event != null && event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    String str = editText.getText().toString();
                    if (str.length() > 0) {
                        showStoreName();
                        editText.selectAll();
                    }
                    return true;
                }
                return false;
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
        scanBarcode.setOnClickListener(new View.OnClickListener() {
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

    private String getStoreName() {
        EditText editText = (EditText) findViewById(R.id.add_store_for_card);
        return editText.getText().toString();
    }

    private void showStoreName() {
        TextView textView = (TextView) findViewById(R.id.input_store_name);
        textView.setText(getStoreName());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                TextView barcode_type = (TextView) findViewById(R.id.barcode_type);
                barcode_type.setText(format);
                TextView barcode_content = (TextView) findViewById(R.id.barcode_content);
                barcode_content.setText(contents);
                ImageView code_image = (ImageView) findViewById(R.id.scanned_barcode);
                MultiFormatWriter writer = new MultiFormatWriter();
                Bitmap bitmap;
                try {
                    BitMatrix bm = writer.encode(contents, BarcodeFormat.valueOf(format), barcodeWidth, barcodeHeight);
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
        } else if (resultCode == RESULT_CANCELED) {
            Log.i("App", "Scan unsuccessful");
        }
    }
}
