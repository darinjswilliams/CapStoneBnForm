package com.dw.capstonebnform.view.fragments;

import android.Manifest;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dw.capstonebnform.R;
import com.dw.capstonebnform.analytics.Analytics;
import com.dw.capstonebnform.camera.CameraSource;
import com.dw.capstonebnform.camera.CameraSourcePreview;
import com.dw.capstonebnform.camera.GraphicOverlay;
import com.dw.capstonebnform.databinding.FragmentLiveScanBinding;
import com.dw.capstonebnform.scanning.barcodedetection.BarcodeField;
import com.dw.capstonebnform.scanning.barcodedetection.BarcodeResultFragment;
import com.dw.capstonebnform.scanning.barcodedetection.BarcodeScannerProcessor;
import com.dw.capstonebnform.scanning.camera.WorkflowModel;
import com.dw.capstonebnform.scanning.camera.WorkflowModel.WorkflowState;
import com.google.android.material.chip.Chip;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


public class LiveScanFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = LiveScanFragment.class.getSimpleName();
    private static final int REQUEST_CAMERA = 2;
    private FragmentLiveScanBinding mFragmentLiveScanBinding;

    private GraphicOverlay mGgraphicOverlay;
    private CameraSource mCameraSource;
    private CameraSourcePreview mPreview;
    private Chip mPrompChip;
    private AnimatorSet mPromptChipAnimator;
    private WorkflowModel mWorkflowModel;
    private WorkflowState mWorkflowState;

    boolean autoFocus = false;
    boolean useFlash = false;
    private View settingsButton;
    private View flashButton;


    public LiveScanFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mFragmentLiveScanBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_live_scan, container, false);

        checkPermission();

        return mFragmentLiveScanBinding.getRoot();
    }


    private void defaultSetUp() {

        mWorkflowModel = new ViewModelProvider(this).get(WorkflowModel.class);

        mPreview = mFragmentLiveScanBinding.cameraPreviewId;

        mFragmentLiveScanBinding.cameraPreviewGraphicOverlay.setOnClickListener(this);

        mFragmentLiveScanBinding.cameraPreviewGraphicOverlay.setOnClickListener(this::onClick);

        mCameraSource = new CameraSource(mFragmentLiveScanBinding.cameraPreviewGraphicOverlay);

        mPromptChipAnimator =
                (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.bottom_prompt_chip_enter);
        mPromptChipAnimator.setTarget(mFragmentLiveScanBinding.bottomPromptChip);

        mFragmentLiveScanBinding.scanActionBarTop.flashButton.setOnClickListener(this::onClick);

        setUpWorkflowModel();
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.flash_button:
                Log.i(TAG, "onClick: FlashButton");
                if (mFragmentLiveScanBinding.scanActionBarTop.flashButton.isSelected()) {
                    mFragmentLiveScanBinding.scanActionBarTop.flashButton.setSelected(false);
                    //TODO UDPATE FLASHMODEL to off
                    mCameraSource.updateFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                } else {
                    mFragmentLiveScanBinding.scanActionBarTop.flashButton.setSelected(true);
                    //TODO UDPATE FLASHMODEL to on
                    mCameraSource.updateFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                }

                break;

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // The permission was granted! Start up the camera!

                    mWorkflowModel = new ViewModelProvider(this).get(WorkflowModel.class);

                    defaultSetUp();

                } else {
                    //Camera permission was denied
                    Toast.makeText(getActivity(), getResources().getString(R.string.no_camera_permission), Toast.LENGTH_SHORT).show();
                    mFragmentLiveScanBinding.scanActionBarTop.flashButton.setVisibility(View.INVISIBLE);

                }
            }
            // Other permissions could go down here

        }
    }

    private void checkPermission() {

        //check if the user has already granted your app a particular permission
        // If we don't have the camera permission...
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // And if we're on SDK M or later...
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // Ask again, nicely, for the permissions.
                String[] permissionsWeNeed = new String[]{Manifest.permission.CAMERA};
                requestPermissions(permissionsWeNeed, REQUEST_CAMERA);
            }
        } else {
            // Otherwise, permissions were granted and we are ready to go!
            defaultSetUp();

        }


    }

    private void setUpWorkflowModel() {

        Log.i(TAG, "setUpWorkflowModel: Setup workflow");

        mWorkflowModel = new ViewModelProvider(this).get(WorkflowModel.class);
        mWorkflowModel.workflowState.observe(
                getViewLifecycleOwner(),
                workflowState -> {
                    if (workflowState == null || Objects.equals(mWorkflowState, workflowState)) {
                        return;
                    }
                    mWorkflowState = workflowState;
                    Log.i(TAG, "CurrentWorkflow state setUpWorkflowModel: " + mWorkflowState.name());

                    boolean wasPrompChipGone = (mFragmentLiveScanBinding.bottomPromptChip.getVisibility() == View.GONE);

                    switch (workflowState) {
                        case DETECTING:
                            mFragmentLiveScanBinding.bottomPromptChip.setVisibility(View.VISIBLE);
                            mFragmentLiveScanBinding.bottomPromptChip.setText(R.string.prompt_point_at_a_barcode);
                            startCameraPreview();
                            break;
                        case CONFIRMING:
                            mFragmentLiveScanBinding.bottomPromptChip.setVisibility(View.VISIBLE);
                            mFragmentLiveScanBinding.bottomPromptChip.setText(R.string.prompt_move_camera_closer);
                            startCameraPreview();
                            break;
                        case SEARCHING:
                            mFragmentLiveScanBinding.bottomPromptChip.setVisibility(View.VISIBLE);
                            mFragmentLiveScanBinding.bottomPromptChip.setText(R.string.prompt_searching);
                            stopCameraPreview();
                            break;
                        case DETECTED:
                        case SEARCHED:
                            mFragmentLiveScanBinding.bottomPromptChip.setVisibility(View.GONE);
                            stopCameraPreview();
                            break;
                        default:
                            mFragmentLiveScanBinding.bottomPromptChip.setVisibility(View.GONE);
                            break;
                    }

                    boolean shouldPlayPromptChipEnteringAnimation =
                            wasPrompChipGone && (mFragmentLiveScanBinding.bottomPromptChip.getVisibility() == View.VISIBLE);
                    if (shouldPlayPromptChipEnteringAnimation && !mPromptChipAnimator.isRunning()) {
                        mPromptChipAnimator.start();
                    }
                }
        );

        mWorkflowModel.detectedBarcode.observe(

                getViewLifecycleOwner(),
                barcode -> {
                    mFragmentLiveScanBinding.scanActionBarTop.flashButton.setVisibility(View.INVISIBLE);
                    ArrayList<BarcodeField> barcodeFieldList = new ArrayList<>();
                    barcodeFieldList.add(new BarcodeField(getResources().getString(R.string.product_upc), barcode.getRawValue()));
                    BarcodeResultFragment.show(getParentFragmentManager(), barcodeFieldList);

                    //Send Scanned Product Event to Firebase Analytics
                    Analytics.logEventScanActivity(getContext(), getResources().getString(R.string.scanned));
                }
        );

    }

    private void startCameraPreview() {
        if (!mWorkflowModel.isCameraLive() && mCameraSource != null) {
            try {
                mWorkflowModel.markCameraLive();
                mPreview.start(mCameraSource);
            } catch (IOException e) {
                Log.e(TAG, "Failed to start camera preview!", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

    private void stopCameraPreview() {
        if (mWorkflowModel.isCameraLive()) {
            mWorkflowModel.markCameraFrozen();
            mFragmentLiveScanBinding.scanActionBarTop.flashButton.setSelected(false);
            mPreview.stopCamera();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWorkflowModel != null) {
            checkPermission();
            mWorkflowModel.markCameraFrozen();
            mWorkflowState = WorkflowState.NOT_STARTED;
            mCameraSource.setFrameProcessor(new BarcodeScannerProcessor(mFragmentLiveScanBinding.cameraPreviewGraphicOverlay, mWorkflowModel));
            mWorkflowModel.setWorkflowState(WorkflowState.DETECTING);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mWorkflowModel != null) {
            mWorkflowState = WorkflowState.NOT_STARTED;
            stopCameraPreview();
        }

    }

    @Override
    public void onDestroy() {
        if (mCameraSource != null) {
            mCameraSource.release();
            mCameraSource = null;
        }


        if (getView() != null) {
            ViewGroup parent = (ViewGroup) getView().getParent();
            parent.removeAllViews();
        }
        super.onDestroy();
    }


}
