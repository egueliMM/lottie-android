package com.airbnb.lottie.loader;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;

import com.airbnb.lottie.L;
import com.airbnb.lottie.animatable.keyframe.KeyframeAnimatableFloatValue;
import com.airbnb.lottie.animatable.keyframe.KeyframeAnimatableIntegerValue;
import com.airbnb.lottie.animatable.keyframe.KeyframeAnimatablePathValue;
import com.airbnb.lottie.animatable.keyframe.KeyframeAnimatableScaleValue;
import com.airbnb.lottie.model.Layer;
import com.airbnb.lottie.model.LottieComposition;
import com.airbnb.lottie.model.Mask;
import com.airbnb.lottie.model.ShapeGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enrico on 07/02/2017.
 */
public class JsonCompositionParser implements CompositionParser {
  private static final String TAG = "JsonCompositionParser";
  
  @SuppressWarnings("WeakerAccess")
  public LottieComposition parseComposition(Resources res, JSONObject json) {
    LottieComposition composition = new LottieComposition(res);

    int width = -1;
    int height = -1;
    try {
      width = json.getInt("w");
      height = json.getInt("h");
    } catch (JSONException e) {
      // ignore.
    }
    if (width != -1 && height != -1) {
      int scaledWidth = (int) (width * composition.getScale());
      int scaledHeight = (int) (height * composition.getScale());
      if (Math.max(scaledWidth, scaledHeight) > LottieComposition.MAX_PIXELS) {
        float factor = (float) LottieComposition.MAX_PIXELS / (float) Math.max(scaledWidth, scaledHeight);
        scaledWidth *= factor;
        scaledHeight *= factor;
        composition.setScale(composition.getScale() * factor);
      }
      composition.setBounds(new Rect(0, 0, scaledWidth, scaledHeight));
    }

    try {
      composition.setStartFrame(json.getLong("ip"));
      composition.setEndFrame(json.getLong("op"));
      composition.setFrameRate(json.getInt("fr"));
    } catch (JSONException e) {
      //
    }

    if (composition.getEndFrame() != 0 && composition.getFrameRate() != 0) {
      long frameDuration = composition.getEndFrame() - composition.getStartFrame();
      composition.setDuration((long) (frameDuration / (float) composition.getFrameRate() * 1000));
    }

    try {
      JSONArray jsonLayers = json.getJSONArray("layers");
      for (int i = 0; i < jsonLayers.length(); i++) {
        Layer layer = parseLayer(jsonLayers.getJSONObject(i), composition);
        addLayer(composition, layer);
      }
    } catch (JSONException e) {
      throw new IllegalStateException("Unable to find layers.", e);
    }

    // These are precomps. This naively adds the precomp layers to the main composition.
    // TODO: Significant work will have to be done to properly support them.
    try {
      JSONArray assets = json.getJSONArray("assets");
      for (int i = 0; i < assets.length(); i++) {
        JSONObject asset = assets.getJSONObject(i);
        JSONArray layers = asset.getJSONArray("layers");
        for (int j = 0; j < layers.length(); j++) {
          Layer layer = parseLayer(layers.getJSONObject(j), composition);
          addLayer(composition, layer);
        }
      }
    } catch (JSONException e) {
      // Do nothing.
    }


    return composition;
  }

  private static void addLayer(LottieComposition composition, Layer layer) {
    composition.getLayers().add(layer);
    composition.getLayerMap().put(layer.getId(), layer);
    if (!layer.getMasks().isEmpty()) {
      composition.setHasMasks(true);
    }
    if (layer.getMatteType() != null && layer.getMatteType() != Layer.MatteType.None) {
      composition.setHasMattes(true);
    }
  }

  public Layer parseLayer(JSONObject json, LottieComposition composition) {
    Layer layer = new Layer(composition);
    try {
      if (L.DBG) Log.d(TAG, "Parsing new layer.");
      layer.setName(json.getString("nm"));
      if (L.DBG) Log.d(TAG, "\tName=" + layer.getName());
      layer.setId(json.getLong("ind"));
      if (L.DBG) Log.d(TAG, "\tId=" + layer.getId());
      layer.setFrameRate(composition.getFrameRate());

      int layerType = json.getInt("ty");
      if (layerType <= Layer.LottieLayerType.Shape.ordinal()) {
        layer.setLayerType(Layer.LottieLayerType.values()[layerType]);
      } else {
        layer.setLayerType(Layer.LottieLayerType.Unknown);
      }

      try {
        layer.setParentId(json.getLong("parent"));
      } catch (JSONException e) {
        // Do nothing.
      }
      layer.setInFrame(json.getLong("ip"));
      layer.setOutFrame(json.getLong("op"));
      if (L.DBG) Log.d(TAG, "\tFrames=" + layer.getInFrame() + "->" + layer.getOutFrame());

      if (layer.getLayerType() == Layer.LottieLayerType.Solid) {
        layer.setSolidWidth((int) (json.getInt("sw") * composition.getScale()));
        layer.setSolidHeight((int) (json.getInt("sh") * composition.getScale()));
        layer.setSolidColor(Color.parseColor(json.getString("sc")));
        if (L.DBG) {
          Log.d(TAG, "\tSolid=" + Integer.toHexString(layer.getSolidColor()) + " " +
              layer.getSolidWidth() + "x" + layer.getSolidHeight() + " " + composition.getBounds());
        }
      }

      JSONObject ks = json.getJSONObject("ks");

      JSONObject opacity = null;
      try {
        opacity = ks.getJSONObject("o");
      } catch (JSONException e) {
        // Do nothing.
      }
      if (opacity != null) {
        layer.setOpacity(new KeyframeAnimatableIntegerValue(opacity, layer.getFrameRate(), composition, false, true));
        if (L.DBG) Log.d(TAG, "\tOpacity=" + layer.getOpacity());
      }

      JSONObject rotation;
      try {
        rotation = ks.getJSONObject("r");
      } catch (JSONException e) {
        rotation = ks.getJSONObject("rz");
      }

      if (rotation != null) {
        layer.setRotation(new KeyframeAnimatableFloatValue(rotation, layer.getFrameRate(), composition, false));
        if (L.DBG) Log.d(TAG, "\tRotation=" + layer.getRotation());
      }

      JSONObject position = null;
      try {
        position = ks.getJSONObject("p");
      } catch (JSONException e) {
        // Do nothing.
      }
      if (position != null) {
        layer.setPosition(new KeyframeAnimatablePathValue(position, layer.getFrameRate(), composition));
        if (L.DBG) Log.d(TAG, "\tPosition=" + layer.getPosition().toString());
      }

      JSONObject anchor = null;
      try {
        anchor = ks.getJSONObject("a");
      } catch (JSONException e) {
        // DO nothing.
      }
      if (anchor != null) {
        layer.setAnchor(new KeyframeAnimatablePathValue(anchor, layer.getFrameRate(), composition));
        if (L.DBG) Log.d(TAG, "\tAnchor=" + layer.getAnchor().toString());
      }

      JSONObject scale = null;
      try {
        scale = ks.getJSONObject("s");
      } catch (JSONException e) {
        // Do nothing.
      }
      if (scale != null) {
        layer.setScale(new KeyframeAnimatableScaleValue(scale, layer.getFrameRate(), composition, false));
        if (L.DBG) Log.d(TAG, "\tScale=" + layer.getScale().toString());
      }

      try {
        layer.setMatteType(Layer.MatteType.values()[json.getInt("tt")]);
        if (L.DBG) Log.d(TAG, "\tMatte=" + layer.getMatteType());
      } catch (JSONException e) {
        // Do nothing.
      }

      JSONArray jsonMasks = null;
      try {
        jsonMasks = json.getJSONArray("masksProperties");
      } catch (JSONException e) {
        // Do nothing.
      }
      if (jsonMasks != null) {
        for (int i = 0; i < jsonMasks.length(); i++) {
          Mask mask = new Mask(jsonMasks.getJSONObject(i), layer.getFrameRate(), composition);
          layer.getMasks().add(mask);
          if (L.DBG) Log.d(TAG, "\tMask=" + mask.getMaskMode());
        }
      }

      JSONArray shapes = null;
      try {
        shapes = json.getJSONArray("shapes");
      } catch (JSONException e) {
        // Do nothing.
      }
      if (shapes != null) {
        for (int i = 0; i < shapes.length(); i++) {
          Object shape = ShapeGroup.shapeItemWithJson(shapes.getJSONObject(i), layer.getFrameRate(), composition);
          if (shape != null) {
            layer.getShapes().add(shape);
            if (L.DBG) Log.d(TAG, "\tShapes+=" + shape.getClass().getSimpleName());
          }
        }
      }
    } catch (JSONException e) {
      throw new IllegalStateException("Unable to parse layer json.", e);
    }

    layer.setHasInAnimation(layer.getInFrame() > composition.getStartFrame());
    layer.setHasOutAnimation(layer.getOutFrame() < composition.getEndFrame());
    layer.setHasInOutAnimation(layer.isHasInAnimation() || layer.isHasOutAnimation());

    if (layer.isHasInOutAnimation()) {
      List<Float> keys = new ArrayList<>();
      List<Float> keyTimes = new ArrayList<>();
      long length = composition.getEndFrame() - composition.getStartFrame();

      if (layer.isHasInAnimation()) {
        keys.add(0f);
        keyTimes.add(0f);
        keys.add(1f);
        float inTime = layer.getInFrame() / (float) length;
        keyTimes.add(inTime);
      } else {
        keys.add(1f);
        keyTimes.add(0f);
      }

      if (layer.isHasOutAnimation()) {
        keys.add(0f);
        keyTimes.add(layer.getOutFrame() / (float) length);
        keys.add(0f);
        keyTimes.add(1f);
      } else {
        keys.add(1f);
        keyTimes.add(1f);
      }

      layer.setInOutKeyTimes(keyTimes);
      layer.setInOutKeyFrames(keys);

    }

    return layer;
  }
}
