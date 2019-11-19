package ml.huytools.ycnanswer.Views.GameViews.Effects;

import android.graphics.Canvas;
import android.graphics.Paint;

public class EffectCircle extends Effect {

    int r, g, b, a;
    int radius, x, y;
    float upd_radius, upd_colorAlpha, upd_color;
    Paint paint;
    float t;

    public EffectCircle(int radius, int x, int y){
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);
        paint.setAntiAlias(true);
        this.radius = radius;
        this.x = x;
        this.y = y;
        setColor(255, 255, 255, 255);
        this.upd_radius = 1;
        this.upd_colorAlpha = 1;
        this.t = 1;
    }

    public void setColor(int a, int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        paint.setARGB(a, r, g, b);
    }

    public void setRadiusUpdate(float update){
        this.upd_radius = update;
    }

    public void setAlphaUpdate(float update){
        this.upd_colorAlpha = update;
    }

    public void setColorUpdate(float update){
        this.upd_color = update;
    }


    @Override
    public void update() {
        a-= upd_colorAlpha;
        r-= upd_color;
        g-= upd_color;
        b-= upd_color;
        this.setColor(a, r, g, b);

        radius += upd_radius;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle( x, y, radius, paint );
    }

    @Override
    public boolean canRemove() {
        return a < 0;
    }
}
