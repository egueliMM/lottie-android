package com.airbnb.lottie.animatable;

import com.airbnb.lottie.animation.Animation;

public interface AnimatableValue<T> {

  Animation<T> createAnimation();

  boolean hasAnimation();

  T getInitialValue();
}
