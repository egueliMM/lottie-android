package com.airbnb.lottie.loader;

import android.os.AsyncTask;

import com.airbnb.lottie.model.LottieComposition;

abstract class CompositionLoader<Params>
    extends AsyncTask<Params, Void, LottieComposition>
    implements LottieComposition.Cancellable {

  @Override
  public void cancel() {
    cancel(true);
  }
}
