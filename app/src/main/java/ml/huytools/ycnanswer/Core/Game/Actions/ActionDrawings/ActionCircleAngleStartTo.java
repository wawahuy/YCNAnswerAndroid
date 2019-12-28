package ml.huytools.ycnanswer.Core.Game.Actions.ActionDrawings;

import ml.huytools.ycnanswer.Core.Exceptions.DoNotDrawableException;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionTiming;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.CircleShape;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.Drawable;

public class ActionCircleAngleStartTo extends ActionTiming {
    private CircleShape circleShape;
    private int angleEStart;
    private int angleEEnd;
    private int angleMagnitude;

    protected ActionCircleAngleStartTo(int time) {
        super(time);
    }

    public static ActionCircleAngleStartTo create(int angleSEnd, int time){
        ActionCircleAngleStartTo actionCircleAngleSTo = new ActionCircleAngleStartTo(time);
        actionCircleAngleSTo.angleEEnd = angleSEnd;
        return actionCircleAngleSTo;
    }

    @Override
    protected void OnActionRestart() {
        super.OnActionRestart();
        angleEStart = circleShape.getAngleSwept();
        angleMagnitude = angleEEnd - angleEStart;
    }

    @Override
    protected boolean OnActionUpdateWithPercent(float per) {
        circleShape.setStartAngle(angleEStart + (int)(angleMagnitude*per));
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
