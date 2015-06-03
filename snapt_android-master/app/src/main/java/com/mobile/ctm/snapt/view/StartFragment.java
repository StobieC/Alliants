package com.mobile.ctm.snapt.view;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.ctm.snapt.R;

import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by cameron on 13/05/15.
 */
public class StartFragment extends Fragment {

    @InjectView(R.id.startBtn) Button startBtn;
    @InjectView(R.id.et_email)EditText etEmail;
    @InjectView(R.id.cbTC)CheckBox cbTC;
    @InjectView(R.id.cbMarketing)CheckBox cbMarketing;

    private String email = "";
    private static boolean  marketingAllowed = false;

    public StartFragment() {

    }

    public static boolean getMarketingAllowed() {
        return marketingAllowed;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_start, container, false);
        ButterKnife.inject(this, view);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Fragment titleFragment = new StartFragmentTitle();

        ft.replace(R.id.title_container, titleFragment);
        ft.commit();



        String styledText = "I have agreed to the <font color='green'><a href=\"https://www.google.com\">terms and conditions</a></font>.";

        cbTC.setText(Html.fromHtml(styledText),TextView.BufferType.SPANNABLE);
        cbTC.setMovementMethod(LinkMovementMethod.getInstance());

        etEmail.setBackgroundResource(R.drawable.edittext_border_green);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = etEmail.getText().toString();

                //uses isEmailValid method to check whether the user has entered matches the format of an email or not
                if (isEmailValid(email) && cbTC.isChecked()) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                SharedPreferences sp = getActivity().getSharedPreferences("Key", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor edt = sp.edit();
                                edt.putString("Email Address",email);

                                edt.apply();
                            } catch (Exception e) {
                            }
                        }
                    }).start();
                    ((MainActivity) getActivity()).openFragment(new BeginScanFragment());
                }
                else if (!cbTC.isChecked() && !isEmailValid(email)) {
                    etEmail.setBackgroundResource(R.drawable.edittext_border_red);
                    Toast.makeText(getActivity(), "You must enter a valid email address and agree to the Terms & Conditions",
                            Toast.LENGTH_LONG).show();
                }
                else if (!isEmailValid(email)) {
                    etEmail.setBackgroundResource(R.drawable.edittext_border_red);
                    Toast.makeText(getActivity(), "You must enter a valid email address",
                            Toast.LENGTH_LONG).show();
                }
                else if(!cbTC.isChecked()) {
                    Toast.makeText(getActivity(), "You must agree to the Terms & Conditions",
                            Toast.LENGTH_LONG).show();
                }

                if (cbMarketing.isChecked()) {
                    marketingAllowed = true;
                }

            }
        });

        return view;
    }



    public static boolean marketingAllowed() {return marketingAllowed;}



    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email) {
        String expression = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(email).matches();
    }





}
