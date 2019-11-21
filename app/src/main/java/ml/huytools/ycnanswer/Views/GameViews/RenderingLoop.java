package ml.huytools.ycnanswer.Views.GameViews;

import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.LinkedList;
import java.util.List;

/***
 * RenderingLoop.java
 * Author: Nguyen Gia Huy
 * Project: https://github.com/wawahuy/YCNAnswerAndroid
 * Start: 21/11/2019
 * Update:
 *
 */
public class RenderingLoop extends Thread {
    private static final RenderingLoop ourInstance = new RenderingLoop();

    public static RenderingLoop getInstance() {
        return ourInstance;
    }

    LinkedList<CustomSurfaceView> customSurfaceViews;
    LinkedList<Runnable> actionQueue;

    int FPS = 30;
    int sleep;
    boolean loop;

    private RenderingLoop() {
        customSurfaceViews = new LinkedList<>();
        actionQueue = new LinkedList<>();
        setFPSMax(FPS);
        loop = true;
        start();
    }

    @Override
    public void start(){
        loop = true;
        super.start();
    }

    public void finish(){
        loop = false;
    }

    @Override
    public void run() {
        SurfaceHolder holder;
        Canvas canvas;

        /// Can fix khi thoi gian update lan ap thoi gian render
        /// Su dung bien luu thoi gian 1 frame va can giai quyet no
        /// ........ UPDATE ..............
        while (loop){
            //logic
            for(CustomSurfaceView surfaceView:customSurfaceViews) {
                surfaceView.OnUpdate(sleep);
            }

            //draw
            for(CustomSurfaceView surfaceView:customSurfaceViews) {
                holder = surfaceView.getHolder();
                canvas = holder.lockCanvas();
                if(canvas == null)
                    continue;

                synchronized (holder){
                    surfaceView.OnDraw(canvas);
                }
                holder.unlockCanvasAndPost(canvas);
            }

            //upd list
            if(actionQueue.size() > 0){
                for(Runnable runnable:actionQueue){
                    runnable.run();
                }
                actionQueue.clear();
            }

            //sleep
            SystemClock.sleep(sleep);
        }
    }



    public void add(final CustomSurfaceView render){
        actionQueue.add(new Runnable() {
            @Override
            public void run() {
                if(!customSurfaceViews.contains(render)){
                    Log.v("Log", "Add surfaceView to rendering!");
                    customSurfaceViews.add(render);
                    setFPSMax(FPS);
                }
            }
        });
    }

    public void remove(final CustomSurfaceView render){
        actionQueue.add(new Runnable() {
            @Override
            public void run() {
                if(customSurfaceViews.contains(render)){
                    Log.v("Log", "Remove queue surfaceView to rendering!");
                    customSurfaceViews.remove(render);

                    if(customSurfaceViews.size()<= 0){
                        sleep = 1000/4;
                    }
                }
            }
        });
    }

    public void setFPSMax(int fps){
        FPS = fps;
        sleep = 1000/fps;
    }


}
