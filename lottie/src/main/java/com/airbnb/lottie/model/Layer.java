package com.airbnb.lottie.model;

import android.graphics.PointF;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;

import com.airbnb.lottie.animatable.AnimatableValue;
import com.airbnb.lottie.utils.ScaleXY;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class Layer implements Transform {
  private final LottieComposition composition;

  public enum LottieLayerType {
    None,
    Solid,
    Unknown,
    Null,
    Shape
  }

  public enum MatteType {
    None,
    Add,
    Invert,
    Unknown
  }

  private final List<Object> shapes = new ArrayList<>();

  private String layerName;
  private long layerId;
  private LottieLayerType layerType;
  private long parentId = -1;
  private long inFrame;
  private long outFrame;
  private int frameRate;

  private final List<Mask> masks = new ArrayList<>();

  private int solidWidth;
  private int solidHeight;
  private int solidColor;

  private AnimatableValue<Integer> opacity;
  private AnimatableValue<Float> rotation;
  private AnimatableValue<PointF> position;

  private AnimatableValue<PointF> anchor;
  private AnimatableValue<ScaleXY> scale;

  private boolean hasOutAnimation;
  private boolean hasInAnimation;
  private boolean hasInOutAnimation;
  @Nullable private List<Float> inOutKeyFrames;
  @Nullable private List<Float> inOutKeyTimes;

  private MatteType matteType;

  public Layer(LottieComposition composition) {
    this.composition = composition;
  }

  @Override
  public Rect getBounds() {
    return composition.getBounds();
  }

  @Override
  public AnimatableValue<PointF> getAnchor() {
    return anchor;
  }

  public LottieComposition getComposition() {
    return composition;
  }

  public boolean hasInAnimation() {
    return isHasInAnimation();
  }

  public boolean hasInOutAnimation() {
    return isHasInOutAnimation();
  }

  @Nullable
  public List<Float> getInOutKeyFrames() {
    return inOutKeyFrames;
  }

  @Nullable
  public List<Float> getInOutKeyTimes() {
    return inOutKeyTimes;
  }

  public long getId() {
    return layerId;
  }

  public String getName() {
    return layerName;
  }

  public List<Mask> getMasks() {
    return masks;
  }

  public MatteType getMatteType() {
    return matteType;
  }

  @Override
  public AnimatableValue<Integer> getOpacity() {
    return opacity;
  }

  public long getParentId() {
    return parentId;
  }

  @Override
  public AnimatableValue<PointF> getPosition() {
    return position;
  }

  @Override
  public AnimatableValue<Float> getRotation() {
    return rotation;
  }

  @Override
  public AnimatableValue<ScaleXY> getScale() {
    return scale;
  }

  public List<Object> getShapes() {
    return shapes;
  }

  public int getSolidColor() {
    return solidColor;
  }

  public int getSolidHeight() {
    return solidHeight;
  }

  public int getSolidWidth() {
    return solidWidth;
  }

  public void setName(String layerName) {
    this.layerName = layerName;
  }

  public void setId(long layerId) {
    this.layerId = layerId;
  }

  public LottieLayerType getLayerType() {
    return layerType;
  }

  public void setLayerType(LottieLayerType layerType) {
    this.layerType = layerType;
  }

  public void setParentId(long parentId) {
    this.parentId = parentId;
  }

  public long getInFrame() {
    return inFrame;
  }

  public void setInFrame(long inFrame) {
    this.inFrame = inFrame;
  }

  public long getOutFrame() {
    return outFrame;
  }

  public void setOutFrame(long outFrame) {
    this.outFrame = outFrame;
  }

  public int getFrameRate() {
    return frameRate;
  }

  public void setFrameRate(int frameRate) {
    this.frameRate = frameRate;
  }

  public void setSolidWidth(int solidWidth) {
    this.solidWidth = solidWidth;
  }

  public void setSolidHeight(int solidHeight) {
    this.solidHeight = solidHeight;
  }

  public void setSolidColor(int solidColor) {
    this.solidColor = solidColor;
  }

  public void setOpacity(AnimatableValue<Integer> opacity) {
    this.opacity = opacity;
  }

  public void setRotation(AnimatableValue<Float> rotation) {
    this.rotation = rotation;
  }

  public void setPosition(AnimatableValue<PointF> position) {
    this.position = position;
  }

  public void setAnchor(AnimatableValue<PointF> anchor) {
    this.anchor = anchor;
  }

  public void setScale(AnimatableValue<ScaleXY> scale) {
    this.scale = scale;
  }

  public void setMatteType(MatteType matteType) {
    this.matteType = matteType;
  }

  public boolean isHasOutAnimation() {
    return hasOutAnimation;
  }

  public void setHasOutAnimation(boolean hasOutAnimation) {
    this.hasOutAnimation = hasOutAnimation;
  }

  public boolean isHasInAnimation() {
    return hasInAnimation;
  }

  public void setHasInAnimation(boolean hasInAnimation) {
    this.hasInAnimation = hasInAnimation;
  }

  public boolean isHasInOutAnimation() {
    return hasInOutAnimation;
  }

  public void setHasInOutAnimation(boolean hasInOutAnimation) {
    this.hasInOutAnimation = hasInOutAnimation;
  }

  public void setInOutKeyFrames(@Nullable List<Float> inOutKeyFrames) {
    this.inOutKeyFrames = inOutKeyFrames;
  }

  public void setInOutKeyTimes(@Nullable List<Float> inOutKeyTimes) {
    this.inOutKeyTimes = inOutKeyTimes;
  }




  @Override
  public String toString() {
    return toString("");
  }

  public String toString(String prefix) {
    StringBuilder sb = new StringBuilder();
    sb.append(prefix).append(getName()).append("\n");
    Layer parent = composition.layerModelForId(getParentId());
    if (parent != null) {
      sb.append("\t\tParents: ").append(parent.getName());
      parent = composition.layerModelForId(parent.getParentId());
      while (parent != null) {
        sb.append("->").append(parent.getName());
        parent = composition.layerModelForId(parent.getParentId());
      }
      sb.append(prefix).append("\n");
    }
    if (getPosition().hasAnimation()) {
      sb.append(prefix).append("\tPosition: ").append(getPosition()).append("\n");
    }
    if (getRotation().hasAnimation()) {
      sb.append(prefix).append("\tRotation: ").append(getRotation()).append("\n");
    }
    if (getScale().hasAnimation()) {
      sb.append(prefix).append("\tScale: ").append(getScale()).append("\n");
    }
    if (getAnchor().hasAnimation()) {
      sb.append(prefix).append("\tAnchor: ").append(getAnchor()).append("\n");
    }
    if (!getMasks().isEmpty()) {
      sb.append(prefix).append("\tMasks: ").append(getMasks().size()).append("\n");
    }
    if (getSolidWidth() != 0 && getSolidHeight() != 0) {
      sb.append(prefix).append("\tBackground: ").append(String.format(Locale.US, "%dx%d %X\n", getSolidWidth(), getSolidHeight(), getSolidColor()));
    }
    if (!getShapes().isEmpty()) {
      sb.append(prefix).append("\tShapes:\n");
      for (Object shape : getShapes()) {
        sb.append(prefix).append("\t\t").append(shape).append("\n");
      }
    }
    return sb.toString();
  }
}
