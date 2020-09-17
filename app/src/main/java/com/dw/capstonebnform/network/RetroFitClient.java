package com.dw.capstonebnform.network;

import android.util.Log;

import com.dw.capstonebnform.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitClient {

    private static Retrofit retrofit = null;
    private static final String TAG = RetroFitClient.class.getSimpleName();

    public static Retrofit getClient() {

        if(null == retrofit){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.RECALL_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        Log.d(TAG, "getClient: retrofit client..." + retrofit);

        return retrofit;
    }
}
