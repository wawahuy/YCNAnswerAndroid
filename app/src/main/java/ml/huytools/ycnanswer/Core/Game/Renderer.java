package ml.huytools.ycnanswer.Core.Game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import ml.huytools.ycnanswer.Core.Game.Event.Event;
import ml.huytools.ycnanswer.Core.Game.Event.TouchEvent;
import ml.huytools.ycnanswer.Core.Game.Schedules.ScheduleAction;
import ml.huytools.ycnanswer.Core.Game.Schedules.ScheduleCallback;
import ml.huytools.ycnanswer.Core.LinkedListQueue;
import ml.huytools.ycnanswer.Core.Math.Vector2D;

/***
 *
 */
public class Renderer implements SurfaceHolder.Callback, View.OnTouchListener {

    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    IGameObject gameObject;
    Callback callback;
    boolean flagUpdate;
    boolean flagCreated;

    /// ... Need Update ...
    /// Event
    LinkedListQueue<Event> events;

    public Renderer(SurfaceView surfaceView, IGameObject gameObject) {
        this.surfaceView = surfaceView;
        this.gameObject = gameObject;
        this.surfaceHolder = surfaceView.getHolder();
        this.events = new LinkedListQueue<>();
        this.surfaceView.setOnTouchListener(this);
    }

    public void updateInput(){
        events.updateQueue();
        for(Event event:events){
            gameObject.updateInput(event);

        }
        events.clear();
    }

    public void update(){
        flagUpdate = gameObject.update();
    }

    public void render(){
        if(flagUpdate){
            Canvas canvas = surfaceHolder.lockCanvas();
            if(canvas == null){
                return;
            }

            synchronized (surfaceHolder){
                /// Clear
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
                /// Draw
                gameObject.draw(canvas);
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

    public void transparent(){
        // transparent
        surfaceView.setZOrderOnTop(true);
        surfaceView.getHolder().setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        GameDirector.getInstance().registration(this);

        // clear background transparent
        Canvas canvas = surfaceHolder.lockCanvas();
        synchronized (surfaceHolder){
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
        }
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Vector2D size = new Vector2D(i1, i2);
        if(!flagCreated){
            callback.OnCreate(size);
            flagCreated = true;
        }
        callback.OnResume(size);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        GameDirector.getInstance().cancelRegistration(this);
        callback.OnDestroy();
    }

    @Override
    public boolean onTouch(View view, final MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        float x = motionEvent.getX();
        float y = motionEvent.getY();

        switch (action) {
                case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        events.addQueue(new TouchEvent(TouchEvent.TouchType.BEGIN, new Vector2D(x, y)));
                        break;

                case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        events.addQueue(new TouchEvent(TouchEvent.TouchType.END, new Vector2D(motionEvent.getX(), motionEvent.getY())));
                        break;
        }
        return true;
    }

    public interface Callback {
        void OnCreate(Vector2D size);
        void OnResume(Vector2D size);
        void OnDestroy();
    }
}
