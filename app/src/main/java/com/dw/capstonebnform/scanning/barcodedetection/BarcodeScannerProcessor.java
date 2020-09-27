

package com.dw.capstonebnform.scanning.barcodedetection;

import android.animation.ValueAnimator;
import android.graphics.RectF;
import android.util.Log;

import com.dw.capstonebnform.camera.GraphicOverlay;
import com.dw.capstonebnform.scanning.camera.FrameProcessorBase;
import com.dw.capstonebnform.scanning.camera.WorkflowModel;
import com.dw.capstonebnform.scanning.camera.WorkflowModel.WorkflowState;
import com.dw.capstonebnform.scanning.utils.PreferenceUtils;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.io.IOException;
import java.util.List;

/** Barcode Detector Demo. */
public class BarcodeScannerProcessor extends FrameProcessorBase<List<FirebaseVisionBarcode>> {

    private final static String TAG = BarcodeScannerProcessor.class.getSimpleName();


    private final FirebaseVisionBarcodeDetector detector =
            FirebaseVision.getInstance().getVisionBarcodeDetector();

    private final WorkflowModel mWorkflowModel;
    private final CameraReticleAnimator mCameraRetilceAnimator;


    public BarcodeScannerProcessor(GraphicOverlay graphicOverlay, WorkflowModel mWorkflowModel) {
        this.mWorkflowModel = mWorkflowModel;
        this.mCameraRetilceAnimator = new CameraReticleAnimator(graphicOverlay);
    }

    @Override
    protected Task<List<FirebaseVisionBarcode>> detectInImage(FirebaseVisionImage image) {

        return detector.detectInImage(image);
    }

    @Override
    protected void onSuccess(FirebaseVisionImage image, List<FirebaseVisionBarcode> results, GraphicOverlay graphicOverlay) {
      if(!mWorkflowModel.isCameraLive()){
          return;
      }

        Log.i(TAG, "onSuccess: Barcode result size"  +  results.size());

        // Picks the barcode, if exists, that covers the center of graphic overlay.
        FirebaseVisionBarcode barcodeInCenter = null;
        for (FirebaseVisionBarcode barcode : results) {
            RectF box = graphicOverlay.translateRect(barcode.getBoundingBox());
            if (box.contains(graphicOverlay.getWidth() / 2f, graphicOverlay.getHeight() / 2f)) {
                barcodeInCenter = barcode;
                break;
            }
        }

        graphicOverlay.clear();
        if (barcodeInCenter == null) {
            mCameraRetilceAnimator.start();
            graphicOverlay.add(new BarcodeReticleGraphic(graphicOverlay, mCameraRetilceAnimator));
            mWorkflowModel.setWorkflowState(WorkflowState.DETECTING);

        } else {
            mCameraRetilceAnimator.cancel();
            float sizeProgress =
                    PreferenceUtils.getProgressToMeetBarcodeSizeRequirement(graphicOverlay, barcodeInCenter);
            if (sizeProgress < 1) {
                // Barcode in the camera view is too small, so prompt user to move camera closer.
                graphicOverlay.add(new BarcodeConfirmingGraphic(graphicOverlay, barcodeInCenter));
                mWorkflowModel.setWorkflowState(WorkflowState.CONFIRMING);

            } else {
                // Barcode size in the camera view is sufficient.
                if (PreferenceUtils.shouldDelayLoadingBarcodeResult(graphicOverlay.getContext())) {
                    ValueAnimator loadingAnimator = createLoadingAnimator(graphicOverlay, barcodeInCenter);
                    loadingAnimator.start();
                    graphicOverlay.add(new BarcodeLoadingGraphic(graphicOverlay, loadingAnimator));
                    mWorkflowModel.setWorkflowState(WorkflowState.SEARCHING);

                } else {
                    mWorkflowModel.setWorkflowState(WorkflowState.DETECTED);
                    mWorkflowModel.detectedBarcode.setValue(barcodeInCenter);
                }
            }
        }
        graphicOverlay.invalidate();
    }

    private ValueAnimator createLoadingAnimator(
            GraphicOverlay graphicOverlay, FirebaseVisionBarcode barcode) {
        float endProgress = 1.1f;
        ValueAnimator loadingAnimator = ValueAnimator.ofFloat(0f, endProgress);
        loadingAnimator.setDuration(2000);
        loadingAnimator.addUpdateListener(
                animation -> {
                    if (Float.compare((float) loadingAnimator.getAnimatedValue(), endProgress) >= 0) {
                        graphicOverlay.clear();
                        mWorkflowModel.setWorkflowState(WorkflowState.SEARCHED);
                        mWorkflowModel.detectedBarcode.setValue(barcode);
                    } else {
                        graphicOverlay.invalidate();
                    }
                });

        return loadingAnimator;
    }

    @Override
    protected void onFailure(Exception e) {
        Log.e(TAG, "onFailure: Barcode detection failed...", e );

    }

    @Override
    public void stop() {
        try {
            detector.close();
        } catch (IOException e) {
            Log.e(TAG, "Failed to close barcode detector!", e);
        }
    }


}



