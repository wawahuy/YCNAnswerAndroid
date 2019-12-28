package ml.huytools.ycnanswer.Core.Game;

import android.graphics.Canvas;

public interface IGameObject {
    void draw(Canvas canvas);
    boolean update();
}
