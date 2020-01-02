package ml.huytools.ycnanswer.Views.ViewComponents;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import ml.huytools.ycnanswer.Core.Game.Renderer;
import ml.huytools.ycnanswer.Core.Game.Scenes.NodeGroup;
import ml.huytools.ycnanswer.Core.Math.Vector2D;
import ml.huytools.ycnanswer.Views.GameComponents.Loading;

public class LoadingView extends SurfaceView implements Renderer.Callback {
    Renderer renderer;
    Loading nodeGroup;

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        nodeGroup = new Loading();
        renderer = new Renderer(this, nodeGroup);
        renderer.enableAutoRegisterDirector(this);
        renderer.transparent();
    }


    public static LoadingView create(Activity activity){
        LoadingView loading = new LoadingView(activity.getBaseContext(), null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        activity.addContentView(loading, params);
        return loading;
    }

    public void removeOnView(){
        ((ViewGroup)this.getParent()).removeView(this);
    }


    @Override
    public void OnCreate(Vector2D size) {
        nodeGroup.setBoundingSize(size);
    }

    @Override
    public void OnResume(Vector2D size) {
        nodeGroup.setBoundingSize(size);
    }

    @Override
    public void OnDestroy() {
    }
}
