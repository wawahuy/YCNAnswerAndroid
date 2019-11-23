package ml.huytools.ycnanswer.Presenters;

import android.content.Context;
import android.widget.Toast;

import java.util.Iterator;

import ml.huytools.ycnanswer.Commons.ApiProvider;
import ml.huytools.ycnanswer.Commons.ModelManager;
import ml.huytools.ycnanswer.Commons.Presenter;
import ml.huytools.ycnanswer.Commons.Resource;
import ml.huytools.ycnanswer.Models.CHDiemCauHoi;
import ml.huytools.ycnanswer.Models.CauHoi;
import ml.huytools.ycnanswer.R;

public class GamePresenter extends Presenter<GamePresenter.View> {

    public interface View {
        ///--------------
        void OpenLoading();
        void CloseLoading();

        ///--------------
        void ConfigTableML(ModelManager<CHDiemCauHoi> chDiemCauHoi);
        void SetLevelTableML(int level);

        ///--------------
        void UpdateQuestion(CauHoi cauHoi);

        ///--------------
        void ConfigCountDownTime(int second);
        void RestartCountDown();

        ///--------------
        void RunEffectLight();
    }

    ;

    public enum ANSWER {A, B, C, D}

    ;

    ModelManager<CHDiemCauHoi> chDiemCauHoi;


    ;

    public GamePresenter(Context context) {
        super(context);
    }

    //// ------------ Start Game ---------------
    @Override
    protected void OnStart() {
        load();
    }

    public void load(){
    }

    /// ------------ Cau hoi -------------------
    public void Answer(ANSWER answer) {
        nextAnswer();
    }

    private void nextAnswer() {
        view.RestartCountDown();
    }


    /// ------------ Bang diem -----------------
    private void increaseTableML() {
    }



}