package ml.huytools.ycnanswer.Views.GameViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/***
 * CustomSurfaceView.java
 * Author: Nguyen Gia Huy
 * Project: https://github.com/wawahuy/YCNAnswerAndroid
 * Start: 21/11/2019
 * Update:
 *
 */
public abstract class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback, IRender {
    protected SurfaceHolder holder;

    public CustomSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Canvas canvas = holder.lockCanvas();
        OnInit(canvas);
        holder.unlockCanvasAndPost(canvas);
        registerLoop();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        unregisterLoop();
    }

    public abstract void OnInit(Canvas canvas);

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
}
