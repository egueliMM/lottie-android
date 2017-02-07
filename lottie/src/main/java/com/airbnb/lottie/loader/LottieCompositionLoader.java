package com.airbnb.lottie.loader;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.airbnb.lottie.model.LottieComposition;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by enrico on 07/02/2017.
 */
public class LottieCompositionLoader {
  public static LottieComposition.Cancellable fromAssetFileName(Context context, String fileName, LottieComposition.OnCompositionLoadedListener loadedListener) {
    InputStream stream;
    try {
      stream = context.getAssets().open(fileName);
    } catch (IOException e) {
      throw new IllegalStateException("Unable to find file " + fileName, e);
    }
    return fromInputStream(context, stream, loadedListener);
  }

  public static LottieComposition.Cancellable fromInputStream(Context context, InputStream stream, LottieComposition.OnCompositionLoadedListener loadedListener) {
    FileCompositionLoader loader = new FileCompositionLoader(context.getResources(), loadedListener);
    loader.execute(stream);
    return loader;
  }

  public static LottieComposition fromFileSync(Context context, String fileName) {
    InputStream file;
    try {
      file = context.getAssets().open(fileName);
    } catch (IOException e) {
      throw new IllegalStateException("Unable to find file " + fileName, e);
    }
    return fromInputStream(context.getResources(), file);
  }

  public static LottieComposition.Cancellable fromJson(Resources res, JSONObject json, LottieComposition.OnCompositionLoadedListener loadedListener) {
    JsonCompositionLoader loader = new JsonCompositionLoader(res, loadedListener);
    loader.execute(json);
    return loader;
  }

  @SuppressWarnings("WeakerAccess")
  public static LottieComposition fromInputStream(Resources res, InputStream file) {
    try {
      int size = file.available();
      byte[] buffer = new byte[size];
      //noinspection ResultOfMethodCallIgnored
      file.read(buffer);
      file.close();
      String json = new String(buffer, "UTF-8");

      JSONObject jsonObject = new JSONObject(json);
      return JsonCompositionParser.parseComposition(res, jsonObject);
    } catch (IOException e) {
      throw new IllegalStateException("Unable to find file.", e);
    } catch (JSONException e) {
      throw new IllegalStateException("Unable to load JSON.", e);
    }
  }

  abstract static class CompositionLoader<Params>
      extends AsyncTask<Params, Void, LottieComposition>
      implements LottieComposition.Cancellable {

    @Override
    public void cancel() {
      cancel(true);
    }
  }

  static final class FileCompositionLoader extends CompositionLoader<InputStream> {

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

  static final class JsonCompositionLoader extends CompositionLoader<JSONObject> {

    private final Resources res;
    private final LottieComposition.OnCompositionLoadedListener loadedListener;

    JsonCompositionLoader(Resources res, LottieComposition.OnCompositionLoadedListener loadedListener) {
      this.res = res;
      this.loadedListener = loadedListener;
    }

    @Override
    protected LottieComposition doInBackground(JSONObject... params) {
      return JsonCompositionParser.parseComposition(res, params[0]);
    }

    @Override
    protected void onPostExecute(LottieComposition composition) {
      loadedListener.onCompositionLoaded(composition);
    }
  }
}
