package com.dw.capstonebnform.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dw.capstonebnform.adapter.LowAlertAdapter;
import com.dw.capstonebnform.databinding.FragmentLowAlertBinding;
import com.dw.capstonebnform.dto.RecallWithInjuriesAndImagesAndProducts;
import com.dw.capstonebnform.viewModel.LowViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class LowAlertFragment extends Fragment implements LowAlertAdapter.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    private FragmentLowAlertBinding mFragmentLowAlertBinding;
    private LinearLayoutManager linearLayoutManager;
    private LowAlertAdapter lowAlertAdapter;
    private RecyclerView mRecyclerView;
    private LowViewModel lowViewModel;

    private static final String TAG = LowAlertFragment.class.getSimpleName();

    public LowAlertFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        mFragmentLowAlertBinding = FragmentLowAlertBinding.inflate(inflater, container, false);

        return mFragmentLowAlertBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initRecyclerView(getContext());

        lowViewModel = new ViewModelProvider(this).get(LowViewModel.class);


        lowViewModel.getmRecallWithInjuriesAndImagesAndProducts().observe(getViewLifecycleOwner(), recallProducts -> {
            Log.d(TAG, "onCreateView: size of products return.." + recallProducts.size());
            lowAlertAdapter.setmRecallWithInjuriesAndImagesAndProducts(recallProducts);
//          lowAlertAdapter.setRecallWithProductsAndImages(lowViewModel.getMockDummyData());
        });
    }

    private void initRecyclerView(Context context) {
        Log.i(TAG, "initRecyclerView: ");
        linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        lowAlertAdapter = new LowAlertAdapter(this);


        //Bind Recycler View
        mRecyclerView = mFragmentLowAlertBinding.alertLowRecyclerView;

        //set layout manager on recycler view
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(lowAlertAdapter);


    }

    @Override
    public void onClick(RecallWithInjuriesAndImagesAndProducts mRecallWithInjuriesAndImagesAndProducts) {
        Log.i(TAG, "onClick: ");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}