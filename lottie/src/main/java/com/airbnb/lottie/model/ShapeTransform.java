package com.airbnb.lottie.model;

import android.graphics.Rect;
import android.support.annotation.RestrictTo;
import android.util.Log;

import com.airbnb.lottie.L;
import com.airbnb.lottie.animatable.keyframe.KeyframeAnimatableFloatValue;
import com.airbnb.lottie.animatable.keyframe.KeyframeAnimatableIntegerValue;
import com.airbnb.lottie.animatable.keyframe.KeyframeAnimatablePathValue;
import com.airbnb.lottie.animatable.keyframe.KeyframeAnimatableScaleValue;

import org.json.JSONException;
import org.json.JSONObject;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class ShapeTransform implements Transform {
  private static final String TAG = ShapeTransform.class.getSimpleName();

  private final Rect compBounds;
  private final KeyframeAnimatablePathValue position;
  private final KeyframeAnimatablePathValue anchor;
  private final KeyframeAnimatableScaleValue scale;
  private final KeyframeAnimatableFloatValue rotation;
  private final KeyframeAnimatableIntegerValue opacity;

  public ShapeTransform(LottieComposition composition) {
    this.compBounds = composition.getBounds();
    this.position = new KeyframeAnimatablePathValue(composition);
    this.anchor = new KeyframeAnimatablePathValue(composition);
    this.scale = new KeyframeAnimatableScaleValue(composition);
    this.rotation = new KeyframeAnimatableFloatValue(composition, 0f);
    this.opacity = new KeyframeAnimatableIntegerValue(composition, 255);
  }

  ShapeTransform(JSONObject json, int frameRate, LottieComposition composition) {
    this.compBounds = composition.getBounds();

    JSONObject jsonPosition;
    try {
      jsonPosition = json.getJSONObject("p");
    } catch (JSONException e) {
      throw new IllegalStateException("Transform has no position.");
    }
    position = new KeyframeAnimatablePathValue(jsonPosition, frameRate, composition);

    JSONObject jsonAnchor;
    try {
      jsonAnchor = json.getJSONObject("a");
    } catch (JSONException e) {
      throw new IllegalStateException("Transform has no anchor.");
    }
    anchor = new KeyframeAnimatablePathValue(jsonAnchor, frameRate, composition);

    JSONObject jsonScale;
    try {
      jsonScale = json.getJSONObject("s");
    } catch (JSONException e) {
      throw new IllegalStateException("Transform has no scale.");
    }
    scale = new KeyframeAnimatableScaleValue(jsonScale, frameRate, composition, false);

    JSONObject jsonRotation;
    try {
      jsonRotation = json.getJSONObject("r");
    } catch (JSONException e) {
      throw new IllegalStateException("Transform has no rotation.");
    }
    rotation = new KeyframeAnimatableFloatValue(jsonRotation, frameRate, composition, false);

    JSONObject jsonOpacity;
    try {
      jsonOpacity = json.getJSONObject("o");
    } catch (JSONException e) {
      throw new IllegalStateException("Transform has no opacity.");
    }
    opacity = new KeyframeAnimatableIntegerValue(jsonOpacity, frameRate, composition, false, true);

    if (L.DBG) Log.d(TAG, "Parsed new shape transform " + toString());
  }

  public Rect getBounds() {
    return compBounds;
  }

  public KeyframeAnimatablePathValue getPosition() {
    return position;
  }

  public KeyframeAnimatablePathValue getAnchor() {
    return anchor;
  }

  public KeyframeAnimatableScaleValue getScale() {
    return scale;
  }

  public KeyframeAnimatableFloatValue getRotation() {
    return rotation;
  }

  public KeyframeAnimatableIntegerValue getOpacity() {
    return opacity;
  }

  @Override
  public String toString() {
    return "ShapeTransform{" + "anchor=" + anchor.toString() +
        ", compBounds=" + compBounds +
        ", position=" + position.toString() +
        ", scale=" + scale.toString() +
        ", rotation=" + rotation.getInitialValue() +
        ", opacity=" + opacity.getInitialValue() +
        '}';
  }
}
