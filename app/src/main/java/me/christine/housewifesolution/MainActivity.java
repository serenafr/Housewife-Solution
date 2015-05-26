package me.christine.housewifesolution;


import android.app.Activity;
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
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import me.christine.sqlite.DatabaseHandler.DatabaseHandler;

public class MainActivity extends Activity {

    public ArrayList<ShoppingItem> arrayOfItems = new ArrayList<ShoppingItem>();
    public ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupTabs();
        DatabaseHandler dh = new DatabaseHandler(this);
        SQLiteDatabase db = dh.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT rowid _id,* FROM shoppinglist", null);
        itemAdapter = new ItemAdapter(this, cursor);
        setItemAdapter(itemAdapter);
        displayShoppingList();
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

    private void setupTabs() {

        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tab1, tab2, tab3;
        tab1 = tabHost.
                newTabSpec("First Tab").
                setIndicator("Shopping").
                setContent(R.id.shop_content_layout);


        tab2 = tabHost.
                newTabSpec("Second Tab").
                setIndicator("Cards").
                setContent(R.id.cards_content_layout);

        tab3 = tabHost.
                newTabSpec("Third Tab").
                setIndicator("Recipe").
                setContent(R.id.recipe_content_layout);

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
    }

    protected void setItemAdapter(final ItemAdapter itemAdapter) {
        final ListView listView = (ListView) findViewById(R.id.shopping_list);
        listView.setAdapter(itemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), cursor.getString(cursor.getColumnIndexOrThrow("name")), Toast.LENGTH_SHORT).show();
                itemAdapter.notifyDataSetChanged();
            }
        });
    }

    public void addShoppingItems(View v) {
        final EditText editText = (EditText) findViewById(R.id.add_shopping_items);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event != null && event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    String str = editText.getText().toString();
                    if (str.length() > 0) {
                        editText.selectAll();
                        ShoppingItem newItem = new ShoppingItem(str);
                        DatabaseHandler dh = new DatabaseHandler(getBaseContext());
                        dh.addShoppingItem(newItem);
                        refreshListView(dh);
                    }
                    return true;
                }
                return false;
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.delete_input_text);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
    }

    protected void refreshListView(DatabaseHandler dh) {
        SQLiteDatabase db = dh.getWritableDatabase();
        Cursor newCursor = db.rawQuery("SELECT rowid _id,* FROM shoppinglist", null);
        itemAdapter.changeCursor(newCursor);
        itemAdapter.notifyDataSetChanged();
    }

    protected void displayShoppingList() {
        DatabaseHandler dh = new DatabaseHandler(getBaseContext());
        List<ShoppingItem> shoppingItems = dh.getShoppingList();
        for (ShoppingItem shoppingItem : shoppingItems) {
            arrayOfItems.add(shoppingItem);
            itemAdapter.notifyDataSetChanged();
        }
    }

    public void deleteShoppingItem(View v, ShoppingItem shoppingItem) {
        DatabaseHandler dh = new DatabaseHandler(getBaseContext());
        dh.deleteShoppingItem(shoppingItem);
        refreshListView(dh);
    }
}
