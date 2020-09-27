package com.dw.capstonebnform.scanning.barcodedetection;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dw.capstonebnform.R;
import com.dw.capstonebnform.scanning.camera.WorkflowModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BarcodeResultFragment extends BottomSheetDialogFragment {

    private static final String TAG = BarcodeResultFragment.class.getSimpleName();

    private static final String ARG_BARCODE_FIELD_LIST = "arg_barcode_field_list";

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
        View view = layoutInflater.inflate(R.layout.barcode_bottom_sheet, viewGroup);
        ArrayList<BarcodeField> barcodeFieldList;
        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(ARG_BARCODE_FIELD_LIST)) {
            barcodeFieldList = arguments.getParcelableArrayList(ARG_BARCODE_FIELD_LIST);
        } else {
            Log.e(TAG, "No barcode field list passed in!");
            barcodeFieldList = new ArrayList<>();
        }

        RecyclerView fieldRecyclerView = view.findViewById(R.id.barcode_field_recycler_view);
        fieldRecyclerView.setHasFixedSize(true);
        fieldRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fieldRecyclerView.setAdapter(new BarcodeFieldAdapter(barcodeFieldList));

        return view;
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
}
