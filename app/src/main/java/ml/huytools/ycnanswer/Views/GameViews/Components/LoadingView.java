package ml.huytools.ycnanswer.Views.GameViews.Components;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import ml.huytools.ycnanswer.Views.GameViews.CustomSurfaceView;
import ml.huytools.ycnanswer.Views.GameViews.Effects.EffectCircleRotate;
import ml.huytools.ycnanswer.Views.GameViews.Effects.EffectManager;

/***
 * Loading.java
 * Author: Nguyen Gia Huy
 * Project: https://github.com/wawahuy/YCNAnswerAndroid
 * Start: 20/11/2019
 * Update: 21/11/2019
 *
 *
 */
public class LoadingView extends CustomSurfaceView {
    final int EDGE = 60;

    Paint paintBg;
    EffectManager effectManager;

    int centerX, centerY;

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.transparent();
        paintBg = new Paint();
        paintBg.setARGB(25, 0, 0, 0);
        effectManager = new EffectManager();
    }

    public static LoadingView Create(Activity activity){
        LoadingView loading = new LoadingView(activity.getBaseContext(), null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        activity.addContentView(loading, params);

        return loading;
    }

    public void removeOnView(){
        super.unregisterLoop();
        ((ViewGroup)this.getParent()).removeView(this);
    }

    private void addEffect(){
        for(int i=0; i<10; i++){
            effectManager.add(new EffectCircleRotate(centerX, centerY,10, 60, 110*i));
        }
    }

    @Override
    public void OnInit(Canvas canvas) {
        centerX = canvas.getWidth()/2;
        centerY = canvas.getHeight()/2;
        addEffect();
    }


    @Override
    public void OnUpdate(int sleep) {
        effectManager.OnUpdate(sleep);
    }

    @Override
    public void OnDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
        canvas.drawARGB(100, 0, 0, 0);
        effectManager.OnDraw(canvas);
    }

}
