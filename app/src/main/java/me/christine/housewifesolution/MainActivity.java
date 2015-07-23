package me.christine.housewifesolution;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import me.christine.sqlite.DatabaseHandler.DatabaseHandler;

public class MainActivity extends Activity{
    public ItemAdapter itemAdapter;
    public CardsAdapter cardsAdapter;
    private final int GET_STORE_NAME_REQUEST = 1;
    private final int ADD_STORE_AND_BARCODE = 2;
    private final int EIDT_STORE_OR_BARCODE = 3;
    public static final String STORE_NAME = "Store Name";
    public static final String ITEM_ID = "ItemId";
    private Tabs tabs;
    private DatabaseHandler dh = new DatabaseHandler(this);
    private final ShoppingListOperations shoppingListOperations = new ShoppingListOperations(dh);
    private final BarcodeGridOperations barcodeGridOperations = new BarcodeGridOperations(dh);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabs = new Tabs();
        tabs.setupTabs(getWindow().getDecorView());
        if (savedInstanceState != null) {
            TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
            tabHost.setCurrentTab(savedInstanceState.getInt("CurrentTab"));
        }

        //Shopping tab
        SQLiteDatabase db = dh.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT rowid _id,* FROM shoppinglist ORDER BY store_name", null);
        itemAdapter = new ItemAdapter(this, cursor);
        final ListView listView = (ListView) findViewById(R.id.shopping_list);
        listView.setAdapter(itemAdapter);
        setOnItemClickListenerForListView(listView);
        setOnClickListenerforEditTextItemInput();
        setOnClickListenerforImageViewDeleteItemInput();

        //cards tab
        GridView gridView = (GridView) findViewById(R.id.cards_grid_view);
        Cursor cardCursor = db.rawQuery("SELECT rowid _id,* FROM membershipcard", null);
        cardsAdapter = new CardsAdapter(this, cardCursor);
        gridView.setAdapter(cardsAdapter);
        setOnItemClickListenerForGridView(gridView);
        setOnItemLongClickListenerForGridView(gridView);
    }

    //Save current tab, so app won't return to default tab when mobile switches between portrait and landscape modes
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        outState.putInt("CurrentTab", tabHost.getCurrentTab());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch(id) {
            case R.id.action_add_item:
                showPopup(findViewById(id));
                return true;
            case R.id.action_search:
                openSearch(item.getActionView());
                return true;
            case R.id.action_settings:
                //openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void showPopup(View v) {
        PopupMenu popup;
        popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_add, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                switch (id) {
                    case R.id.add_shopping_list:

                }
                return true;
            }
        });
    }

    public void openSearch(View v) {
        EditText txtSearch = (EditText) v.findViewById(R.id.txt_search);
        txtSearch.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Toast toast = Toast.makeText(getBaseContext(), "Search : " + v.getText(), Toast.LENGTH_SHORT);
                toast.show();
                return false;
            }
        });
    }

    //**************** The following codes are for tab shopping list********************************
    public void setOnClickListenerforEditTextItemInput() {
        final EditText editText = (EditText) findViewById(R.id.add_shopping_items);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event != null && event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    String itemName = editText.getText().toString();
                    editText.selectAll();// not working????
                    shoppingListOperations.addShoppingItems(itemName);
                    itemAdapter.changeCursor(shoppingListOperations.getNewCursor());
                    return true;
                }
                return false;
            }
        });
    }

    public void setOnClickListenerforImageViewDeleteItemInput() {
        final ImageView imageView = (ImageView) findViewById(R.id.delete_input_text);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText) findViewById(R.id.add_shopping_items);
                editText.setText("");
            }
        });
    }

    public void setOnItemClickListenerForListView(final ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final Cursor cursor = (Cursor) listView.getItemAtPosition(position);
                final String itemId = cursor.getString(cursor.getColumnIndexOrThrow("item_id"));
                PopupMenu popup;
                popup = new PopupMenu(MainActivity.this, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.add_store_menu, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent = new Intent(MainActivity.this, AddStoreActivity.class);
                        intent.putExtra(ITEM_ID, itemId);
                        startActivityForResult(intent, GET_STORE_NAME_REQUEST);
                        return true;
                    }
                });
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_STORE_NAME_REQUEST && resultCode == RESULT_OK && data != null) {
            Bundle receivedInfo = data.getExtras();
            int receivedId = Integer.parseInt(receivedInfo.getString(ITEM_ID));
            String receivedStoreName = receivedInfo.getString(STORE_NAME);
            if (receivedStoreName != "") {
                ShoppingItem shoppingItem = shoppingListOperations.getShoppingItemByID(receivedId);
                String itemName = shoppingItem.getName();
                shoppingItem = new ShoppingItem(receivedId, itemName, receivedStoreName);
                shoppingListOperations.editStoreName(shoppingItem);
                itemAdapter.changeCursor(shoppingListOperations.getNewCursor());
            }
        }

        if (requestCode == ADD_STORE_AND_BARCODE && resultCode == RESULT_OK && data != null) {
            Bundle receivedInfo = data.getExtras();
            String receivedStoreName = receivedInfo.getString("Store Name");
            String receivedBarcodeFormat = receivedInfo.getString("Barcode Format");
            String receivedBarcodeContent = receivedInfo.getString("Barcode Content");
            BarcodeItem barcodeItem = new BarcodeItem(receivedStoreName, receivedBarcodeFormat, receivedBarcodeContent);
            dh.createBarcodeItem(barcodeItem);
        }

        if (requestCode == EIDT_STORE_OR_BARCODE && resultCode == RESULT_OK && data != null) {
            Bundle receivedInfo = data.getExtras();
            int receivedCardId = Integer.parseInt(receivedInfo.getString("Card Id"));
            String receivedStoreName = receivedInfo.getString("Store Name");
            String receivedBarcodeFormat = receivedInfo.getString("Barcode Format");
            String receivedBarcodeContent = receivedInfo.getString("Barcode Content");
            BarcodeItem barcodeItem = new BarcodeItem(receivedCardId, receivedStoreName, receivedBarcodeFormat, receivedBarcodeContent);
            dh.updateMembershipCard(barcodeItem);
            cardsAdapter.changeCursor(barcodeGridOperations.getNewCursor());
            cardsAdapter.notifyDataSetChanged();
        }
    }

    //******************* The following codes are for operations in tab cards***********************//

    public void startAddBarcodeActivity(View view) {

        Intent intent = new Intent(MainActivity.this, AddBarcodeActivity.class);
        startActivityForResult(intent, ADD_STORE_AND_BARCODE);
    }

    private void setOnItemClickListenerForGridView(final GridView gridView) {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) gridView.getItemAtPosition(position);
                String cardId = cursor.getString(0);
                String storeName = cursor.getString(cursor.getColumnIndexOrThrow("store_name"));
                String barcodeFormat = cursor.getString(cursor.getColumnIndexOrThrow("barcode_format"));
                String barcodeContent = cursor.getString(cursor.getColumnIndexOrThrow("barcode_content"));
                Intent intent = new Intent(MainActivity.this, AddBarcodeActivity.class);
                intent.putExtra("Card Id", cardId);
                intent.putExtra("Store Name", storeName);
                intent.putExtra("Barcode Format", barcodeFormat);
                intent.putExtra("Barcode Content", barcodeContent);
                startActivityForResult(intent, EIDT_STORE_OR_BARCODE);
            }
        });
    }

    private void setOnItemLongClickListenerForGridView(final GridView gridView) {
        /*gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DatabaseHandler dh = new DatabaseHandler(getBaseContext());
                Cursor cursor = (Cursor) gridView.getItemAtPosition(position);
                dh.deleteBarcodeItem();
                return false;
            }
        });*/
    }
}

