package com.mobile.ctm.snapt.view;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.mobile.ctm.snapt.R;
import com.mobile.ctm.snapt.adapter.MyAdapter;


public class MainActivity extends AppCompatActivity {

    String TITLES[] = {"FAQS","Terms Of Use", "Privacy Policy", "Contact Us", "Start New Scan", "Logout"};
    int ICONS[] = {R.drawable.ic_action_help,R.drawable.ic_action_view_as_list, R.drawable.ic_action_about, R.drawable.ic_action_email, R.drawable.ic_cam, R.drawable.ic_action};

    private Toolbar toolbar;                              // Declaring the Toolbar Object

    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer

    String NAME = "";
    String EMAIL = "";
    int PROFILE = R.drawable.logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        SharedPreferences pref = getSharedPreferences("mypref", MODE_PRIVATE);

        if(pref.getBoolean("firststart", true)) {
            openFragment(new StartFragment());

            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("firststart", false);
            editor.apply(); // apply changes
        }
        else {
            openFragment(new BeginScanFragment());
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View
        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size
        mAdapter = new MyAdapter(TITLES,ICONS,NAME,EMAIL,PROFILE);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        // And passing the titles,icons,header view name, header view email,
        // and header view profile picture

        mRecyclerView.setAdapter(mAdapter);

        final GestureDetector mGestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {

            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());

                // public void onTouchDrawer (final int position) {

                //   }

                if(child!=null && mGestureDetector.onTouchEvent(motionEvent)){
                    Drawer.closeDrawers();
                    //Toast.makeText(MainActivity.this,"The Item Clicked is: "+recyclerView.getChildPosition(child), Toast.LENGTH_SHORT).show();
                    // onTouchDrawer(recyclerView.getChildPosition(child));

                    if (recyclerView.getChildPosition(child) ==1) {
                        goToUrl("http://www.comparethemarket.com/snaptoquote/faqs/");
                    }
                    else if (recyclerView.getChildPosition(child) ==2) {
                        goToUrl("http://www.comparethemarket.com/information/terms-and-conditions/");
                    }
                    else if (recyclerView.getChildPosition(child) ==3) {
                        goToUrl("http://www.comparethemarket.com/information/privacy-policy/");
                    }
                    else if (recyclerView.getChildPosition(child) ==4) {
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto","service@contact-ctm.com", null));
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Snap to Quote Support");
                        startActivity(Intent.createChooser(emailIntent, "Send email..."));
                    }
                    else if (recyclerView.getChildPosition(child)==5){
                        //TODO open new scanner
                    }
                    else {
                        SharedPreferences pref = getSharedPreferences("mypref", MODE_PRIVATE);

                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean("firststart", false);
                        editor.apply();

                        openFragment(new StartFragment());
                    }



                    return true;

                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }
        });

        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager
        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager


        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.openDrawer,R.string.closeDrawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }



        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //method to replace frame container with a new fragment
    public void openFragment(final Fragment fragment) {
        new Thread (new Runnable () {
            public void run() {

                /*
                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim, R.anim.enter_anim, R.anim.exit_anim)
                        .replace(R.id.frame_container, fragment).addToBackStack("Tag")
                         provides proper back navigation between fragments
                        .commit();
*/
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                ft.replace(R.id.frame_container, fragment);
                ft.commit();

            }
        }).start();
    }


    /*
      method for opening webpages
      @params url
     */
    public void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}
