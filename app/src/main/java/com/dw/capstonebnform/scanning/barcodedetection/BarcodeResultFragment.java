package com.dw.capstonebnform.scanning.barcodedetection;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dw.capstonebnform.R;
import com.dw.capstonebnform.analytics.Analytics;
import com.dw.capstonebnform.databinding.BarcodeBottomSheetBinding;
import com.dw.capstonebnform.dto.UpcWithOfferItemList;
import com.dw.capstonebnform.scanning.camera.WorkflowModel;
import com.dw.capstonebnform.utils.InjectorUtils;
import com.dw.capstonebnform.viewModel.LowViewModel;
import com.dw.capstonebnform.viewModel.SearchRecallViewModelFactory;
import com.dw.capstonebnform.viewModel.SearchUPCViewModel;
import com.dw.capstonebnform.viewModel.SearchViewModelFactory;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

public class BarcodeResultFragment extends BottomSheetDialogFragment {

    private static final String TAG = BarcodeResultFragment.class.getSimpleName();

    private static final String ARG_BARCODE_FIELD_LIST = "arg_barcode_field_list";

    private BarcodeBottomSheetBinding barcodeBottomSheetBinding;

    private SearchUPCViewModel mSearchUPCViewModel;

    private LowViewModel mSearchLowViewModel;

    private String barCodeUpcNumber;

    private List<UpcWithOfferItemList> searchRecallWithProductNameList = new ArrayList<>();

    private String productName;

    public static void show(
            FragmentManager fragmentManager, ArrayList<BarcodeField> barcodeFieldArrayList) {
        BarcodeResultFragment barcodeResultFragment = new BarcodeResultFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ARG_BARCODE_FIELD_LIST, barcodeFieldArrayList);
        barcodeResultFragment.setArguments(bundle);
        barcodeResultFragment.show(fragmentManager, TAG);
    }

    public static void dismiss(FragmentManager fragmentManager) {
        BarcodeResultFragment barcodeResultFragment =
                (BarcodeResultFragment) fragmentManager.findFragmentByTag(TAG);
        if (barcodeResultFragment != null) {
            barcodeResultFragment.dismiss();
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater layoutInflater,
            @Nullable ViewGroup viewGroup,
            @Nullable Bundle bundle) {

        barcodeBottomSheetBinding = DataBindingUtil.inflate(layoutInflater, R.layout.barcode_bottom_sheet, viewGroup, false);

        ArrayList<BarcodeField> barcodeFieldList;
        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(ARG_BARCODE_FIELD_LIST)) {
            barcodeFieldList = arguments.getParcelableArrayList(ARG_BARCODE_FIELD_LIST);
            barCodeUpcNumber = barcodeFieldList.get(0).value.toString();
        } else {
            Log.e(TAG, "No barcode field list passed in!");
            barcodeFieldList = new ArrayList<>();
        }


        barcodeBottomSheetBinding.barcodeFieldRecyclerView.setHasFixedSize(true);
        barcodeBottomSheetBinding.barcodeFieldRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        barcodeBottomSheetBinding.barcodeFieldRecyclerView.setAdapter(new BarcodeFieldAdapter(barcodeFieldList));

        barcodeBottomSheetBinding.fabBottomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseApplicationToShareContext(barcodeFieldList);
            }
        });

        SearchViewModelFactory searchViewModelFactory = InjectorUtils.provideSearchViewModelFactory(getContext(), barCodeUpcNumber);

        mSearchUPCViewModel = new ViewModelProvider(this, searchViewModelFactory).get(SearchUPCViewModel.class);

        mSearchUPCViewModel.getmUPCwithOfferItemListLiveData().observe(this, upCode -> {

//            Log.d(TAG, "onCreateView: Search UPC size.." + upCode.size());
//            Log.d(TAG, "onCreateView: Brand Name..." + upCode.get(0).itemList.get(0).getBrand() );
            searchRecallWithProductNameList = upCode.stream().filter(upc -> upc.itemList.size() > 0).collect(Collectors.toList());
//            searchRecallWithProductName = upCode.get(0).itemList.get(0).getBrand();
        });

        if(searchRecallWithProductNameList.size() > 0){
            productName = searchRecallWithProductNameList.get(0).itemList.get(0).getBrand();
        }

        SearchRecallViewModelFactory searchRecallViewModelFactory = InjectorUtils.provideSearchRecallViewModelFactory(getContext(), productName);
        mSearchLowViewModel = new ViewModelProvider(this, searchRecallViewModelFactory).get(LowViewModel.class);

        Log.i(TAG, "onCreateView: IS PRODUCT ON RECALL" + mSearchLowViewModel.isProductIsOnRecall() );
        if(mSearchLowViewModel.isProductIsOnRecall()){
            Log.i(TAG, "onCreateView: PRODUCT IS NOT ON RECALL SHOW HAPPY FACE");
            barcodeBottomSheetBinding.happyEmoji.setVisibility(View.VISIBLE);
            barcodeBottomSheetBinding.sadEmoji.setVisibility(View.GONE);
        } else {
            Log.i(TAG, "onCreateView: PRODUCT IS ON RECALL SHOW SAD FACE");
            barcodeBottomSheetBinding.happyEmoji.setVisibility(View.GONE);
            barcodeBottomSheetBinding.sadEmoji.setVisibility(View.VISIBLE);
        }

        return barcodeBottomSheetBinding.getRoot();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialogInterface) {
        if (getActivity() != null) {
            // Back to working state after the bottom sheet is dismissed.
            new ViewModelProvider(getActivity()).get(WorkflowModel.class)
                    .setWorkflowState(WorkflowModel.WorkflowState.DETECTING);
        }
        super.onDismiss(dialogInterface);
    }

    private void chooseApplicationToShareContext(ArrayList<BarcodeField> barcodeFields) {

        //Log Analytics sharing screen content
        Analytics.logEventScanActivity(getContext(), getResources().getString(R.string.shareContent));

        ShareCompat.IntentBuilder.from(getActivity())
                .setChooserTitle(getResources().getString(R.string.app_name))
                .setType(getResources().getString(R.string.mime_type))
                .setText(barcodeFields.get(0).label.toString() + ": " + barcodeFields.get(0).value.toString())
                .startChooser();

    }

    private void searchWeb(String query) {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, query);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }


}
