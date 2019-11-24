package ml.huytools.ycnanswer.Presenters;

import android.content.Context;

import ml.huytools.ycnanswer.Commons.APIProvider;
import ml.huytools.ycnanswer.Commons.Loader;
import ml.huytools.ycnanswer.Commons.ModelManager;
import ml.huytools.ycnanswer.Commons.Presenter;
import ml.huytools.ycnanswer.Models.CHDiemCauHoi;
import ml.huytools.ycnanswer.Models.CauHoi;

public class GamePresenter extends Presenter<GamePresenter.View> implements Loader.Callback {

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
    ModelManager<CHDiemCauHoi> chDiemCauHoi;

    ;

    public GamePresenter(Context context) {
        super(context);
    }



    //// ------------ Start Game ---------------
    @Override
    protected void OnStart() {
        /// Loading
        view.OpenLoading();
        Loader.Create(this);
    }

    @Override
    public void OnBackgroundLoad(Loader loader) {
        /// Load Config Bang Cau Hoi
        loader.change("Download config table answer...");
        chDiemCauHoi = APIProvider.GET(APIUri.CAU_HINH_CAU_HOI).toModelManager(CHDiemCauHoi.class);
        if(chDiemCauHoi == null){
            loader.change("Download config table answer [Error]");
            loader.restart(2000);
            return;
        }
    }

    @Override
    public void OnChangeLoad(Object object, Loader loader) {
    }

    @Override
    public void OnFinishLoad(Loader loader) {
        view.CloseLoading();
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