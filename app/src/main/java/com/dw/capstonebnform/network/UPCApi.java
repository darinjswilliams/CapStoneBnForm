package com.dw.capstonebnform.network;

import com.dw.capstonebnform.upc.UPCodeSearch;
import com.dw.capstonebnform.utils.Constants;

import androidx.annotation.Keep;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

@Keep
public interface UPCApi {

    @GET(Constants.UPC_LOOKUP)
    Call<UPCodeSearch> searchForUPC( @Query("upc") String upc );
}
