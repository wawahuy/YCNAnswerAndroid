package ml.huytools.ycnanswer.Core.Game;

import android.graphics.Canvas;

import ml.huytools.ycnanswer.Core.Game.Event.Event;

public interface IGameObject {
    void draw(Canvas canvas);
    boolean update();
    void updateInput(Event event);
}
