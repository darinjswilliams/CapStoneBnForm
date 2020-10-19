package com.dw.capstonebnform.viewModel;

import com.dw.capstonebnform.network.AppRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SearchRecallViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final AppRepository appRepository;
    private final String productName;
//    private final Application application;

    public SearchRecallViewModelFactory(AppRepository appRepository, String productName) {
//        this.application = application;
        this.appRepository = appRepository;
        this.productName = productName;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LowViewModel(appRepository, productName);
    }
}
