package ml.huytools.ycnanswer.Views.GameViews.Effects;

import android.graphics.Canvas;

import java.util.LinkedList;

import ml.huytools.ycnanswer.Commons.Removing.IRender;

public class EffectManager<T extends Effect> implements IRender {

    LinkedList<Effect> effects;

    public EffectManager(){
        effects = new LinkedList<>();
    }

    public void add(Effect effect){
        effects.add(effect);
    }

    public void add(LinkedList<T> effects){
        for(Effect effect:effects){
            this.effects.add(effect);
        }
    }

    public void removeAll(){
        effects.clear();
    }

    public void remove(Effect effect){
        effects.remove(effect);
    }

    @Override
    public boolean OnUpdate(int sleep){
        LinkedList<Effect> effectRM = new LinkedList<>();
        for(Effect effect:effects){
            effect.OnUpdate(sleep);
            if(effect.canRemove()){
                effectRM.add(effect);
            }
        }

        for(Effect effect:effectRM){
            effect.runEventRemove();
            effects.remove(effect);
        }

        return true;
    }

    @Override
    public void OnDraw(Canvas canvas){
        for(Effect effect:effects){
            effect.OnDraw(canvas);
        }
    }

    public Effect get(int index){
        return effects.get(index);
    }
}
