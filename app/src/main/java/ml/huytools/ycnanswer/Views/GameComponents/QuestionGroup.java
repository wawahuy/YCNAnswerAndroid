package ml.huytools.ycnanswer.Views.GameComponents;

import android.graphics.Paint;
import android.util.Log;

import ml.huytools.ycnanswer.Core.Game.Actions.Action;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionDrawings.ActionColorTo;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionRepeat;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionRepeatForever;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionSequence;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionCubicBezier;
import ml.huytools.ycnanswer.Core.Game.Event.OnTouchListener;
import ml.huytools.ycnanswer.Core.Game.Graphics.Color;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.PolygonShape;
import ml.huytools.ycnanswer.Core.Game.Scenes.Node;
import ml.huytools.ycnanswer.Core.Game.Scenes.NodeGroup;
import ml.huytools.ycnanswer.Core.Math.Vector2D;
import ml.huytools.ycnanswer.Models.Entities.QuestionEntity;

public class QuestionGroup extends NodeGroup implements OnTouchListener {
    final String[] PLANChar = new String[]{"A", "B", "C", "D"};
    BoxQuestion question;
    BoxQuestion plans[];
    QuestionCallback questionCallback;

    public interface QuestionCallback {
        void OnAnswer(String answer);
    }

    public QuestionGroup(QuestionCallback questionCallback){
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
            boxQuestion.getText().setText(PLANChar[i] + ".");
            plans[i] = boxQuestion;
            add(boxQuestion);
        }

        this.questionCallback = questionCallback;
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

    public void showQuestion(QuestionEntity questionEntity){
        question.getText().setText(questionEntity.noidung);
        plans[0].getText().setText("A. " + questionEntity.phuongan_A);
        plans[1].getText().setText("B. " + questionEntity.phuongan_B);
        plans[2].getText().setText("C. " + questionEntity.phuongan_C);
        plans[3].getText().setText("D. " + questionEntity.phuongan_D);

        for(BoxQuestion pl:plans){
            pl.getBackground().runAction(ActionColorTo.create(new Color(255, 3, 14, 51), 200));
        }
    }


    private void CheckAnswer(Node node) {
        int id = node.getId();
        if(id == plans[0].getId()){
            questionCallback.OnAnswer("A");
        } else if(id == plans[1].getId()){
            questionCallback.OnAnswer("B");
        } else if(id == plans[2].getId()){
            questionCallback.OnAnswer("C");
        } else if(id == plans[3].getId()){
            questionCallback.OnAnswer("D");
        }
    }

    public void setWarningPlanQuestion(String planQuestion) {
        Node node = plans[planStrToInt(planQuestion)].getBackground();
        node.runAction(ActionColorTo.create(new Color(200, 251, 189, 35), 200));
    }

    public void setSuccessPlanQuestion(String planQuestion) {
        Node node = plans[planStrToInt(planQuestion)].getBackground();
        node.runAction(ActionColorTo.create(new Color(200, 103, 180, 89), 200));
    }

    public void setErrorPlanQuestion(String planQuestion) {
        Node node = plans[planStrToInt(planQuestion)].getBackground();
        node.runAction(
                ActionRepeatForever.create(
                        ActionSequence.create(
                                ActionCubicBezier.Ease(ActionColorTo.create(new Color(255, 221, 81, 69), 100)),
                                ActionCubicBezier.Ease(ActionColorTo.create(new Color(255, 241, 184, 180), 100))
                        )
                )
        );
    }

    public void setInfoPlanQuestion(String planQuestion){
        Node node = plans[planStrToInt(planQuestion)].getBackground();
        node.runAction(ActionColorTo.create(new Color(255, 3, 14, 51), 200));
    }

    public int planStrToInt(String plan){
        if(plan.equals("A")) return 0;
        if(plan.equals("B")) return 1;
        if(plan.equals("C")) return 2;
        return 3;
    }

    @Override
    public void OnTouchBegin(Node node, Vector2D p) {

    }


    @Override
    public void OnTouchMove(Node node, Vector2D p) {
    }

    @Override
    public void OnTouchEnd(Node node, Vector2D p) {
        CheckAnswer(node);
    }
}
