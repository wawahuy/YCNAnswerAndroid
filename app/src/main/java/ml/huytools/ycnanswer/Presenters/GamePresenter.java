package ml.huytools.ycnanswer.Presenters;

import android.content.Context;
import android.widget.Toast;

import java.util.Iterator;

import ml.huytools.ycnanswer.Commons.ModelManager;
import ml.huytools.ycnanswer.Commons.Presenter;
import ml.huytools.ycnanswer.Commons.Resource;
import ml.huytools.ycnanswer.Models.CauHoi;
import ml.huytools.ycnanswer.R;

public class GamePresenter extends Presenter<GamePresenter.View> {

    public enum ANSWER {A, B, C, D}

    ;

    ModelManager<CauHoi> dsCsauHoi;
    Iterator<CauHoi> cauHoiIterator;
    int levelQuestion;

    public GamePresenter(Context context) {
        super(context);

        /// Test Cau hoi
        dsCsauHoi = ModelManager.ParseJSON(CauHoi.class, Resource.readRawTextFile(context, R.raw.test_question));

        levelQuestion = 0;
        cauHoiIterator = dsCsauHoi.iterator();
    }

    @Override
    protected void OnStart() {
        nextAnswer();
    }

    public void Answer(ANSWER answer) {
        ///// Test
        Toast.makeText(context, "Test test test", Toast.LENGTH_LONG).show();

        nextAnswer();
    }

    private void nextAnswer() {
        if(!cauHoiIterator.hasNext()){
            Toast.makeText(context, "Test nhung het cau hoi roi thay oi!", Toast.LENGTH_LONG).show();
            return;
        }

        increaseTableLevelQuestion();
        view.UpdateQuestion(cauHoiIterator.next());
        view.RestartCountDown();
    }

    private void increaseTableLevelQuestion() {
        view.SetQuestionLevelTable(levelQuestion);
        levelQuestion++;
    }

    public interface View {
        void SetQuestionLevelTable(int level);
        void UpdateQuestion(CauHoi cauHoi);
        void RestartCountDown();
    }

}