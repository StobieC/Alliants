package com.mobile.ctm.snapt.view;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.ctm.snapt.R;
import com.mobile.ctm.snapt.controller.AppController;
import com.mobile.ctm.snapt.model.QRResults;
import com.mobile.ctm.snapt.utils.Communicator;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.core.ViewFinderView;
import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScannerFragment extends Fragment implements ZBarScannerView.ResultHandler
{

    private static final String FLASH_STATE = "FLASH_STATE";
    private static final String AUTO_FOCUS_STATE = "AUTO_FOCUS_STATE";
    private static final String SELECTED_FORMATS = "SELECTED_FORMATS";
    private static final String CAMERA_ID = "CAMERA_ID";
    private ZBarScannerView mScannerView;
    private boolean mFlash;
    private boolean mAutoFocus;
    private ArrayList<Integer> mSelectedIndices;
    private int mCameraId = -1;
    Communicator messenger;



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        messenger =(Communicator) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {

        mScannerView = new ZBarScannerView(getActivity()) {
            @Override
            protected IViewFinder createViewFinderView(Context context) {
                ViewFinderView finderView = new ViewFinderView(context);
                finderView.setBorderColor(Color.WHITE);
                return finderView ;
            }
        };

        if (state != null) {
            mFlash = state.getBoolean(FLASH_STATE, false);
            mAutoFocus = state.getBoolean(AUTO_FOCUS_STATE, true);
            mSelectedIndices = state.getIntegerArrayList(SELECTED_FORMATS);
            mCameraId = state.getInt(CAMERA_ID, -1);
        } else {
            mFlash = false;
            mAutoFocus = true;
            mSelectedIndices = null;
            mCameraId = -1;
        }
        setupFormats();
        return mScannerView;

    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setHasOptionsMenu(true);

    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuItem;
        if (mFlash) {
            menuItem = menu.add(Menu.NONE, R.id.menu_flash, 0, R.string.flash_on);
        } else {
            menuItem = menu.add(Menu.NONE, R.id.menu_flash, 0, R.string.flash_off);
        }
        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menu_flash:
                mFlash = !mFlash;
                if (mFlash) {
                    item.setTitle(R.string.flash_on);
                } else {
                    item.setTitle(R.string.flash_off);
                }
                mScannerView.setFlash(mFlash);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
        mScannerView.setFlash(mFlash);
        mScannerView.setAutoFocus(mAutoFocus);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FLASH_STATE, mFlash);
        outState.putBoolean(AUTO_FOCUS_STATE, mAutoFocus);
        outState.putIntegerArrayList(SELECTED_FORMATS, mSelectedIndices);
        outState.putInt(CAMERA_ID, mCameraId);
    }

    @Override
    public void handleResult(Result rawResult) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone r = RingtoneManager.getRingtone(getActivity().getApplicationContext(), notification);
                    r.play();
                }
                catch (Exception e) {
                }
            }
        }).start();


        QRResults scanResult = QRResults.getInstance();
        scanResult.Extract(rawResult.getContents());
        if (scanResult.dataExtractedOK)
        {
            

            Boolean doLeccy = scanResult.canPerformElectricityComparison();
            Boolean doGassy = scanResult.canPerformGasComparison();
            if (doLeccy || doGassy) {
                if (scanResult.areTwoPostcodesPresent() && doLeccy && doGassy) {
                    scanResult.errorMessage = "Your QR code contains two different supply postcodes, we are unable to help you compare.";
                    scanResult.errorMessageForPost = "QR_PC_01";
                    messenger.updateTextView("Invalid Code");
                    AppController.getInstance().postQRScanFailure();
                    messenger.scanErrorToast(scanResult.errorMessage);
                }
                else {
                    messenger.updateTextViewSuccess("Found Code");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Intent i = new Intent(getActivity(), SearchResult.class);
                            startActivity(i);
                        }
                    }, 2000);
                }
            }
            else {
                scanResult.errorMessage = "Your QR code does not contain sufficient data for a comparison";
                scanResult.errorMessageForPost = "QR_NF_01";
                messenger.updateTextView("Invalid Code");
                AppController.getInstance().postQRScanFailure();
                messenger.scanErrorToast(scanResult.errorMessage);
            }
        }
        else
        {
            messenger.updateTextView("Invalid Code");
            messenger.scanErrorToast(scanResult.errorMessage);

            // Restart Camera after 2 second delay
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                mScannerView.startCamera();
                mScannerView.setFlash(mFlash);
                mScannerView.setAutoFocus(mAutoFocus);
                messenger.updateTextViewReset("Finding code...");
            }
        }, 2000);

    }


    public void setupFormats() {
        List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        formats.add(BarcodeFormat.QRCODE);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();

    }

public class customView extends View {

    public customView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
}

}
