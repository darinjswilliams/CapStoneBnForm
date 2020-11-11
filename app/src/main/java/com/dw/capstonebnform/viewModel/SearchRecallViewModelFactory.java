package com.dw.capstonebnform.viewModel;

import android.app.Application;

import com.dw.capstonebnform.network.AppRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SearchRecallViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final AppRepository appRepository;
    private final String productName;
    private final Application application;

    public SearchRecallViewModelFactory(AppRepository appRepository, String productName, Application application) {
        this.appRepository = appRepository;
        this.productName = productName;
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LowViewModel(appRepository, productName, application);
    }
}
