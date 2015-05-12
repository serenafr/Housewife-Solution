package me.christine.housewifesolution;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

public class AddShoppingListActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopping_list);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}