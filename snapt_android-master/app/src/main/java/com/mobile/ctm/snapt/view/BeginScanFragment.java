package com.mobile.ctm.snapt.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

/**
 * Created by cameron on 14/05/15.
 */
public class BeginScanFragment extends Fragment{

    @InjectView(R.id.scanBtn)Button btnScan;
    @InjectView(R.id.noCodeEt)TextView tvNoCode;

    public BeginScanFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_begin_scan, container, false);

        ButterKnife.inject(this, view);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Fragment titleFragment = new BeginScanTitleFragment();

        ft.replace(R.id.begin_scan_title_container, titleFragment);
        ft.commit();


        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ScannerFragmentActivity.class);
                startActivity(intent);
            }
        });

        tvNoCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).goToUrl("http://www.comparethemarket.com/energy/DontHaveMyBill/YourEnergy");
            }
        });



        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
