package com.dw.capstonebnform.adapter;

import com.dw.capstonebnform.view.fragments.HighAlertFragment;
import com.dw.capstonebnform.view.fragments.LowAlertFragment;
import com.dw.capstonebnform.view.fragments.ModerateAlertFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AlertFragmentAdapter extends FragmentStateAdapter {

    public AlertFragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return new LowAlertFragment();
            case 1:
                return new ModerateAlertFragment();
            default:
                return new HighAlertFragment();

        }
    }

    @Override
    public int getItemCount() {
        //return tab count
        return 3;
    }
}
