package co.edu.udea.moviemapps.test.view;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.robotium.solo.*;

import org.junit.Assert;

import java.util.ArrayList;

import co.edu.udea.moviemapps.R;
import co.edu.udea.moviemapps.adapters.MovieAdapter;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@SuppressWarnings("rawtypes")
public class MovieDetailTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "co.edu.udea.moviemapps.activities.MainActivity";

    private static Class<?> launcherActivityClass;

    static {
        try {
            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public MovieDetailTest() throws ClassNotFoundException {
        super(launcherActivityClass);
    }

    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation());
        getActivity();
        solo.unlockScreen();
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testThirdElement() {
        solo.waitForActivity("MainActivity", 2000);
        ArrayList<TextView> recicler = solo.clickInRecyclerView(2, 0);
        String titleSelect = recicler.get(0).getText().toString();
        solo.waitForText(titleSelect);
        Assert.assertTrue(solo.searchText(titleSelect));
    }

    public void testFirstElement() {
        solo.waitForActivity("MainActivity", 2000);
        solo.unlockScreen();
        ArrayList<TextView> recicler = solo.clickInRecyclerView(0, 0);
        String titleSelect = recicler.get(0).getText().toString();
        solo.waitForText(titleSelect);
        Assert.assertTrue(solo.searchText(titleSelect));
    }

    public void testLastElement() {
        solo.waitForActivity("MainActivity", 2000);
        //Timeout.setSmallTimeout(20000);
        solo.scrollDown();
        solo.scrollDown();
        solo.scrollDown();
        solo.scrollDown();
        ArrayList<TextView> recycler = solo.clickInRecyclerView(5, 0);
        String titleSelect = recycler.get(0).getText().toString();
        solo.waitForText(titleSelect);
        Assert.assertTrue(solo.searchText(titleSelect));
    }

}
