package com.dw.capstonebnform.view.fragments;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dw.capstonebnform.databinding.FragmentScanBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


public class ScanFragment extends Fragment {

    private static final String TAG = ScanFragment.class.getSimpleName();
    FragmentScanBinding mFragmentScanBinding;



    public ScanFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFragmentScanBinding.readBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Using safeargs to pass arguments to fragment
                ScanFragmentDirections.ActionScanFragmentToLiveScanFragment action = ScanFragmentDirections.actionScanFragmentToLiveScanFragment();

                //Check to see if user clicked on autofocus or useflash check boxes
                action.setAutofocus(mFragmentScanBinding.autoFocus.isChecked());
                action.setUseFlash(mFragmentScanBinding.useFlash.isChecked());

                Navigation.findNavController(view).navigate(action);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentScanBinding = FragmentScanBinding.inflate(inflater, container, false);

        View view = mFragmentScanBinding.getRoot();

        return view;
    }

    private void scanBarcodes(InputImage image) {
        // [START set_detector_options]
        BarcodeScannerOptions options =
                new BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                        .build();
        // [END set_detector_options]

        // [START get_detector]
        BarcodeScanner scanner = BarcodeScanning.getClient();
        // Or, to specify the formats to recognize:
        // BarcodeScanner scanner = BarcodeScanning.getClient(options);
        // [END get_detector]

        // [START run_detector]
        Task<List<Barcode>> result = scanner.process(image)
                .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                    @Override
                    public void onSuccess(List<Barcode> barcodes) {
                        // Task completed successfully
                        // [START_EXCLUDE]
                        // [START get_barcodes]
                        for (Barcode barcode: barcodes) {
                            Rect bounds = barcode.getBoundingBox();
                            Point[] corners = barcode.getCornerPoints();

                            String rawValue = barcode.getRawValue();

                            int valueType = barcode.getValueType();
                            // See API reference for complete list of supported types
                            switch (valueType) {
                                case Barcode.TYPE_WIFI:

                                    String ssid = barcode.getWifi().getSsid();
                                    String password = barcode.getWifi().getPassword();
                                    int type = barcode.getWifi().getEncryptionType();

                                    Log.i(TAG, "onSuccess: TYPE WIFI..." + ssid);
                                    break;
                                case Barcode.TYPE_URL:

                                    String title = barcode.getUrl().getTitle();
                                    String url = barcode.getUrl().getUrl();
                                    Log.i(TAG, "onSuccess: TYPE URL.." + url);
                                    break;
                                case Barcode.TYPE_ISBN:
                                     String isbn = barcode.getDisplayValue().toString();
                                     String text = barcode.getRawValue();
                                    Log.i(TAG, "onSuccess: type isbn..." + isbn);
                                    break;
                                default:
                                    String info = barcode.getRawValue();
                                    Log.i(TAG, "onSuccess: Default...  " + info);


                            }
                        }
                        // [END get_barcodes]
                        // [END_EXCLUDE]
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Task failed with an exception
                        // ...
                    }
                });
        // [END run_detector]
    }
}