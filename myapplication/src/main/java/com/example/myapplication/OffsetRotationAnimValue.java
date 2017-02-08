package com.example.myapplication;

import android.support.annotation.FloatRange;

import com.airbnb.lottie.animatable.AnimatableValue;
import com.airbnb.lottie.animation.AbstractAnimation;
import com.airbnb.lottie.animation.Animation;

/**
 * Created by enrico on 07/02/2017.
 */
public class OffsetRotationAnimValue implements AnimatableValue<Float> {
  private final AnimatableValue<Float> source;
  private float offset = 0;

  public OffsetRotationAnimValue(AnimatableValue<Float> source) {
    this.source = source;
  }

  public float getOffset() {
    return offset;
  }

  public void setOffset(float offset) {
    this.offset = offset;
  }

  @Override
  public Animation<Float> createAnimation() {
    return new OffsetRotationAnimation(source.createAnimation());
  }

  @Override
  public boolean hasAnimation() {
    return source.hasAnimation();
  }

  @Override
  public Float getInitialValue() {
    return source.getInitialValue() + offset;
  }

  private class OffsetRotationAnimation extends AbstractAnimation<Float> {
    private final Animation<Float> source;

    private OffsetRotationAnimation(Animation<Float> source) {
      this.source = source;
    }

    @Override
    public void setStartDelay(long startDelay) {
      source.setStartDelay(startDelay);
    }

    @Override
    public void setIsDiscrete() {
      source.setIsDiscrete();
    }

    @Override
    public void setProgress(@FloatRange(from = 0f, to = 1f) float progress) {
      source.setProgress(progress);
    }

    @Override
    public Float getValue() {
      return source.getValue() + offset;
    }
  }
}
