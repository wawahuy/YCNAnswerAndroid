package ml.huytools.ycnanswer.Core.Game.Event;

import ml.huytools.ycnanswer.Core.Game.Scenes.Node;
import ml.huytools.ycnanswer.Core.Math.Vector2D;

public interface OnTouchListener {
    void OnTouchBegin(Node node , Vector2D p);
    void OnTouchMove(Node node, Vector2D p);
    void OnTouchEnd(Node node, Vector2D p);
}
