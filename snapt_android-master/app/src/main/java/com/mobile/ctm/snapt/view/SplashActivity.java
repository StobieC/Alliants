package com.mobile.ctm.snapt.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.mobile.ctm.snapt.R;

/**
 * Created by cameron on 13/05/15.
 */
public class SplashActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(3000); //Delay of 3 seconds
                } catch (Exception e) {

                } finally {

                    SharedPreferences pref = getSharedPreferences("mypref", MODE_PRIVATE);

                    if(pref.getBoolean("firststart", true)){
                        // update sharedpreference - another start wont be the first
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean("firststart", true);
                        editor.apply(); // apply changes

                        // first start, show your dialog | first-run code goes here
                        Intent i = new Intent(SplashActivity.this,
                        MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else {
                        Intent i = new Intent(SplashActivity.this,
                                MainActivity.class);
                        startActivity(i);
                        finish();
                    }


                }
            }
        };
        welcomeThread.start();
    }

}
