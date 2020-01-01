package ml.huytools.ycnanswer.Views.GameComponents;

import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.Drawable;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.PolygonShape;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.RoundRectangleShape;
import ml.huytools.ycnanswer.Core.Game.Scenes.NodeGroup;
import ml.huytools.ycnanswer.Core.Math.Vector2D;

public class BoxMoney extends NodeGroup {
    final int BORDER_WIDTH = 15;
    final int BORDER_ROUND = 35;
    RoundRectangleShape background;
    RoundRectangleShape border;

    public BoxMoney(){
        background = new RoundRectangleShape();
        background.setColor(255, 3, 14, 51);
        background.setRoundSize(BORDER_ROUND - 5);
        background.centerOrigin(true);
        background.setZOrder(-90);
        add(background);

        border = new RoundRectangleShape();
        border.setStrokeWidth(BORDER_WIDTH);
        border.setStyle(Drawable.Style.STROKE);
        border.setColor(255, 102, 189, 204);
        border.setRoundSize(BORDER_ROUND);
        border.setZOrder(-100);
        border.centerOrigin(true);
        add(border);
    }

    public void setBoundingSize(Vector2D size){
        Vector2D c = size.div(2);
        background.setPosition(c);
        background.setSize(size.sub(new Vector2D(BORDER_WIDTH/2, BORDER_WIDTH/2)));
        border.setPosition(c);
        border.setSize(size);
    }
}
