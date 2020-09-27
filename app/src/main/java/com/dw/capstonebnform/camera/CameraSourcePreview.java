package com.dw.capstonebnform.camera;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;

import com.dw.capstonebnform.R;
import com.dw.capstonebnform.scanning.utils.Utils;
import com.google.android.gms.common.images.Size;

import java.io.IOException;

import androidx.annotation.NonNull;

public class CameraSourcePreview extends FrameLayout {
    private static final String TAG = CameraSourcePreview.class.getSimpleName();

    private GraphicOverlay mGraphicOverLay;
    private Context mContext;
    private SurfaceView mSurfaceView;
    private boolean mStartRequested = false;
    private boolean mSurfaceAvailable = false;
    private CameraSource mCameraSource;
    private Size mCameraPreviewSize;

    public CameraSourcePreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext  = context;
        this.mSurfaceView = new SurfaceView(context);

        //Extracted to Class
       mSurfaceView.getHolder().addCallback(new SurfaceCallback());
       addView(mSurfaceView);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mGraphicOverLay = findViewById(R.id.camera_preview_graphic_overlay);
    }


    public void start(CameraSource cameraSource) throws IOException{
        this.mCameraSource = cameraSource;
        mStartRequested = true;
        checkToSeeIfCameraIsReady();
    }

    public void stopCamera() {
        if(mCameraSource != null){
            mCameraSource.stop();
            mCameraSource = null;
            mStartRequested = false;
        }
    }

    private void checkToSeeIfCameraIsReady() throws IOException {
        if(mStartRequested && mSurfaceAvailable) {
           mCameraSource.start(mSurfaceView.getHolder());

           if(mGraphicOverLay != null){
                 mGraphicOverLay.setCameraInfo(mCameraSource);
                 mGraphicOverLay.clear();
               }
            mStartRequested = false;
           }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int width = right - left;
        int height = bottom - top;

        if(mCameraSource != null && mCameraSource.getPreviewSize() != null){
            mCameraPreviewSize = mCameraSource.getPreviewSize();
        }

        float previewAspectRatio = (float) width / height;

        if (mCameraPreviewSize != null) {
            if (Utils.isPortraitMode(getContext())) {
                previewAspectRatio = (float) mCameraPreviewSize.getHeight() / mCameraPreviewSize.getWidth();
            } else {
                previewAspectRatio = (float) mCameraPreviewSize.getWidth() / mCameraPreviewSize.getHeight();
            }
        }


        int childWidth = width;
        int childHeight = (int) (childWidth / previewAspectRatio);
        if(childHeight <= height){
            for(int i = 0; i < getChildCount(); ++i){
                getChildAt(i).layout(0,0,childWidth, childHeight);
            }

        } else {

            int excessLenInHalf = (childHeight - height) / 2;
            for (int i = 0; i < getChildCount(); ++i) {

                View childView = getChildAt(i);
                if (childView.getId() == R.id.static_overlay_container) {
                    childView.layout(0, 0, childWidth, childHeight);
                } else {
                    childView.layout(0, -excessLenInHalf, childWidth, height + excessLenInHalf);
                }
            }
        }

        try {
            checkToSeeIfCameraIsReady();
        } catch (SecurityException sec) {
            Log.e(TAG, "onLayout: SecurityException...do not have permission.. ", sec);
        }catch (IOException ioe) {
            Log.e(TAG, "onLayout: IOEception...camera could not start", ioe );
        }
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

}
