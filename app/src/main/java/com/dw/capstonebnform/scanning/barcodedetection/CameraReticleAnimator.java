package com.dw.capstonebnform.scanning.barcodedetection;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;

import com.dw.capstonebnform.camera.GraphicOverlay;

import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

public class CameraReticleAnimator {

    private static final long DURATION_RIPPLE_FADE_IN_MS = 333;
    private static final long DURATION_RIPPLE_FADE_OUT_MS = 500;
    private static final long DURATION_RIPPLE_EXPAND_MS = 833;
    private static final long DURATION_RIPPLE_STROKE_WIDTH_SHRINK_MS = 833;
    private static final long DURATION_RESTART_DORMANCY_MS = 1333;
    private static final long START_DELAY_RIPPLE_FADE_OUT_MS = 667;
    private static final long START_DELAY_RIPPLE_EXPAND_MS = 333;
    private static final long START_DELAY_RIPPLE_STROKE_WIDTH_SHRINK_MS = 333;
    private static final long START_DELAY_RESTART_DORMANCY_MS = 1167;

    private float rippleAlphaScale = 0f;
    private float rippleSizeScale = 0f;
    private float rippleStrokeWidthScale = 1f;

    private final AnimatorSet animatorSet;

    public CameraReticleAnimator(GraphicOverlay graphicOverlay) {
        ValueAnimator rippleFadeInAnimator =
                ValueAnimator.ofFloat(0f, 1f).setDuration(DURATION_RIPPLE_FADE_IN_MS);
        rippleFadeInAnimator.addUpdateListener(
                animation -> {
                    rippleAlphaScale = (float) animation.getAnimatedValue();
                    graphicOverlay.postInvalidate();
                });

        ValueAnimator rippleFadeOutAnimator =
                ValueAnimator.ofFloat(1f, 0f).setDuration(DURATION_RIPPLE_FADE_OUT_MS);
        rippleFadeOutAnimator.setStartDelay(START_DELAY_RIPPLE_FADE_OUT_MS);
        rippleFadeOutAnimator.addUpdateListener(
                animation -> {
                    rippleAlphaScale = (float) animation.getAnimatedValue();
                    graphicOverlay.postInvalidate();
                });

        ValueAnimator rippleExpandAnimator =
                ValueAnimator.ofFloat(0f, 1f).setDuration(DURATION_RIPPLE_EXPAND_MS);
        rippleExpandAnimator.setStartDelay(START_DELAY_RIPPLE_EXPAND_MS);
        rippleExpandAnimator.setInterpolator(new FastOutSlowInInterpolator());
        rippleExpandAnimator.addUpdateListener(
                animation -> {
                    rippleSizeScale = (float) animation.getAnimatedValue();
                    graphicOverlay.postInvalidate();
                });

        ValueAnimator rippleStrokeWidthShrinkAnimator =
                ValueAnimator.ofFloat(1f, 0.5f).setDuration(DURATION_RIPPLE_STROKE_WIDTH_SHRINK_MS);
        rippleStrokeWidthShrinkAnimator.setStartDelay(START_DELAY_RIPPLE_STROKE_WIDTH_SHRINK_MS);
        rippleStrokeWidthShrinkAnimator.setInterpolator(new FastOutSlowInInterpolator());
        rippleStrokeWidthShrinkAnimator.addUpdateListener(
                animation -> {
                    rippleStrokeWidthScale = (float) animation.getAnimatedValue();
                    graphicOverlay.postInvalidate();
                });

        ValueAnimator fakeAnimatorForRestartDelay =
                ValueAnimator.ofInt(0, 0).setDuration(DURATION_RESTART_DORMANCY_MS);
        fakeAnimatorForRestartDelay.setStartDelay(START_DELAY_RESTART_DORMANCY_MS);

        animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                rippleFadeInAnimator,
                rippleFadeOutAnimator,
                rippleExpandAnimator,
                rippleStrokeWidthShrinkAnimator,
                fakeAnimatorForRestartDelay);
    }

    /** Returns the scale value of ripple alpha ranges in [0, 1]. */
    public float getRippleAlphaScale() {
        return rippleAlphaScale;
    }

    /** Returns the scale value of ripple size ranges in [0, 1]. */
    public float getRippleSizeScale() {
        return rippleSizeScale;
    }

    /** Returns the scale value of ripple stroke width ranges in [0, 1]. */
    public float getRippleStrokeWidthScale() {
        return rippleStrokeWidthScale;
    }

    public void start() {
        if (!animatorSet.isRunning()) {
            animatorSet.start();
        }
    }

    public void cancel() {
        animatorSet.cancel();
        rippleAlphaScale = 0f;
        rippleSizeScale = 0f;
        rippleStrokeWidthScale = 1f;
    }
}
