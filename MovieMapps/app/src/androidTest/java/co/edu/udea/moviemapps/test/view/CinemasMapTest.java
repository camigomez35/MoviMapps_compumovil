package co.edu.udea.moviemapps.test.view;

import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.test.ActivityInstrumentationTestCase2;
import com.robotium.solo.Solo;
import com.robotium.solo.Timeout;

import co.edu.udea.moviemapps.R;


public class CinemasMapTest extends ActivityInstrumentationTestCase2 {
  	private Solo solo;
  	
  	private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "co.edu.udea.moviemapps.activities.MainActivity";

    private static Class<?> launcherActivityClass;
    static{
        try {
            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
           throw new RuntimeException(e);
        }
    }
  	
  	@SuppressWarnings("unchecked")
    public CinemasMapTest() throws ClassNotFoundException {
        super(launcherActivityClass);
    }

  	public void setUp() throws Exception {
        super.setUp();
		solo = new Solo(getInstrumentation());
		getActivity();
  	}
  
   	@Override
   	public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
  	}
  
	public void testNearbyCinemas() throws UiObjectNotFoundException {
		solo.waitForActivity("MainActivity", 2000);
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
		solo.clickInRecyclerView(3, 1);
		Timeout.setSmallTimeout(16000);
		UiDevice device = UiDevice.getInstance(getInstrumentation());
		solo.waitForFragmentById(R.id.map);
		UiObject marker = device.findObject(new UiSelector().descriptionContains("Google Map"));
		assertEquals(true,marker.click());
	}

}
