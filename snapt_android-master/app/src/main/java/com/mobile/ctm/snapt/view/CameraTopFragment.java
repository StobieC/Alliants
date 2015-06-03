package com.mobile.ctm.snapt.view;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mobile.ctm.snapt.R;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class CameraTopFragment extends Fragment {

    @InjectView(R.id.scanner_text)TextView tvScanner;
    @InjectView(R.id.close_btn)Button btnClose;

    public CameraTopFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera_top, container, false);
        ButterKnife.inject(this,view);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    public void ChangeScanningMessage(String message) {
        tvScanner.setText(message);
        tvScanner.setTextColor(getResources().getColor(R.color.red));


   }

    public void ChangeScanningMessageSuccess(String message) {
        tvScanner.setText(message);
        tvScanner.setTextColor(getResources().getColor(R.color.green));

    }

    public void ChangeScanningMessageReset(String message) {
        tvScanner.setText(message);
        tvScanner.setTextColor(getResources().getColor(R.color.white));

    }
}
