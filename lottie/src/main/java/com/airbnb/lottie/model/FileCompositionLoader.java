package com.airbnb.lottie.model;

import android.content.res.Resources;

import java.io.InputStream;

final class FileCompositionLoader extends CompositionLoader<InputStream> {

  private final Resources res;
  private final LottieComposition.OnCompositionLoadedListener loadedListener;

  FileCompositionLoader(Resources res, LottieComposition.OnCompositionLoadedListener loadedListener) {
    this.res = res;
    this.loadedListener = loadedListener;
  }

  @Override
  protected LottieComposition doInBackground(InputStream... params) {
    return LottieComposition.fromInputStream(res, params[0]);
  }

  @Override
  protected void onPostExecute(LottieComposition composition) {
    loadedListener.onCompositionLoaded(composition);
  }
}
