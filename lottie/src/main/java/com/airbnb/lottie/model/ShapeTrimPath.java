package com.airbnb.lottie.model;

import android.support.annotation.RestrictTo;

import com.airbnb.lottie.animatable.keyframe.KeyframeAnimatableFloatValue;

import org.json.JSONException;
import org.json.JSONObject;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class ShapeTrimPath {
  private final KeyframeAnimatableFloatValue start;
  private final KeyframeAnimatableFloatValue end;
  private final KeyframeAnimatableFloatValue offset;

  ShapeTrimPath(JSONObject json, int frameRate, LottieComposition composition) {
    try {
      start = new KeyframeAnimatableFloatValue(json.getJSONObject("s"), frameRate, composition, false);
      end = new KeyframeAnimatableFloatValue(json.getJSONObject("e"), frameRate, composition, false);
      offset = new KeyframeAnimatableFloatValue(json.getJSONObject("o"), frameRate, composition, false);
    } catch (JSONException e) {
      throw new IllegalArgumentException("Unable to parse trim path " + json, e);
    }
  }

  public KeyframeAnimatableFloatValue getEnd() {
    return end;
  }

  public KeyframeAnimatableFloatValue getStart() {
    return start;
  }

  public KeyframeAnimatableFloatValue getOffset() {
    return offset;
  }

  @Override
  public String toString() {
    return "Trim Path: {start: " + start + ", end: " + end + ", offset: " + offset + "}";
  }
}
