package ml.huytools.ycnanswer.Views.GameViews.Components;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import ml.huytools.ycnanswer.Core.Removing.CustomSurfaceView;

public class FPSDebugView extends CustomSurfaceView {

    Paint textPaint;
    long timeCurrent;
    int fps, fpsShow;

    public FPSDebugView(Context context, AttributeSet attrs) {
        super(context, attrs);
        transparent();

        textPaint = new Paint();
        textPaint.setARGB(230, 200, 200, 200);
        textPaint.setTextSize(30);
        textPaint.setAntiAlias(true);

        fps = 0;
    }

    public static void AddOnActivity(Activity activity){
        FPSDebugView loading = new FPSDebugView(activity.getBaseContext(), null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(300, 100);
        activity.addContentView(loading, params);
    }

    @Override
    public void OnInit(Canvas canvas) {

    }

    @Override
    public boolean OnUpdate(int sleep) {
        fps++;
        long time = System.currentTimeMillis();
        if(time - timeCurrent >= 250){
            fpsShow = fps*4;
            fps = 0;
            timeCurrent = time;
            return true;
        }

        return false;
    }

    @Override
    public void OnDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
        canvas.drawText(Integer.toString(fpsShow) +" FPS", 40, 60, textPaint);
    }
}
