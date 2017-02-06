package com.airbnb.lottie.model;

import android.support.annotation.RestrictTo;

import com.airbnb.lottie.animatable.keyframe.KeyframeAnimatableColorValue;
import com.airbnb.lottie.animatable.keyframe.KeyframeAnimatableFloatValue;
import com.airbnb.lottie.animatable.keyframe.KeyframeAnimatableIntegerValue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class ShapeStroke {

  public enum LineCapType {
    Butt,
    Round,
    Unknown
  }

  public enum LineJoinType {
    Miter,
    Round,
    Bevel
  }

  private KeyframeAnimatableFloatValue offset;
  private final List<KeyframeAnimatableFloatValue> lineDashPattern = new ArrayList<>();

  private final KeyframeAnimatableColorValue color;
  private final KeyframeAnimatableIntegerValue opacity;
  private final KeyframeAnimatableFloatValue width;
  private final LineCapType capType;
  private final LineJoinType joinType;

  ShapeStroke(JSONObject json, int frameRate, LottieComposition composition) {
    try {
      JSONObject colorJson = json.getJSONObject("c");
      color = new KeyframeAnimatableColorValue(colorJson, frameRate, composition);

      JSONObject widthJson = json.getJSONObject("w");
      width = new KeyframeAnimatableFloatValue(widthJson, frameRate, composition);

      JSONObject opacityJson = json.getJSONObject("o");
      opacity = new KeyframeAnimatableIntegerValue(opacityJson, frameRate, composition, false, true);

      capType = LineCapType.values()[json.getInt("lc") - 1];
      joinType = LineJoinType.values()[json.getInt("lj") - 1];

      if (json.has("d")) {
        JSONArray dashesJson = json.getJSONArray("d");
        for (int i = 0; i < dashesJson.length(); i++) {
          JSONObject dashJson = dashesJson.getJSONObject(i);
          String n = dashJson.getString("n");
          if (n.equals("o")) {
            JSONObject value = dashJson.getJSONObject("v");
            offset = new KeyframeAnimatableFloatValue(value, frameRate, composition);
          } else if (n.equals("d") || n.equals("g")) {
            JSONObject value = dashJson.getJSONObject("v");
            lineDashPattern.add(new KeyframeAnimatableFloatValue(value, frameRate, composition));
          }
        }
        if (lineDashPattern.size() == 1) {
          // If there is only 1 value then it is assumed to be equal parts on and off.
          lineDashPattern.add(lineDashPattern.get(0));
        }
      }
    } catch (JSONException e) {
      throw new IllegalArgumentException("Unable to parse stroke " + json, e);
    }
  }

  public KeyframeAnimatableColorValue getColor() {
    return color;
  }

  public KeyframeAnimatableIntegerValue getOpacity() {
    return opacity;
  }

  public KeyframeAnimatableFloatValue getWidth() {
    return width;
  }

  public List<KeyframeAnimatableFloatValue> getLineDashPattern() {
    return lineDashPattern;
  }

  public KeyframeAnimatableFloatValue getDashOffset() {
    return offset;
  }

  public LineCapType getCapType() {
    return capType;
  }

  public LineJoinType getJoinType() {
    return joinType;
  }
}
