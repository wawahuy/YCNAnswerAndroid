package ml.huytools.ycnanswer.Views.GameViews.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import ml.huytools.ycnanswer.Views.GameViews.Effects.Effect;
import ml.huytools.ycnanswer.Views.GameViews.Effects.EffectCircle;
import ml.huytools.ycnanswer.Views.GameViews.Effects.EffectManager;
import ml.huytools.ycnanswer.Views.GameViews.RenderLooper;

public class CountDown extends SurfaceView implements RenderLooper.ILooper {

    final int BARSIZE = 20;

    SurfaceHolder holder;
    RenderLooper looper;
    EffectManager effectManager;

    Paint textPaint;
    Paint barPaint;
    Paint backgroundPaint;
    int timeCountDown;
    int timeCurrent;
    int step;
    int cx, cy, cw, ch;


    public CountDown(Context context, AttributeSet attrs) {
        super(context, attrs);
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.RGBA_8888);
        holder = getHolder();
        timeCurrent = 0;
        timeCountDown = 100000;
        step = 0;


        /// init text
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(50);
        textPaint.setTextAlign(Paint.Align.CENTER);

        /// background
        backgroundPaint = new Paint();
        backgroundPaint.setARGB(255, 20, 29, 58);
        backgroundPaint.setAntiAlias(true);

        /// bar
        barPaint = new Paint();
        barPaint.setARGB(255,255, 102, 0);
        barPaint.setAntiAlias(true);

        /// effect
        effectManager = new EffectManager();

        looper = new RenderLooper(this);
        looper.setFPS(25);
        looper.execute();
    }

    public void setTimeCountDown(int seconds){
        this.timeCountDown = seconds*1000;
    }

    public void start(){
        timeCurrent = timeCountDown;
    }

    public void stop(){
    }

    @Override
    public void update() {
        if(canDeepSleep())
            return;

        int sleep = looper.getSleep();
        timeCurrent -= sleep;
        step += sleep;

        /// Cap nhat mau
        float per = timeCurrent*100/timeCountDown;
        int r, g, b;
        if(per > 66){
            r = 89;
            g = 168;
            b = 105;
        } else if(per > 33){
            r = 255;
            g = 102;
            b = 0;
        } else {
            r = 250;
            g = 20;
            b = 0;
        }
        barPaint.setARGB(255, r, g, b);


        // cap nhat hieu ung
        float t = per/100*1000;
        t = t < 300 ? 300 : t;
        if(step > t){
            step = 0;
            EffectCircle effect = new EffectCircle(cw, cx, cy);
            effect.setColor(255,r, g, b);
            effect.setAlphaUpdate(20);
            effect.setRadiusUpdate(5);
            effect.setColorUpdate(0);
            effectManager.add(effect);
        }

        effectManager.update();
    }

    @Override
    public void draw() {
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) {
        } else {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);

            // draw effect
            cy = canvas.getHeight()/2;
            cx = canvas.getWidth()/2;
            cw = cx/2;
            ch = cy/2;
            effectManager.draw(canvas);

            // draw full bar load
            canvas.drawCircle(cx, cy, cw, barPaint);

            // draw bar out
            int barOutAngle = (timeCountDown - timeCurrent)*360/timeCountDown;
            RectF rectF = new RectF(cw, ch, cx+cw, cy+ch);
            canvas.drawArc(rectF, 270, barOutAngle, true, backgroundPaint);

            // draw bg
            canvas.drawCircle(cx, cy, cw-BARSIZE, backgroundPaint);

            // draw text
            int t = timeCurrent/1000;
            int yt = cy - (int)(textPaint.descent() + textPaint.ascent()) / 2;
            canvas.drawText(Integer.toString(t), cx, yt, textPaint);


            holder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean canDeepSleep() {
        return timeCurrent <= 0;
    }

}