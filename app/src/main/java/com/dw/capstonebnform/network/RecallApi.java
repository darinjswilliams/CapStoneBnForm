package com.dw.capstonebnform.network;

import com.dw.capstonebnform.dto.Recall;
import com.dw.capstonebnform.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

//Retrofit Data feed
public interface RecallApi {

    @GET(Constants.RECALL_FORMAT)
    Call<List<Recall>> getRecallProducts();

    @GET(Constants.RECALL_BY_DATE)
    Call<List<Recall>> getRecallProductsByDate();

    @GET(Constants.RECALL_BY_PRODUCTS)
    Call<List<Recall>> getProductsAndImages();
}


