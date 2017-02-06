package com.airbnb.lottie.animatable;

import com.airbnb.lottie.animation.Animation;

interface AnimatableValue {

  Animation createAnimation();

  boolean hasAnimation();
}
