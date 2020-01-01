package ml.huytools.ycnanswer.Presenters;

import ml.huytools.ycnanswer.Core.MVP.Entity;
import ml.huytools.ycnanswer.Core.MVP.EntityManager;
import ml.huytools.ycnanswer.Core.MVP.Presenter;
import ml.huytools.ycnanswer.Core.Resource;
import ml.huytools.ycnanswer.Models.Entities.CHDiemCauHoi;
import ml.huytools.ycnanswer.Models.Entities.CauHoi;
import ml.huytools.ycnanswer.R;

public class GamePresenter extends Presenter<GamePresenter.View> {

    /// Interface
    /// Activity cần impl để Presenter có thể tương tác với Activity
    /// Tách biệc hoạt động UI và Control
    public interface View {
        //        void ResumeUI(ResumeData model);

        ///--------------
        void OpenLoading();
        void CloseLoading();
        void UpdateLoadingText(String message);
        void UpdateLoadingBar(int per);

        ///--------------
        void ConfigTableML(EntityManager<CHDiemCauHoi> chDiemCauHoi);
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

    /// DataSaved
    /// Dữ liệu bị thay đổi mõi khi cấu hình activity bị thay đổi
    /// Entity này sẽ chứa các thông tin thay đổi đó
    //    public static class ResumeData extends Entity {
    //        public long countDownStart;
    //        public int levelTableML;
    //    }

    ;

    public enum ANSWER {A, B, C, D}
    EntityManager<CHDiemCauHoi> chDiemCauHoi;

    ;


    //// ------------ Start Game ---------------
    @Override
    protected void OnCreate() {
        loadGameDebug();

        ///
        view.ConfigTableML(chDiemCauHoi);
    }


    /***
     * OnResume được gọi mỗi khi vòng đời activity được tạo mới
     * @param model
     */
    @Override
    protected void OnResume(Entity model){
//        /// add lại table
//        view.ConfigTableML(chDiemCauHoi);
//
//        /// Yêu cầu UI cập nhật Data ở view lại
//        view.ResumeUI((ResumeData) model);
    }



    /// -------------- Load ------------------
    private void loadGameDebug() {

        /// Debug
        String s = Resource.readRawTextFile(R.raw.test_cau_hinh_diem_cau_hoi);
        chDiemCauHoi = EntityManager.ParseJSON(CHDiemCauHoi.class, s);
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