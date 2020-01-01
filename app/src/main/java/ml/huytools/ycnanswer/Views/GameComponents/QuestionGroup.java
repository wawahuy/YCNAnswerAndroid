package ml.huytools.ycnanswer.Views.GameComponents;

import android.graphics.Paint;
import android.util.Log;

import ml.huytools.ycnanswer.Core.Game.Event.OnTouchListener;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.PolygonShape;
import ml.huytools.ycnanswer.Core.Game.Scenes.Node;
import ml.huytools.ycnanswer.Core.Game.Scenes.NodeGroup;
import ml.huytools.ycnanswer.Core.Math.Vector2D;

public class QuestionGroup extends NodeGroup implements OnTouchListener {
    BoxQuestion question;
    BoxQuestion plans[];

    public QuestionGroup(){
        /// Question
        question = new BoxQuestion();
        question.getText().setSize(35);
        add(question);

        /// Plan A, B, C, D <=> 0..3
        plans = new BoxQuestion[4];
        for(int i=0; i<4; i++){
            BoxQuestion boxQuestion = new BoxQuestion();
            boxQuestion.setBorderWidth(8);
            boxQuestion.setTouchListener(this);
            boxQuestion.getText().setSize(30);
            boxQuestion.setTextAlign(Paint.Align.LEFT);
            boxQuestion.getText().setText(i+".");
            plans[i] = boxQuestion;
            add(boxQuestion);
        }
    }

    public void setBoundingSize(Vector2D v) {
        ///
        int halfW = (int)v.x/2;
        int halfH = (int)v.y/2;

        /// Question
        float boxQuestionH = halfH*0.6f;
        question.setBoundingSize(new Vector2D(v.x, boxQuestionH));;

        /// Plan
        final float margin_lr = v.x*0.04f;
        final float margin_plan = v.x*0.04f;
        float boxPlanH = boxQuestionH*0.7f;
        float boxPlanW = (v.x-margin_lr*3)/2;
        float boxPlanAnchor = boxQuestionH+margin_plan;

        plans[0].setPosition(margin_lr, boxPlanAnchor);
        plans[1].setPosition(margin_lr*2 + boxPlanW, boxPlanAnchor);
        plans[2].setPosition(margin_lr, boxPlanAnchor + margin_plan + boxPlanH);
        plans[3].setPosition(margin_lr*2 + boxPlanW, boxPlanAnchor + margin_plan + boxPlanH);

        Vector2D sizePlan = new Vector2D(boxPlanW, boxPlanH);
        for(BoxQuestion boxQuestion: plans){
            boxQuestion.setBoundingSize(sizePlan);
        }

    }

    @Override
    public void OnTouchBegin(Node node, Vector2D p) {
        int id = node.getId();
        if(id == plans[0].getId()){
        } else if(id == plans[1].getId()){
        } else if(id == plans[2].getId()){
        } else if(id == plans[3].getId()){
        }
    }

    @Override
    public void OnTouchMove(Node node, Vector2D p) {
    }

    @Override
    public void OnTouchEnd(Node node, Vector2D p) {
    }
}
