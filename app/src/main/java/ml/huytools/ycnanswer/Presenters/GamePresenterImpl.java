package ml.huytools.ycnanswer.Presenters;

import java.util.LinkedList;

import ml.huytools.ycnanswer.Core.API.ApiOutput;
import ml.huytools.ycnanswer.Core.API.ApiProvider;
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

    int positionQuestionCurrent;

    public GamePresenterImpl(GameView gameView) {
        this.gameView = gameView;
    }

    private void error(String m){
        gameView.showMessage(m);
        gameView.close();
    }

    private void startGame(){
        gameView.hideLoading();
        gameView.setDataTableScore(configAllEntity.cau_hinh_cau_hoi);
        gameView.visibleAll(true);
        positionQuestionCurrent = 0;
        showQuestionCurrent();
    }

    private void showQuestionCurrent(){
        QuestionEntity question = questionEntities.get(positionQuestionCurrent);
        gameView.showQuestion(question);
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


}
