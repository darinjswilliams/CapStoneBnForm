package com.dw.capstonebnform.utils;

import android.content.Context;

import com.dw.capstonebnform.network.UpcRepository;
import com.dw.capstonebnform.persistance.AppExecutors;
import com.dw.capstonebnform.viewModel.SearchViewModelFactory;

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

}
