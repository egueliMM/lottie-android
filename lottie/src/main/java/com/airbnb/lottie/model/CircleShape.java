package com.airbnb.lottie.model;

import android.support.annotation.RestrictTo;

import com.airbnb.lottie.animatable.keyframe.KeyframeAnimatablePathValue;
import com.airbnb.lottie.animatable.keyframe.KeyframeAnimatablePointValue;

import org.json.JSONException;
import org.json.JSONObject;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class CircleShape {
  private final KeyframeAnimatablePathValue position;
  private final KeyframeAnimatablePointValue size;

  CircleShape(JSONObject json, int frameRate, LottieComposition composition) {
    try {
      position = new KeyframeAnimatablePathValue(json.getJSONObject("p"), frameRate, composition);
      size = new KeyframeAnimatablePointValue(json.getJSONObject("s"), frameRate, composition);
    } catch (JSONException e) {
      throw new IllegalArgumentException("Unable to parse circle " + json, e);
    }
  }

  public KeyframeAnimatablePathValue getPosition() {
    return position;
  }

  public KeyframeAnimatablePointValue getSize() {
    return size;
  }
}
