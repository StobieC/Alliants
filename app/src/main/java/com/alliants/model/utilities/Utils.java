package com.alliants.model.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {
	public static final String SITE_UL = "http://fast-gorge.herokuapp.com/contacts";

	/**
	 * This method takes one parameters and returns true if network is available
	 *
	 * @param context
	 *            set the ConnectivityManager application context
	 * @return boolean
	 * @see NetworkInfo
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

}
