package com.dw.capstonebnform.camera;

import android.Manifest;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import com.google.android.gms.common.images.Size;


import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;

public class CameraSourcePreview extends ViewGroup {
    private static final String TAG = CameraSourcePreview.class.getSimpleName();

    private GraphicOverlay mGraphicOverLay;
    private Context mContext;
    private SurfaceView mSurfaceView;
    private boolean mStartRequested;
    private boolean mSurfaceAvailable;
    private CameraSource mCameraSource;

    public CameraSourcePreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext  = context;
        this.mSurfaceAvailable = false;
        this.mStartRequested = false;
        this.mSurfaceView = new SurfaceView(context);

        //Extracted to Class
       mSurfaceView.getHolder().addCallback(new SurfaceCallback());
       addView(mSurfaceView);
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    public void startCamera(CameraSource cameraSource) throws IOException, SecurityException{

        if(cameraSource.equals(null)){
            stopCamera();
        }

        mCameraSource = cameraSource;

        if(mCameraSource != null){
            mStartRequested = true;
            checkToSeeIfCameraIsReady();
        }
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    public void startCamera(CameraSource cameraSource, GraphicOverlay graphicOverlay) throws IOException, SecurityException {
        mGraphicOverLay = graphicOverlay;
        startCamera(cameraSource);
    }


    public void stopCamera() {
        if(mCameraSource != null){
            mCameraSource.stop();
        }
    }

    public void releaseCamera(){
        if(mCameraSource != null){
            mCameraSource.release();
            mCameraSource = null;
        }
        mSurfaceView.getHolder().getSurface().release();
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    private void checkToSeeIfCameraIsReady() throws IOException {
        if(mStartRequested && mSurfaceAvailable) {
           mCameraSource.start(mSurfaceView.getHolder());

           if(mGraphicOverLay != null){
               Size size = mCameraSource.getPreviewSize();
               int min = Math.min(size.getWidth(), size.getHeight());
               int max = Math.max(size.getWidth(), size.getHeight());
               if (determineOrientation()) {
                   mGraphicOverLay.setCameraInfo(min, max, mCameraSource.getCameraFacing());
               } else {
                   mGraphicOverLay.setCameraInfo(max, min, mCameraSource.getCameraFacing());
               }
                mGraphicOverLay.clear();
           }
           mStartRequested = false;
        }
    }

    private boolean determineOrientation(){
        int orientation = mContext.getResources().getConfiguration().orientation;
        Log.d(TAG, "determineOrientation: Is phone Orientation LandScape..." + orientation);

       return orientation == Configuration.ORIENTATION_LANDSCAPE ? false : true;
    }


    private class SurfaceCallback implements  SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

                mSurfaceAvailable = true;

            try {
                checkToSeeIfCameraIsReady();
            } catch (SecurityException sec) {
                Log.e(TAG, "surfaceCreated: No Permission to start Camera " + sec.getLocalizedMessage() );
            } catch (IOException ioe) {
                Log.e(TAG, "surfaceCreated: Camera did not start " + ioe.getLocalizedMessage());
            }
        }

        @Override
        public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
            mSurfaceAvailable = false;
        }
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int width = 320;
        int height = 240;

        if(mCameraSource != null){
            Size size = mCameraSource.getPreviewSize();

            if(size != null){
                width = size.getWidth();
                height = size.getHeight();
            }
        }

        //determine orientation
        if(determineOrientation()){
            int temp = width;
            width = height;
            height = temp;
        }


        float previewAspectRatio = (float) width / height;
        int layoutWidth = right - left;
        int layoutHeight = bottom - top;
        float layoutAspectRatio = (float) layoutWidth / layoutHeight;
        if (previewAspectRatio > layoutAspectRatio) {
            // The preview input is wider than the layout area. Fit the layout height and crop
            // the preview input horizontally while keep the center.
            int horizontalOffset = (int) (previewAspectRatio * layoutHeight - layoutWidth) / 2;
            mSurfaceView.layout(-horizontalOffset, 0, layoutWidth + horizontalOffset, layoutHeight);
        } else {
            // The preview input is taller than the layout area. Fit the layout width and crop the preview
            // input vertically while keep the center.
            int verticalOffset = (int) (layoutWidth / previewAspectRatio - layoutHeight) / 2;
            mSurfaceView.layout(0, -verticalOffset, layoutWidth, layoutHeight + verticalOffset);
        }

        try {
            checkToSeeIfCameraIsReady();
        } catch (SecurityException sec) {
            Log.e(TAG, "onLayout: SecurityException...do not have permission.. ", sec);
        }catch (IOException ioe) {
            Log.e(TAG, "onLayout: IOEception...camera could not start", ioe );
        }


    }
}
