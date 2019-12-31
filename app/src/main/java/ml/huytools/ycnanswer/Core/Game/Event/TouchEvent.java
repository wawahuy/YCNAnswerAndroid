package ml.huytools.ycnanswer.Core.Game.Event;

import ml.huytools.ycnanswer.Core.Math.Vector2D;

public class TouchEvent extends Event {
    public enum TouchType { MOVE, BEGIN, END, NORMAL };
    private TouchType type;
    private Vector2D point;

    public TouchEvent(TouchType type, Vector2D point) {
        super(EventType.Touch);
        this.type = type;
        this.point = point;
    }

    public TouchType getType() {
        return type;
    }

    public void setType(TouchType type) {
        this.type = type;
    }

    public Vector2D getPoint() {
        return point;
    }

    public void setPoint(Vector2D point) {
        this.point = point;
    }
}
