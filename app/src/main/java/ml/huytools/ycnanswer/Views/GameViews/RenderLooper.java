package ml.huytools.ycnanswer.Views.GameViews;

import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/***
 * RenderLooper.java
 * Author: Nguyen Gia Huy
 * Project: https://github.com/wawahuy/YCNAnswerAndroid
 * Start: 19/11/2019
 * Update: 20/11/2019
 *
 */
public class RenderLooper extends Thread {
    ILooper looper;
    boolean loop;
    int sleep;

    SurfaceHolder sfHolder;

    public RenderLooper(ILooper looper){
        super();
        this.loop = true;
        this.sleep = 1000/30;
        this.looper = looper;
        sfHolder = looper.getSurfaceHolder();
    }

    public void startThread(){
        super.start();
    }

    public void stopThread(){
        this.loop = false;
    }

    public void setFPS(int fps){
        this.sleep = 1000/fps;
    }

    public int getSleep() {
        return sleep;
    }

    @Override
    public void run() {
        super.run();
        Canvas canvas = null;


        while (loop){

            // logic
            looper.onUpdate();

            // draw
            canvas = sfHolder.lockCanvas();
            if(canvas != null){
                synchronized (sfHolder){
                    looper.onDraw(canvas);
                }
                sfHolder.unlockCanvasAndPost(canvas);
            }

            if(looper.canDeepSleep()){
                SystemClock.sleep(250);
//                try {
//                    sleep(250);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            } else {
                SystemClock.sleep(sleep);
//                try {
//                    sleep(sleep);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        }
    }

    public interface ILooper {
        void onUpdate();
        void onDraw(Canvas canvas);
        boolean canDeepSleep();
        SurfaceHolder getSurfaceHolder();
    }
}
