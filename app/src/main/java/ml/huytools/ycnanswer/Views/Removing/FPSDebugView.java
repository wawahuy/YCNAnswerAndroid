package ml.huytools.ycnanswer.Views.Removing;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

import ml.huytools.ycnanswer.Core.Game.Renderer;
import ml.huytools.ycnanswer.Core.Math.Vector2D;
import ml.huytools.ycnanswer.Views.GameComponents.FPSDebug;

public class FPSDebugView extends SurfaceView implements Renderer.Callback {
    FPSDebug debug;
    Renderer renderer;

    public FPSDebugView(Context context, AttributeSet attrs) {
        super(context, attrs);
        debug = new FPSDebug();
        renderer = new Renderer(this, debug);
        renderer.enableAutoRegisterDirector(this);
        renderer.transparent();
    }


    @Override
    public void OnCreate(Vector2D size) {
        debug.setBoundingSize(size);
    }

    @Override
    public void OnResume(Vector2D size) {
        debug.setBoundingSize(size);
    }

    @Override
    public void OnDestroy() {
    }

    public static void AddOnActivity(Activity activity){
        FPSDebugView loading = new FPSDebugView(activity.getBaseContext(), null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(200, 100);
        activity.addContentView(loading, params);
    }
}
