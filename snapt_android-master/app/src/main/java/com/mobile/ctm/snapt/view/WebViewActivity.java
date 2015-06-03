package com.mobile.ctm.snapt.view;

import android.content.Intent;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.mobile.ctm.snapt.R;

/**
 * Created by cameron on 14/05/15.
 */
public class WebViewActivity extends Activity {

private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        mWebView = (WebView) findViewById(R.id.activity_price_webview);

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        Intent intent = getIntent();
        String message = intent.getStringExtra("url");
//String urlParam = "9ba7892a-ef5d-4958-8fdc-3ec0742c69cf";
       // String u =  Uri.encode(urlParam);
      //  String url = "https://www.comparethemarket.com/energy/retrieveresults?ResultsKey="+ u;

        mWebView.loadUrl(message);
    }

}
