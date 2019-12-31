package ml.huytools.ycnanswer.Views.GameComponents;

import java.util.LinkedList;

import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.Drawable;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.PolygonShape;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.RectangleShape;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.RoundRectangleShape;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.Text;
import ml.huytools.ycnanswer.Core.Game.Scenes.NodeGroup;
import ml.huytools.ycnanswer.Core.Math.Vector2D;

public class BoxQuestion extends NodeGroup {
    final int BORDER_SIZE = 15;
    private Text text;
    private PolygonShape background;
    private PolygonShape border;
    private Vector2D size;


    public BoxQuestion(){
        text = new Text();
        text.setColor(255, 255, 255, 255);
        text.setSize(40);

        background = new PolygonShape();
        background.setColor(255, 3, 14, 51);
        background.setPosition(BORDER_SIZE/2, BORDER_SIZE/2);
        background.setZOrder(-100);

        border = new PolygonShape();
        border.setStrokeWidth(BORDER_SIZE);
        border.setStyle(Drawable.Style.STROKE);
        border.setColor(255, 102, 189, 204);
        border.setZOrder(-90);;


        add(text);
        add(background);
        add(border);
    }

    @Override
    protected boolean testTouchPoint(Vector2D point) {
        /// Need Update Performances
        /// Test
        Vector2D sizeMul = new Vector2D(size.x*scale.x, size.y*scale.y);
        Vector2D originMul = new Vector2D(origin.x*scale.x, origin.y*scale.y);
        Vector2D min = positionWord.sub(originMul);
        Vector2D max = new Vector2D(sizeMul.x + positionWord.x, sizeMul.y + positionWord.y).sub(originMul);
        if(point.x < min.x || point.x > max.x) return false;
        if(point.y < min.y || point.y > max.y) return false;
        return true;
    }

    public void setBoundingSize(Vector2D size){
        this.size = size;

        final float ARROW_SIZE = size.x*0.07f;
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

    }

}
