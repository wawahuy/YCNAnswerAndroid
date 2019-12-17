package ml.huytools.ycnanswer.Commons.Views.Animations;

import android.graphics.Canvas;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ml.huytools.ycnanswer.Commons.Annotation.JsonName;
import ml.huytools.ycnanswer.Commons.Math.Vector2D;
import ml.huytools.ycnanswer.Commons.Model;
import ml.huytools.ycnanswer.Commons.ModelManager;
import ml.huytools.ycnanswer.Commons.Views.AbstractAnimation;
import ml.huytools.ycnanswer.Commons.Views.Image;

/***
 * AnimationManager.java
 * Author: Nguyen Gia Huy
 * Project: https://github.com/wawahuy/YCNAnswerAndroid
 * Start: 15/12/2019
 * Update:
 *
 */


public class AnimationManager extends AbstractAnimation {
    Image image;
    Vector2D positionRender;
    int positionFrameCurrent;

    public AnimationManager(Image image){
        this.image = image;
        this.positionFrameCurrent = 0;
    }

    public void setPosition(Vector2D position){
        positionRender = position;
    }

    @Override
    protected boolean OnUpdateAnimation(float per) {
        return false;
    }

    @Override
    public void OnDraw(Canvas canvas) {
    }

}
