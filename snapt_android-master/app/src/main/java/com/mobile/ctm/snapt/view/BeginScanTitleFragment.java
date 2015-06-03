package com.mobile.ctm.snapt.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.ctm.snapt.R;

/**
 * Created by cameron on 19/05/15.
 */
public class BeginScanTitleFragment extends Fragment {

    public BeginScanTitleFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_begin_scan_title, container, false);

        return view;
    }

}
