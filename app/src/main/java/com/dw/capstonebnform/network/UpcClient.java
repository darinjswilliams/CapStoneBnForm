package com.dw.capstonebnform.network;

import android.util.Log;

import com.dw.capstonebnform.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpcClient {

    private static Retrofit retrofit = null;
    private static final String TAG = UpcClient.class.getSimpleName();

    public static Retrofit getClient() {

        if(null == retrofit){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.UPC_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        Log.d(TAG, "getClient: upc client..." + retrofit);

        return retrofit;
    }
}
