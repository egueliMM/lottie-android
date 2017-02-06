package com.airbnb.lottie.animatable.keyframe;

import android.support.annotation.RestrictTo;

import com.airbnb.lottie.animation.Animation;
import com.airbnb.lottie.animation.keyframe.KeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.NumberKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.StaticKeyframeAnimation;
import com.airbnb.lottie.model.LottieComposition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class KeyframeAnimatableIntegerValue extends KeyframeAnimatableValue<Integer, Integer> {

  public KeyframeAnimatableIntegerValue(LottieComposition composition, Integer initialValue) {
    super(composition);
    this.initialValue = initialValue;
  }

  public KeyframeAnimatableIntegerValue(JSONObject json, int frameRate, LottieComposition composition, boolean isDp, boolean remap100To255) {
    super(json, frameRate, composition, isDp);
    if (remap100To255) {
      initialValue = initialValue * 255 / 100;
      for (int i = 0; i < keyValues.size(); i++) {
        keyValues.set(i, keyValues.get(i) * 255 / 100);
      }
    }
  }

  @Override
  protected Integer valueFromObject(Object object, float scale) throws JSONException {
    if (object instanceof Integer) {
      return Math.round((Integer) object * scale);
    } else if (object instanceof JSONArray && ((JSONArray) object).get(0) instanceof Integer) {
      return Math.round(((JSONArray) object).getInt(0) * scale);
    }
    return null;
  }

  @Override
  public Animation<Integer> createAnimation() {
    if (!hasAnimation()) {
      return new StaticKeyframeAnimation<>(initialValue);
    }

    KeyframeAnimation<Integer> animation = new NumberKeyframeAnimation<>(duration, composition, keyTimes, Integer.class, keyValues, interpolators);
    animation.setStartDelay(delay);
    return animation;
  }

  public Integer getInitialValue() {
    return initialValue;
  }
}
