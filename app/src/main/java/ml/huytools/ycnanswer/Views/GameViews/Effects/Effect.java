package ml.huytools.ycnanswer.Views.GameViews.Effects;

import ml.huytools.ycnanswer.Core.Game.Removing.AbstractAnimation;
import ml.huytools.ycnanswer.Core.Removing.IRender;

public abstract class Effect extends AbstractAnimation implements IRender {
    private Runnable eventRemove;

    public abstract  boolean canRemove();

    public void setEventRemoveListener(Runnable runnable){
        eventRemove = runnable;
    }

    public void runEventRemove(){
        if(eventRemove != null){
            eventRemove.run();
        }
    }
}
