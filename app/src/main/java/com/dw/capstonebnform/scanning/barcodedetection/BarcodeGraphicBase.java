package com.dw.capstonebnform.scanning.barcodedetection;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

import com.dw.capstonebnform.R;
import com.dw.capstonebnform.camera.GraphicOverlay;
import com.dw.capstonebnform.scanning.utils.PreferenceUtils;

import androidx.core.content.ContextCompat;

public abstract class BarcodeGraphicBase extends GraphicOverlay.Graphic {

    private final Paint boxPaint;
    private final Paint scrimPaint;
    private final Paint eraserPaint;

    final int boxCornerRadius;
    final Paint pathPaint;
    final RectF boxRect;

    BarcodeGraphicBase(GraphicOverlay overlay) {
        super(overlay);

        boxPaint = new Paint();
        boxPaint.setColor(ContextCompat.getColor(context, R.color.barcode_reticle_stroke));
        boxPaint.setStyle(Paint.Style.STROKE);
        boxPaint.setStrokeWidth(
                context.getResources().getDimensionPixelOffset(R.dimen.barcode_reticle_stroke_width));

        scrimPaint = new Paint();
        scrimPaint.setColor(ContextCompat.getColor(context, R.color.barcode_reticle_background));
        eraserPaint = new Paint();
        eraserPaint.setStrokeWidth(boxPaint.getStrokeWidth());
        eraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        boxCornerRadius =
                context.getResources().getDimensionPixelOffset(R.dimen.barcode_reticle_corner_radius);

        pathPaint = new Paint();
        pathPaint.setColor(Color.WHITE);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(boxPaint.getStrokeWidth());
        pathPaint.setPathEffect(new CornerPathEffect(boxCornerRadius));

        boxRect = PreferenceUtils.getBarcodeReticleBox(overlay);
    }

    @Override
    protected void draw(Canvas canvas) {
        // Draws the dark background scrim and leaves the box area clear.
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), scrimPaint);
        // As the stroke is always centered, so erase twice with FILL and STROKE respectively to clear
        // all area that the box rect would occupy.
        eraserPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(boxRect, boxCornerRadius, boxCornerRadius, eraserPaint);
        eraserPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(boxRect, boxCornerRadius, boxCornerRadius, eraserPaint);

        // Draws the box.
        canvas.drawRoundRect(boxRect, boxCornerRadius, boxCornerRadius, boxPaint);
    }
}
