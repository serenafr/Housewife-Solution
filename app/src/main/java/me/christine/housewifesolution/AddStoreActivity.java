package me.christine.housewifesolution;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import me.christine.sqlite.DatabaseHandler.DatabaseHandler;


public class AddStoreActivity extends Activity {
    public ItemInfoAdapter itemInfoAdapter;
    public String[] infos = new String[2];
    public String storeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store);
        String itemName = "Item: " + getItemName();
        String storeName = "Store: " + getItemStore();
        infos[0] = itemName;
        infos[1] = storeName;
        itemInfoAdapter = new ItemInfoAdapter(getBaseContext(), infos);
        setAdapter(itemInfoAdapter);
        Button saveButton = (Button) findViewById(R.id.save_store_button);
        setOnClickListenerForSaveButton(saveButton);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_store, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getItemName() {
        Intent receivedIntent = getIntent();
        int itemId = Integer.parseInt(receivedIntent.getStringExtra(MainActivity.ITEM_ID));
        DatabaseHandler dh = new DatabaseHandler(getBaseContext());
        ShoppingItem shoppingItem = dh.getShoppingItem(itemId);
        String itemName = shoppingItem.getName();
        return itemName;
    }

    private String getItemStore() {
        Intent receivedIntent = getIntent();
        String gotten = receivedIntent.getExtras().toString();
        int itemId = Integer.parseInt(receivedIntent.getStringExtra(MainActivity.ITEM_ID));
        DatabaseHandler dh = new DatabaseHandler(getBaseContext());
        ShoppingItem shoppingItem = dh.getShoppingItem(itemId);
        String storeName = shoppingItem.getStore();
        if (storeName == null) {
            storeName = "";
        }
        return storeName;
    }

    private void setAdapter(ItemInfoAdapter itemInfoAdapter) {
        ListView listView = (ListView) findViewById(R.id.edit_store);
        listView.setAdapter(itemInfoAdapter);
    }

    public void addStoreInfo(View view) {
        EditText editText = (EditText) findViewById(R.id.add_a_store_name);
        storeName = editText.getText().toString();
        infos[1] = "Store: " + storeName;
        itemInfoAdapter.notifyDataSetChanged();
    }

    private void setOnClickListenerForSaveButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent receivedIntent = getIntent();
                Intent resultIntent = new Intent();
                resultIntent.putExtra(MainActivity.ITEM_ID, receivedIntent.getStringExtra(MainActivity.ITEM_ID));
                resultIntent.putExtra(MainActivity.STORE_NAME, storeName);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
