package ml.huytools.ycnanswer.Presenters;

import java.util.LinkedList;

import ml.huytools.ycnanswer.Core.API.ApiOutput;
import ml.huytools.ycnanswer.Core.API.ApiProvider;
import ml.huytools.ycnanswer.Core.Game.GameDirector;
import ml.huytools.ycnanswer.Core.Game.Schedules.ScheduleAction;
import ml.huytools.ycnanswer.Core.Game.Schedules.ScheduleCallback;
import ml.huytools.ycnanswer.Core.Game.Schedules.Scheduler;
import ml.huytools.ycnanswer.Core.MVP.EntityManager;
import ml.huytools.ycnanswer.Models.ConfigModel;
import ml.huytools.ycnanswer.Models.Entities.ConfigAllEntity;
import ml.huytools.ycnanswer.Models.Entities.ConfigHelpEntity;
import ml.huytools.ycnanswer.Models.Entities.ConfigQuestionEntity;
import ml.huytools.ycnanswer.Models.Entities.QuestionEntity;
import ml.huytools.ycnanswer.Models.QuestionModel;
import ml.huytools.ycnanswer.Presenters.Interface.GamePresenter;
import ml.huytools.ycnanswer.Views.Interface.GameView;

public class GamePresenterImpl implements GamePresenter {
    final int ID_HELP_50 = 1;
    final int ID_HELP_SPECTATOR =2;
    final int ID_HELP_CALL = 3;

    GameView gameView;
    EntityManager<QuestionEntity> questionEntities;
    ConfigAllEntity configAllEntity;
    Scheduler scheduler;

    int positionQuestionCurrent;

    public GamePresenterImpl(GameView gameView) {
        this.gameView = gameView;
        scheduler = GameDirector.getInstance().getScheduler();
    }

    private void error(String m){
        gameView.showMessage(m);
        gameView.close();
    }

    private void startGame(){
        gameView.hideLoading();
        gameView.setDataTableScore(configAllEntity.cau_hinh_cau_hoi);
        gameView.visibleAll(true);
        positionQuestionCurrent = -1;

        scheduler.schedule(ScheduleAction.One(new ScheduleCallback() {
            @Override
            public void OnScheduleCallback(float dt) {
                nextQuestion();
            }
        }, 100));
    }

    private void nextQuestion(){
        positionQuestionCurrent++;
        gameView.setPositionScoreTable(positionQuestionCurrent);
        gameView.runEffectLight();
        gameView.stopContDown();
        gameView.setEnableTouchAll(false);

        /// disable and run effect
        scheduler.schedule(ScheduleAction.One(new ScheduleCallback() {
            @Override
            public void OnScheduleCallback(float dt) {
                gameView.showQuestion(questionEntities.get(positionQuestionCurrent));
                gameView.startCountDown();
                gameView.setEnableTouchAll(true);
            }
        }, 2000));
    }


    private void loadConfig(){
        gameView.updateTextLoading("Tải cấu hình...");

        ApiProvider.Async.Callback callback = new ApiProvider.Async.Callback() {
            @Override
            public void OnAPIResult(ApiOutput output, int requestCode) {
                if(output.isDJObject()){
                    configAllEntity = (ConfigAllEntity) output.toModel(ConfigAllEntity.class);
                    initDataConfig();
                    startGame();
                } else {
                    error(output.Message);
                }
            }
        };
        ConfigModel.getConfig(callback);
    }

    private void initDataConfig(){
        for(ConfigQuestionEntity configQuestionEntity:configAllEntity.cau_hinh_cau_hoi){
            for(ConfigHelpEntity configHelpEntity:configAllEntity.cau_hinh_tro_giup){
                if(configQuestionEntity.thu_tu == configHelpEntity.thu_tu){
                    if(configQuestionEntity.helps == null){
                        configQuestionEntity.helps = new EntityManager<>();
                        configQuestionEntity.moc = true;
                    }
                    configQuestionEntity.helps.add(configHelpEntity);
                }
            }
        }
    }

    @Override
    public void loadQuestions(int categoriesID) {
        gameView.showLoading();
        gameView.updateTextLoading("Tải câu hỏi...");

        ApiProvider.Async.Callback callback = new ApiProvider.Async.Callback() {
            @Override
            public void OnAPIResult(ApiOutput output, int requestCode) {
                if(output.isDJArray()){
                    questionEntities = output.toModelManager(QuestionEntity.class);
                    loadConfig();
                } else {
                    error(output.Message);
                }
            }
        };
        QuestionModel.getQuestionByCategoriesID(categoriesID, callback);
    }

    @Override
    public void answer(final String answer) {
        gameView.setEnableTouchAll(false);
        gameView.setWarningPlanQuestion(answer);
        gameView.stopContDown();
        gameView.runEffectLightSlow();

        scheduler.schedule(ScheduleAction.One(new ScheduleCallback() {
            @Override
            public void OnScheduleCallback(float dt) {
                QuestionEntity cur = questionEntities.get(positionQuestionCurrent);
                if(answer.equals(cur.dapan)){
                    gameView.setSuccessPlanQuestion(answer);
                    nextQuestion();
                } else {
                    gameView.setErrorPlanQuestion(answer);
                    gameView.setSuccessPlanQuestion(cur.dapan);
                    lose();
                }
            }
        }, 1500));
    }

    @Override
    public void lose() {
    }


}
