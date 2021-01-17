package com.dw.capstonebnform.network;

import android.app.Application;
import android.util.Log;

import com.dw.capstonebnform.dto.UpcWithOfferItemList;
import com.dw.capstonebnform.persistance.AppDatabase;
import com.dw.capstonebnform.persistance.AppExecutors;
import com.dw.capstonebnform.upc.Item;
import com.dw.capstonebnform.upc.Offer;
import com.dw.capstonebnform.upc.UPCodeSearch;
import com.dw.capstonebnform.upc.UrlList;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpcRepository  {

    private static final String TAG = UpcRepository.class.getSimpleName();

    private static UpcRepository ourInstance;
    private Call<UPCodeSearch> call;
    private Retrofit retrofit;
    private UPCApi mUpcApi;
    private List<UPCodeSearch> mUpCodeSearch = new ArrayList<>();
    Map<Integer, List<Item>> itemMap = new HashMap<>();
    Map<Integer, List<Offer>> offerMap = new HashMap<>();
    Map<Integer, List<UrlList>> urlMap = new HashMap<>();

    private AppDatabase appDatabase;
    private AppExecutors mAppExecutors;

    public static  UpcRepository getInstance(Application application){
        if(ourInstance == null){

            ourInstance = new UpcRepository(application);
        }
        return  ourInstance;
    }

    public UpcRepository(Application application) {

        retrofit = UpcClient.getClient();
        mUpcApi = retrofit.create(UPCApi.class);

        appDatabase = AppDatabase.getsInstance(application);
        mAppExecutors = AppExecutors.getInstance();

    }


    private void lookForUPCProductCode(String upc){

        call = mUpcApi.searchForUPC(upc);

        call.enqueue(new Callback<UPCodeSearch>() {
            @Override
            public void onResponse(@NotNull Call<UPCodeSearch> call, @NotNull Response<UPCodeSearch> response) {
                Log.d(TAG, "onResponse: AppRepository Success");
                if(response.isSuccessful()){

                     UPCodeSearch upcProduct = response.body();
                     itemMap =
                             Stream.of(upcProduct).filter(p -> p.getItems() != null && p.getItems().size() > 0)
                             .collect(Collectors.toMap(UPCodeSearch::getTotal, UPCodeSearch::getItems,
                                     (oldKey, newKey) -> oldKey));

                     //Will always have items, offer and url are child elements in ItemMap
                     if(upcProduct.getItems().get(0).getOffers().size() > 0) {
                         offerMap =
                                 itemMap.entrySet().stream().collect(Collectors.toMap(
                                         x -> x.getKey(), x -> x.getValue().get(0).getOffers(),
                                         (oldKey, newKey) -> oldKey));
                     }


                     if(upcProduct.getItems().get(0).getImages().size() > 0) {
                         urlMap =
                                 itemMap.entrySet().stream().collect(Collectors.toMap(
                                         x -> x.getKey(), x -> x.getValue().get(0).getImages(),
                                         (oldKey, newKey) -> oldKey));
                     }

                    mUpCodeSearch.add(upcProduct);

                    Log.i(TAG, "onResponse: Name" + upcProduct.getItems().get(0).getBrand());
                    Log.i(TAG, "onResponse: UPC" + upcProduct.getItems().get(0).getUpc());

                    //Call Database operartion
                    insertTask();

                }
            }

            @Override
            public void onFailure(@NotNull Call<UPCodeSearch> call, @NotNull Throwable t) {
                Log.d(TAG, "AppRepository: onFailure: " + t.getLocalizedMessage());
                Log.d(TAG, "onFailure: Stack Trace.." + t.getStackTrace());
            }
        });
    }

    private void insertTask(){
        Log.d(TAG, "insertTask: ");
        mAppExecutors.mDbExecutor().execute(new Runnable() {
            @Override
            public void run() {

                //UPC
                if(mUpCodeSearch.size() > 0)
                    appDatabase.upCodeSearchDAO().insertTask(mUpCodeSearch);
                Log.d(TAG, "ProductDAOL Insert:" + mUpCodeSearch.size());

                //ITEM
                if(itemMap.size() > 0)
                appDatabase.itemDAO().insertTask(itemMap);
                Log.d(TAG, "ItemDAO: Insert: " + itemMap.size());

                //Offer
                if(offerMap.size() > 0)
                appDatabase.offerDAO().insertTask(offerMap);
                Log.d(TAG, "OfferDAO: Insert:" + offerMap.values().size() );

                //URL
                if(urlMap.size() > 0)
                appDatabase.urlListDAO().insertTask(urlMap);
                Log.i(TAG, "UrlList: Insert " + urlMap.values().size());;


            }
        });
    }


public LiveData<List<UpcWithOfferItemList>> getUpcWithOfferItems(String upc){
    Log.d(TAG, "getUpcWithOfferItems: ");
    lookForUPCProductCode(upc);
    return appDatabase.upCodeSearchDAO().getUpcWithOfferItemList();
}
}
