package ml.huytools.ycnanswer.Core.Removing;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

import java.util.LinkedList;

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

    int FPS = 60;
    int sleep;
    boolean loop;

    private RenderingLoop() {
        customSurfaceViews = new LinkedList<>();
        actionQueue = new LinkedList<>();
        setFPSMax(FPS);
        sleep = 1000/4;
        loop = true;

        //fix surface view don't attach windows
        //https://github.com/anastr/FlatTimeCollection/issues/5
        //run on main thread
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                RenderingLoop.this.start();
            }
        });
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
        //tinh thoi gian ngu
        long sl;
        boolean update;

        while (loop){
            sl = System.currentTimeMillis();

            //logic
            for(CustomSurfaceView surfaceView:customSurfaceViews) {
                update = surfaceView.update();
                if(update){
                    surfaceView.draw();
                }
            }


            //upd list
            if(actionQueue.size() > 0){
                for(Runnable runnable:actionQueue){
                    runnable.run();
                }
                actionQueue.clear();
            }

            sl = System.currentTimeMillis() - sl;

            //sleep
            SystemClock.sleep(sl > sleep ? 1 : sleep);
        }
    }



    public void add(final CustomSurfaceView render){
        actionQueue.add(new Runnable() {
            @Override
            public void run() {
                if(!customSurfaceViews.contains(render)){
                    Log.v("Log", "Add Loop surfaceView to rendering!");
                    render.refreshTimeUpdate();
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
                    Log.v("Log", "Remove Loop surfaceView to rendering!");
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


    public void post(Runnable runnable){
        actionQueue.add(runnable);
    }
}
