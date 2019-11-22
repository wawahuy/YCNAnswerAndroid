package ml.huytools.ycnanswer.Views.GameViews.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.Vector;

import ml.huytools.ycnanswer.Views.GameViews.CustomSurfaceView;
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
    public void OnUpdate(int sleep) {
        effectManager.OnUpdate(sleep);
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
        final boolean DIR_SE = true;
        final boolean DIR_ES = false;

        float speed, spA;

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
            this.speed = 4;
            this.spA = 4;
            this.dir = DIR_SE;
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
        }


        public void setSpeed(float speed){
            this.speed = speed;
        }

        @Override
        public boolean canRemove() {
            return false;
        }

        public boolean checkOutSE(){
            return this.s > 0 ?
                        this.angleC > this.angleE :
                        this.angleC < this.angleE;
        }

        public boolean checkOutES(){
            return this.s > 0 ?
                    this.angleC < this.angleS :
                    this.angleC > this.angleS;
        }

        @Override
        public void OnUpdate(int sleep) {
            if(this.dir == DIR_SE){
                this.angleC += this.spA*this.s;
                if(checkOutSE()){
                    this.dir = DIR_ES;
                    this.spA = this.speed;
                }
            } else {
                this.angleC -= this.spA*this.s;
                if(checkOutES()){
                    this.dir = DIR_SE;
                    this.spA = this.speed;
                }
            }
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
