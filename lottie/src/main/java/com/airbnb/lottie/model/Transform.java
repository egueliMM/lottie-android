package com.airbnb.lottie.model;

import android.graphics.Rect;
import android.support.annotation.RestrictTo;

import com.airbnb.lottie.animatable.keyframe.KeyframeAnimatableFloatValue;
import com.airbnb.lottie.animatable.keyframe.KeyframeAnimatableIntegerValue;
import com.airbnb.lottie.animatable.keyframe.KeyframeAnimatablePathValue;
import com.airbnb.lottie.animatable.keyframe.KeyframeAnimatableScaleValue;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public interface Transform {
  Rect getBounds();

  KeyframeAnimatablePathValue getPosition();

  KeyframeAnimatablePathValue getAnchor();

  KeyframeAnimatableScaleValue getScale();

  KeyframeAnimatableFloatValue getRotation();

  KeyframeAnimatableIntegerValue getOpacity();
}