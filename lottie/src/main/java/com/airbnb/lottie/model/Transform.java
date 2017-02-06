package com.airbnb.lottie.model;

import android.graphics.Rect;
import android.support.annotation.RestrictTo;

import com.airbnb.lottie.animatable.keyframe.AnimatableFloatValue;
import com.airbnb.lottie.animatable.keyframe.AnimatableIntegerValue;
import com.airbnb.lottie.animatable.keyframe.AnimatablePathValue;
import com.airbnb.lottie.animatable.keyframe.AnimatableScaleValue;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public interface Transform {
  Rect getBounds();

  AnimatablePathValue getPosition();

  AnimatablePathValue getAnchor();

  AnimatableScaleValue getScale();

  AnimatableFloatValue getRotation();

  AnimatableIntegerValue getOpacity();
}