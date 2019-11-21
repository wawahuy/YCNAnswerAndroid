package ml.huytools.ycnanswer.Views.GameViews.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import ml.huytools.ycnanswer.Views.GameViews.Effects.EffectCircle;
import ml.huytools.ycnanswer.Views.GameViews.Effects.EffectManager;
import ml.huytools.ycnanswer.Views.GameViews.RenderLooper;


/***
 * CountDown.java
 * Author: Nguyen Gia Huy
 * Project: https://github.com/wawahuy/YCNAnswerAndroid
 * Start: 19/11/2019
 * Update: 20/11/2019
 *
 *
 */
public class CountDownView extends SurfaceView implements RenderLooper.ILooper, SurfaceHolder.Callback {

    /// Vien cua thanh xoay nguoc
    final int BAR_SIZE = 20;

    SurfaceHolder holder;
    RenderLooper looper;
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

    /// cx, cy la trong tam
    /// cw, ch bang 1/4 do dai cua view
    /// yt la toa do y de can giua theo y cho text
    int cx, cy, cw, ch, yt;
    RectF rectF;

    /// Su kien Tick va Timeout
    boolean hasCallEnd;
    Callback callback;

    public CountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Transparent
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.RGBA_8888);

        /// default
        holder = getHolder();
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

        /// dang ki su kien SurfaceHolder
        holder.addCallback(this);

        /// Khoi tao vong lap re-draw
        looper = new RenderLooper(this);
        looper.setFPS(30);
        looper.startThread();
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
    }

    public void stop(){
        timeCurrent = -1;
    }

    @Override
    public void onUpdate() {

        // Kiem tra khi timeout (timeCurrent <= 0)
        if(canDeepSleep()){

            // Call event
            if(!hasCallEnd){
                hasCallEnd = true;
                if(callback != null){
                    callback.OnEnd();
                }
            }
            return;
        }

        // tinh step frame
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


        // tick
        if(step > 999){
            // callback
            if(callback != null){
                callback.OnTick(timeCurrent);
            }

            // effect
            step = 0;
            EffectCircle effect = new EffectCircle(cw, cx, cy);
            effect.setColor(255,r, g, b);
            effect.setAlphaUpdate(20);
            effect.setRadiusUpdate(5);
            effect.setColorUpdate(0);
            effectManager.add(effect);
        }

        effectManager.update(sleep);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);

        // draw effect
        effectManager.draw(canvas);

        // draw full bar load
        canvas.drawCircle(cx, cy, cw, barPaint);

        // draw bar out
        int barOutAngle = (timeCountDown - timeCurrent) * 360 / timeCountDown;
        canvas.drawArc(rectF, 270, barOutAngle, true, backgroundPaint);

        // draw bg
        canvas.drawCircle(cx, cy, cw - BAR_SIZE, backgroundPaint);

        // draw text
        int t = timeCurrent / 1000;
        canvas.drawText(Integer.toString(t), cx, yt, textPaint);
    }

    @Override
    public boolean canDeepSleep() {
        return timeCurrent <= 0;
    }

    @Override
    public SurfaceHolder getSurfaceHolder() {
        return holder;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        /// Init
        Canvas canvas = holder.lockCanvas();
        cx = canvas.getWidth()/2;
        cy = canvas.getHeight()/2;
        cw = cx/2;
        ch = cy/2;
        yt = cy - (int)(textPaint.descent() + textPaint.ascent()) / 2;
        rectF = new RectF(cw, ch, cx+cw, cy+ch);
        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    public interface Callback {
        void OnEnd();
        void OnTick(int cur);
    }
}