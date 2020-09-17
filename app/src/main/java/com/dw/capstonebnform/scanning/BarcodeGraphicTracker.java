package com.dw.capstonebnform.scanning;

import android.content.Context;

import com.dw.capstonebnform.camera.GraphicOverlay;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

import androidx.annotation.UiThread;

public class BarcodeGraphicTracker extends Tracker<Barcode> {

    private GraphicOverlay<BarcodeGraphic> mGrapicOverlay;
    private BarcodeGraphic mBarcodeGraphic;
    private BarcodeUpdateListener mBarcodeUpdateListener;


    //detect item, Ordinarily, an app's UI thread is also the main thread. However,
    // under special circumstances, an app's UI thread might not be its main thread
    public interface  BarcodeUpdateListener {
        @UiThread
        void onBarcodeDetected(Barcode barcode);
    }

    BarcodeGraphicTracker(GraphicOverlay<BarcodeGraphic> mOverlay, BarcodeGraphic mGraphic,
                          Context context) {
        this.mGrapicOverlay = mOverlay;
        this.mBarcodeGraphic = mGraphic;
        if (context instanceof BarcodeUpdateListener) {
            this.mBarcodeUpdateListener = (BarcodeUpdateListener) context;
        } else {
            throw new RuntimeException("Hosting activity must implement BarcodeUpdateListener");
        }
    }

    /**
     * Start tracking the detected item instance within the item overlay.
     */
    @Override
    public void onNewItem(int id, Barcode item) {
        mBarcodeGraphic.setId(id);
        mBarcodeUpdateListener.onBarcodeDetected(item);
    }

    /**
     * Update the position/characteristics of the item within the overlay.
     */
    @Override
    public void onUpdate(Detector.Detections<Barcode> detectionResults, Barcode item) {
        mGrapicOverlay.add(mBarcodeGraphic);
        mBarcodeGraphic.updateItem(item);
    }

    /**
     * Hide the graphic when the corresponding object was not detected.  This can happen for
     * intermediate frames temporarily, for example if the object was momentarily blocked from
     * view.
     */
    @Override
    public void onMissing(Detector.Detections<Barcode> detectionResults) {
        mGrapicOverlay.remove(mBarcodeGraphic);
    }

    /**
     * Called when the item is assumed to be gone for good. Remove the graphic annotation from
     * the overlay.
     */
    @Override
    public void onDone() {
        mGrapicOverlay.remove(mBarcodeGraphic);
    }
}
