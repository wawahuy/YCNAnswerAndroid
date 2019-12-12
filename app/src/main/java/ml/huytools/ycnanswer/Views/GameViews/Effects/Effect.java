package ml.huytools.ycnanswer.Views.GameViews.Effects;

import java.util.LinkedList;

import ml.huytools.ycnanswer.Commons.Views.AbstractAnimation;
import ml.huytools.ycnanswer.Commons.Views.IRender;

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
