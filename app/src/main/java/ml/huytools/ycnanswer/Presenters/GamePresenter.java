package ml.huytools.ycnanswer.Presenters;

import android.content.Context;

import ml.huytools.ycnanswer.Commons.APIProvider;
import ml.huytools.ycnanswer.Commons.CustomLoader;
import ml.huytools.ycnanswer.Commons.ModelManager;
import ml.huytools.ycnanswer.Commons.Presenter;
import ml.huytools.ycnanswer.Models.CHDiemCauHoi;
import ml.huytools.ycnanswer.Models.CauHoi;

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
        loadGame();
    }



    /// -------------- Load ------------------
    private void loadGame(){
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

                AddLoad(new Load<ModelManager<CHDiemCauHoi>>("Load config answer2...", "Load config answer error"){
                    @Override
                    protected ModelManager<CHDiemCauHoi> OnRun() {
                        return chDiemCauHoi = APIProvider.GET(APIUri.CAU_HINH_CAU_HOI).toModelManager(CHDiemCauHoi.class);
                    }
                });

                AddLoad(new Load<ModelManager<CHDiemCauHoi>>("Load config answer3...", "Load config answer error"){
                    @Override
                    protected ModelManager<CHDiemCauHoi> OnRun() {
                        return chDiemCauHoi = APIProvider.GET(APIUri.CAU_HINH_CAU_HOI).toModelManager(CHDiemCauHoi.class);
                    }
                });

                AddLoad(new Load<ModelManager<CHDiemCauHoi>>("Load config answer4...", "Load config answer error"){
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
    }

    private void nextAnswer() {
        view.RestartCountDown();
    }


    /// ------------ Bang diem -----------------
    private void increaseTableML() {
    }



}