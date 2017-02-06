package com.airbnb.lottie.model;

import android.graphics.PointF;
import android.graphics.Rect;
import android.support.annotation.RestrictTo;

import com.airbnb.lottie.animatable.AnimatableValue;
import com.airbnb.lottie.utils.ScaleXY;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public interface Transform {
  Rect getBounds();

  AnimatableValue<PointF> getPosition();

  AnimatableValue<PointF> getAnchor();

  AnimatableValue<ScaleXY> getScale();

  AnimatableValue<Float> getRotation();

  AnimatableValue<Integer> getOpacity();
}