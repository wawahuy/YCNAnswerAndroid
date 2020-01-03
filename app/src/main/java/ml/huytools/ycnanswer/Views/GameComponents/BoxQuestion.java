package ml.huytools.ycnanswer.Views.GameComponents;

import android.graphics.Paint;

import java.util.LinkedList;

import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.Drawable;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.PolygonShape;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.Text;
import ml.huytools.ycnanswer.Core.Game.Scenes.NodeGroup;
import ml.huytools.ycnanswer.Core.Math.Vector2D;

public class BoxQuestion extends NodeGroup {
    private int borderWidth;
    private Text text;
    private PolygonShape background;
    private PolygonShape border;
    private Vector2D size;
    private Paint.Align textAlign;


    public BoxQuestion(){
        borderWidth = 15;
        textAlign = Paint.Align.CENTER;

        text = new Text();
        text.setColor(255, 255, 255, 255);
        text.setSize(30);

        background = new PolygonShape();
        background.setColor(255, 3, 14, 51);
        background.setPosition(borderWidth /2, borderWidth /2);
        background.setZOrder(-100);

        border = new PolygonShape();
        border.setStrokeWidth(borderWidth);
        border.setStyle(Drawable.Style.STROKE);
        border.setColor(255, 102, 189, 204);
        border.setZOrder(-90);;


        add(text);
        add(background);
        add(border);
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        border.setStrokeWidth(borderWidth);
        background.setPosition(borderWidth /2, borderWidth /2);
    }

    @Override
    protected boolean testTouchPoint(Vector2D point) {
        /// Need Update Performances
        /// Test
        Vector2D min = positionWord.add(computeVector2DWordTrx(new Vector2D(0, 0)));
        Vector2D max = positionWord.add(computeVector2DWordTrx(new Vector2D(size.x, size.y)));
        if(point.x < min.x || point.x > max.x) return false;
        if(point.y < min.y || point.y > max.y) return false;
        return true;
    }

    public void setBoundingSize(Vector2D size){
        this.size = size;

        final float ARROW_SIZE = size.x*0.05f;
        LinkedList<Vector2D> points = new LinkedList<>();
        points.add(new Vector2D(ARROW_SIZE, 0));
        points.add(new Vector2D(size.x - ARROW_SIZE, 0));
        points.add(new Vector2D(size.x, size.y/2));
        points.add(new Vector2D(size.x - ARROW_SIZE, size.y));
        points.add(new Vector2D(ARROW_SIZE, size.y));
        points.add(new Vector2D(0, size.y/2));
        points.add(new Vector2D(ARROW_SIZE + 6, -2));

        background.setPoints(points);
        border.setPoints(points);

        setTextAlign(textAlign);
    }

    public Text getText() {
        return text;
    }

    public PolygonShape getBackground() {
        return background;
    }

    public void setTextAlign(Paint.Align align){
        textAlign = align;
        if(align == Paint.Align.CENTER){
            text.centerOrigin(true);
            if (size != null){
                text.setPosition(size.div(2));
            }
        } else {
            text.centerOrigin(false);
            if (size != null){
                text.centerOrigin(true);
                float y = text.getOrigin().y;
                text.centerOrigin(false);
                text.setPosition(size.x*0.1f, size.y/2);
                text.setOrigin(0, y);
            }
        }
    }
}
