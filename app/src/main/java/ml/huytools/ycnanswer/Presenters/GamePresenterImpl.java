package ml.huytools.ycnanswer.Presenters;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONArray;

import java.util.LinkedHashMap;
import java.util.Random;

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
import ml.huytools.ycnanswer.Models.Entities.findConfigQuestionEntity;
import ml.huytools.ycnanswer.Models.Entities.QuestionEntity;
import ml.huytools.ycnanswer.Models.Entities.UserEntity;
import ml.huytools.ycnanswer.Models.QuestionModel;
import ml.huytools.ycnanswer.Models.TurnModel;
import ml.huytools.ycnanswer.Models.UserModel;
import ml.huytools.ycnanswer.Presenters.Interface.GamePresenter;
import ml.huytools.ycnanswer.Views.CreditActivity;
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

    JSONArray jsonTL;
    int creditUse;

    public GamePresenterImpl(GameView gameView) {
        this.gameView = gameView;
        scheduler = GameDirector.getInstance().getScheduler();
        jsonTL = new JSONArray();
        creditUse = 0;
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
        updateCredit(0);

        gameView.hideIconSupportSpectator();
        gameView.hideIconSupport50();
        gameView.hideIconSupportCall();

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
                QuestionEntity questionEntity = questionEntities.get(positionQuestionCurrent);
                gameView.showQuestion(questionEntity);
                gameView.startCountDown();
                gameView.setEnableTouchAll(true);
                jsonTL.put(questionEntity.id);

                /// Check support
                findConfigQuestionEntity configQuestionEntity = findConfigLL(positionQuestionCurrent+1);
                if(configQuestionEntity.moc){
                    for(ConfigHelpEntity helpEntity:configQuestionEntity.helps){
                        switch (helpEntity.loai_tro_giup){
                            case ID_HELP_50:
                              gameView.showIconSupport50();
                              break;

                            case ID_HELP_CALL:
                                gameView.showIconSupportCall();
                                break;

                            case ID_HELP_SPECTATOR:
                                gameView.showIconSupportSpectator();
                                break;
                        }
                    }
                }

            }
        }, 2000));
    }


    private findConfigQuestionEntity findConfigLL(int tt){
        for(findConfigQuestionEntity configQuestionEntity:configAllEntity.cau_hinh_cau_hoi){
            if(configQuestionEntity.thu_tu == tt){
                return configQuestionEntity;
            }
        }
        return null;
    }

    private void updateCredit(int creditAdd){
        UserEntity userEntity = UserModel.getUserGlobal();
        userEntity.credit += creditAdd;
        gameView.setBoxCredit(userEntity.credit);
        creditUse += creditAdd;
    }

    public int planStrToInt(String plan){
        if(plan.equals("A")) return 0;
        if(plan.equals("B")) return 1;
        if(plan.equals("C")) return 2;
        return 3;
    }

    public String planIntToStr(int plan){
        final String[] splans = new String[]{"A", "B", "C", "D"};
        return splans[plan];
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
        for(findConfigQuestionEntity configQuestionEntity:configAllEntity.cau_hinh_cau_hoi){
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
                    if(positionQuestionCurrent >= questionEntities.size()){
                        win();
                        return;
                    }
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
    public void support50() {
        int credit = 0;
        for(ConfigHelpEntity configHelpEntity:configAllEntity.cau_hinh_tro_giup){
            if(configHelpEntity.loai_tro_giup == ID_HELP_50){
                credit = configHelpEntity.credit;
            }
        }

        if(credit > UserModel.getUserGlobal().credit){
            gameView.showMessage("Không đủ credit!");
            return;
        }
        updateCredit(-credit);


        QuestionEntity questionEntity = questionEntities.get(positionQuestionCurrent);

        LinkedHashMap<Integer, String> list = new LinkedHashMap<>();
        list.put(0, "A");
        list.put(1, "B");
        list.put(2, "C");
        list.put(3, "D");
        list.remove(planStrToInt(questionEntity.dapan));
        list.remove(list.keySet().toArray()[new Random().nextInt(3)]);
        gameView.clearPlans(list.values().toArray());
        gameView.hideIconSupport50();
    }

    @Override
    public void supportSpectator() {
        int credit = 0;
        for(ConfigHelpEntity configHelpEntity:configAllEntity.cau_hinh_tro_giup){
            if(configHelpEntity.loai_tro_giup == ID_HELP_SPECTATOR){
                credit = configHelpEntity.credit;
            }
        }

        if(credit > UserModel.getUserGlobal().credit){
            gameView.showMessage("Không đủ credit!");
            return;
        }

        updateCredit(-credit);


        int rWin = new Random().nextInt(30)+30;

        int rLose[] = new int[3];
        rLose[0] = new Random().nextInt((100 - rWin)/3);
        rLose[1] = new Random().nextInt((100- rWin -rLose[0])/2);
        rLose[2] = 100 - rWin - rLose[1];

        QuestionEntity questionEntity = questionEntities.get(positionQuestionCurrent);
        LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(questionEntity.dapan, rWin);

        String[] dps = new String[]{ "A", "B", "C", "D"};
        int i= 0;
        for (String dp:dps) {
            if(!dp.equals(questionEntity.dapan)){
                linkedHashMap.put(dp, rLose[i++]);
            }
        }
        gameView.addBoxSpectator(linkedHashMap);
        scheduler.schedule(ScheduleAction.One(new ScheduleCallback() {
            @Override
            public void OnScheduleCallback(float dt) {
                gameView.removeBoxSpectator();
            }
        }, 5000));

        gameView.hideIconSupportSpectator();
    }

    @Override
    public void supporCall() {
        gameView.showMessage("Chức năng chưa hổ trợ.");
    }

    @Override
    public void lose() {
        gameView.showLose("Thua cuộc!");
        saveDataCredit();
    }

    public void win(){
        gameView.showWin("Thắng cuộc!");
        saveDataCredit();
    }

    private void saveDataCredit(){
        UserModel.getUserGlobal().credit += creditUse;

        /// Net
        final ApiProvider.Async.Callback callback = new ApiProvider.Async.Callback() {
            @Override
            public void OnAPIResult(ApiOutput output, int requestCode) {
                saveDataTurn();
            }
        };

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                gameView.showLoading();
                UserModel.addCredit(creditUse, callback);
            }
        });
    }

    private void saveDataTurn(){
        /// Net
        final ApiProvider.Async.Callback callback = new ApiProvider.Async.Callback() {
            @Override
            public void OnAPIResult(ApiOutput output, int requestCode) {
                gameView.hideLoading();
            }
        };

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                TurnModel.addTurn(jsonTL, callback);
            }
        });
    }


}
