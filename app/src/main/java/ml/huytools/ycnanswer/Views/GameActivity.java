package ml.huytools.ycnanswer.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import ml.huytools.ycnanswer.Core.MVP.ModelManager;
import ml.huytools.ycnanswer.Core.MVP.Presenter;
import ml.huytools.ycnanswer.Models.CHDiemCauHoi;
import ml.huytools.ycnanswer.Models.CauHoi;
import ml.huytools.ycnanswer.Presenters.GamePresenter;
import ml.huytools.ycnanswer.R;
import ml.huytools.ycnanswer.Views.ViewComponents.CountDownView;
import ml.huytools.ycnanswer.Views.Removing.Components.CountDownAudio;
import ml.huytools.ycnanswer.Views.Removing.Components.FPSDebugView;
import ml.huytools.ycnanswer.Views.Removing.Components.SpotLightView;
import ml.huytools.ycnanswer.Views.Removing.Components.TableMLView;


public class GameActivity extends AppCompatActivity implements GamePresenter.View {
    GamePresenter presenter;
    ResourceManager resourceManager;
    CountDownView countDown;
    CountDownAudio countDownAudio;
    SpotLightView spotLightView;
    TableMLView tableMLView;

    TextView txv_question;
    TextView txv_paA;
    TextView txv_paB;
    TextView txv_paC;
    TextView txv_paD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Log.v("Log", "Activity create: "+ toString());

        /// init
        resourceManager = ResourceManager.getInstance(this);
        initView();
        initCountDown();

        /// debug
        FPSDebugView.AddOnActivity(this);

        /// Tạo Presenter gắn với activity hiện tại
        presenter = Presenter.of(this, GamePresenter.class);
    }



    /// --------------- Resume Saved Data ------------------
    @Override
    protected void onDestroy() {
//        /// Save Data
//        GamePresenter.ResumeData data = new GamePresenter.ResumeData();
//        data.countDownStart = countDown.getTimeStart();
//        data.levelTableML = tableMLView.getPos();
//
//        /// Lưu data vào presenter
//        /// Nếu bắt đầu vòng đời mới presenter sẽ kích hoạt OnResume
//        presenter.postDataSaved(data);
        super.onDestroy();
    }

//    @Override
//    public void ResumeUI(final GamePresenter.ResumeData model) {
//        if(model != null){
//            countDown.setTimeStart(model.countDownStart);
//            countDown.startIfNotPaused();
//            tableMLView.setPos(model.levelTableML);
//            tableMLView.draw();
//        }
//    }


    /// ----------------- Init -------------------
    private void initView(){
        countDown = findViewById(R.id.countDown);
        tableMLView = findViewById(R.id.iv_tb_level_question);
        spotLightView = findViewById(R.id.spotLight);
        txv_question = findViewById(R.id.txv_cauhoi);
        txv_paA = findViewById(R.id.txv_paA);
        txv_paB = findViewById(R.id.txv_paB);
        txv_paC = findViewById(R.id.txv_paC);
        txv_paD = findViewById(R.id.txv_paD);
    }

    private void initCountDown(){
        //set audio
        countDownAudio = new CountDownAudio();
        countDownAudio.setAudioTimeout(resourceManager.audioTimeout);
//        countDown.setCallback(countDownAudio);
    }


    /// ----------- CustomLoader --------------------
    @Override
    public void OpenLoading() {
        setContentView(R.layout.game_loading);
    }

    @Override
    public void CloseLoading() {
        setContentView(R.layout.activity_game);
        initView();
        initCountDown();
    }

    @Override
    public void UpdateLoadingText(String message) {
        ((TextView) findViewById(R.id.txvLoad)).setText(message);
    }

    @Override
    public void UpdateLoadingBar(int p) {
        ((ProgressBar)findViewById(R.id.barLoad)).setProgress(p);
    }

    /// ------------- Bang diem ------------------
    @Override
    public void ConfigTableML(ModelManager<CHDiemCauHoi> chDiemCauHoi) {
        tableMLView.Config(chDiemCauHoi);
    }

    @Override
    public void SetLevelTableML(int level) {
    }

    @Override
    public void IncreaseLevelTableML() {
        tableMLView.incPos();
    }


    /// ------------- Cau Hoi --------------------
    @Override
    public void UpdateQuestion(CauHoi cauHoi) {
        txv_question.setText(cauHoi.getCauhoi());
        txv_paA.setText(cauHoi.getPaA());
        txv_paB.setText(cauHoi.getPaB());
        txv_paC.setText(cauHoi.getPaC());
        txv_paD.setText(cauHoi.getPaD());
    }

    public void OnAnswer(View view){
        GamePresenter.ANSWER answer;
        switch (view.getId()){
            case R.id.txv_paA: answer = GamePresenter.ANSWER.A; break;
            case R.id.txv_paB: answer = GamePresenter.ANSWER.B; break;
            case R.id.txv_paC: answer = GamePresenter.ANSWER.C; break;
            default:
                answer = GamePresenter.ANSWER.D;
                break;
        }

        presenter.Answer(answer);
    }



    /// ------------- Dem Nguoc --------------------
    @Override
    public void RestartCountDown() {
//        countDown.reset();
    }

    @Override
    public void ConfigCountDownTime(int second) {
//        countDown.setTimeCountDown(second);
    }



    /// ------------- Light ------------------------
    @Override
    public void RunEffectLight() {
        /// Run on thread RenderingLoop.class
       spotLightView.post(new Runnable() {
            @Override
            public void run() {
                spotLightView.runFlickerAmbientLight();
            }
        });
    }



    /// -------------- Full screen, hide navigation bar ---------
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
