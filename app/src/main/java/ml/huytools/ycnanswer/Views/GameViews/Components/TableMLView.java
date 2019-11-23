package ml.huytools.ycnanswer.Views.GameViews.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;

import ml.huytools.ycnanswer.Commons.ModelManager;
import ml.huytools.ycnanswer.Models.CHDiemCauHoi;
import ml.huytools.ycnanswer.Views.GameViews.CustomSurfaceView;

public class TableMLView extends CustomSurfaceView {

    int w, h;
    RectF border, bg;
    Paint paintBorder;
    Paint paintBg;
    Paint paintText;
    Paint paintTextCheckPoint;

    ModelManager<CHDiemCauHoi> chDiemCauHoi;

    public TableMLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        transparent();

        paintBorder = new Paint();
        paintBorder.setARGB(255, 102, 189, 204);
        paintBorder.setStyle(Paint.Style.STROKE);
        paintBorder.setStrokeWidth(20);
        paintBorder.setAntiAlias(true);

        paintBg = new Paint();
        paintBg.setARGB(255, 3, 14, 51);
        paintBg.setAntiAlias(true);
    }

    @Override
    public void OnInit(Canvas canvas) {
        w = canvas.getWidth();
        h = canvas.getHeight();

        border = new RectF(10, 10, w-20, h-20);
        bg = new RectF(15, 15, w-30, h-30);
    }

    @Override
    public void OnStart() {
        //comment super don't register loop
        //super.OnStart();
        draw();
    }

    @Override
    public void OnUpdate(int sleep) {
    }

    @Override
    public void OnDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
        canvas.drawRect(bg, paintBg);
        canvas.drawRoundRect(border, 15, 15, paintBorder);
    }

    public void Config(ModelManager<CHDiemCauHoi> chDiemCauHoi){
        this.chDiemCauHoi = chDiemCauHoi;
        draw();
    }
}
