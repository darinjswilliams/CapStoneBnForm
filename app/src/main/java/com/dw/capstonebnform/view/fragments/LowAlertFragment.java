package com.dw.capstonebnform.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dw.capstonebnform.R;
import com.dw.capstonebnform.adapter.LowAlertAdapter;
import com.dw.capstonebnform.databinding.FragmentLowAlertBinding;
import com.dw.capstonebnform.dto.RecallWithInjuriesAndImagesAndProducts;
import com.dw.capstonebnform.utils.InjectorUtils;
import com.dw.capstonebnform.viewModel.LowViewModel;
import com.dw.capstonebnform.viewModel.SharedViewModelFactory;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class LowAlertFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private FragmentLowAlertBinding mFragmentLowAlertBinding;
    private LinearLayoutManager linearLayoutManager;
    private LowAlertAdapter lowAlertAdapter;
    private RecyclerView mRecyclerView;
    private LowViewModel lowViewModel;
    //TODO SET RECALL NUMBER IN SafeArgs
    private NavController navController;
    NavHostFragment navHostFragment;
    FragmentManager fragmentManager;
    private AlertInteractionListener mAlertsListner;


    private static final String TAG = LowAlertFragment.class.getSimpleName();

    public LowAlertFragment() {
        // Required empty public constructor
    }

    public interface AlertInteractionListener {
        void onClickAlert();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(TAG, "onCreateView: ");

        mFragmentLowAlertBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_low_alert, container, false);

        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_container);
        return mFragmentLowAlertBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        initRecyclerView(getContext());

        SharedViewModelFactory sharedViewModelFactory = InjectorUtils.provideSharedViewModelFactory(getActivity().getApplication());
        lowViewModel = new ViewModelProvider(this, sharedViewModelFactory).get(LowViewModel.class);

        lowViewModel.getmRecallWithInjuriesAndImagesAndProducts().observe(getViewLifecycleOwner(), recallProducts -> {
            Log.d(TAG, "onCreateView: size of products return.." + recallProducts.size());
            lowAlertAdapter.setmRecallWithInjuriesAndImagesAndProducts(recallProducts);

        });
    }


    private void onClickAlert(RecallWithInjuriesAndImagesAndProducts rProduct) {

        Log.i(TAG, "onClickAlert: Products " + rProduct.productList.get(0).getNumberOfUnits());
        Log.i(TAG, "onClickAlert: Images " + rProduct.imagesList.get(0).getUrl());
        Log.i(TAG, "onClickAlert: Recall Number " + rProduct.injuriesList.get(0).getName());

        LowAlertFragmentDirections.ActionLowAlertFragmentToRecallDetailFragment action =
                LowAlertFragmentDirections.actionLowAlertFragmentToRecallDetailFragment(rProduct);


        action.setRProduct(rProduct);


        navController.navigate(action);
    }

    private void initRecyclerView(Context context) {
        Log.i(TAG, "initRecyclerView: ");
        linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        lowAlertAdapter = new LowAlertAdapter(this::onClickAlert);


        //Bind Recycler View
        mRecyclerView = mFragmentLowAlertBinding.alertLowRecyclerView;

        //set layout manager on recycler view
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(lowAlertAdapter);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}