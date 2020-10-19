package com.dw.capstonebnform.viewModel;

import android.content.Context;

import com.dw.capstonebnform.dto.RecallWithInjuriesAndImagesAndProducts;
import com.dw.capstonebnform.dto.RecallWithProductsAndImages;
import com.dw.capstonebnform.network.AppRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class LowViewModel extends ViewModel {

    private static final String TAG = LowViewModel.class.getSimpleName();
    private List<RecallWithProductsAndImages> mRecallProductandImages;
    private AppRepository appRepo;
    private LiveData<List<RecallWithInjuriesAndImagesAndProducts>>  mRecallWithInjuriesAndImagesAndProducts;
    private boolean productIsOnRecall;

    public LowViewModel(@NonNull Context context) {

        appRepo = AppRepository.getInstance(context.getApplicationContext());
        mRecallWithInjuriesAndImagesAndProducts = appRepo.getRecallWithInjuriesAndImagesAndProducts();
    }

    public LowViewModel(@NonNull AppRepository appRepo, String productName) {
        this.appRepo = appRepo;
         productIsOnRecall = appRepo.searchRecallWithProductName(productName);
    }

    public LiveData<List<RecallWithInjuriesAndImagesAndProducts>> getmRecallWithInjuriesAndImagesAndProducts(){
        return mRecallWithInjuriesAndImagesAndProducts;
    }


    public boolean isProductIsOnRecall(){
        return productIsOnRecall;
    }

}
