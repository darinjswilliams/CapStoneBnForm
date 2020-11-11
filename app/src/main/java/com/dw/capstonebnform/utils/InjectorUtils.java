package com.dw.capstonebnform.utils;

import android.app.Application;

import com.dw.capstonebnform.network.AppRepository;
import com.dw.capstonebnform.network.UpcRepository;
import com.dw.capstonebnform.persistance.AppExecutors;
import com.dw.capstonebnform.viewModel.SearchRecallViewModelFactory;
import com.dw.capstonebnform.viewModel.SearchViewModelFactory;
import com.dw.capstonebnform.viewModel.SharedViewModelFactory;

public class InjectorUtils {

    private static UpcRepository provideUPCsearchRepository(Application application){
        AppExecutors appThreadExecutor = AppExecutors.getInstance();
//        UPCodeSearchDAO upCodeSearchDAO  = AppDatabase.getsInstance(context).upCodeSearchDAO();
        return UpcRepository.getInstance(application, appThreadExecutor);
    }

    public static SearchViewModelFactory provideSearchViewModelFactory(Application application, String upc){
        UpcRepository upcRepository = provideUPCsearchRepository(application);
        return new SearchViewModelFactory(upcRepository, upc);
    }


    private static AppRepository provideAppRespository(Application application){
        return AppRepository.getInstance(application);
    }


    public static SearchRecallViewModelFactory provideSearchRecallViewModelFactory(Application application, String productName){
        AppRepository appRepository = provideAppRespository(application);
        return new SearchRecallViewModelFactory(appRepository, productName, application);
    }

    public static SharedViewModelFactory provideSharedViewModelFactory(Application application){
        return new SharedViewModelFactory(application);
    }

}
