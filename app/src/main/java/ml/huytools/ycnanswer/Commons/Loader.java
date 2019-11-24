package ml.huytools.ycnanswer.Commons;

import android.os.Handler;
import android.os.Looper;


public class Loader extends Thread {

    public interface Callback {
        void OnBackgroundLoad(Loader loader);
        void OnChangeLoad(Object object, Loader loader);
        void OnFinishLoad(Loader loader);
    }

    Callback callback;
    Handler handler;
    Thread thread;
    boolean isStop;


    private Loader(Callback callback){
        this.callback = callback;
        this.handler = new Handler(Looper.myLooper());
    }

    public static void Create(Callback callback){
        new Loader(callback).start();
    }

    public void change(final Object object){
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.OnChangeLoad(object, Loader.this);
            }
        });
    }

    public void restart(int delay){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Loader.this.isStop = true;
                Loader.Create(Loader.this.callback);
            }
        }, delay);
    }

    public void restart(){
        restart(1);
    }

    @Override
    public synchronized void start() {
        this.isStop = false;
        super.start();
    }

    @Override
    public void run() {
        callback.OnBackgroundLoad(this);

        if(isStop){
            return;
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.OnFinishLoad(Loader.this);
            }
        });
    }

}
