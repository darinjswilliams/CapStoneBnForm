package com.dw.capstonebnform.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dw.capstonebnform.R;
import com.dw.capstonebnform.databinding.FragmentModerateAlertBinding;


public class ModerateAlertFragment extends Fragment {


    private static final String TAG = ModerateAlertFragment.class.getSimpleName();
    private FragmentModerateAlertBinding mFragmentModerateAlertBinding;

    public ModerateAlertFragment() {
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
        mFragmentModerateAlertBinding = FragmentModerateAlertBinding.inflate(inflater, container, false);
        View view = mFragmentModerateAlertBinding.getRoot();

        return view;
    }
}