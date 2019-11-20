package ml.huytools.ycnanswer.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import ml.huytools.ycnanswer.Models.CauHoi;
import ml.huytools.ycnanswer.Presenters.GamePresenter;
import ml.huytools.ycnanswer.R;
import ml.huytools.ycnanswer.Views.GameViews.Components.CountDown;
import ml.huytools.ycnanswer.Views.GameViews.Components.CountDownAudio;
import ml.huytools.ycnanswer.Views.GameViews.Components.Loading;


public class GameActivity extends AppCompatActivity
        implements GamePresenter.View {

    GamePresenter presenter;
    ResourceManager resourceManager;
    CountDown countDown;
    CountDownAudio countDownAudio;

    ImageView imv_tableLevelQuestion;
    TextView txv_question;
    TextView txv_paA;
    TextView txv_paB;
    TextView txv_paC;
    TextView txv_paD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Test
        Loading loading = Loading.Create(this);

        /// init
        resourceManager = ResourceManager.getInstance(this);
        initView();
        initCountDown();

        /// P
        presenter = new GamePresenter(this);
        presenter.Start();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initView(){
        countDown = findViewById(R.id.countDown);
        txv_question = findViewById(R.id.txv_cauhoi);
        txv_paA = findViewById(R.id.txv_paA);
        txv_paB = findViewById(R.id.txv_paB);
        txv_paC = findViewById(R.id.txv_paC);
        txv_paD = findViewById(R.id.txv_paD);
        imv_tableLevelQuestion = findViewById(R.id.iv_tb_level_question);
    }

    private void initCountDown(){
        countDownAudio = new CountDownAudio();
        countDownAudio.setAudioTimeout(resourceManager.audioTimeout);
        countDown.setCallback(countDownAudio);
        countDown.setTimeCountDown(5);
    }

    @Override
    public void SetQuestionLevelTable(int level) {
    }

    @Override
    public void UpdateQuestion(CauHoi cauHoi) {
        txv_question.setText(cauHoi.getCauhoi());
        txv_paA.setText(cauHoi.getPaA());
        txv_paB.setText(cauHoi.getPaB());
        txv_paC.setText(cauHoi.getPaC());
        txv_paD.setText(cauHoi.getPaD());
    }

    @Override
    public void RestartCountDown() {
        countDown.start();
    }


    public void OnAnswer(View view){
        GamePresenter.ANSWER answer;
        switch (view.getId()){
            case R.id.txv_paA:
                answer = GamePresenter.ANSWER.A;
                break;

            case R.id.txv_paB:
                answer = GamePresenter.ANSWER.B;
                break;

            case R.id.txv_paC:
                answer = GamePresenter.ANSWER.C;
                break;

                default:
                answer = GamePresenter.ANSWER.D;
                break;
        }
        presenter.Answer(answer);
    }


    /***
     * Update Full Screen
     * @param hasFocus
     */
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
