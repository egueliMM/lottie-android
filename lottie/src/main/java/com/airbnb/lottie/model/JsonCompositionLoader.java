package com.airbnb.lottie.model;

import android.content.res.Resources;

import org.json.JSONObject;

final class JsonCompositionLoader extends CompositionLoader<JSONObject> {

  private final Resources res;
  private final LottieComposition.OnCompositionLoadedListener loadedListener;

  JsonCompositionLoader(Resources res, LottieComposition.OnCompositionLoadedListener loadedListener) {
    this.res = res;
    this.loadedListener = loadedListener;
  }

  @Override
  protected LottieComposition doInBackground(JSONObject... params) {
    return LottieComposition.fromJsonSync(res, params[0]);
  }

  @Override
  protected void onPostExecute(LottieComposition composition) {
    loadedListener.onCompositionLoaded(composition);
  }
}
