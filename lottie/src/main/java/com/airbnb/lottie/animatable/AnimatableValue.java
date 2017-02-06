package com.airbnb.lottie.animatable;

import com.airbnb.lottie.animation.Animation;

public interface AnimatableValue {

  Animation createAnimation();

  boolean hasAnimation();
}
