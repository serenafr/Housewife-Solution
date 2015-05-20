package me.christine.housewifesolution;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.io.File;
import java.io.PrintWriter;

public class MainActivity extends Activity {

    public ArrayList<ShoppingItem> arrayOfItems = new ArrayList<ShoppingItem>();
    public ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupTabs();
        itemAdapter = new ItemAdapter(this, arrayOfItems);
        setItemAdapter(itemAdapter);
        showShoppingList();
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

        /*ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(actionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);
        Tab tab1 = actionBar
                .newTab()
                .setText("Shopping")
                .setTabListener(new FragmentTabListener<ShopFragment>(this, "shopping", ShopFragment.class));
        actionBar.addTab(tab1);

        Tab tab2 = actionBar
                .newTab()
                .setText("To_do")
                .setTabListener(new FragmentTabListener<ToDoFragment>(this, "to_do", ToDoFragment.class));
        actionBar.addTab(tab2);

        Tab tab3 = actionBar
                .newTab()
                .setText("Recipe")
                .setTabListener(new FragmentTabListener<RecipeFragment>(this, "recipe", RecipeFragment.class));
        actionBar.addTab(tab3);*/

    }

    protected void setItemAdapter(final ItemAdapter itemAdapter) {
        final ListView listView = (ListView) findViewById(R.id.shopping_list);
        final EditText editText = (EditText) findViewById(R.id.add_shopping_items);
        listView.setAdapter(itemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShoppingItem selectedItem = itemAdapter.getItem(position);
                Toast.makeText(getApplicationContext(), selectedItem.getName(), Toast.LENGTH_SHORT).show();
                itemAdapter.notifyDataSetChanged();
            }
        });
    }

    public void addShoppingItems(View v) {
        final EditText editText = (EditText) findViewById(R.id.add_shopping_items);
        ImageButton imageButton = (ImageButton) findViewById(R.id.add_shopping_cart);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                if (str.length() > 0) {
                    editText.selectAll();
                    ShoppingItem newItem = new ShoppingItem(str);
                    arrayOfItems.add(newItem);
                    itemAdapter.notifyDataSetChanged();
                    writeToFile(str);
                }
            }
        });
    }

    protected void writeToFile(String str) {
        try {
            FileOutputStream fos = openFileOutput("shopping_list", Context.MODE_APPEND|Context.MODE_WORLD_READABLE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(str);
            writer.newLine();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void showShoppingList() {
        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                    openFileInput("shopping_list")));
            String inputString;
            StringBuffer stringBuffer = new StringBuffer();
            while ((inputString = inputReader.readLine()) != null) {
                stringBuffer.append(inputString + "\n");
                ShoppingItem item = new ShoppingItem(inputString);
                arrayOfItems.add(item);
                itemAdapter.notifyDataSetChanged();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteShoppingItem(View v) {
        final ListView listView = (ListView) findViewById(R.id.shopping_list);
        ImageView imageView = (ImageView) findViewById(R.id.deleteItem);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = listView.getPositionForView(v);
                deleteItemFromStorage(position);
                //delete from the listview
                listView.removeViewInLayout(v);
                itemAdapter.notifyDataSetChanged();
            }
        });
    }

    public void deleteItemFromStorage(int position) {
        arrayOfItems.remove(position);
        updateFile(arrayOfItems);
    }

    public void updateFile(ArrayList<ShoppingItem> arrayOfItems) {
        try {
            FileOutputStream fos = openFileOutput("shopping_list", Context.MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write("");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       for (int i = 0; i < arrayOfItems.size(); i++) {
            ShoppingItem shoppingItem = arrayOfItems.get(i);
            String itemName = shoppingItem.getName();
            writeToFile(itemName);
        }
    }
}
