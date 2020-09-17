package com.dw.capstonebnform.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dw.capstonebnform.R;
import com.dw.capstonebnform.databinding.FragmentHighAlertBinding;


public class HighAlertFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private FragmentHighAlertBinding mFragmentHighAlertBinding;

    private static final String TAG = HighAlertFragment.class.getSimpleName();

    public HighAlertFragment() {
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
        mFragmentHighAlertBinding = FragmentHighAlertBinding.inflate(inflater, container, false);
        View view = mFragmentHighAlertBinding.getRoot();

        return view;
    }
}