package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.animatable.AnimatableValue;
import com.airbnb.lottie.loader.JsonCompositionParser;
import com.airbnb.lottie.loader.LottieCompositionLoader;
import com.airbnb.lottie.model.Layer;
import com.airbnb.lottie.model.LottieComposition;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animation_view);

    Single.<LottieComposition>create(s -> {
        try {
          LottieCompositionLoader loader = new LottieCompositionLoader();
          loader.setParser(new JsonCompositionParser() {
            @Override
            public Layer parseLayer(JSONObject json, LottieComposition composition) {
              Layer layer = super.parseLayer(json, composition);
              if (layer.getName().equals("Shape Layer 1")) {
                AnimatableValue<Float> rotation = layer.getRotation();
                layer.setRotation(new OffsetRotationAnimValue(rotation, 15));
              }
              return layer;
            }
          });
          loader.fromInputStream(this, getAssets().open("star.json"), s::onSuccess);
        } catch (IOException e) {
          s.onError(e);
        }
      })
      .subscribe(composition -> {
          animationView.setComposition(composition);
          animationView.loop(true);
          animationView.playAnimation();

          List<Layer> layers = composition.getLayers();
          Observable.fromIterable(layers)
              .subscribe(layer -> {
                Log.d(TAG, String.format("Name: '%s', type: %s", layer.getName(), layer.getMatteType()));
              });

      });
  }


}
