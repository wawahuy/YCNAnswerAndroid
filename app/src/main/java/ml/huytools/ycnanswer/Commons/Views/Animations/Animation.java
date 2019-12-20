package ml.huytools.ycnanswer.Commons.Views.Animations;

import android.graphics.Canvas;

import ml.huytools.ycnanswer.Commons.Views.AbstractAnimation;
import ml.huytools.ycnanswer.Commons.Views.CubicBezier;

public class Animation extends AbstractAnimation {
    AnimationData.Action action;
    int numFrame;
    int positionFrameCurrent;

    public Animation(AnimationData.Action action){
        this.action = action;
        this.positionFrameCurrent = 0;
        this.numFrame = action.frames.size();
        setInfinite(action.infinite);
        setTime(action.time);
        setTiming(new CubicBezier(CubicBezier.StringToTiming(action.timing)));
        setReverse(action.reverse);
    }

    public AnimationData.Action getActionData(){
        return action;
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
        canvas.drawBitmap(action.frames.get(positionFrameCurrent).getImage().getBitmap(), 0, 0, null);
    }
}
