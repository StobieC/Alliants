package com.mobile.ctm.snapt.view;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mobile.ctm.snapt.R;
import com.mobile.ctm.snapt.controller.AppController;
import com.mobile.ctm.snapt.model.QRResults;
import com.mobile.ctm.snapt.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class SearchResult extends AppCompatActivity {

    @InjectView(R.id.getPricesBtn) Button getPricesBtn;
    @InjectView(R.id.post_code_elec_result) TextView epc;
    @InjectView(R.id.supplier_name_elec_result) TextView esn;
    @InjectView(R.id.tariff_elec_result) TextView etn;
    @InjectView(R.id.meter_no_elec_result) TextView emn;
    @InjectView(R.id.usage_elec_result) TextView eau;
    @InjectView(R.id.statement_date_elec_result) TextView esd;
    @InjectView(R.id.payment_method_elec_result) TextView epm;

    @InjectView(R.id.post_code_gas_result) TextView gpc;
    @InjectView(R.id.supplier_name_gas_result) TextView gsn;
    @InjectView(R.id.tariff_gas_result) TextView gtn;
    @InjectView(R.id.meter_no_gas_result) TextView gmn;
    @InjectView(R.id.usage_gas_result) TextView gau;
    @InjectView(R.id.statement_date_gas_result) TextView gsd;
    @InjectView(R.id.payment_method_gas_result) TextView gpm;

    @InjectView(R.id.card_view_electricity) CardView cvElec;
    @InjectView(R.id.card_view_gas)CardView cvGas;

    @InjectView(R.id.postcode_electricity)TextView ePC;
    @InjectView(R.id.supplier_electricity)TextView eSN;
    @InjectView(R.id.tariff_electricity)TextView eTF;
    @InjectView(R.id.payment_electricity)TextView ePM;
    @InjectView(R.id.meter_electricity)TextView eMN;
    @InjectView(R.id.usage_electricity)TextView eUsg;
    @InjectView(R.id.statement_date_electricity)TextView eSD;

    @InjectView(R.id.postcode_gas)TextView gPC;
    @InjectView(R.id.supplier_gas)TextView gSN;
    @InjectView(R.id.tariff_gas)TextView gTF;
    @InjectView(R.id.payment_gas)TextView gPM;
    @InjectView(R.id.meter_gas)TextView gMN;
    @InjectView(R.id.usage_gas)TextView gUsg;
    @InjectView(R.id.statement_date_gas)TextView gSD;

    @InjectView(R.id.tool_bar)Toolbar toolbar;

    private ProgressDialog pDialog;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        ButterKnife.inject(this);

        setSupportActionBar(toolbar);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("We're currently searching for your savings...");
        pDialog.setCancelable(true);
        pDialog.setCanceledOnTouchOutside(false);

        QRResults qr = QRResults.getInstance();
        qr.performingSecondScan = false;
        if (qr.canPerformElectricityComparison())
        {
            epc.setText(qr.electricityPostCode);
            esn.setText(qr.electricityCurrentProvider);
            etn.setText(qr.electricityCurrentTariff);
            emn.setText(qr.electricityMeterReference);
            eau.setText(qr.ConsumptionForDisplay(qr.electricityAnnualConsumption));
            esd.setText(qr.ElectricityStatementPeriodForDisplay(context));
            epm.setText(qr.PaymentMethodForDisplay(qr.electricityPaymentMethod));
        }
        else
        {
            ePM.setMovementMethod(LinkMovementMethod.getInstance());
            SpannableString ss = new SpannableString("Scan a second code if you'd like us to compare dual-fuel suppliers for you");
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    QRResults.getInstance().performingSecondScan = true;
                    Intent intent = new Intent(SearchResult.this, ScannerFragmentActivity.class);
                    startActivity(intent);
                }
            };
            ss.setSpan(clickableSpan, 0, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ePC.setVisibility(View.INVISIBLE);
            eSN.setText("No Electricity usage found.");
            eTF.setVisibility(View.INVISIBLE);
            ePM.setText(ss);
            eMN.setVisibility(View.INVISIBLE);
            eUsg.setVisibility(View.INVISIBLE);
            eSD.setVisibility(View.INVISIBLE);
        }

        if (qr.canPerformGasComparison())
        {
            gpc.setText(qr.gasPostCode);
            gsn.setText(qr.gasCurrentProvider);
            gtn.setText(qr.gasCurrentTariff);
            gmn.setText(qr.gasMeterReference);
            gau.setText(qr.ConsumptionForDisplay(qr.gasAnnualConsumption));
            gsd.setText(qr.GasStatementPeriodForDisplay(context));
            gpm.setText(qr.PaymentMethodForDisplay(qr.gasPaymentMethod));
        }

        else
        {
            gPM.setMovementMethod(LinkMovementMethod.getInstance());
            SpannableString ss = new SpannableString("Scan a second code if you'd like us to compare dual-fuel suppliers for you");
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    QRResults.getInstance().performingSecondScan = true;
                    Intent intent = new Intent(SearchResult.this, ScannerFragmentActivity.class);
                    startActivity(intent);
                }
            };
            ss.setSpan(clickableSpan, 0, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            gPC.setVisibility(View.INVISIBLE);
            gSN.setText("No Gas usage found.");
            gTF.setVisibility(View.INVISIBLE);
            gPM.setText(ss);
            gMN.setVisibility(View.INVISIBLE);
            gUsg.setVisibility(View.INVISIBLE);
            gSD.setVisibility(View.INVISIBLE);
        }

        getPricesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Utils.isNetworkAvailable(SearchResult.this)) {
                    makeJsonObjectRequest();
                }
                else {
                    Toast.makeText(getApplicationContext(),"No network available",Toast.LENGTH_SHORT);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!QRResults.getInstance().performingSecondScan) {
            QRResults.getInstance().WipeInstance();
        }
    }

    private void makeJsonObjectRequest() {

        showpDialog();
        String quickly = QRResults.getInstance().GenerateUrlQuickly();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, quickly, (String)null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Boolean successful = response.getBoolean("Successful");
                    if (successful) {
                        String url = response.getString("Url");
//                        Intent i = new Intent(context, WebViewActivity.class);
//                        i.putExtra("url", url);
//                        startActivity(i);
                        goToUrl(url);
                    }
                    else {
                        String serverMessage = response.getString("Message");
                        Toast.makeText(getApplicationContext(), serverMessage, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("asd", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20),
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(jsonObjReq);


    }


    public void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}





