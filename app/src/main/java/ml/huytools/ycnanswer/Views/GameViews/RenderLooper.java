package ml.huytools.ycnanswer.Views.GameViews;

import android.os.AsyncTask;
import android.os.SystemClock;

public class RenderLooper extends AsyncTask<Void, Void, Void> {
    ILooper looper;
    boolean loop;
    int sleep;

    public RenderLooper(ILooper looper){
        this.loop = true;
        this.sleep = 1000/30;
        this.looper = looper;
    }

    public void stop(){
        this.loop = false;
    }

    public void setFPS(int fps){
        assert (fps < 60);
        this.sleep = 1000/fps;
    }

    public int getSleep() {
        return sleep;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        while (loop){
            looper.update();
            publishProgress();
            if(looper.canDeepSleep()){
                SystemClock.sleep(250);
            } else {
                SystemClock.sleep(sleep);
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        looper.draw();
    }

    public interface ILooper {
        void update();
        void draw();
        boolean canDeepSleep();
    }
}
