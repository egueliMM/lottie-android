package com.airbnb.lottie.model;

import android.support.annotation.RestrictTo;
import android.util.Log;

import com.airbnb.lottie.L;
import com.airbnb.lottie.animatable.keyframe.KeyframeAnimatableColorValue;
import com.airbnb.lottie.animatable.keyframe.KeyframeAnimatableIntegerValue;

import org.json.JSONException;
import org.json.JSONObject;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class ShapeFill {
  private static final String TAG = ShapeFill.class.getSimpleName();

  private boolean fillEnabled;
  private KeyframeAnimatableColorValue color;
  private KeyframeAnimatableIntegerValue opacity;

  ShapeFill(JSONObject json, int frameRate, LottieComposition composition) {
    JSONObject jsonColor = null;
    try {
      jsonColor = json.getJSONObject("c");
    } catch (JSONException e) {
      // Do nothing.
    }
    if (jsonColor != null) {
      color = new KeyframeAnimatableColorValue(jsonColor, frameRate, composition);
    }

    JSONObject jsonOpacity = null;
    try {
      jsonOpacity = json.getJSONObject("o");
    } catch (JSONException e) {
      // Do nothing.
    }
    if (jsonOpacity != null) {
      opacity = new KeyframeAnimatableIntegerValue(jsonOpacity, frameRate, composition, false, true);
    }

    try {
      fillEnabled = json.getBoolean("fillEnabled");
    } catch (JSONException e) {
      // Do nothing.
    }
    if (L.DBG) Log.d(TAG, "Parsed new shape fill " + toString());
  }

  public KeyframeAnimatableColorValue getColor() {
    return color;
  }

  public KeyframeAnimatableIntegerValue getOpacity() {
    return opacity;
  }

  @Override
  public String toString() {
    return "ShapeFill{" + "color=" + Integer.toHexString(color.getInitialValue()) +
        ", fillEnabled=" + fillEnabled +
        ", opacity=" + opacity.getInitialValue() +
        '}';
  }
}
