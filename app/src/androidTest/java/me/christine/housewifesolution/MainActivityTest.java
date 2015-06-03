package me.christine.housewifesolution;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.content.Intent;

/**
 * Created by christine on 15-5-18.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>{
    private MainActivity mainActivity;
    private ImageView imageView;
    private EditText editText;
    private ListView listView;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(true);

        mainActivity = getActivity();
        imageView = (ImageView) mainActivity.findViewById(R.id.delete_input_text);
        editText = (EditText) mainActivity.findViewById(R.id.add_shopping_items);
        listView = (ListView) mainActivity.findViewById(R.id.shopping_list);
    }

    public void testPreconditions() {
        assertNotNull("MainActivity is null", mainActivity);
        assertNotNull("ImageButton is null", imageView);
        assertNotNull("EditText is null", editText);
        assertNotNull("ListView is null", listView);
    }

    //Verify that button is displayed correctly.
    public void testImageButton_layout() {
        final View decorView = mainActivity.getWindow().getDecorView();
        ViewAsserts.assertOnScreen(decorView, imageView);
        final ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        assertNotNull(layoutParams);
        assertEquals(layoutParams.width, WindowManager.LayoutParams.WRAP_CONTENT);
        assertEquals(layoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
    }
}
