package com.mobile.ctm.snapt.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class Utils {


    public static final String Quickly = "https://energy.dev.internal.comparethemarket.com/Energy/Qr/Quickly";
    public static final String ScanFail = "https://energy.dev.internal.comparethemarket.com/Energy/Qr/ScanningFailure";

//    public static final String CTM_ENERGY_TEST = "http://10.0.2.2:8085/Energy/Qr/Quickly?postcode=pe25hq&lecSupplier=&lecTariff=&lecPaymentMethod=&electricityUsage=&gasSupplier=Zog+Energy&gasTariff=Mercury+12&gasPaymentMethod=1&gasUsage=12345&nightPercentage=33.5";

    public static final String Postcode_Parameter ="?postcode=";
    public static final String Electricity_Supplier_Name_Parameter ="&lecSupplier=";
    public static final String Electricity_Tariff_Name_Parameter ="&lecTariff=";
    public static final String Electricity_Payment_Method_Parameter ="&lecPaymentMethod=";
    public static final String Electricity_Usage_Parameter ="&lecUsage=";
    public static final String Gas_Supplier_Name_Parameter ="&gasSupplier=";
    public static final String Gas_Tariff_Name_Parameter ="&gasTariff=";
    public static final String Gas_Payment_Method_Parameter ="&gasPaymentMethod=";
    public static final String Gas_Usage_Parameter ="&gasUsage=";
    public static final String Night_Percentage_Parameter ="&lecUsageTwo=";
    public static final String Email_Parameter ="&email=";
    public static final String Marketing_Allowed_Parameter ="&marketingAllowed=";
    public static final String Meter_Reference_Parameter ="&meterReference=";
    public static final String Platform_Parameter ="&platform=";
    public static final String Android_Platform ="Android";

    public static final String Platform_Parameter_ScanFailure ="?platform=";
    public static final String Meter_Parameter ="&meter=";
    public static final String Message_Parameter ="?message=";
    public static final String Data_Parameter ="?data=";

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




}
