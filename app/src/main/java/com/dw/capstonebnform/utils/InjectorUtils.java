package com.dw.capstonebnform.utils;

import android.content.Context;

import com.dw.capstonebnform.network.AppRepository;
import com.dw.capstonebnform.network.UpcRepository;
import com.dw.capstonebnform.persistance.AppExecutors;
import com.dw.capstonebnform.viewModel.SearchRecallViewModelFactory;
import com.dw.capstonebnform.viewModel.SearchViewModelFactory;
import com.dw.capstonebnform.viewModel.SharedViewModelFactory;

public class InjectorUtils {

    private static UpcRepository provideUPCsearchRepository(Context context){
        AppExecutors appThreadExecutor = AppExecutors.getInstance();
//        UPCodeSearchDAO upCodeSearchDAO  = AppDatabase.getsInstance(context).upCodeSearchDAO();
        return UpcRepository.getInstance(context, appThreadExecutor);
    }

    public static SearchViewModelFactory provideSearchViewModelFactory(Context context, String upc){
        UpcRepository upcRepository = provideUPCsearchRepository(context.getApplicationContext());
        return new SearchViewModelFactory(upcRepository, upc);
    }


    private static AppRepository provideAppRespository(Context context){
        return AppRepository.getInstance(context.getApplicationContext());
    }


    public static SearchRecallViewModelFactory provideSearchRecallViewModelFactory(Context context, String productName){
        AppRepository appRepository = provideAppRespository(context.getApplicationContext());
        return new SearchRecallViewModelFactory(appRepository, productName);
    }

    public static SharedViewModelFactory provideSharedViewModelFactory(Context context){
        return new SharedViewModelFactory(context.getApplicationContext());
    }

}
