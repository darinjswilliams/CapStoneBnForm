package com.dw.capstonebnform.viewModel;

import com.dw.capstonebnform.network.UpcRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SearchViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final UpcRepository upcRepository;
    private final String mUpc;


    public SearchViewModelFactory(UpcRepository upcRepository, String mUpc) {
        this.mUpc = mUpc;
        this.upcRepository = upcRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SearchUPCViewModel(upcRepository, mUpc);
    }
}
