package com.dw.capstonebnform.scanning.camera;

public class FrameMetadata {
    final int width;
    final int height;
    final int rotation;

  public FrameMetadata(int width, int height, int rotation) {
        this.width = width;
        this.height = height;
        this.rotation = rotation;
    }
}
