package com.alliants.test;

import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


@SuppressWarnings("rawtypes")
public class RotateTestCase extends ActivityInstrumentationTestCase2 {
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
    public RotateTestCase() throws ClassNotFoundException {
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
        //Wait for activity: 'com.alliants.view.MainActivity'
        solo.waitForActivity("MainActivity", 2000);
        //Rotate the screen
        solo.setActivityOrientation(Solo.LANDSCAPE);
        //Take screenshot
        solo.takeScreenshot();
        //Wait for dialog
        solo.waitForDialogToOpen(5000);
        //Rotate the screen
        solo.setActivityOrientation(Solo.PORTRAIT);
        //Wait for dialog
        solo.waitForDialogToOpen(5000);
        //Click on Jamie  Mordington
        solo.clickInList(6, 0);
        //Wait for activity: 'com.alliants.view.DetailActivity'
        assertTrue("DetailActivity is not found!", solo.waitForActivity("DetailActivity"));
        //Assert that: 'Jamie' is shown
        assertTrue("'Jamie' is not shown!", solo.waitForView(solo.getView("first_name")));
        //Click on HomeView Detail Screen
        solo.clickOnView(solo.getView(android.widget.LinearLayout.class, 8));
        //Wait for activity: 'com.alliants.view.MainActivity'
        assertTrue("MainActivity is not found!", solo.waitForActivity("MainActivity"));
        //Wait for dialog
        solo.waitForDialogToOpen(5000);
        //Click on Nadia  Nostra
        solo.clickInList(2, 0);
        //Wait for activity: 'com.alliants.view.DetailActivity'
        assertTrue("DetailActivity is not found!", solo.waitForActivity("DetailActivity"));
        //Rotate the screen
        solo.setActivityOrientation(Solo.LANDSCAPE);
        //Rotate the screen
        solo.setActivityOrientation(Solo.PORTRAIT);
        //Assert that: '2015-01-15T15:23:27.000Z' is shown
        assertTrue("'2015-01-15T15:23:27.000Z' is not shown!", solo.waitForView(solo.getView("createdAt_label")));
    }
}