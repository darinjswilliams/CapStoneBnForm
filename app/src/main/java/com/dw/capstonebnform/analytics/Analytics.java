package com.dw.capstonebnform.analytics;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class Analytics {
    public static void logEventScanActivity(Context context,String category){
        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, category);
        FirebaseAnalytics.getInstance(context).logEvent( FirebaseAnalytics.Event.SHARE, params);
    }

    public static void logEventViewDetail(Context context, String viewProductDetail) {
        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.CONTENT_TYPE, viewProductDetail);
        FirebaseAnalytics.getInstance(context).logEvent( FirebaseAnalytics.Event.SHARE, params);
    }
}
