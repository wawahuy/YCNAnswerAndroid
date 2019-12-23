package ml.huytools.ycnanswer.Commons.Graphics.Animations;

import android.graphics.Canvas;

import ml.huytools.ycnanswer.Commons.Math.Vector2D;
import ml.huytools.ycnanswer.Commons.Math.CubicBezier;

public class Animation extends AbstractAnimation {
    AnimationData.Action action;
    int numFrame;
    int positionFrameCurrent;
    Vector2D positionRender;

    public Animation(AnimationData.Action action){
        this.action = action;
        this.positionFrameCurrent = 0;
        this.numFrame = action.frames.size();
        this.positionRender = new Vector2D(0, 0);
        setInfinite(action.infinite);
        setTime(action.time);
        setTiming(new CubicBezier(CubicBezier.StringToTiming(action.timing)));
        setReverse(action.reverse);
    }

    public AnimationData.Action getActionData(){
        return action;
    }

    public Vector2D getPositionRender() {
        return positionRender;
    }

    public void setPositionRender(Vector2D positionRender) {
        this.positionRender = positionRender;
    }


    private int getPositionFrameByPercent(float per){
        return (int)(per*numFrame/100);
    }

    @Override
    protected boolean OnUpdateAnimation(float per) {
        int pos = getPositionFrameByPercent(per);
        if(pos == positionFrameCurrent || pos >= numFrame){
            return false;
        }
        positionFrameCurrent = pos;
        return true;
    }

    @Override
    public void OnDraw(Canvas canvas) {
        canvas.drawBitmap(action.frames.get(positionFrameCurrent).getImage().getBitmap(), positionRender.x, positionRender.y, null);
    }
}
