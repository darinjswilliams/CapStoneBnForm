package com.dw.capstonebnform.network;

import android.app.Application;
import android.util.Log;

import com.dw.capstonebnform.dto.Images;
import com.dw.capstonebnform.dto.Injuries;
import com.dw.capstonebnform.dto.Product;
import com.dw.capstonebnform.dto.Recall;
import com.dw.capstonebnform.dto.RecallWithInjuriesAndImagesAndProducts;
import com.dw.capstonebnform.dto.RecallWithProductsAndImages;
import com.dw.capstonebnform.dto.SearchRecallProducts;
import com.dw.capstonebnform.persistance.AppDatabase;
import com.dw.capstonebnform.persistance.AppExecutors;
import com.dw.capstonebnform.utils.DateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@Keep
public class AppRepository {

    private static final String TAG = AppRepository.class.getSimpleName();

    private static AppRepository ourInstance;
    private RecallApi mRecallApi;
    private Retrofit retrofit;
    private Call<List<Recall>> call;
    private Call<List<SearchRecallProducts>> callSearchProducts;

    //Database Objects
    private List<Product> mProductList = new ArrayList<>();
    private List<Images> mImagesList  = new ArrayList<>();
    private List<Recall> mRecallList = new ArrayList<>();
    private List<SearchRecallProducts> mVerifyRecallProductList = new ArrayList<>();
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
                if(mRecallList.size() > 0) {
                    appDatabase.recallDAO().insertTask(mRecallList);
                    Log.d(TAG, "RecallDAO: Insert: " + mRecallList.size());
                }

                //Images
                if(imageMap.values().size() > 0) {
                    appDatabase.imagesDAO().insertTask(imageMap);
                    Log.d(TAG, "ImagesDAO: Insert:" + imageMap.values().size());
                }

                //Injuries
                if(injuriesMap.values().size() > 0) {
                    appDatabase.injuriesDAO().insertTask(injuriesMap);
                    Log.i(TAG, "InjuriesDAO: Insert " + injuriesMap.values().size());
                }

                //Products
                if(productMap.values().size() > 0) {
                    appDatabase.productsDAO().insertTask(productMap);
                    Log.d(TAG, "ProductDAOL Insert:" + productMap.values().size());
                }

                //SearchRecallProducts
                if(mVerifyRecallProductList.size() > 0){
                    appDatabase.searchRecallProductsDAO().insertTask(mVerifyRecallProductList);
                    Log.d(TAG, "SearchRecallProductsDAO Insert:  " + mVerifyRecallProductList.size());
                }
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



    public LiveData<List<SearchRecallProducts>> retrieveSearchRecallProducts(String description){
        Log.d(TAG, "getRecallWithInjuriesAndImagesAndProducts: ");

        searchRecallWithProductName(description);

        return appDatabase.searchRecallProductsDAO().getSearchProductsByDescription(description);
    }

    public void searchRecallWithProductName(String productName){

        callSearchProducts = mRecallApi.searchForRecallByName(productName);

        callSearchProducts.enqueue(new Callback<List<SearchRecallProducts>>() {
            @Override
            public void onResponse(@NonNull Call<List<SearchRecallProducts>> call, @NonNull Response<List<SearchRecallProducts>> response) {

                if(response.isSuccessful()){

                    List<SearchRecallProducts> searchRecallList = response.body();
                    mVerifyRecallProductList.addAll(searchRecallList);
                    Log.i(TAG, "onResponse: RecallSize: Boolean... " + mVerifyRecallProductList.size() );

                    insertTask();

                }
            }

            @Override
            public void onFailure(Call<List<SearchRecallProducts>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });

    }




}
