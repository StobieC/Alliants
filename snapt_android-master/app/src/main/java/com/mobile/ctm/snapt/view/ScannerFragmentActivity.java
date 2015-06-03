package com.mobile.ctm.snapt.view;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


import com.mobile.ctm.snapt.R;
import com.mobile.ctm.snapt.utils.Communicator;

public class ScannerFragmentActivity extends AppCompatActivity implements Communicator{

    private Toolbar toolbar;



    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_scanner_fragment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Make sure the code is in focus");



    }

    public void updateTextView(String message)
    {
        FragmentManager manager = getFragmentManager();
        CameraTopFragment topBar = (CameraTopFragment) manager.findFragmentById(R.id.top_bar);
        topBar.ChangeScanningMessage(message);
    }

    public void updateTextViewSuccess(String message)
    {
        FragmentManager manager = getFragmentManager();
        CameraTopFragment topBar = (CameraTopFragment) manager.findFragmentById(R.id.top_bar);
        topBar.ChangeScanningMessageSuccess(message);
    }

    public void updateTextViewReset(String message)
    {
        FragmentManager manager = getFragmentManager();
        CameraTopFragment topBar = (CameraTopFragment) manager.findFragmentById(R.id.top_bar);
        topBar.ChangeScanningMessageReset(message);
    }


    public void scanErrorToast(String message)
    {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }


}