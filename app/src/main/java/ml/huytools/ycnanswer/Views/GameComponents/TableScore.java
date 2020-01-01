package ml.huytools.ycnanswer.Views.GameComponents;

import android.graphics.Paint;
import android.graphics.Typeface;

import androidx.constraintlayout.widget.Group;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import ml.huytools.ycnanswer.Core.Game.Actions.Action;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionDrawings.ActionColorTo;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionRepeat;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionSequence;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionCubicBezier;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionMoveTo;
import ml.huytools.ycnanswer.Core.Game.Event.OnTouchListener;
import ml.huytools.ycnanswer.Core.Game.Graphics.Color;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.Drawable;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.RectangleShape;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.RoundRectangleShape;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.Text;
import ml.huytools.ycnanswer.Core.Game.Scenes.Node;
import ml.huytools.ycnanswer.Core.Game.Scenes.NodeGroup;
import ml.huytools.ycnanswer.Core.MVP.EntityManager;
import ml.huytools.ycnanswer.Core.Math.Vector2D;
import ml.huytools.ycnanswer.Models.Entities.CHDiemCauHoi;

public class TableScore extends NodeGroup {
    final int BORDER_SIZE = 15;
    final int BORDER_RADIUS = 45;
    final int PADDING_LINE = 5;

    Vector2D size;
    RectangleShape background;
    RoundRectangleShape border;
    RoundRectangleShape select;
    NodeGroup groupText;

    EntityManager<CHDiemCauHoi> dsDiemCauHoi;
    int positionSelect;

    public TableScore() {
        super();
        background = new RectangleShape();
        background.setColor(255, 3, 14, 51);
        background.setPosition(BORDER_SIZE/2, BORDER_SIZE/2);
        background.setZOrder(-100);

        border = new RoundRectangleShape();
        border.setRoundSize(BORDER_RADIUS);
        border.setStrokeWidth(BORDER_SIZE);
        border.setStyle(Drawable.Style.STROKE);
        border.setColor(255, 102, 189, 204);
        border.setZOrder(-90);

        select = new RoundRectangleShape();
        select.setRoundSize(30);
        select.setColor(255, 255, 204, 0);

        groupText = new NodeGroup();
        groupText.setPosition(BORDER_SIZE, BORDER_SIZE);
        groupText.setZOrder(100);

        add(groupText);
        add(border);
        add(background);
        add(select);
    }

    public void setBoundingSize(Vector2D vector2D){
        size = vector2D;

        background.setSize(new Vector2D(size.x - BORDER_SIZE, size.y - BORDER_SIZE));
        border.setSize(new Vector2D(size.x, size.y));
        transformText();
    }

    public void initData(EntityManager<CHDiemCauHoi> chDiemCauHoi){
        dsDiemCauHoi = chDiemCauHoi;
        positionSelect = 0;
        groupText.clear();

        Collections.sort(dsDiemCauHoi, new Comparator<CHDiemCauHoi>() {
            @Override
            public int compare(CHDiemCauHoi c1, CHDiemCauHoi c2) {
                return c1.thu_tu > c2.thu_tu ? -1 : (c1.thu_tu < c2.thu_tu ? 1 : 0);
            }
        });


        for(CHDiemCauHoi c:dsDiemCauHoi){
            NodeGroup line = new NodeGroup();

            Text text = new Text();
            text.setText(scoreFormat(c.diem));
            text.setPosition(new Vector2D(100, 0));
            text.setTextStyle(Typeface.BOLD);
            text.setSize(40);
            line.add(text);

            Text id = new Text();
            id.setText(c.thu_tu + "");
            id.setTextStyle(Typeface.BOLD);
            id.setSize(40);
            id.setPosition(0, 0);
            id.setTextAlign(Paint.Align.RIGHT);
            Paint paint;
            line.add(id);

            Color color;
            if(c.moc == false){
                color = new Color(255, 102, 189, 204);
            } else {
                color = new Color(255, 255, 255, 255);
            }
            id.setColor(color);
            text.setColor(color);

            groupText.add(line);
        }

        transformText();
    }


    private void transformText(){
        if(size == null){
            return;
        }

        int lineHeight = (int)(size.y-BORDER_SIZE*4)/dsDiemCauHoi.size() - PADDING_LINE*2;
        int i = 0;

        select.setSize(new Vector2D(size.x*0.8f, lineHeight + PADDING_LINE));
        select.setOrigin(size.x*0.1f, PADDING_LINE);

        for(Node node:groupText.getListNode()){
            i++;
            node.setPosition(size.x*0.2f, i*(lineHeight + PADDING_LINE*2) + BORDER_SIZE);

            // size font
            List<Node> lines = ((NodeGroup) node).getListNode();
            for(Node line:lines){
                ((Text)line).setSize(lineHeight);
            }
        }

        setPositionSelect(positionSelect);
    }

    private void updateUISelect(){
        if(size == null || dsDiemCauHoi == null){
            return;
        }

        int posList = dsDiemCauHoi.size() - 1 - positionSelect;
        Node nodeText = groupText.getListNode().get(posList);
        Vector2D position = nodeText.getPosition();

        Action go = ActionCubicBezier.EaseOut(ActionMoveTo.create(position.clone(), 400));
        if(dsDiemCauHoi.get(posList).moc){
            Action color1 = ActionCubicBezier.EaseInOut(ActionColorTo.create(new Color(0, 255, 255, 255), 100));
            Action color2 = ActionCubicBezier.EaseInOut(ActionColorTo.create(new Color(255, 255, 204, 0), 100));
            Action seq = ActionSequence.create(color1, color2);
            Action flick = ActionRepeat.create(seq, 8);
            select.runAction(ActionSequence.create(go, flick));
        } else {
            select.runAction(go);
        }
    }


    public int getPositionSelect() {
        return positionSelect;
    }

    public void setPositionSelect(int positionSelect) {
        this.positionSelect = positionSelect;
        updateUISelect();
    }

    private String scoreFormat(int score){
        return String.format("%,d", score).replaceAll("\\.", " ");
    }
}
