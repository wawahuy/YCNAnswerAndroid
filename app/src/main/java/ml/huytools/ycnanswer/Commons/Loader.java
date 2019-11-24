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

    public static Loader Create(Callback callback){
        return new Loader(callback);
    }

    public void change(final Object object){
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.OnChangeLoad(object, Loader.this);
            }
        });
    }

    public void restart(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                Loader.this.isStop = true;
                Loader.Create(Loader.this.callback).start();
            }
        });
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
