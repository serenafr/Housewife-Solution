package me.christine.housewifesolution;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.widget.TabHost.TabSpec;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupTabs();

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

        TabHost.TabSpec tab1, tab2, tab3;
        tab1 = tabHost.
                newTabSpec("First Tab").
                setIndicator("Shopping").
                setContent(new TabHost.TabContentFactory() {
                    @Override
                    public View createTabContent(String tag) {
                        return new TextView(MainActivity.this);
                    }
                });


        tab2 = tabHost.
                newTabSpec("Second Tab").
                setIndicator("To_do").
                setContent(new TabHost.TabContentFactory() {
                    @Override
                    public View createTabContent(String tag) {
                        return new TextView(MainActivity.this);
                    }
                });

        tab3 = tabHost.
                newTabSpec("Third Tab").
                setIndicator("Recipe").
                setContent(new TabHost.TabContentFactory() {
                    @Override
                    public View createTabContent(String tag) {
                        return new TextView(MainActivity.this);
                    }
                });

        tabHost.setup();
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
}
