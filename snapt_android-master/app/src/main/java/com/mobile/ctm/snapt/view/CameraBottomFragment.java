package com.mobile.ctm.snapt.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.ctm.snapt.R;

import butterknife.ButterKnife;

/**
 * Created by cameron on 18/05/15.
 */
public class CameraBottomFragment extends Fragment {

    public CameraBottomFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera_bottom, container, false);

        return view;
    }

}
