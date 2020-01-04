package ml.huytools.ycnanswer.Views.Interface;

import java.util.LinkedHashMap;

import ml.huytools.ycnanswer.Core.MVP.EntityManager;
import ml.huytools.ycnanswer.Models.Entities.findConfigQuestionEntity;
import ml.huytools.ycnanswer.Models.Entities.QuestionEntity;

public interface GameView {
    void visibleAll(boolean status);
    void setEnableTouchAll(boolean status);
    void showLoading();
    void updateTextLoading(String text);
    void hideLoading();
    void showMessage(String message);
    void setDataTableScore(EntityManager<findConfigQuestionEntity> configQuestionEntities);
    void setPositionScoreTable(int positionScoreTable);
    void showQuestion(QuestionEntity questionEntity);
    void setWarningPlanQuestion(String planQuestion);
    void setSuccessPlanQuestion(String planQuestion);
    void setErrorPlanQuestion(String planQuestion);
    void runEffectLight();
    void runEffectLightSlow();
    void startCountDown();
    void stopContDown();
    void setBoxCredit(int credit);
    void addBoxSpectator(LinkedHashMap<String, Integer> dp);
    void removeBoxSpectator();
    void clearPlans(Object[] plans);
    void showIconSupport50();
    void hideIconSupport50();
    void showIconSupportSpectator();
    void hideIconSupportSpectator();
    void showIconSupportCall();
    void hideIconSupportCall();
    void showWin(String messeage);
    void showLose(String messeage);
    void close();
}
