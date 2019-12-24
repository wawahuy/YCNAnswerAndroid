package ml.huytools.ycnanswer.Commons.Removing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/***
 * CustomSurfaceView.java
 * Author: Nguyen Gia Huy
 * Project: https://github.com/wawahuy/YCNAnswerAndroid
 * Start: 23/11/2019
 * Update:
 *
 */
public abstract class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback, IRender {
    protected SurfaceHolder holder;
    protected long time;
    protected boolean isPaused, hasCallInit;

    public CustomSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();
        holder.addCallback(this);
        isPaused = false;
        hasCallInit = false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if(!hasCallInit) {
            forceInit();
            hasCallInit = true;
        }
        OnStart();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        unregisterLoop();
    }

    public abstract void OnInit(Canvas canvas);

    public void forceInit(){
        Canvas canvas = holder.lockCanvas();
        OnInit(canvas);
        holder.unlockCanvasAndPost(canvas);
    }

    public void OnStart(){
        registerLoop();
    }

    public void unregisterLoop(){
        RenderingLoop.getInstance().remove(this);
    }

    public void registerLoop(){
        RenderingLoop.getInstance().add(this);
    }

    public void transparent(){
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.RGBA_8888);
    }

    public void clearTransparent(Canvas canvas){
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
    }

    public void refreshTimeUpdate(){
        time = System.currentTimeMillis();
    }

    public boolean post(Runnable runnable){
        RenderingLoop.getInstance().post(runnable);
        return false;
    }

    public void draw(){
        Canvas canvas = holder.lockCanvas();
        if(canvas != null) {
            synchronized (holder) {
                OnDraw(canvas);
            }
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public boolean update(){
        if(isPaused)
            return false;

        long time_old = time;
        refreshTimeUpdate();
        return OnUpdate((int)(time-time_old));
    }

    public void pause(){
        isPaused = true;
    }

    public void start(){
        isPaused = false;
    }

}
