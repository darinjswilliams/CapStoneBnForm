package com.dw.capstonebnform.utils;

import android.app.Application;

import com.dw.capstonebnform.network.AppRepository;
import com.dw.capstonebnform.viewModel.SearchRecallViewModelFactory;
import com.dw.capstonebnform.viewModel.SearchViewModelFactory;
import com.dw.capstonebnform.viewModel.SharedViewModelFactory;

public class InjectorUtils {

//    private static UpcRepository provideUPCsearchRepository(Application application){
//        AppExecutors appThreadExecutor = AppExecutors.getInstance();
//        return UpcRepository.getInstance(application, appThreadExecutor);
//    }

    public static SearchViewModelFactory provideSearchViewModelFactory(Application application, String upc){
//        UpcRepository upcRepository = provideUPCsearchRepository(application);
        return new SearchViewModelFactory(application, upc);
    }


    private static AppRepository provideAppRespository(Application application){
        return AppRepository.getInstance(application);
    }


    public static SearchRecallViewModelFactory provideSearchRecallViewModelFactory(Application application, String productName){
        AppRepository appRepository = provideAppRespository(application);
        return new SearchRecallViewModelFactory(application, productName);
    }

    public static SharedViewModelFactory provideSharedViewModelFactory(Application application){
        return new SharedViewModelFactory(application);
    }

}
