package com.dw.capstonebnform.viewModel;

import com.dw.capstonebnform.dto.UpcWithOfferItemList;
import com.dw.capstonebnform.network.UpcRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class SearchUPCViewModel extends ViewModel {
    public static String TAG = SearchUPCViewModel.class.getName();
    private LiveData<List<UpcWithOfferItemList>> mUPCwithOfferItemListLiveData;
    private UpcRepository upcRepository;

    public SearchUPCViewModel(UpcRepository upcRepository, String upc) {

        mUPCwithOfferItemListLiveData = upcRepository.getUpcWithOfferItems(upc);

    }

    public LiveData<List<UpcWithOfferItemList>> getmUPCwithOfferItemListLiveData(){
        return mUPCwithOfferItemListLiveData;
    }
}
