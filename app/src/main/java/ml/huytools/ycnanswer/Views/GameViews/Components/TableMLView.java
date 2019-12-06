package ml.huytools.ycnanswer.Views.GameViews.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;

import java.text.NumberFormat;
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
    Paint paintSelect;
    Paint paintText;
    Paint paintTextCheckPoint;

    int w, h, hLine, hReal, pos;

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

        paintSelect = new Paint();
        paintSelect.setARGB(255, 255, 204, 0);
        paintSelect.setAntiAlias(true);

        paintText = new Paint();
        paintText.setARGB(255, 102, 189, 204);
        paintText.setAntiAlias(true);
        paintText.setTypeface(Typeface.DEFAULT_BOLD);

        paintTextCheckPoint = new Paint();
        paintTextCheckPoint.setARGB(255, 255, 255, 255);
        paintTextCheckPoint.setAntiAlias(true);
        paintTextCheckPoint.setTypeface(Typeface.DEFAULT_BOLD);

        pos = 0;
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
            int y = (hReal - hLine*i) - PADDING_LINE*3;

            if(pos==cauHoi.thu_tu){
                canvas.drawRoundRect(BORDER_SIZE*2, y, w-BORDER_SIZE*3, y + hLine, BORDER_SIZE, BORDER_SIZE, paintSelect);
            }

            int yt = (int)(y + hLine*0.75f);
            canvas.drawText(Integer.toString(cauHoi.thu_tu), BORDER_SIZE*4, yt, cauHoi.moc ? paintTextCheckPoint : paintText);
            canvas.drawText(scoreFormat(cauHoi.diem), w*0.3f, yt, cauHoi.moc ? paintTextCheckPoint : paintText);
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
        //yReal = BORDER_SIZE+PADDING_LINE*3;
        hReal = h-(BORDER_SIZE+PADDING_LINE*3)*2;
        hLine = hReal/chDiemCauHoi.size();
        pos = 1;

        paintText.setTextSize(hLine-PADDING_LINE*2 );
        paintTextCheckPoint.setTextSize(hLine-PADDING_LINE*2);
    }

    private String scoreFormat(int score){
       return String.format("%,d", score).replaceAll("\\.", " ");
    }

    public void setPos(int pos){
        this.pos = pos;
        draw();
    }

    public int getPos(){
        return this.pos;
    }

    public void incPos(){
        pos++;
        draw();
    }

    public void decPos(){
        pos--;
        draw();
    }
}
