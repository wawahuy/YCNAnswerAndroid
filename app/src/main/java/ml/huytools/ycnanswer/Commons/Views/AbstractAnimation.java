package ml.huytools.ycnanswer.Commons.Views;

import android.graphics.Canvas;
import android.os.SystemClock;

import ml.huytools.ycnanswer.Commons.Math.Vector2D;

/**
 * Xây dựng các chuyển động hoàn hảo
 */
public abstract class AbstractAnimation implements IRender {

    /// Cubic Bezier Timing
    private CubicBezier timing;

    /// Thời gian thưc hiện hành động
    private float time;

    /// Thực hiện đảo ngược
    boolean isReverse;

    /// Lập vô hạn
    boolean isInfinite;


    /// ------------
    private long timeStart;
    private boolean isLoop;
    private boolean isReverseCurrent;
    /// test public
    public long dt;


    public AbstractAnimation(){
        time = 0;
        isReverse = false;
        isInfinite = false;
        timing = new CubicBezier(CubicBezier.TIMING.Linear);
        reset();
    }


    public CubicBezier getTiming() {
        return timing;
    }

    public void setTiming(CubicBezier timing) {
        this.timing = timing;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public boolean isReverse() {
        return isReverse;
    }

    public void setReverse(boolean reverse) {
        isReverse = reverse;
    }

    public boolean isInfinite() {
        return isInfinite;
    }

    public void setInfinite(boolean infinite) {
        isInfinite = infinite;
    }


    public boolean isLoop() {
        return isLoop;
    }


    public void reset(){
        timeStart = System.currentTimeMillis();
        isReverseCurrent = false;
        isLoop = true;
    }

    @Override
    public boolean OnUpdate(int sleep) {

        if(!isLoop)
            return false;

        long t = System.currentTimeMillis();
        dt = t - timeStart;

        if(dt > time) {
            if (isInfinite) {
                timeStart = t - (long) (time * 0.01f);
                dt = t - timeStart;
                isReverseCurrent = isReverse ? !isReverseCurrent : false;
            } else {
                isLoop = false;
            }
        }


        float timePer = dt * 100 / (float) time;

        /// Cubic bezier
        /// x axis is time
        /// y axis is progression
        Vector2D cTim = timing.B((isReverseCurrent ? 100 - timePer : timePer) / 100.0f);

        return OnUpdateAnimation(cTim.y * 100);
    }


    //// Update with per 0->100
    protected abstract boolean OnUpdateAnimation(float per);

}
