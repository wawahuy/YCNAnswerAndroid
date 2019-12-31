package ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings;

import ml.huytools.ycnanswer.Core.Game.Actions.Action;

public abstract class ActionTiming extends Action {
    protected float time;
    protected long timeStart;

    private long dt;
    private float per;

    protected ActionTiming(int time){
        this.time = time;
    }

    public float getTime() {
        return time;
    }

    /***
     * Cập nhật lại thời gian
     * Sẽ không được làm min nhưng có một phương án thay thế
     * Hãy sử dụng têm một ActionCubicBezier.Linear để thay thế
     * @param time
     */
    public void setTime(float time) {
        this.time = time;
    }

    public void computePercent(){
        dt  = getTimeCurrent() - timeStart;
        if(dt > time){
            dt = (long)time;
        }

        per = time == 0 ? 1 : dt / time;
    }

    @Override
    protected void OnActionRestart() {
        timeStart = getTimeCurrent();
    }

    @Override
    protected boolean OnActionUpdate() {
        computePercent();
        if(per >= 1){
            setFinish(true);
        }
        return updateWithPercent(per);
    }

    protected abstract boolean OnActionUpdateWithPercent(float per);

    public boolean updateWithPercent(float per){
        return OnActionUpdateWithPercent(per);
    }
}
