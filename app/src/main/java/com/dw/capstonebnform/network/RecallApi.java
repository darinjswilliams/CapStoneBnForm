package com.dw.capstonebnform.network;

import com.dw.capstonebnform.dto.Recall;
import com.dw.capstonebnform.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//Retrofit Data feed
public interface RecallApi {

    @GET(Constants.RECALL_FORMAT)
    Call<List<Recall>> getRecallProducts();

    @GET(Constants.RECALL_BY_DATE)
    Call<List<Recall>> getRecallProductsByDate(@Query("RecallDateStart") String recallStartDate,
                                               @Query("RecallEndDate") String recallEndDate);


    @GET(Constants.RECALL_BY_PRODUCTS)
    Call<List<Recall>> getProductsAndImages();

    @GET(Constants.SEARCH_RECALL_BY_NAME)
    Call<List<Recall>> searchForRecallByName(@Query("ProductName") String productName);
}


