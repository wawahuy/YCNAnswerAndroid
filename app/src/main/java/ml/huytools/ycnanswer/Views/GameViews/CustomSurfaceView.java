package ml.huytools.ycnanswer.Views.GameViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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
        RenderingLoop.getInstance().add(this);
        holder.unlockCanvasAndPost(canvas);
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

    public void transparent(){
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.RGBA_8888);
    }
}
