package ml.huytools.ycnanswer.Core.Game.Graphics.Drawing;

import android.graphics.Canvas;
import android.graphics.Rect;

import ml.huytools.ycnanswer.Core.Math.Vector2D;

public class RectangleShape extends Drawable {
    Rect rect;
    Vector2D size;
    private boolean center;

    public RectangleShape(){
        super();
        rect = new Rect(0, 0, 0, 0);
        size = new Vector2D();
    }

    public RectangleShape(Vector2D size){
        this();
        setSize(size);
    }

    public Vector2D getSize() {
        return size;
    }

    public void setSize(Vector2D size) {
        this.size.x = size.x;
        this.size.y = size.y;
        rect.right = (int)size.x;
        rect.bottom = (int)size.y;
        hasUpdateDraw = true;
        computeOrigin();
    }

    public void centerOrigin(boolean status){
        center = status;
    }

    private void computeOrigin(){
        if(center){
            setOrigin(size.x/2, size.y/2);
        }
    }

    @Override
    protected void OnDraw(Canvas canvas) {
        canvas.drawRect(rect, paint);
    }
}
