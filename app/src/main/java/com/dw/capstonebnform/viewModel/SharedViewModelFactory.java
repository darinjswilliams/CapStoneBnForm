package com.dw.capstonebnform.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SharedViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Application application;


    public SharedViewModelFactory(Application application) {

        this.application = application;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LowViewModel(application);
    }



}
