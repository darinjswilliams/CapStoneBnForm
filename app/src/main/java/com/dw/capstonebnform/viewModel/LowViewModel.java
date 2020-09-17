package com.dw.capstonebnform.viewModel;

import android.app.Application;
import android.util.Log;

import com.dw.capstonebnform.dto.Images;
import com.dw.capstonebnform.dto.Product;
import com.dw.capstonebnform.dto.Recall;
import com.dw.capstonebnform.dto.RecallWithInjuriesAndImagesAndProducts;
import com.dw.capstonebnform.dto.RecallWithProductsAndImages;
import com.dw.capstonebnform.network.AppRepository;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class LowViewModel extends AndroidViewModel {

    private static final String TAG = LowViewModel.class.getSimpleName();
    private List<RecallWithProductsAndImages> mRecallProductandImages;
    private AppRepository appRepo;
    private LiveData<List<RecallWithInjuriesAndImagesAndProducts>>  mRecallWithInjuriesAndImagesAndProducts;

    public LowViewModel(@NonNull Application application) {

        super(application);
        appRepo = AppRepository.getInstance(this.getApplication());
        mRecallWithInjuriesAndImagesAndProducts = appRepo.getRecallWithInjuriesAndImagesAndProducts();

//        mRecallProductandImages = getMockDummyData();
    }


    public LiveData<List<RecallWithInjuriesAndImagesAndProducts>> getmRecallWithInjuriesAndImagesAndProducts(){
        return mRecallWithInjuriesAndImagesAndProducts;
    }


    //mock data
    public List<RecallWithProductsAndImages> getMockDummyData(){

        Log.i(TAG, "entering getMockDummyData: ");
        List<RecallWithProductsAndImages> mockData = new ArrayList<>();

        RecallWithProductsAndImages mockRecallWithProductsAndImages = RecallWithProductsAndImages.builder().imagesList(retrieveMockImages()).productList(retrieveMockProduct()).recall(retreiveMockRecall()).build();

        mockData.add(mockRecallWithProductsAndImages);
        Log.i(TAG, "getMockDummyData: size.." + mockData.size() );
        Log.i(TAG, "exiting getMockDummyData: ");



        return mockData;
    }

    private List<Images> retrieveMockImages(){
        List<Images>  mockImages = new ArrayList<>();

        Images mImageItem = Images.builder().recallId(88).url("https://www.cpsc.gov/s3fs-public/RecalledPier1ImportsThreeWickcandleswithrespectiveSKUnumbers_image2.JPG").build();

        mockImages.add(mImageItem);

        return mockImages;
    }


    private List<Product> retrieveMockProduct(){

        List<Product> mockProduct = new ArrayList<>();

        Product mProductItem = Product.builder().categoryId("Test").description("Test data Test data").model("TestData").name("test data test data").numberOfUnits("test data").recallId(88).build();

        mockProduct.add(mProductItem);

        return mockProduct;
    }

    private Recall retreiveMockRecall(){

        String dummyData = "Recall Data";

        return Recall.builder().mRecallDate(dummyData).mConsumerContact(dummyData).mDescription(dummyData).mLastPublishDate(dummyData).mSoldAtLabel(dummyData).mTitle(dummyData).recallId(88)
                .mUrl(dummyData).recallNumber(dummyData).build();

    }
}
