package ml.huytools.ycnanswer.Views.GameViews.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import ml.huytools.ycnanswer.Commons.Views.Animations.AnimationData;
import ml.huytools.ycnanswer.Commons.Views.Animations.AnimationManager;
import ml.huytools.ycnanswer.Commons.Views.CustomSurfaceView;
import ml.huytools.ycnanswer.R;

public class MCView extends CustomSurfaceView {
    AnimationManager animationManager;
    int pos = 0;

    public MCView(Context context, AttributeSet attrs) {
        super(context, attrs);
        transparent();

        /// Test
        AnimationData animationData = AnimationData.CreateByResource(R.raw.frames_mc);
        animationManager = new AnimationManager(animationData);
    }

    @Override
    public void OnInit(Canvas canvas) {

    }

    @Override
    public boolean OnUpdate(int sleep) {
        animationManager.get("mc_default").OnUpdate(sleep);
        return true;
    }

    @Override
    public void OnDraw(Canvas canvas) {
        clearTransparent(canvas);
        animationManager.get("mc_default").OnDraw(canvas);
    }
}
