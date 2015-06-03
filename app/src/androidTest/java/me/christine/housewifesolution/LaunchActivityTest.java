package me.christine.housewifesolution;

import android.content.Intent;
import android.test.ActivityUnitTestCase;

/**
 * Created by christine on 15-5-27.
 */
public class LaunchActivityTest extends ActivityUnitTestCase<MainActivity> {

    public LaunchActivityTest() {
        super(MainActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        Intent mLaunchIntent = new Intent(getInstrumentation().getTargetContext(), MainActivity.class);
        startActivity(mLaunchIntent, null,null);
    }

    public void testNextActivityIsLaunchedWithIntent() {
        final Intent launchIntent = getStartedActivityIntent();
        assertNotNull("Intent was null", launchIntent);
        assertTrue(isFinishCalled());
    }
}
