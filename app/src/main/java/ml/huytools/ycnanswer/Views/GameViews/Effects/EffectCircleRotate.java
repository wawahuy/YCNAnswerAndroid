package ml.huytools.ycnanswer.Views.GameViews.Effects;

import android.graphics.Canvas;
import android.graphics.Paint;

public class EffectCircleRotate extends Effect {

    int radius;
    int radiusRotate;
    int delay;

    Paint paint;

    public EffectCircleRotate(int radius, int radiusRotate, int delay){
        this.radius = radius;
        this.radiusRotate = radiusRotate;
        this.delay = delay;

        paint = new Paint();
        paint.setARGB(150, 255, 255, 255);
        paint.setAntiAlias(true);
    }

    @Override
    public boolean canRemove() {
        return false;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(200, 200, radius, paint);
    }
}
