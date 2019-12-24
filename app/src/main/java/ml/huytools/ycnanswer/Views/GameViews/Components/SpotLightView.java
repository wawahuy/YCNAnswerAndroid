package ml.huytools.ycnanswer.Views.GameViews.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;

import java.util.LinkedList;

import ml.huytools.ycnanswer.Commons.Math.Vector2D;
import ml.huytools.ycnanswer.Commons.Math.CubicBezier;
import ml.huytools.ycnanswer.Commons.Removing.CustomSurfaceView;
import ml.huytools.ycnanswer.Views.GameViews.Effects.Effect;
import ml.huytools.ycnanswer.Views.GameViews.Effects.EffectManager;

public class SpotLightView extends CustomSurfaceView {

    Vector2D size;
    EffectManager effectManager;
    LinkedList<SpotLightChild> spotLightChildren;


    public SpotLightView(Context context, AttributeSet attrs) {
        super(context, attrs);
        transparent();

    }

    @Override
    public void OnInit(Canvas canvas) {
        int w = canvas.getWidth() + 150;
        int h = canvas.getHeight();
        int xc = w/7;
        size = new Vector2D(w, h);

        /// SpotLight
        spotLightChildren = new LinkedList<>();
        spotLightChildren.add(new SpotLightChild(xc, -5, h/2-10, 10, 120, -45, 45));
        spotLightChildren.add(new SpotLightChild(xc*2, -5, h/2-10, 10, 120, 45, -45));
        spotLightChildren.add(new SpotLightChild(w-xc*2, -5, h/2-10, 10, 120, -45, 45));
        spotLightChildren.add(new SpotLightChild(w-xc, -5, h/2-10, 10, 120, 45, -45));

        /// SpotLight to Effect
        effectManager = new EffectManager();
        effectManager.add(spotLightChildren);
    }


    public void runFlickerAmbientLight(){
        /// Update speed spotlight
        this.setSpeedSpotLight(200);

        /// Add effect flicker
        FlickerAmbientLight flickerAmbientLight = new FlickerAmbientLight(size);

        /// Rollback speed spotlight
        flickerAmbientLight.setEventRemoveListener(new Runnable() {
            @Override
            public void run() {
                SpotLightView.this.setSpeedSpotLight(1000);
            }
        });

        effectManager.add(flickerAmbientLight);
    }


    public void setSpeedSpotLight(final int ms){
        for(final SpotLightChild spotLightChild:spotLightChildren){
            /// Khi time trên cubic bezier được cập nhật mới
            /// nếu gọi time trước đó là old và time mới là new
            /// old < new, progression tức trục y nằm trong khoản 0.2->0.8 sẽ xãy ra giật do percent bị thay đổi đột ngột
            /// do vậy đăng kí hành động với AbstractAnimation nhầm chỉ cập nhật khi y là cực đại hoặc cực tiểu
            if(spotLightChild.getTime() < ms) {
                /// Đăng kí sự kiến gọi khi vòng đời TIMING kết thúc
                spotLightChild.setCallbackWhenEndTiming(new Runnable() {
                    @Override
                    public void run() {
                        if(spotLightChild == null)
                            return;

                        /// cập nhật lại time
                        spotLightChild.setTime(ms);

                        /// xóa sự kiện
                        spotLightChild.setCallbackWhenEndTiming(null);
                    }
                });
            } else {
                spotLightChild.setTime(ms);
            }
        }
    }

    @Override
    public boolean OnUpdate(int sleep) {
        //test
        return effectManager.OnUpdate(sleep);
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

    /**
     * Flicker
     */
    public static class FlickerAmbientLight extends Effect {

        Vector2D size;
        Rect rect;
        Paint paint;
        float perOld;
        boolean lightStage;

        public FlickerAmbientLight(Vector2D size) {
            this.size = size;
            setInfinite(false);
            setTiming(new CubicBezier(CubicBezier.TIMING.EaseOut));
            setTime(1000);

            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setARGB(150, 255, 255, 255);

            rect = new Rect(0, 0, (int)size.x, (int)size.y);
            perOld = 0;
            lightStage = false;
        }

        @Override
        public boolean canRemove() {
            return !isLoop();
        }

        @Override
        protected boolean OnUpdateAnimation(float per) {
            /// 0->0.2+->0.4->...->1.0
            float dper = per - perOld;
            if(dper >= 20){
                perOld = per;
                dper = 0;
            }

            int alpha = (int)(dper/100.0f*230);
            paint.setARGB(lightStage ? 230-alpha : alpha, 255, 255, 255);

            return true;
        }

        @Override
        public void OnDraw(Canvas canvas) {
            canvas.drawRect(rect, paint);
        }
    }
}
