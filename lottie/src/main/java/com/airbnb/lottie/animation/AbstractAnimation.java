package com.airbnb.lottie.animation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enrico on 07/02/2017.
 */

public abstract class AbstractAnimation<T> implements Animation<T> {
  protected final List<AnimationListener<T>> listeners = new ArrayList<>();

  @Override
  public void addUpdateListener(AnimationListener<T> listener) {
    listeners.add(listener);
  }

  @Override
  public void removeUpdateListener(AnimationListener<T> listener) {
    listeners.remove(listener);
  }
}
