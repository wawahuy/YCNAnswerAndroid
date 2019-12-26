package ml.huytools.ycnanswer.Core.Game.Graphics.Drawing;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class CircleShape extends Drawable {
    private RectF rect;
    private int radius;
    private int startAngle;
    private int endAngle;
    private boolean center;

    public CircleShape(){
        radius = 0;
        rect = new RectF();
        startAngle = 0;
        endAngle = 0;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.rect.right = this.rect.bottom = radius*2;
        this.radius = radius;
        computeOrigin();
    }

    public int getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }

    public int getEndAngle() {
        return endAngle;
    }

    public void setEndAngle(int endAngle) {
        this.endAngle = endAngle;
    }

    public void alwaysCenterOrigin(){
        center = true;
        computeOrigin();
    }

    public void computeOrigin(){
        if(center){
            setOrigin(radius, radius);
        }
    }

    @Override
    protected void OnDraw(Canvas canvas) {
        canvas.drawArc(rect, startAngle, endAngle, false, paint);
    }
}
