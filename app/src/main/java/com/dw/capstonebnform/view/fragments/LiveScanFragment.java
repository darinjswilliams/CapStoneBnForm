package com.dw.capstonebnform.view.fragments;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.SearchManager;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private String gUrl = "http://www.google.com/";


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

        return mFragmentLiveScanBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mPreview = mFragmentLiveScanBinding.cameraPreviewId;

        mFragmentLiveScanBinding.cameraPreviewGraphicOverlay.setOnClickListener(this);

        mFragmentLiveScanBinding.cameraPreviewGraphicOverlay.setOnClickListener(this::onClick);

        mCameraSource = new CameraSource(mFragmentLiveScanBinding.cameraPreviewGraphicOverlay);

        mPromptChipAnimator =
                (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.bottom_prompt_chip_enter);
        mPromptChipAnimator.setTarget(mFragmentLiveScanBinding.bottomPromptChip);

        mFragmentLiveScanBinding.scanActionBarTop.closeButton.setOnClickListener(this::onClick);

        mFragmentLiveScanBinding.scanActionBarTop.flashButton.setOnClickListener(this::onClick);

        mFragmentLiveScanBinding.scanActionBarTop.settingsButton.setOnClickListener(this::onClick);

//        mFragmentLiveScanBinding.scanActionBarTop.fabButton.setOnClickListener(this::onClick);onClick


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

            case R.id.close_button:
                //todo Call onBackPressed
                Log.i(TAG, "onClick: close button");
                break;

            case R.id.settings_button:
                mFragmentLiveScanBinding.scanActionBarTop.settingsButton.setEnabled(false);
                //Todo Create settings fragment
                Log.i(TAG, "onClick: setting button");

                break;

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
                    ArrayList<BarcodeField> barcodeFieldList = new ArrayList<>();
                    barcodeFieldList.add(new BarcodeField(getResources().getString(R.string.product_upc), barcode.getRawValue()));
                    BarcodeResultFragment.show(getParentFragmentManager(), barcodeFieldList);
//                    searchWeb(gUrl + barcode.getRawValue());
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
        mWorkflowModel.markCameraFrozen();
        mFragmentLiveScanBinding.scanActionBarTop.settingsButton.setEnabled(true);
        mWorkflowState = WorkflowState.NOT_STARTED;
        mCameraSource.setFrameProcessor(new BarcodeScannerProcessor(mFragmentLiveScanBinding.cameraPreviewGraphicOverlay, mWorkflowModel));
        mWorkflowModel.setWorkflowState(WorkflowState.DETECTING);
    }

    @Override
    public void onPause() {
        super.onPause();
        mWorkflowState = WorkflowState.NOT_STARTED;
        stopCameraPreview();
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
