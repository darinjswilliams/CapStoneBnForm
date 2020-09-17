package com.dw.capstonebnform.view.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dw.capstonebnform.camera.CameraSource;
import com.dw.capstonebnform.camera.CameraSourcePreview;
import com.dw.capstonebnform.camera.GraphicOverlay;
import com.dw.capstonebnform.databinding.FragmentLiveScanBinding;
import com.dw.capstonebnform.scanning.BarcodeGraphic;
import com.dw.capstonebnform.scanning.BarcodeGraphicTracker;
import com.dw.capstonebnform.scanning.BarcodeTrackerFactory;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


public class LiveScanFragment extends Fragment implements BarcodeGraphicTracker.BarcodeUpdateListener {

    private static final int REQUEST_CAMERA = 2;
    private FragmentLiveScanBinding mFragmentLiveScanBinding;
    private static final String TAG = LiveScanFragment.class.getSimpleName();
    private GraphicOverlay<BarcodeGraphic> mGraphicOverlay;
    private CameraSource mCameraSource;
    private CameraSourcePreview mPreview;
    boolean autoFocus = false;
    boolean useFlash = false;

    // helper objects for detecting taps and pinches.
    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;


    public LiveScanFragment() {
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
        mFragmentLiveScanBinding = FragmentLiveScanBinding.inflate(inflater, container, false);

        View view = mFragmentLiveScanBinding.getRoot();

        mPreview = mFragmentLiveScanBinding.cameraPreviewId;

        mGraphicOverlay = mFragmentLiveScanBinding.cameraPreviewGraphicOverlay;

        gestureDetector = new GestureDetector(getContext(), new CaptureGestureListener());
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                boolean gd = gestureDetector.onTouchEvent(motionEvent);
                boolean sg = scaleGestureDetector.onTouchEvent(motionEvent);


                return gd || sg || true;
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //Get arguments passed in from SafeArgs
        this.autoFocus = LiveScanFragmentArgs.fromBundle(getArguments()).getAutofocus();
        this.useFlash = LiveScanFragmentArgs.fromBundle(getArguments()).getUseFlash();

        Log.i(TAG, "onViewCreated: AutoFocus..." + autoFocus);
        Log.i(TAG, "onViewCreated: UseFlash...." + useFlash);



        showCamera(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        startCameraSource();
    }

    private void startCameraSource() throws SecurityException {
        if (mCameraSource != null) {
            try {

                mPreview.startCamera(mCameraSource, mGraphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mPreview != null){
            mPreview.stopCamera();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPreview != null){
            mPreview.releaseCamera();
        }
    }



    public void showCamera(View view){

        //lets check permission
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            //ShowCamera preview
            showCameraPreview();
        } else {
            //Todo add snackbar
            if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                Toast.makeText(getContext(), "Camera Permission is needed to show camera preview.", Toast.LENGTH_SHORT).show();
            }

            //Todo request permissiond
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }
    }



    public void showCameraPreview(){

        Log.i(TAG, "showCameraPreview: Permission was granted");
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(getContext()).build();

        // A barcode detector is created to track barcodes.  An associated multi-processor instance
        // is set to receive the barcode detection results, track the barcodes, and maintain
        // graphics for each barcode on screen.  The factory is used by the multi-processor to
        // create a separate tracker instance for each barcode.
        BarcodeTrackerFactory barcodeTrackerFactory = new BarcodeTrackerFactory(mGraphicOverlay, getContext());

        barcodeDetector.setProcessor(new MultiProcessor.Builder<>(barcodeTrackerFactory).build());

        // Creates and starts the camera.  Note that this uses a higher resolution in comparison
        // to other detection examples to enable the barcode detector to detect small barcodes
        // at long distances.
        CameraSource.Builder builder = new CameraSource.Builder(getContext(), barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1600, 1024)
                .setRequestedFps(15.0f);

        // make sure that auto focus is an available option
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            builder = builder.setFocusMode(
                    autoFocus ? Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE : null);
        }

        mCameraSource = builder.setFlashMode(useFlash ? Camera.Parameters.FLASH_MODE_TORCH : null)
                .build();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        Log.i(TAG, "onRequestPermissionsResult: ");
        if(requestCode == REQUEST_CAMERA){

            //Check if only required permission has been granted
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                showCameraPreview();
            } else {
                Toast.makeText(getContext(), "Permission was not granted", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }


    @Override
    public void onBarcodeDetected(Barcode barcode) {

    }

    private boolean onTap(float rawX, float rawY) {
        // Find tap point in preview frame coordinates.
        Log.i(TAG, "onTap: ");
        int[] location = new int[2];
        mGraphicOverlay.getLocationOnScreen(location);
        float x = (rawX - location[0]) / mGraphicOverlay.getWidthScaleFactor();
        float y = (rawY - location[1]) / mGraphicOverlay.getHeightScaleFactor();

        // Find the barcode whose center is closest to the tapped point.
        Barcode best = null;
        float bestDistance = Float.MAX_VALUE;
        for (BarcodeGraphic graphic : mGraphicOverlay.getGraphics()) {
            Barcode barcode = graphic.getBarcode();
            if (barcode.getBoundingBox().contains((int) x, (int) y)) {
                // Exact hit, no need to keep looking.
                best = barcode;
                break;
            }
            float dx = x - barcode.getBoundingBox().centerX();
            float dy = y - barcode.getBoundingBox().centerY();
            float distance = (dx * dx) + (dy * dy);  // actually squared distance
            if (distance < bestDistance) {
                best = barcode;
                bestDistance = distance;
            }
        }

        if (best != null) {

            return true;
        }
        return false;
    }

    private class CaptureGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.i(TAG, "onSingleTapConfirmed: ");
            return onTap(e.getRawX(), e.getRawY()) || super.onSingleTapConfirmed(e);
        }
    }

    private class ScaleListener implements ScaleGestureDetector.OnScaleGestureListener {

        /**
         * Responds to scaling events for a gesture in progress.
         * Reported by pointer motion.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         * @return Whether or not the detector should consider this event
         * as handled. If an event was not handled, the detector
         * will continue to accumulate movement until an event is
         * handled. This can be useful if an application, for example,
         * only wants to update scaling factors if the change is
         * greater than 0.01.
         */
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            return false;
        }

        /**
         * Responds to the beginning of a scaling gesture. Reported by
         * new pointers going down.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         * @return Whether or not the detector should continue recognizing
         * this gesture. For example, if a gesture is beginning
         * with a focal point outside of a region where it makes
         * sense, onScaleBegin() may return false to ignore the
         * rest of the gesture.
         */
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        /**
         * Responds to the end of a scale gesture. Reported by existing
         * pointers going up.
         * <p/>
         * Once a scale has ended, {@link ScaleGestureDetector#getFocusX()}
         * and {@link ScaleGestureDetector#getFocusY()} will return focal point
         * of the pointers remaining on the screen.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         */
        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            mCameraSource.doZoom(detector.getScaleFactor());
        }

    }
}
