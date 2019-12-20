package ml.huytools.ycnanswer.Views.GameViews.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import ml.huytools.ycnanswer.Commons.Views.Animations.AnimationData;
import ml.huytools.ycnanswer.Commons.Views.Animations.AnimationManager;
import ml.huytools.ycnanswer.Commons.Views.CustomSurfaceView;
import ml.huytools.ycnanswer.R;

public class MCView extends CustomSurfaceView {
    AnimationData animationData;
    int pos = 0;

    public MCView(Context context, AttributeSet attrs) {
        super(context, attrs);
        transparent();

        /// Test
        animationData = AnimationData.CreateByResource(R.raw.frames_mc);
        AnimationManager animationManager = new AnimationManager(animationData);
    }

    @Override
    public void OnInit(Canvas canvas) {

    }

    @Override
    public boolean OnUpdate(int sleep) {
        pos++;
        if(pos == 5) pos = 0;
        return true;
    }

    @Override
    public void OnDraw(Canvas canvas) {
        canvas.drawBitmap(animationData.images.get(0).frames.get(animationData.actions.get(0).frames.get(pos).framePos).getImageCrop().getBitmap(), 0, 0, null);
    }
}
