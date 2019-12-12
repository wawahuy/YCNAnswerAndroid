package ml.huytools.ycnanswer.Views.GameViews.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;

import ml.huytools.ycnanswer.Commons.Views.AbstractAnimation;
import ml.huytools.ycnanswer.Commons.Views.CubicBezier;
import ml.huytools.ycnanswer.Commons.Views.CustomSurfaceView;
import ml.huytools.ycnanswer.Views.GameViews.Effects.Effect;
import ml.huytools.ycnanswer.Views.GameViews.Effects.EffectManager;

public class SpotLightView extends CustomSurfaceView {

    EffectManager effectManager;

    public SpotLightView(Context context, AttributeSet attrs) {
        super(context, attrs);
        transparent();
    }

    @Override
    public void OnInit(Canvas canvas) {
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        int xc = w/7;

        effectManager = new EffectManager();
        effectManager.add(new SpotLightChild(xc, -5, h/2-10, 10, 100, -45, 45));
        effectManager.add(new SpotLightChild(xc*2, -5, h/2-10, 10, 100, 45, -45));
        effectManager.add(new SpotLightChild(w-xc*2, -5, h/2-10, 10, 100, -45, 45));
        effectManager.add(new SpotLightChild(w-xc, -5, h/2-10, 10, 100, 45, -45));
}

    @Override
    public boolean OnUpdate(int sleep) {
        effectManager.OnUpdate(sleep);
        return true;
    }

    @Override
    public void OnDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
        effectManager.OnDraw(canvas);
    }


    /**
     * One SpotLight
     */
    class SpotLightChild extends Effect {

        int x, y, h, r1, r2, s, l;
        Path path;
        Paint paint;
        int angleS, angleE, angleC;
        boolean dir;


        public SpotLightChild(int x, int y, int h, int r1, int r2, int angleS, int angleE) {
            this.x = x;
            this.y = y;
            this.h = h;
            this.r1 = r1;
            this.r2 = r2;
            this.angleS = angleS;
            this.angleE = angleE;
            this.angleC = 0;
            this.l = this.angleE-this.angleS;
            this.s = l/Math.abs(l);

            path = new Path();
            path.moveTo(-r1, 0);
            path.lineTo(r1, 0);
            path.lineTo(r2, h);
            path.lineTo(-r2, h);

            paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setARGB(40, 255, 255, 255);
            paint.setShader(new LinearGradient(0, 0, 0, h,0xffffffff, 0x00ffffff, Shader.TileMode.MIRROR));

            setInfinite(true);
            setReverse(true);
            setTiming(new CubicBezier(0.58f, 0, 0.58f, 1));
            setTime(1000);
        }



        @Override
        public boolean canRemove() {
            return !isLoop();
        }



        @Override
        protected boolean OnUpdateAnimation(float per) {
            angleC = angleS + (int)(per/100.0f*(angleE-angleS));
            return true;
        }

        @Override
        public void OnDraw(Canvas canvas) {
            canvas.save();
            canvas.translate(x, y);
            canvas.rotate(angleC);
            canvas.drawPath(path,paint);
            canvas.restore();
        }
    }
}
