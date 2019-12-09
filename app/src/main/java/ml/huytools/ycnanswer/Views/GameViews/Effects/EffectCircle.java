package ml.huytools.ycnanswer.Views.GameViews.Effects;

import android.graphics.Canvas;
import android.graphics.Paint;

import ml.huytools.ycnanswer.Commons.Math.Vector2D;

public class EffectCircle extends Effect {

    int r, g, b;
    Vector2D position;
    int radiusCurrent, radiusStart, radiusEnd;
    int alphaCurrent, alphaStart, alphaEnd;
    Paint paint;

    public EffectCircle(Vector2D position){
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);
        paint.setAntiAlias(true);
        this.position = position;
        setColor(255, 255, 255);
    }

    public void setColor(int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
        paint.setARGB(255, r, g, b);
    }

    public void setAlphaAnimation(int start, int end){
        alphaStart = start;
        alphaEnd = end;
    }

    public void setRadiusAnimation(int start, int end){
        radiusStart = start;
        radiusEnd = end;
    }

    @Override
    public void reset() {
        super.reset();
        alphaCurrent = 0;
        radiusCurrent = 0;
    }

    @Override
    protected void OnUpdateAnimation(float per) {
        alphaCurrent = alphaStart + (int)(per/100.0f*(alphaEnd-alphaStart));
        radiusCurrent = radiusStart + (int)(per/100.0f*(radiusEnd - radiusStart));
    }

    @Override
    public void OnDraw(Canvas canvas) {
        paint.setARGB(alphaCurrent, r, g, b);
        canvas.drawCircle( position.x, position.y, radiusCurrent, paint );
    }

    @Override
    public boolean canRemove() {
        return !isLoop();
    }
}
