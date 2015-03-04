package com.alliants.test;

import com.robotium.solo.*;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.test.ActivityInstrumentationTestCase2;

import java.lang.reflect.Method;


@SuppressWarnings("rawtypes")
public class TransitionTest extends ActivityInstrumentationTestCase2 {
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
    public TransitionTest() throws ClassNotFoundException {
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
        //Wait for dialog
        solo.waitForDialogToOpen(5000);
        //Set default small timeout to 18217 milliseconds
        Timeout.setSmallTimeout(18217);

        solo.clickInList(10, 0);
        //Wait for activity: 'com.alliants.view.DetailActivity'
        assertTrue("DetailActivity is not found!", solo.waitForActivity("DetailActivity"));

        solo.clickOnActionBarHomeButton();

        //Wait for activity: 'com.alliants.view.MainActivity'
        solo.waitForActivity("MainActivity", 2000);

        solo.clickInList(1,0);
        //Wait for activity: 'com.alliants.view.DetailActivity'
        assertTrue("DetailActivity is not found!", solo.waitForActivity("DetailActivity"));
        solo.goBack();
        assertTrue("MainActivity is not found!",solo.waitForActivity("MainActivity"));




    }
}
