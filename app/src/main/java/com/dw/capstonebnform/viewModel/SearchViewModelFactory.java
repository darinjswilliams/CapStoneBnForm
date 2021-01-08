package com.dw.capstonebnform.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SearchViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Application application;
    private final String mUpc;


    public SearchViewModelFactory(Application application, String mUpc) {
        this.application = application;
        this.mUpc = mUpc;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SearchUPCViewModel(application, mUpc);
    }
}
