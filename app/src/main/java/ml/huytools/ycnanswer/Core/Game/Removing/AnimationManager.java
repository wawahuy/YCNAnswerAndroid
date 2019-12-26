package ml.huytools.ycnanswer.Core.Game.Removing;

import java.util.LinkedHashMap;

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
