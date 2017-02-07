package com.airbnb.lottie.model;

import android.os.AsyncTask;

abstract class CompositionLoader<Params>
    extends AsyncTask<Params, Void, LottieComposition>
    implements LottieComposition.Cancellable {

  @Override
  public void cancel() {
    cancel(true);
  }
}
