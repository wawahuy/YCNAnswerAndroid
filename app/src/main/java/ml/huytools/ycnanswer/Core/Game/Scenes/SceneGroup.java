package ml.huytools.ycnanswer.Core.Game.Scenes;

import android.graphics.Canvas;

import ml.huytools.ycnanswer.Core.Game.Event.Event;
import ml.huytools.ycnanswer.Core.LinkedListQueue;

public class SceneGroup extends SceneBase {
    LinkedListQueue<SceneBase> scenes;

    public SceneGroup() {
        super();
        scenes = new LinkedListQueue<>();
    }

    public void add(SceneBase sceneBase){
        scenes.addQueue(sceneBase);
    }

    public void remove(SceneBase sceneBase){
        scenes.removeQueue(sceneBase);
    }


    @Override
    protected void OnDraw(Canvas canvas) {
        for (SceneBase scene:scenes){
            scene.drawApplyCameraToModel(canvas);
        }
    }

    @Override
    protected boolean OnUpdate() {
        /// Update scene child
        boolean hasUpdate =false;
        for (SceneBase scene:scenes){
            hasUpdate = scene.update() || hasUpdate;
        }

        /// Update queue
        scenes.updateQueue();

        return hasUpdate;
    }

    @Override
    public void updateInput(Event event) {
        for(SceneBase scene:scenes){
            scene.updateInput(event);
        }
    }
}
