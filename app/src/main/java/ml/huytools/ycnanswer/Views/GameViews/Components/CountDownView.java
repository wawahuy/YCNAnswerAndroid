package ml.huytools.ycnanswer.Views.GameViews.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.util.AttributeSet;

import ml.huytools.ycnanswer.Commons.Math.Vector2D;
import ml.huytools.ycnanswer.Commons.Math.Vector3D;
import ml.huytools.ycnanswer.Commons.Views.CustomSurfaceView;
import ml.huytools.ycnanswer.Views.GameViews.Effects.EffectCircle;
import ml.huytools.ycnanswer.Views.GameViews.Effects.EffectManager;


/***
 * CountDown.java
 * Author: Nguyen Gia Huy
 * Project: https://github.com/wawahuy/YCNAnswerAndroid
 * Start: 19/11/2019
 * Update: 21/11/2019
 *
 *
 */
public class CountDownView extends CustomSurfaceView {

    /// Vien cua thanh xoay nguoc
    final int BAR_SIZE = 20;

    EffectManager effectManager;
    Paint textPaint;
    Paint barPaint;
    Paint backgroundPaint;

    /// Thoi gian dem nguoc
    int timeCountDown;

    /// Thoi gian dem nguoc hien tai
    int timeCurrent;

    /// Tong thoi gian cua cac frame
    /// Dung de tao su kien tick, va effect
    int step;

    int yTextTrans;
    Vector2D centerPosition, quarterSize;
    RectF rectF;

    /// Su kien Tick va Timeout
    boolean hasCallEnd;
    Callback callback;

    public CountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.transparent();

        /// default
        timeCurrent = 0;
        timeCountDown = 100000;
        step = 0;

        /// init paint text
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(40);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);

        /// init paint background
        backgroundPaint = new Paint();
        backgroundPaint.setARGB(255, 20, 29, 58);
        backgroundPaint.setAntiAlias(true);

        /// init paint bar
        barPaint = new Paint();
        barPaint.setARGB(255,255, 102, 0);
        barPaint.setAntiAlias(true);

        /// init list effect
        effectManager = new EffectManager();

    }

    public void setCallback(Callback callback){
        this.callback = callback;
    }

    public void setTimeCountDown(int seconds){
        this.timeCountDown = seconds*1000;
    }

    public void start(){
        effectManager.removeAll();
        hasCallEnd = false;
        timeCurrent = timeCountDown;

        /// add loop
        super.registerLoop();
    }

    public void stop(){
        timeCurrent = -1;

        /// unregister
        super.unregisterLoop();
    }

    @Override
    public void OnUpdate(int sleep) {

        // Kiem tra khi timeout (timeCurrent <= 0)
        if(timeCurrent<=0){

            // Call event
            if(!hasCallEnd){
                hasCallEnd = true;
                if(callback != null){

                    /// unregister
                    super.unregisterLoop();

                    /// call
                    callback.OnEnd();
                }
            }
            return;
        }

        // tinh step frame
        timeCurrent -= sleep;
        step += sleep;

        /// Cap nhat mau
        float per = timeCurrent*100/timeCountDown;
        Vector3D color; // r, g, b as x, y, z
        if(per > 66){
            color = new Vector3D(89, 168, 105);
        } else if(per > 33){
            color = new Vector3D(255, 102, 0);
        } else {
            color = new Vector3D(250, 20, 0);
        }
        barPaint.setARGB(255, (int)color.x, (int)color.y, (int)color.z);

        // tick
        if(step > 999){
            // callback
            if(callback != null){
                callback.OnTick(timeCurrent);
            }

            // effect
            step = 0;
            EffectCircle effect = new EffectCircle(centerPosition);
            effect.setColor(color);
            effect.setAlphaAnimation(255, 0);
            effect.setRadiusAnimation((int)quarterSize.x - 5, (int)centerPosition.x);
            effect.setTime(1000);
            effectManager.add(effect);
        }

        effectManager.OnUpdate(sleep);
    }

    @Override
    public void OnDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);

        // draw effect
        effectManager.OnDraw(canvas);

        // draw full bar load
        canvas.drawCircle(centerPosition.x, centerPosition.y, quarterSize.x, barPaint);

        // draw bar out
        int barOutAngle = (timeCountDown - timeCurrent) * 360 / timeCountDown;
        canvas.drawArc(rectF, 270, barOutAngle, true, backgroundPaint);

        // draw bg
        canvas.drawCircle(centerPosition.x, centerPosition.y, quarterSize.x - BAR_SIZE, backgroundPaint);

        // draw text
        int t = timeCurrent / 1000;
        canvas.drawText(Integer.toString(t), centerPosition.x, yTextTrans, textPaint);
    }


    @Override
    public void OnInit(Canvas canvas) {
        centerPosition = new Vector2D(canvas.getWidth()/2, canvas.getHeight()/2);
        quarterSize = centerPosition.div(2.0f);
        yTextTrans = (int)centerPosition.y - (int)(textPaint.descent() + textPaint.ascent()) / 2;
        rectF = new RectF(quarterSize.x, quarterSize.y, centerPosition.x+quarterSize.x, quarterSize.y+centerPosition.y);
    }

    @Override
    public void OnStart() {
        //comment super don't register loop
        //super.OnStart();
    }


    public interface Callback {
        void OnEnd();
        void OnTick(int cur);
    }
}