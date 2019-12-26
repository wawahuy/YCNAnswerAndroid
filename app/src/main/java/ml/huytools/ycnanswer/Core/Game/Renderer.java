package ml.huytools.ycnanswer.Core.Game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import ml.huytools.ycnanswer.Core.Math.Vector2D;

/***
 *
 */
public class Renderer implements SurfaceHolder.Callback {

    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    Scene scene;
    Callback callback;
    boolean flagUpdate;
    boolean flagCreated;

    public Renderer(SurfaceView surfaceView, Scene scene) {
        this.surfaceView = surfaceView;
        this.scene = scene;
        this.surfaceHolder = surfaceView.getHolder();
    }

    public void update(){
        flagUpdate = scene.update();
    }

    public void render(){
        if(flagUpdate){
            Canvas canvas = surfaceHolder.lockCanvas();
            synchronized (surfaceHolder){
                scene.render(canvas);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public boolean isFlagUpdate() {
        return flagUpdate;
    }

    public void enableAutoRegisterDirector(Callback callback) {
        this.callback = callback;
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if(!flagCreated){
            callback.OnCreate();
            flagCreated = true;
        }
        GameDirector.getInstance().registration(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        callback.OnResume(new Vector2D(i1, i2));
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        GameDirector.getInstance().cancelRegistration(this);
        callback.OnDestroy();
    }

    public interface Callback {
        void OnCreate();
        void OnResume(Vector2D size);
        void OnDestroy();
    }
}
