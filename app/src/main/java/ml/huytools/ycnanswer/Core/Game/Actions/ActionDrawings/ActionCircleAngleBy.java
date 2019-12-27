package ml.huytools.ycnanswer.Core.Game.Actions.ActionDrawings;

import ml.huytools.ycnanswer.Core.Exceptions.DoNotDrawableException;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionTiming;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.CircleShape;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.Drawable;

public class ActionCircleAngleBy extends ActionTiming {
    private CircleShape circleShape;
    private int angleEStart;
    private int angleMagnitude;

    protected ActionCircleAngleBy(int time) {
        super(time);
    }

    public static ActionCircleAngleBy create(int angleEBy, int time){
        ActionCircleAngleBy actionCircleAngleEBy = new ActionCircleAngleBy(time);
        actionCircleAngleEBy.angleMagnitude = angleEBy;
        return actionCircleAngleEBy;
    }

    @Override
    protected void OnActionRestart() {
        super.OnActionRestart();
        angleEStart = circleShape.getEndAngle();
    }

    @Override
    protected boolean OnActionUpdateWithPercent(float per) {
        circleShape.setAngleSwept(angleEStart + (int)(angleMagnitude*per));
        return false;
    }

    @Override
    protected void OnActionSetup() {
        if(!(node instanceof Drawable)){
            throw new DoNotDrawableException();
        }
        circleShape = (CircleShape) node;
    }
}
