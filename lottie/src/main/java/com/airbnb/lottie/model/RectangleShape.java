package com.airbnb.lottie.model;

import android.support.annotation.RestrictTo;
import android.util.Log;

import com.airbnb.lottie.L;
import com.airbnb.lottie.animatable.keyframe.KeyframeAnimatableFloatValue;
import com.airbnb.lottie.animatable.keyframe.KeyframeAnimatablePathValue;
import com.airbnb.lottie.animatable.keyframe.KeyframeAnimatablePointValue;

import org.json.JSONException;
import org.json.JSONObject;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class RectangleShape {
  private static final String TAG = RectangleShape.class.getSimpleName();

  private final KeyframeAnimatablePathValue position;
  private final KeyframeAnimatablePointValue size;
  private final KeyframeAnimatableFloatValue cornerRadius;

  RectangleShape(JSONObject json, int frameRate, LottieComposition composition) {
    try {
      JSONObject positionJson = json.getJSONObject("p");
      position = new KeyframeAnimatablePathValue(positionJson, frameRate, composition);
    } catch (JSONException e) {
      throw new IllegalArgumentException("Unable to parse rectangle position.", e);
    }

    try {
      JSONObject cornerRadiusJson = json.getJSONObject("r");
      cornerRadius = new KeyframeAnimatableFloatValue(cornerRadiusJson, frameRate, composition);
    } catch (JSONException e) {
      throw new IllegalArgumentException("Unable to parse rectangle corner radius.", e);
    }

    try {
      JSONObject sizeJson = json.getJSONObject("s");
      size = new KeyframeAnimatablePointValue(sizeJson, frameRate, composition);
    } catch (JSONException e) {
      throw new IllegalArgumentException("Unable to parse rectangle size.", e);
    }

    if (L.DBG) Log.d(TAG, "Parsed new rectangle " + toString());
  }

  public KeyframeAnimatableFloatValue getCornerRadius() {
    return cornerRadius;
  }

  public KeyframeAnimatablePointValue getSize() {
    return size;
  }

  public KeyframeAnimatablePathValue getPosition() {
    return position;
  }

  @Override
  public String toString() {
    return "RectangleShape{" + "cornerRadius=" + cornerRadius.getInitialValue() +
        ", position=" + position +
        ", size=" + size +
        '}';
  }
}
