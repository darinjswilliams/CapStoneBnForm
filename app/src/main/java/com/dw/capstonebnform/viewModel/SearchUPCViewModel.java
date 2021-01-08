package com.dw.capstonebnform.viewModel;

import android.app.Application;

import com.dw.capstonebnform.dto.UpcWithOfferItemList;
import com.dw.capstonebnform.network.UpcRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class SearchUPCViewModel extends ViewModel {
    public static String TAG = SearchUPCViewModel.class.getName();
    private LiveData<List<UpcWithOfferItemList>> mUPCwithOfferItemListLiveData;
    private UpcRepository upcRepository;

    public SearchUPCViewModel(Application application, String upc) {

        upcRepository = UpcRepository.getInstance(application);
        mUPCwithOfferItemListLiveData = upcRepository.getUpcWithOfferItems(upc);

    }

    public LiveData<List<UpcWithOfferItemList>> getmUPCwithOfferItemListLiveData(){
        return mUPCwithOfferItemListLiveData;
    }
}
