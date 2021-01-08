package com.dw.capstonebnform.viewModel;

import android.app.Application;
import android.util.Log;

import com.dw.capstonebnform.dto.RecallWithInjuriesAndImagesAndProducts;
import com.dw.capstonebnform.dto.RecallWithProductsAndImages;
import com.dw.capstonebnform.dto.SearchRecallProducts;
import com.dw.capstonebnform.network.AppRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class LowViewModel extends AndroidViewModel {

    private static final String TAG = LowViewModel.class.getSimpleName();
    private List<RecallWithProductsAndImages> mRecallProductandImages;
    private AppRepository appRepo;
    private LiveData<List<RecallWithInjuriesAndImagesAndProducts>>  mRecallWithInjuriesAndImagesAndProducts;
    private LiveData<List<SearchRecallProducts>> productIsOnRecallList;

    public LowViewModel(@NonNull Application application) {
        super(application);

        appRepo = AppRepository.getInstance(application);
        mRecallWithInjuriesAndImagesAndProducts = appRepo.getRecallWithInjuriesAndImagesAndProducts();
    }

    public LowViewModel(@NonNull  Application application,  String productName) {
        super(application);
        appRepo = AppRepository.getInstance(application);
        Log.d(TAG, "LowViewModel: Here is the RecallDescription.. " + productName);
         productIsOnRecallList = appRepo.retrieveSearchRecallProducts(productName);
    }

    public LiveData<List<RecallWithInjuriesAndImagesAndProducts>> getmRecallWithInjuriesAndImagesAndProducts(){
        return mRecallWithInjuriesAndImagesAndProducts;
    }


    public LiveData<List<SearchRecallProducts>> findRecallProducts(){
        Log.d(TAG, "isProductIsOnRecall: " );
        return productIsOnRecallList;
    }



}
