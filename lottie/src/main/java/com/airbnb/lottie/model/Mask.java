package com.airbnb.lottie.model;

import android.support.annotation.RestrictTo;

import com.airbnb.lottie.animatable.keyframe.KeyframeAnimatableIntegerValue;
import com.airbnb.lottie.animatable.keyframe.KeyframeAnimatableShapeValue;

import org.json.JSONException;
import org.json.JSONObject;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class Mask {

  private enum MaskMode {
    MaskModeAdd,
    MaskModeSubtract,
    MaskModeIntersect,
    MaskModeUnknown
  }

  private final MaskMode maskMode;
  private final KeyframeAnimatableShapeValue maskPath;

  Mask(JSONObject json, int frameRate, LottieComposition composition) {
    try {
      boolean closed = false;
      if (json.has("cl")) {
        closed = json.getBoolean("cl");
      }
      String mode = json.getString("mode");
      switch (mode) {
        case "a":
          maskMode = MaskMode.MaskModeAdd;
          break;
        case "s":
          maskMode = MaskMode.MaskModeSubtract;
          break;
        case "i":
          maskMode = MaskMode.MaskModeIntersect;
          break;
        default:
          maskMode = MaskMode.MaskModeUnknown;
      }

      maskPath = new KeyframeAnimatableShapeValue(json.getJSONObject("pt"), frameRate, composition, closed);
      //noinspection unused
      KeyframeAnimatableIntegerValue opacity = new KeyframeAnimatableIntegerValue(json.getJSONObject("o"), frameRate, composition, false, true);
      // TODO: use this.
    } catch (JSONException e) {
      throw new IllegalArgumentException("Unable to parse mask. " + json, e);
    }
  }


  MaskMode getMaskMode() {
    return maskMode;
  }

  public KeyframeAnimatableShapeValue getMaskPath() {
    return maskPath;
  }
}
