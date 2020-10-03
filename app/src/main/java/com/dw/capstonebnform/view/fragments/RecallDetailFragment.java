package com.dw.capstonebnform.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dw.capstonebnform.R;
import com.dw.capstonebnform.databinding.FragmentRecallDetailBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


public class RecallDetailFragment extends Fragment {

    private final static String TAG = RecallDetailFragment.class.getSimpleName();
    FragmentRecallDetailBinding mfragmentRecallDetailBinding;
    private String recallNumber;

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
            recallNumber = RecallDetailFragmentArgs.fromBundle(getArguments()).getRecallNumber();
            Log.i(TAG, "onViewCreated: " + recallNumber);
        }
    }
}