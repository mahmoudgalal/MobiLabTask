/*
 * Copyright (c) 2019 Mahmoud Galal.
 * Mahmoudgalal57@yahoo.com
 */

package com.mahmoud_galal.mobilabtask.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {


    /**
     * Returns whether there is a valid internet connection or no connection
     *
     * @param context
     * @return true if there is internet connection false otherwise
     */
    public static boolean isInternetConnectionExist(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
