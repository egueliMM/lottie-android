package com.airbnb.lottie.loader;

import android.content.res.Resources;

import com.airbnb.lottie.model.Layer;
import com.airbnb.lottie.model.LottieComposition;

import org.json.JSONObject;

/**
 * Created by enrico on 07/02/2017.
 */
public interface CompositionParser {
  LottieComposition parseComposition(Resources res, JSONObject param);
  Layer parseLayer(JSONObject json, LottieComposition composition);

}
