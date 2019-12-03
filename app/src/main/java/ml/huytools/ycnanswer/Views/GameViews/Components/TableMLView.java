package ml.huytools.ycnanswer.Views.GameViews.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;

import java.util.LinkedList;

import ml.huytools.ycnanswer.Commons.ModelManager;
import ml.huytools.ycnanswer.Models.CHDiemCauHoi;
import ml.huytools.ycnanswer.Models.CauHoi;
import ml.huytools.ycnanswer.Views.GameViews.CustomSurfaceView;

public class TableMLView extends CustomSurfaceView {

    final int BORDER_SIZE = 15;
    final int PADDING_LINE = 5;

    RectF border, bg;
    Paint paintBorder;
    Paint paintBg;
    Paint paintText;
    Paint paintTextCheckPoint;

    int w, h, yLine, hLine, hReal, yReal;

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

        paintText = new Paint();
        paintText.setARGB(255, 102, 189, 204);
        paintText.setAntiAlias(true);

        paintTextCheckPoint = new Paint();
        paintTextCheckPoint.setARGB(255, 255, 255, 255);
        paintTextCheckPoint.setAntiAlias(true);
    }

    @Override
    public void OnInit(Canvas canvas) {
        w = canvas.getWidth();
        h = canvas.getHeight();
        border = new RectF(10, 10, w-20, h-20);
        bg = new RectF(15, 15, w-30, h-30);
        change();
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
        canvas.drawRoundRect(border, BORDER_SIZE, BORDER_SIZE, paintBorder);

        int i = 0;
        for(CHDiemCauHoi cauHoi:chDiemCauHoi){
            int y = hReal - hLine*i + PADDING_LINE;
            canvas.drawText(Integer.toString(cauHoi.thu_tu), BORDER_SIZE*4, y, cauHoi.moc ? paintTextCheckPoint : paintText);
            canvas.drawText(Integer.toString(cauHoi.diem), w*0.3f, y, cauHoi.moc ? paintTextCheckPoint : paintText);
            i++;
        }
    }

    public void Config(ModelManager<CHDiemCauHoi> chDiemCauHoi){
        this.chDiemCauHoi = chDiemCauHoi;

        // upd d
        change();

        // re-draw
        draw();
    }


    private void change(){
        yReal = BORDER_SIZE+PADDING_LINE;
        hReal = h-(BORDER_SIZE+PADDING_LINE)*4;
        hLine = hReal/chDiemCauHoi.size();
        yLine = PADDING_LINE;

        paintText.setTextSize(hLine-PADDING_LINE*2);
        paintTextCheckPoint.setTextSize(hLine-PADDING_LINE*2);
    }
}
