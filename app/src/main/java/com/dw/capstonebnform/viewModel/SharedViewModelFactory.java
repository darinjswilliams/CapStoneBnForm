package com.dw.capstonebnform.viewModel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SharedViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Context mContext;


    public SharedViewModelFactory(Context context) {

        this.mContext = context;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LowViewModel(mContext);
    }



}
