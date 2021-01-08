package com.dw.capstonebnform.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SearchRecallViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final String productName;
    private final Application application;

    public SearchRecallViewModelFactory(Application application, String productName) {
        this.productName = productName;
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LowViewModel(application, productName);
    }
}
