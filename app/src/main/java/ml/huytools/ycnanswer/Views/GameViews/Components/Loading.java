package ml.huytools.ycnanswer.Views.GameViews.Components;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import ml.huytools.ycnanswer.R;
import ml.huytools.ycnanswer.Views.GameViews.RenderLooper;

/***
 * Loading.java
 * Author: Nguyen Gia Huy
 * Project: https://github.com/wawahuy/YCNAnswerAndroid
 * Start: 20/11/2019
 * Update:
 *
 *
 */
public class Loading extends SurfaceView implements RenderLooper.ILooper, SurfaceHolder.Callback {

    RenderLooper looper;
    SurfaceHolder holder;

    Paint paintBg;

    public Loading(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Transparent
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.RGBA_8888);

        init();

    }

    public static Loading Create(Activity activity){
        Loading loading = new Loading(activity.getBaseContext(), null);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        activity.addContentView(loading, params);

        return loading;
    }

    public void removeOnView(){
        ((ViewGroup)this.getParent()).removeView(this);
    }

    public void init(){
        holder = getHolder();

        paintBg = new Paint();
        paintBg.setARGB(25, 0, 0, 0);

        looper = new RenderLooper(this);
        looper.setFPS(30);
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        draw();
        looper.execute();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) {
        } else {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);

            canvas.drawARGB(100, 0, 0, 0);

            holder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean canDeepSleep() {
        return false;
    }
}
