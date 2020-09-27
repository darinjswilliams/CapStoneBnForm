package com.dw.capstonebnform.scanning.camera;

import android.hardware.Camera;

import com.google.android.gms.common.images.Size;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CameraSizePair {
    public final Size preview;
    @Nullable
    public final Size picture;

    public CameraSizePair(@NonNull Camera.Size previewSize, @Nullable Camera.Size pictureSize) {
        preview = new Size(previewSize.width, previewSize.height);
        picture = pictureSize != null ? new Size(pictureSize.width, pictureSize.height) : null;
    }

    public CameraSizePair(Size previewSize, @Nullable Size pictureSize) {
        preview = previewSize;
        picture = pictureSize;
    }
}
