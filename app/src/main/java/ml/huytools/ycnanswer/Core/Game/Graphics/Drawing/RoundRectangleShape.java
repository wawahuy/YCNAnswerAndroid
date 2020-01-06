package ml.huytools.ycnanswer.Core.Game.Graphics.Drawing;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.RectangleShape;
import ml.huytools.ycnanswer.Core.Math.Vector2D;

public class RoundRectangleShape extends RectangleShape {
    int roundSize;
    RectF rectF;

    public RoundRectangleShape() {
        super();
        rectF = new RectF();
    }

    @Override
    public void setSize(Vector2D size) {
        super.setSize(size);
        rectF.right = size.x;
        rectF.bottom = size.y;
    }

    public int getRoundSize() {
        return roundSize;
    }

    public void setRoundSize(int roundSize) {
        this.roundSize = roundSize;
    }

    @Override
    protected void OnDraw(Canvas canvas) {
        canvas.drawRoundRect(rectF, roundSize, roundSize, paint);
    }
}
