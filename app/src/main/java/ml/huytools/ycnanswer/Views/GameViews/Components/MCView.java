package ml.huytools.ycnanswer.Views.GameViews.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import ml.huytools.ycnanswer.Commons.Math.Vector2D;
import ml.huytools.ycnanswer.Commons.Graphics.Animations.Animation;
import ml.huytools.ycnanswer.Commons.Graphics.Animations.AnimationData;
import ml.huytools.ycnanswer.Commons.Graphics.Animations.AnimationManager;
import ml.huytools.ycnanswer.Commons.CustomSurfaceView;
import ml.huytools.ycnanswer.R;

public class MCView extends CustomSurfaceView {
    AnimationManager animationManager;
    Animation animationCharacter;
    Animation animationEye;
    Vector2D screenSize;

    public MCView(Context context, AttributeSet attrs) {
        super(context, attrs);
        transparent();

        ///
        AnimationData animationData = AnimationData.CreateByResource(R.raw.frames_mc);
        setAnimationData(animationData);
    }

    public void setAnimationData(AnimationData animationData){
        animationManager = new AnimationManager(animationData);
        runAnimationIdle();
    }

    private void updatePosition(){
        if(animationCharacter == null || animationEye == null || screenSize == null){
            return;
        }

        float x, y;
        x = 0;
        y = screenSize.y - 260;
        animationCharacter.setPositionRender(new Vector2D(x, y));

        x = screenSize.x * 0.2f;
        y = y + 95;
        animationEye.setPositionRender(new Vector2D(x, y));
    }

    public void runAnimationIdle(){
        animationCharacter = animationManager.get("mc_start");
        animationEye = animationManager.get("mc_eye_default");
        updatePosition();
    }

    public void runAnimationSpeak(){
        updatePosition();
    }

    @Override
    public void OnInit(Canvas canvas) {
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        screenSize = new Vector2D(w, h);
        updatePosition();
    }

    @Override
    public boolean OnUpdate(int sleep) {
        if(animationCharacter == null || animationEye == null){
            return false;
        }
        return animationCharacter.OnUpdate(sleep) || animationEye.OnUpdate(sleep);
    }

    @Override
    public void OnDraw(Canvas canvas) {
        if(animationCharacter == null || animationEye == null){
            return;
        }
        clearTransparent(canvas);
        animationCharacter.OnDraw(canvas);
        animationEye.OnDraw(canvas);
    }
}
