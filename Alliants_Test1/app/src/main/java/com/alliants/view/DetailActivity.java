package com.alliants.view;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.alliants.R;
import com.alliants.datapassing.parceable.DetailInformation;


public class DetailActivity extends Activity {


	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_contact);
     // get the action bar
     ActionBar actionBar = getActionBar();

     // Enabling Back navigation on Action Bar icon
     actionBar.setDisplayHomeAsUpEnabled(true);

     // Fetching data from a parcelable object passed from MainActivity
        DetailInformation detailInfo = getIntent().getParcelableExtra("detailInfo");


        TextView lblName = (TextView) findViewById(R.id.first_name);
        TextView lblSurname = (TextView) findViewById(R.id.surname);
        TextView lblAddress = (TextView) findViewById(R.id.address_label);
        TextView lblPhone = (TextView) findViewById(R.id.phone_label);
        TextView lblEmail = (TextView) findViewById(R.id.email_label);
        TextView lblCreatedAt = (TextView) findViewById(R.id.createdAt_label);
        TextView lblupdatedAt = (TextView) findViewById(R.id.updatedAt_label);

        // Get JSON values from previous intent
        // Displaying all values on the screen

    	if(detailInfo!=null){

			  lblName.setText(detailInfo.mfirst_name);
		        lblSurname.setText(detailInfo.msurname);
		        lblAddress.setText(detailInfo.mAddress);
		        lblPhone.setText(detailInfo.mPhone);
		        lblEmail.setText(detailInfo.mEmail);
		        lblCreatedAt.setText(detailInfo.mCreatedAt);
		        lblupdatedAt.setText(detailInfo.mUpdatedAt);
    	}

    }

}
