package ml.huytools.ycnanswer.Views.GameViews.Effects;

import android.graphics.Canvas;

import java.util.LinkedList;

import ml.huytools.ycnanswer.Views.GameViews.IRenderable;

public class EffectManager implements IRenderable {

    LinkedList<Effect> effects;

    public EffectManager(){
        effects = new LinkedList<>();
    }

    public void add(Effect effect){
        effects.add(effect);
    }

    public void remove(Effect effect){
        effects.remove(effect);
    }

    @Override
    public void update(){
        for(Effect effect:effects){
            effect.update();
            if(effect.canRemove()){
                effects.remove(effect);
            }
        }
    }

    @Override
    public void draw(Canvas canvas){
        for(Effect effect:effects){
            effect.draw(canvas);
        }
    }
}
