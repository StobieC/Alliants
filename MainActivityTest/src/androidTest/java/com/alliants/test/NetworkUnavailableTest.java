package com.alliants.test;

import com.robotium.solo.*;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.test.ActivityInstrumentationTestCase2;

import java.lang.reflect.Method;


@SuppressWarnings("rawtypes")
public class NetworkUnavailableTest extends ActivityInstrumentationTestCase2 {
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
    public NetworkUnavailableTest() throws ClassNotFoundException {
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

        WifiManager wifiMan=(WifiManager)solo.getCurrentActivity().getSystemService(Context.WIFI_SERVICE);
        wifiMan.setWifiEnabled(false);

        //ConnectivityManager dataManager=(ConnectivityManager)solo.getCurrentActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        //Method dataMtd = ConnectivityManager.class.getDeclaredMethod(“setMobileDataEnabled”, boolean.class);
        //dataMtd.setAccessible(true);
        //dataMtd.invoke(dataManager, true);


        //Wait for activity: 'com.alliants.view.MainActivity'
        solo.waitForActivity("MainActivity", 2000);
        //Assert that: 'Network Available...' is shown
        assertTrue("'Network Unavailable", solo.waitForView(solo.getView(0x101)));


    }
}
