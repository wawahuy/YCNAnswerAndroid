package ml.huytools.ycnanswer.Views.GameViews.Effects;

import android.graphics.Canvas;
import android.graphics.Paint;

import ml.huytools.ycnanswer.Core.Math.Vector2D;
import ml.huytools.ycnanswer.Core.Math.Vector3D;

public class EffectCircle extends Effect {

    Vector3D color;
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
        setColor(new Vector3D(255, 255, 255));
    }

    public void setColor(Vector3D color){
        this.color = color;
        paint.setARGB(255, (int)color.x, (int)color.y, (int)color.z);
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
    protected boolean OnUpdateAnimation(float per) {
        alphaCurrent = alphaStart + (int)(per/100.0f*(alphaEnd-alphaStart));
        radiusCurrent = radiusStart + (int)(per/100.0f*(radiusEnd - radiusStart));
        return true;
    }

    @Override
    public void OnDraw(Canvas canvas) {
        paint.setARGB(alphaCurrent, (int)color.x, (int)color.y, (int)color.z);
        canvas.drawCircle( position.x, position.y, radiusCurrent, paint );
    }

    @Override
    public boolean canRemove() {
        return !isLoop();
    }
}
