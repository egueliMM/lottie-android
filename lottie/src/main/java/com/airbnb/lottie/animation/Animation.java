package com.airbnb.lottie.animation;

import android.support.annotation.FloatRange;

public interface Animation<T> {
  void setStartDelay(long startDelay);

  void setIsDiscrete();

  void addUpdateListener(AnimationListener<T> listener);

  void removeUpdateListener(AnimationListener<T> listener);

  void setProgress(@FloatRange(from = 0f, to = 1f) float progress);

  T getValue();

  public interface AnimationListener<T> {
    void onValueChanged(T progress);
  }
}
