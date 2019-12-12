package ml.huytools.ycnanswer.Presenters;

import android.content.Context;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.Log;

import ml.huytools.ycnanswer.Commons.APIProvider;
import ml.huytools.ycnanswer.Commons.CustomLoader;
import ml.huytools.ycnanswer.Commons.ModelManager;
import ml.huytools.ycnanswer.Commons.Presenter;
import ml.huytools.ycnanswer.Commons.Resource;
import ml.huytools.ycnanswer.Commons.Views.AbstractAnimation;
import ml.huytools.ycnanswer.Commons.Views.CubicBezier;
import ml.huytools.ycnanswer.Models.CHDiemCauHoi;
import ml.huytools.ycnanswer.Models.CauHoi;
import ml.huytools.ycnanswer.R;

public class GamePresenter extends Presenter<GamePresenter.View> {

    public interface View {
        ///--------------
        void OpenLoading();
        void CloseLoading();
        void UpdateLoadingText(String message);
        void UpdateLoadingBar(int per);

        ///--------------
        void ConfigTableML(ModelManager<CHDiemCauHoi> chDiemCauHoi);
        void SetLevelTableML(int level);
        void IncreaseLevelTableML();

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
    CustomLoader loading;

    ;

    public GamePresenter(Context context) {
        super(context);
    }



    //// ------------ Start Game ---------------
    @Override
    protected void OnStart() {
        loadGameDebug();

        ///
        view.ConfigTableML(chDiemCauHoi);
    }



    /// -------------- Load ------------------
    private void loadGameDebug() {

        /// Debug
        String s = Resource.readRawTextFile(context, R.raw.test_cau_hinh_diem_cau_hoi);
        chDiemCauHoi = ModelManager.ParseJSON(CHDiemCauHoi.class, s);
    }


    private void loadGame(){
            ///-------- Need Update ----------------
        new CustomLoader(){
            @Override
            protected void OnUpdateProgress(int p) {
                view.UpdateLoadingBar(p);
            }

            @Override
            protected void OnUpdateText(String text) {
                view.UpdateLoadingText(text);
            }

            @Override
            protected void OnStartLoad() {
                /// Mo view loading
                view.OpenLoading();

                /// Load Cau Hinh Diem Cau Hoi
                AddLoad(new Load<ModelManager<CHDiemCauHoi>>("Load config answer...", "Load config answer error"){
                    @Override
                    protected ModelManager<CHDiemCauHoi> OnRun() {
                        return chDiemCauHoi = APIProvider.GET(APIUri.CAU_HINH_CAU_HOI).toModelManager(CHDiemCauHoi.class);
                    }
                });
            }

            @Override
            protected void OnCompleteLoad() {
                view.CloseLoading();
            }
        }.start();
    }




    /// ------------ Cau hoi -------------------
    public void Answer(ANSWER answer) {
        nextAnswer();
        increaseTableML();
        view.RunEffectLight();
    }

    private void nextAnswer() {
        view.RestartCountDown();
    }


    /// ------------ Bang diem -----------------
    private void increaseTableML() {
        view.IncreaseLevelTableML();
    }



}