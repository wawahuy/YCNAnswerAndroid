package ml.huytools.ycnanswer.Views.ViewComponents;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;


import ml.huytools.ycnanswer.Core.Game.GameDirector;
import ml.huytools.ycnanswer.Core.Game.Renderer;
import ml.huytools.ycnanswer.Core.Math.Vector2D;
import ml.huytools.ycnanswer.Views.GameComponents.CountDown;

public class CountDownView extends SurfaceView implements Renderer.Callback {
    private Renderer renderer;
    private CountDown countDown;


    public CountDownView(Context context, AttributeSet attrs) {
        super(context, null);
        countDown = new CountDown();
        countDown.start();
        renderer = new Renderer(this, countDown);
        renderer.enableAutoRegisterDirector(this);
    }


    @Override
    public void OnCreate(Vector2D size) {
        countDown.setSizeBounding(size);
    }

    @Override
    public void OnResume(Vector2D size) {
        countDown.setSizeBounding(size);
    }

    @Override
    public void OnDestroy() {
    }
}
