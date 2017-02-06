package com.airbnb.lottie.layers;

import android.graphics.Path;
import android.graphics.drawable.Drawable;

import com.airbnb.lottie.animation.Animation;
import com.airbnb.lottie.model.Mask;

import java.util.ArrayList;
import java.util.List;

class MaskLayer extends AnimatableLayer {

  private final List<Animation<Path>> masks;

  MaskLayer(List<Mask> masks, Drawable.Callback callback) {
    super(callback);
    this.masks = new ArrayList<>(masks.size());
    for (int i = 0; i < masks.size(); i++) {
      this.masks.add(masks.get(i).getMaskPath().createAnimation());
    }
  }

  List<Animation<Path>> getMasks() {
    return masks;
  }
}
