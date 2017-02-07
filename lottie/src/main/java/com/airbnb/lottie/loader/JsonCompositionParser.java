package com.airbnb.lottie.loader;

import android.content.res.Resources;
import android.graphics.Rect;

import com.airbnb.lottie.model.Layer;
import com.airbnb.lottie.model.LottieComposition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by enrico on 07/02/2017.
 */
public class JsonCompositionParser {
  @SuppressWarnings("WeakerAccess")
  public static LottieComposition fromJsonSync(Resources res, JSONObject json) {
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
        Layer layer = Layer.fromJson(jsonLayers.getJSONObject(i), composition);
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
          Layer layer = Layer.fromJson(layers.getJSONObject(j), composition);
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
}
