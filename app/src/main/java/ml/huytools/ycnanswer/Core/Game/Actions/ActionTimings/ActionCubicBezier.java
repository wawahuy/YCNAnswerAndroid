package ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings;

import ml.huytools.ycnanswer.Core.Game.Actions.Action;
import ml.huytools.ycnanswer.Core.Math.CubicBezier;

public class ActionCubicBezier extends Action {
    protected float time;
    protected long timeStart;
    protected ActionTiming actionTiming;
    protected CubicBezier cubicBezier;

    /// Cache
    private long dt;
    private float per, perDt;

    public static ActionCubicBezier create(ActionTiming actionTiming, CubicBezier cubicBezier){
        ActionCubicBezier actionCubicBezier = new ActionCubicBezier();
        actionCubicBezier.actionTiming = actionTiming;
        actionCubicBezier.cubicBezier = cubicBezier;
        actionCubicBezier.time = actionTiming.getTime();
        return actionCubicBezier;
    }

    public static ActionCubicBezier Ease(ActionTiming actionTiming){
        return create(actionTiming, CubicBezier.Ease);
    }

    public static ActionCubicBezier EaseIn(ActionTiming actionTiming){
        return create(actionTiming, CubicBezier.EaseIn);
    }

    public static ActionCubicBezier EaseInOut(ActionTiming actionTiming){
        return create(actionTiming, CubicBezier.EaseInOut);
    }

    public static ActionCubicBezier EaseOut(ActionTiming actionTiming){
        return create(actionTiming, CubicBezier.EaseOut);
    }

    public float getTime() {
        return time;
    }

    public void setTime(float timeNew) {
        /// Vấn đề làm mịn hiệu ứng theo thời gian hiện taij
        /// Khi thời gian đột ngột thay đổi dẫ đến percent đột ngột tăng giảm
        /// percent tại t1, t2. cần quay về timeStart có per tương ứng
        /// Khoản gian thực thi mới = phần trăm thời gian hiện tai * thời gian mới;
        /// Thời gian bắt đầu = timeStart - Khoản thời gian mới + Khoản thời gian cũ
        computePercent();
        long timeDTNew = (long)(perDt*timeNew);
        long timeDTOld = (long)(perDt*time);
        timeStart = timeStart - timeDTNew + timeDTOld;

        /// Set
        this.time = timeNew;
        actionTiming.setTime(timeNew);
    }

    public void computePercent(){
        dt    = getTimeCurrent() - timeStart;
        if(dt > time){
            dt = (long)time;
        }

        perDt = time == 0 ? 1 : dt / time;
        per   = cubicBezier.computeProgressionOnY(perDt);
    }

    @Override
    protected void OnActionSetup() {
        actionTiming.setup(node);
    }

    @Override
    protected void OnActionRestart() {
        timeStart = getTimeCurrent();
        actionTiming.restart();
    }

    @Override
    protected boolean OnActionUpdate() {
        computePercent();
        if( perDt >= 1 ){
            setFinish(true);
        }
        return actionTiming.updateWithPercent(per);
    }
}
