package com.airbnb.lottie.model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;
import android.util.LongSparseArray;

import com.airbnb.lottie.loader.LottieCompositionLoader;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * After Effects/Bodymovin composition model. This is the serialized model from which the animation will be created.
 * It can be used with a {@link com.airbnb.lottie.LottieAnimationView} or {@link com.airbnb.lottie.layers.LottieDrawable}.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class LottieComposition {

  public interface OnCompositionLoadedListener {
    void onCompositionLoaded(LottieComposition composition);
  }

  public interface Cancellable {
    void cancel();
  }

  /**
   * The largest bitmap drawing cache can be is 8,294,400 bytes. There are 4 bytes per pixel leaving ~2.3M pixels available.
   * Reduce the number a little bit for safety.
   * <p>
   * Hopefully this can be hardware accelerated someday.
   */
  public static final int MAX_PIXELS = 1000;

  /**
   * Loads a composition from a file stored in /assets.
   */
  public static Cancellable fromAssetFileName(Context context, String fileName, OnCompositionLoadedListener loadedListener) {
    return new LottieCompositionLoader().fromAssetFileName(context, fileName, loadedListener);
  }

  /**
   * Loads a composition from an arbitrary input stream.
   * <p>
   * ex: fromInputStream(context, new FileInputStream(filePath), (composition) -> {});
   */
  public static Cancellable fromInputStream(Context context, InputStream stream, OnCompositionLoadedListener loadedListener) {
    return new LottieCompositionLoader().fromInputStream(context, stream, loadedListener);
  }

  public static LottieComposition fromFileSync(Context context, String fileName) {
    return new LottieCompositionLoader().fromFileSync(context, fileName);
  }

  /**
   * Loads a composition from a raw json object. This is useful for animations loaded from the network.
   */
  public static Cancellable fromJson(Resources res, JSONObject json, OnCompositionLoadedListener loadedListener) {
    return new LottieCompositionLoader().fromJson(res, json, loadedListener);
  }

  @SuppressWarnings("WeakerAccess")
  public static LottieComposition fromInputStream(Resources res, InputStream file) {
    return new LottieCompositionLoader().fromInputStream(res, file);
  }

  private final LongSparseArray<Layer> layerMap = new LongSparseArray<>();
  private final List<Layer> layers = new ArrayList<>();
  private Rect bounds;
  private long startFrame;
  private long endFrame;
  private int frameRate;
  private long duration;
  private boolean hasMasks;
  private boolean hasMattes;
  private float scale;

  public LottieComposition(Resources res) {
    setScale(res.getDisplayMetrics().density);
  }

  @VisibleForTesting
  public LottieComposition(long duration) {
    setScale(1f);
    this.setDuration(duration);
  }


  Layer layerModelForId(long id) {
    return layerMap.get(id);
  }

  public Rect getBounds() {
    return bounds;
  }

  public long getDuration() {
    return duration;
  }

  public long getEndFrame() {
    return endFrame;
  }

  public int getFrameRate() {
    return frameRate;
  }

  public List<Layer> getLayers() {
    return layers;
  }

  public long getStartFrame() {
    return startFrame;
  }

  public boolean hasMasks() {
    return hasMasks;
  }

  public boolean hasMattes() {
    return hasMattes;
  }

  public float getScale() {
    return scale;
  }

  public void setBounds(Rect bounds) {
    this.bounds = bounds;
  }

  public void setStartFrame(long startFrame) {
    this.startFrame = startFrame;
  }

  public void setEndFrame(long endFrame) {
    this.endFrame = endFrame;
  }

  public void setFrameRate(int frameRate) {
    this.frameRate = frameRate;
  }

  public void setScale(float scale) {
    this.scale = scale;
  }

  public void setDuration(long duration) {
    this.duration = duration;
  }

  public LongSparseArray<Layer> getLayerMap() {
    return layerMap;
  }

  public boolean isHasMasks() {
    return hasMasks;
  }

  public boolean isHasMattes() {
    return hasMattes;
  }

  public void setHasMasks(boolean hasMasks) {
    this.hasMasks = hasMasks;
  }

  public void setHasMattes(boolean hasMattes) {
    this.hasMattes = hasMattes;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("LottieComposition:\n");
    for (Layer layer : layers) {
      sb.append(layer.toString("\t"));
    }
    return sb.toString();
  }

}
