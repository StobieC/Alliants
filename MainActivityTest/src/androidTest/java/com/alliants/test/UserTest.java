package com.alliants.test;

import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


@SuppressWarnings("rawtypes")
public class UserTest extends ActivityInstrumentationTestCase2 {
  	private Solo solo;
  	
  	private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "com.alliants.view.MainActivity";

    private static Class<?> launcherActivityClass;
    static{
        try {
            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
           throw new RuntimeException(e);
        }
    }
  	
  	@SuppressWarnings("unchecked")
    public UserTest() throws ClassNotFoundException {
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
  
	public void testRun() {
        //Take screenshot
        solo.takeScreenshot();
        //Wait for activity: 'com.alliants.view.MainActivity'
		solo.waitForActivity("MainActivity", 2000);
        //Wait for dialog
		solo.waitForDialogToOpen(5000);
        //Set default small timeout to 14703 milliseconds
		Timeout.setSmallTimeout(14703);
        //Click on David  Ginola
		solo.clickInList(1, 0);
        //Wait for activity: 'com.alliants.view.DetailActivity'
		assertTrue("DetailActivity is not found!", solo.waitForActivity("DetailActivity"));
        //Assert that: 'David' is shown
		assertTrue("'David' is not shown!", solo.waitForView(solo.getView("first_name")));
        //Assert that: 'Ginola' is shown
		assertTrue("'Ginola' is not shown!", solo.waitForView(solo.getView("surname")));
        //Assert that: 'Any perrro street' is shown
		assertTrue("'Any perrro street' is not shown!", solo.waitForView(solo.getView("address_label")));
        //Assert that: '1981098312' is shown
		assertTrue("'1981098312' is not shown!", solo.waitForView(solo.getView("phone_label")));
        //Assert that: 'ginola@david.com' is shown
		assertTrue("'ginola@david.com' is not shown!", solo.waitForView(solo.getView("email_label")));
        //Assert that: '2015-01-15T14:51:49.000Z' is shown
		assertTrue("'2015-01-15T14:51:49.000Z' is not shown!", solo.waitForView(solo.getView("createdAt_label")));
        //Assert that: '2015-02-03T17:03:09.000Z' is shown
		assertTrue("'2015-02-03T17:03:09.000Z' is not shown!", solo.waitForView(solo.getView("updatedAt_label")));
        //Click on HomeView Detail Screen
		solo.clickOnView(solo.getView(android.widget.LinearLayout.class, 8));
        //Wait for activity: 'com.alliants.view.MainActivity'
		assertTrue("MainActivity is not found!", solo.waitForActivity("MainActivity"));
	}
}
