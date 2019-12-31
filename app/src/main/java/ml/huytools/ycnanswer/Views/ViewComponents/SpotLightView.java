package ml.huytools.ycnanswer.Views.ViewComponents;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

import ml.huytools.ycnanswer.Core.Game.Renderer;
import ml.huytools.ycnanswer.Core.Math.Vector2D;
import ml.huytools.ycnanswer.Views.GameComponents.SpotLight;

public class SpotLightView extends SurfaceView implements Renderer.Callback {
    SpotLight spotLight;
    Renderer renderer;

    public SpotLightView(Context context, AttributeSet attrs) {
        super(context, attrs);
        spotLight = new SpotLight();
        renderer = new Renderer(this, spotLight);
        renderer.enableAutoRegisterDirector(this);
    }

    @Override
    public void OnCreate(Vector2D size) {
        spotLight.setBoundingSize(size);
    }

    @Override
    public void OnResume(Vector2D size) {
        spotLight.setBoundingSize(size);
    }

    @Override
    public void OnDestroy() {
    }
}
