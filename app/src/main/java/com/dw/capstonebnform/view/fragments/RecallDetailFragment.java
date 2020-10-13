package com.dw.capstonebnform.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dw.capstonebnform.R;
import com.dw.capstonebnform.analytics.Analytics;
import com.dw.capstonebnform.databinding.FragmentRecallDetailBinding;
import com.dw.capstonebnform.dto.RecallWithInjuriesAndImagesAndProducts;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


public class RecallDetailFragment extends Fragment {

    private final static String TAG = RecallDetailFragment.class.getSimpleName();
    FragmentRecallDetailBinding mfragmentRecallDetailBinding;
    private String recallNumber;
    RecallWithInjuriesAndImagesAndProducts rProduct;


    public RecallDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mfragmentRecallDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recall_detail, container, false);

        return mfragmentRecallDetailBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null) {
            rProduct = RecallDetailFragmentArgs.fromBundle(getArguments()).getRProduct();
            Log.i(TAG, "onViewCreated: " + rProduct.recall.getRecallNumber());

            //Log Analytics
            Analytics.logEventViewDetail(getContext(), getResources().getString(R.string.viewproduct));

            mfragmentRecallDetailBinding.recallId.setText(rProduct.recall.getRecallNumber());
            mfragmentRecallDetailBinding.descriptionId.setText(rProduct.recall.getMDescription());
            mfragmentRecallDetailBinding.unitSoldId.setText(rProduct.productList.get(0).getNumberOfUnits());

            if(rProduct.imagesList.get(0).getUrl() != null){
                Picasso.get()
                        .load(rProduct.imagesList.get(0).getUrl())
                        .into(mfragmentRecallDetailBinding.imageDetailId);

            }
        }
    }
}