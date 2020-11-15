package com.dw.capstonebnform.network;

import android.app.Application;
import android.util.Log;

import com.dw.capstonebnform.dto.Images;
import com.dw.capstonebnform.dto.Injuries;
import com.dw.capstonebnform.dto.Product;
import com.dw.capstonebnform.dto.Recall;
import com.dw.capstonebnform.dto.RecallWithInjuriesAndImagesAndProducts;
import com.dw.capstonebnform.dto.RecallWithProductsAndImages;
import com.dw.capstonebnform.persistance.AppDatabase;
import com.dw.capstonebnform.persistance.AppExecutors;
import com.dw.capstonebnform.utils.DateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AppRepository {

    private static final String TAG = AppRepository.class.getSimpleName();

    private static AppRepository ourInstance;
    private RecallApi mRecallApi;
    private Retrofit retrofit;
    private Call<List<Recall>> call;

    //Database Objects
    private List<Product> mProductList = new ArrayList<>();
    private List<Images> mImagesList  = new ArrayList<>();
    private List<Recall> mRecallList = new ArrayList<>();
    Map<Integer, List<Product>> productMap = new HashMap<>();
    Map<Integer, List<Images>> imageMap = new HashMap<>();
    Map<Integer, List<Injuries>> injuriesMap = new HashMap<>();



    private AppDatabase appDatabase;
    private AppExecutors appExecutors;


    public static  AppRepository getInstance(Application application){
        if(ourInstance == null){
            ourInstance = new AppRepository(application);
        }
        return  ourInstance;
    }

    private AppRepository(Application application){
        retrofit = RetroFitClient.getClient();
        mRecallApi = retrofit.create(RecallApi.class);
        //TODO add appDatabase and appExececutor
        appDatabase = AppDatabase.getsInstance(application);
        appExecutors = AppExecutors.getInstance();
    }


    //** Retrofit Operations

    public void loadRecallProductsFromWeb(){

        //Parse with retrofit
        call = mRecallApi.getRecallProductsByDate(DateUtils.getSixMonthsBeforeCurrentDate(), DateUtils.getCurrentDateWithFormatYYYYMMDD());

        //Place in background thread
        call.enqueue(new Callback<List<Recall>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recall>> call, @NonNull Response<List<Recall>> response) {

                Log.d(TAG, "onResponse: AppRepository Success");
                if(response.isSuccessful()){

                    List<Recall> recallList = response.body();

                    //Stream Products make sure to avoid collection of key objects
                     productMap =
                         recallList.stream().filter( p -> p.getMProducts()!= null && p.getMProducts().size() > 0).collect(Collectors.toMap(Recall::getRecallId, Recall::getMProducts,
                                 (oldKey, newKey) -> oldKey));

                     //Stream Images
                     imageMap =
                            recallList.stream().filter( x -> x.getMImage() != null && x.getMImage().size() > 0 ).collect(Collectors.toMap(Recall::getRecallId, Recall::getMImage,
                                    (oldKey, newKey) -> oldKey));

                     //Stream Injuries
                     injuriesMap =
                             recallList.stream().filter(x -> x.getMInjuries() != null && x.getMInjuries().size() > 0).collect(Collectors.toMap(Recall::getRecallId, Recall::getMInjuries,
                                     (oldKey, newKey) -> oldKey));

                      //Populate class variables
                     mRecallList.addAll(recallList);

                     //Call database Operation
                     insertTask();
                }

            }

            @Override
            public void onFailure(Call<List<Recall>> call, Throwable t) {
                Log.d(TAG, "AppRepository: onFailure: " + t.getLocalizedMessage());
                Log.d(TAG, "onFailure: Stack Trace.." + t.getStackTrace());
            }
        });
    }

    //Insert Objects into Database
    public void insertTask(){
        Log.d(TAG, "insertTask: ");
        appExecutors.mDbExecutor().execute(new Runnable() {
            @Override
            public void run() {

                //Recall List
                appDatabase.recallDAO().insertTask(mRecallList);
                Log.d(TAG, "RecallDAO: Insert: " + mRecallList.size());
                //Images
                appDatabase.imagesDAO().insertTask(imageMap);
                Log.d(TAG, "ImagesDAO: Insert:" + imageMap.values().size() );

                //Injuries
                appDatabase.injuriesDAO().insertTask(injuriesMap);
                Log.i(TAG, "InjuriesDAO: Insert " + injuriesMap.values().size());;
                //Products
                appDatabase.productsDAO().insertTask(productMap);
                Log.d(TAG, "ProductDAOL Insert:" + productMap.values().size());
            }
        });
    }


    //RecalWithProductsAndImages
    public LiveData<List<RecallWithProductsAndImages>> getRecallWithProductsAndImages(int recallId){
        Log.d(TAG, "getRecallWithProductsAndImages: ");
        return appDatabase.recallDAO().getRecallWithProductsAndImagess(recallId);
    }

    public LiveData<List<RecallWithInjuriesAndImagesAndProducts>> getRecallWithInjuriesAndImagesAndProducts(){
        Log.d(TAG, "getRecallWithInjuriesAndImagesAndProducts: ");

        loadRecallProductsFromWeb();
        return appDatabase.recallDAO().getRecallWithInjuriesAndImagesAndProducts();
    }

    public boolean searchRecallWithProductName(String productName){

        List<Recall> verifyRecallProductList = new ArrayList<>();
        call = mRecallApi.searchForRecallByName(productName);

        call.enqueue(new Callback<List<Recall>>() {
            @Override
            public void onResponse(Call<List<Recall>> call, Response<List<Recall>> response) {

                if(response.isSuccessful()){

                    List<Recall> recallList = response.body();
                    verifyRecallProductList.addAll(recallList);
                    Log.i(TAG, "onResponse: RecallSize: Boolean... " + verifyRecallProductList.size() );


                }
            }

            @Override
            public void onFailure(Call<List<Recall>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });

        //Checks if an array of Objects is empty or null.
        return  verifyRecallProductList.isEmpty();

    }




}
