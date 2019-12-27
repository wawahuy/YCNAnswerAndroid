package ml.huytools.ycnanswer.Core.Game.Actions.ActionDrawings;

import ml.huytools.ycnanswer.Core.Exceptions.DoNotDrawableException;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionTiming;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.CircleShape;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.Drawable;

public class ActionCircleAngleTo extends ActionTiming {
    private CircleShape circleShape;
    private int angleEStart;
    private int angleEEnd;
    private int angleMagnitude;

    protected ActionCircleAngleTo(int time) {
        super(time);
    }

    public static ActionCircleAngleTo create(int angleEEnd, int time){
        ActionCircleAngleTo actionCircleAngleETo = new ActionCircleAngleTo(time);
        actionCircleAngleETo.angleEEnd = angleEEnd;
        return actionCircleAngleETo;
    }

    @Override
    protected void OnActionRestart() {
        super.OnActionRestart();
        angleEStart = circleShape.getEndAngle();
        angleMagnitude = angleEEnd - angleEStart;
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
