package com.dw.capstonebnform.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dw.capstonebnform.R;
import com.dw.capstonebnform.adapter.AlertFragmentAdapter;
import com.dw.capstonebnform.databinding.FragmentAlertInfoBinding;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

//Testing
public class AlertInfoFragment extends Fragment {

   private static final String TAG = AlertInfoFragment.class.getSimpleName();

   ViewPager2 viewPager;
   FragmentAlertInfoBinding mFragmentAlertInfoBinding;
   AlertFragmentAdapter mAlertFragmentAdapter;

    public AlertInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG, "onCreateView: ");
        mFragmentAlertInfoBinding = FragmentAlertInfoBinding.inflate(inflater, container, false);
        View view = mFragmentAlertInfoBinding.getRoot();

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i(TAG, "onViewCreated: ");
        //Set Adapter to pager
        mAlertFragmentAdapter = new AlertFragmentAdapter(this);
        mFragmentAlertInfoBinding.pager.setAdapter(mAlertFragmentAdapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                mFragmentAlertInfoBinding.tabLayout, mFragmentAlertInfoBinding.pager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position){
                            case 0:
                                tab.setText(getResources().getString(R.string.low_alert));
                                break;
                            case 1:
                                tab.setText(getResources().getString(R.string.moderate_alert));
                                break;
                            case 2:
                                tab.setText(getResources().getString(R.string.high_alert));
                                break;
                        }
                    }
                }
        );

        tabLayoutMediator.attach();

        mFragmentAlertInfoBinding.pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                BadgeDrawable badgeDrawable = mFragmentAlertInfoBinding.tabLayout.getTabAt(position)
                        .getOrCreateBadge();
                badgeDrawable.setVisible(false);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFragmentAlertInfoBinding = null;
    }
}