package ml.huytools.ycnanswer.Core.Game.Scenes;

import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;

import ml.huytools.ycnanswer.Core.Game.IGameObject;
import ml.huytools.ycnanswer.Core.Game.Schedules.Scheduler;

public abstract class SceneBase implements IGameObject {
    protected Camera camera;
    protected Scheduler scheduler;

    public SceneBase(){
        camera = new Camera();
        scheduler = new Scheduler();
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void drawApplyCameraToModel(Canvas canvas){
        canvas.save();
        Matrix matrix = new Matrix();
        camera.getMatrix(matrix);
        /// ... * [Entity]
        ///        ----- = CameraMatrix * Transform
        /// ... = Project * CameraDifMatrix
        canvas.concat(matrix);
        OnDraw(canvas);
        canvas.restore();
    }

    @Override
    public void draw(Canvas canvas) {
        /// Project * View * Entity
        ///           ----   -----
        ///         Camera  Transform
        camera.applyToCanvas(canvas);
        OnDraw(canvas);
    }

    @Override
    public boolean update() {
        return scheduler.update() || OnUpdate();
    }

    protected abstract void OnDraw(Canvas canvas);
    protected abstract boolean OnUpdate();
}
