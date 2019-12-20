package ml.huytools.ycnanswer.Commons.Views.Animations;

import android.graphics.Canvas;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.LinkedHashMap;

import ml.huytools.ycnanswer.Commons.Annotation.JsonName;
import ml.huytools.ycnanswer.Commons.Math.Vector2D;
import ml.huytools.ycnanswer.Commons.Model;
import ml.huytools.ycnanswer.Commons.ModelManager;
import ml.huytools.ycnanswer.Commons.Views.AbstractAnimation;
import ml.huytools.ycnanswer.Commons.Views.IRender;
import ml.huytools.ycnanswer.Commons.Views.Image;

/***
 * AnimationManager.java
 * Author: Nguyen Gia Huy
 * Project: https://github.com/wawahuy/YCNAnswerAndroid
 * Start: 15/12/2019
 * Update:
 *
 */


public class AnimationManager {
    LinkedHashMap<String, Animation> animations;

    public AnimationManager(AnimationData animationData){
        animations = new LinkedHashMap<>();
        initAnimation(animationData);
    }

    public Animation get(String action){
        return animations.get(action);
    }

    private void initAnimation(AnimationData animationData){
        for(AnimationData.Action action:animationData.actions){
            animations.put(action.name, new Animation(action));
        }
    }
}
