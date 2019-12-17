package ml.huytools.ycnanswer.Views.GameViews.Effects;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

/**
 * Effect này chưa sử dụng AnimationManager Abstract
 * Cần được xây dựng lại
 */
public class EffectCircleRotate extends Effect {

    final int ANGLE_START = 270;
    final int ANGLE_ACCELERATION = 10;

    int radius;
    int radiusRotate;
    int delay;
    int cx;
    int cy;
    int angle;
    float angleA;
    boolean resA;

    Paint paint;

    public EffectCircleRotate(int cx, int cy, int radius, int radiusRotate, int delay){
        this.radius = radius;
        this.radiusRotate = radiusRotate;
        this.delay = delay;
        this.cx = cx;
        this.cy = cy;
        this.angle = ANGLE_START;
        this.angleA = ANGLE_ACCELERATION;
        this.resA = false;

        paint = new Paint();
        paint.setARGB(150, 255, 255, 255);
        paint.setAntiAlias(true);
    }

    @Override
    public boolean canRemove() {
        return false;
    }

    @Override
    public boolean OnUpdate(int sleep) {
        if(delay > 0){
            delay -= sleep;
            return false;
        }

        float dt = sleep/25.0f;
        angleA -= dt/10.0f;
        angle += angleA*dt;
        if(angle > 360){
            angle -= 360;
            resA = false;
        }

        if(angle > ANGLE_START){
            if(!resA){
                angleA = ANGLE_ACCELERATION;
                resA = true;
            }
        }

        return true;
    }

    @Override
    protected boolean OnUpdateAnimation(float per) {
        return false;
    }

    @Override
    public void OnDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(cx, cy);
        canvas.rotate(angle);
        canvas.drawCircle(0, -radiusRotate, radius, paint);
        canvas.restore();
    }
}
