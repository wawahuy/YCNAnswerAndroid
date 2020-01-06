package ml.huytools.ycnanswer.Views;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;

import ml.huytools.ycnanswer.Core.Game.Actions.Action;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionSequence;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionCubicBezier;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionScaleTo;
import ml.huytools.ycnanswer.Core.Game.Event.OnTouchListener;
import ml.huytools.ycnanswer.Core.Game.GameDirector;
import ml.huytools.ycnanswer.Core.Game.Graphics.Color;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.Drawable;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.RectangleShape;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.RoundRectangleShape;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.Text;
import ml.huytools.ycnanswer.Core.Game.Graphics.Image;
import ml.huytools.ycnanswer.Core.Game.Graphics.Sprite;
import ml.huytools.ycnanswer.Core.Game.Graphics.Texture;
import ml.huytools.ycnanswer.Core.Game.Scenes.Node;
import ml.huytools.ycnanswer.Core.Game.Scenes.Scene;
import ml.huytools.ycnanswer.Core.Game.Schedules.ScheduleAction;
import ml.huytools.ycnanswer.Core.Game.Schedules.ScheduleCallback;
import ml.huytools.ycnanswer.Core.MVP.EntityManager;
import ml.huytools.ycnanswer.Core.Math.Vector2D;
import ml.huytools.ycnanswer.Models.Entities.findConfigQuestionEntity;
import ml.huytools.ycnanswer.Models.Entities.QuestionEntity;
import ml.huytools.ycnanswer.Models.TurnModel;
import ml.huytools.ycnanswer.Presenters.GamePresenterImpl;
import ml.huytools.ycnanswer.Presenters.Interface.GamePresenter;
import ml.huytools.ycnanswer.Views.GameComponents.BoxHelpCall;
import ml.huytools.ycnanswer.Views.GameComponents.BoxHelpSpectator;
import ml.huytools.ycnanswer.Views.GameComponents.BoxMoney;
import ml.huytools.ycnanswer.Views.GameComponents.CountDown;
import ml.huytools.ycnanswer.Views.GameComponents.FPSDebug;
import ml.huytools.ycnanswer.Views.GameComponents.QuestionGroup;
import ml.huytools.ycnanswer.Views.GameComponents.SpotLight;
import ml.huytools.ycnanswer.Views.GameComponents.TableScore;
import ml.huytools.ycnanswer.Views.Interface.GameView;
import ml.huytools.ycnanswer.Views.ViewComponents.LoadingView;

import static android.graphics.Typeface.BOLD;

public class GameScene extends Scene implements GameView, OnTouchListener, QuestionGroup.QuestionCallback, CountDown.OnCountDownEnd {
    private Vector2D size;
    private WeakReference<Activity> activity;
    ResourceManager resourceManager;

    Sprite spriteChair;
    Sprite spritePC;
    Sprite spriteHelp50;
    Sprite spriteHelpSpectator;
    Sprite spriteHelpCall;
    Sprite spriteHome;
    FPSDebug fpsDebug;
    CountDown countDown;
    SpotLight spotLight;
    TableScore tableScore;
    QuestionGroup questionGroup;
    BoxMoney boxMoney;
    BoxHelpSpectator boxHelpSpectator;
    BoxHelpCall boxHelpCall;

    LoadingView loadingView;
    GamePresenter gamePresenter;

    public GameScene() {
        super();
    }

    public void create(Activity activity){
        this.activity = new WeakReference<>(activity);
        resourceManager = ResourceManager.getInstance(activity.getBaseContext());

        gamePresenter = new GamePresenterImpl(this);
        gamePresenter.loadQuestions(activity.getIntent().getIntExtra(CategoriesActivity.ID_CATEGORIES_EX, -1));

        /// Chair
        Image imageChair = resourceManager.imageChair;
        Texture textureChair = new Texture(imageChair);
        spriteChair = new Sprite(textureChair);
        spriteChair.setVisible(false);
//        add(spriteChair);

        /// PC
        Image imagePC = resourceManager.imagePC;
        Texture texturePC = new Texture(imagePC);
        spritePC = new Sprite(texturePC);
        spritePC.setVisible(false);
//        add(spritePC);

        /// Help 50
        Image imageHelp50 = resourceManager.imageHelp50;
        Texture textureHelp50 = new Texture(imageHelp50);
        spriteHelp50 = new Sprite(textureHelp50);
        spriteHelp50.setVisible(false);
        spriteHelp50.setTouchListener(this);
        add(spriteHelp50);

        /// Help spectator
        Image imageHelpSpectator = resourceManager.imageHelpSpectator;
        Texture textureHelpSpectator = new Texture(imageHelpSpectator);
        spriteHelpSpectator = new Sprite(textureHelpSpectator);
        spriteHelpSpectator.setVisible(false);
        spriteHelpSpectator.setTouchListener(this);
        add(spriteHelpSpectator);

        /// Help call
        Image imageHelpCall = resourceManager.imageHelpCall;
        Texture textureHelpCall = new Texture(imageHelpCall);
        spriteHelpCall = new Sprite(textureHelpCall);
        spriteHelpCall.setVisible(false);
        spriteHelpCall.setTouchListener(this);
        add(spriteHelpCall);

        /// Home
        Image imageHome = resourceManager.imageHome;
        Texture textureHome = new Texture(imageHome);
        spriteHome = new Sprite(textureHome);
        spriteHome.setVisible(false);
        spriteHome.setTouchListener(this);
        add(spriteHome);

        /// FPS
        fpsDebug = new FPSDebug();
        fpsDebug.setPosition(0, 0);
        fpsDebug.setBoundingSize(new Vector2D(200, 100));
        add(fpsDebug);

        /// CountDown
        countDown = new CountDown();
        countDown.die();
        countDown.setVisible(false);
        add(countDown);

        /// SpotLight Group
        spotLight = new SpotLight();
        spotLight.setZOrder(100);
        spotLight.setVisible(false);
        add(spotLight);

        /// Table Score
        tableScore = new TableScore();
        tableScore.setZOrder(90);
        tableScore.setVisible(false);
        add(tableScore);

        /// Question
        questionGroup = new QuestionGroup(this);
        questionGroup.setVisible(false);
        add(questionGroup);

        /// Money
        boxMoney = new BoxMoney();
        boxMoney.setVisible(false);
        add(boxMoney);
    }

    public void initSizeScreen(Vector2D size) {
        if(size == null){
            return;
        }

        this.size = size;
        int w = (int)size.x;
        int h = (int)size.y;
        int halfW = w/2;
        int halfH = h/2;

        /// Countdown
        int sizeCountDown = (int)(halfW*0.2f);
        countDown.setSizeBounding(new Vector2D(sizeCountDown, sizeCountDown));
        countDown.setPosition(halfW - sizeCountDown/2, -(int)(sizeCountDown/8));
        countDown.setOnCountDownEnd(this);

        /// SpotLight
        spotLight.setBoundingSize(size);

        /// Table Score
        tableScore.setBoundingSize(new Vector2D(halfW*0.35f, h*0.6f));
        tableScore.setPosition(w*0.75f, h*0.25f);

        /// Question
        float xQuestion = halfW*0.4f;
        questionGroup.setPosition(xQuestion, halfH);
        questionGroup.setBoundingSize(new Vector2D(halfW, halfH));

        /// money
        boxMoney.setPosition(halfW*0.05f, halfH*1.2f);
        boxMoney.setBoundingSize(new Vector2D(halfW*0.3f, halfH*0.3f));

        /// Sprite
        Vector2D sizePC = new Vector2D(h*0.2f, h*0.2f);
        float posXPC = xQuestion + halfW/2 - sizePC.x/2;
        float posYPC = halfH - sizePC.y;
        spritePC.scaleDraw(sizePC, Sprite.ScaleType.FitXY);
        spritePC.setPosition(posXPC, posYPC);

        Vector2D sizeChair = new Vector2D(h*0.04f, h*0.15f);
        float posYChair = halfH - sizeChair.y;
        spriteChair.scaleDraw(sizePC, Sprite.ScaleType.FitXY);
        spriteChair.setPosition(posXPC + sizePC.x*0.8f, posYChair);

        Vector2D sizeIcon = new Vector2D(halfW*0.11f, halfW*0.11f);
        float yIcon = halfH*0.02f;
        float xHome = sizeIcon.x;
        spriteHome.setPosition(xHome, yIcon);
        spriteHome.scaleDraw(sizeIcon, Sprite.ScaleType.FitXY);

        /// Help
        float xHelpCall = w - sizeIcon.x*2;
        spriteHelpCall.setPosition(xHelpCall, yIcon);
        spriteHelpCall.scaleDraw(sizeIcon, Sprite.ScaleType.FitXY);

        float xHelp50 = w - sizeIcon.x*3.5f;
        spriteHelp50.setPosition(xHelp50, yIcon);
        spriteHelp50.scaleDraw(sizeIcon, Sprite.ScaleType.FitXY);

        float xHelpSpectator = w - sizeIcon.x*5;
        spriteHelpSpectator.setPosition(xHelpSpectator, yIcon);
        spriteHelpSpectator.scaleDraw(sizeIcon, Sprite.ScaleType.FitXY);


    }


    /// ---------- Interface GameView -----------------
    @Override
    public void visibleAll(boolean status){
        for(Node node:getGroup().getListNode()){
            node.setVisible(status);
        }
    }

    @Override
    public void setEnableTouchAll(boolean status) {
        getGroup().setEnableTouch(status);
    }

    @Override
    public void showLoading() {
        loadingView = LoadingView.create(activity.get());
    }

    @Override
    public void updateTextLoading(final String text) {
        loadingView.setText(text);
    }

    @Override
    public void hideLoading() {
        loadingView.removeOnView();
    }

    @Override
    public void showMessage(final String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity.get().getBaseContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void setDataTableScore(EntityManager<findConfigQuestionEntity> configQuestionEntities) {
        tableScore.initData(configQuestionEntities);
    }

    @Override
    public void showQuestion(QuestionEntity questionEntity) {
        questionGroup.showQuestion(questionEntity);
    }

    @Override
    public void setWarningPlanQuestion(String planQuestion) {
        questionGroup.setWarningPlanQuestion(planQuestion);
    }

    @Override
    public void setSuccessPlanQuestion(String planQuestion) {
        questionGroup.setSuccessPlanQuestion(planQuestion);
    }

    @Override
    public void setErrorPlanQuestion(String planQuestion) {
        questionGroup.setErrorPlanQuestion(planQuestion);
    }

    @Override
    public void setPositionScoreTable(int positionScoreTable) {
        tableScore.setPositionSelect(positionScoreTable);
    }

    @Override
    public void runEffectLight() {
        spotLight.runEffectFlickerAmbient();
    }

    @Override
    public void runEffectLightSlow() {
        spotLight.runEffectSlow();
    }

    @Override
    public void startCountDown() {
        countDown.start();
    }

    @Override
    public void stopContDown() {
        countDown.die();
    }

    @Override
    public void setBoxCredit(int credit) {
        boxMoney.getText().setText("$" + String.valueOf(credit));
        boxMoney.getText().runAction(ActionSequence.create(
                ActionCubicBezier.EaseIn(ActionScaleTo.create(new Vector2D(1.1f, 1.1f), 200)),
                ActionCubicBezier.EaseOut(ActionScaleTo.create(new Vector2D(1.0f, 1.0f), 200))
        ));
    }

    @Override
    public void addBoxSpectator(LinkedHashMap<String, Integer> lp) {
        boxHelpSpectator = new BoxHelpSpectator(lp);
        boxHelpSpectator.setPosition(150, 400);
        add(boxHelpSpectator);
    }

    @Override
    public void addBoxHelpCall(String plan) {
        boxHelpCall = new BoxHelpCall(plan, activity.get().getBaseContext());
        boxHelpCall.setPosition(400, 250);
        add(boxHelpCall);
    }

    @Override
    public void removeBoxHelpCall() {
        if(boxHelpCall != null){
            remove(boxHelpCall);
        }
    }

    @Override
    public void removeBoxSpectator() {
        if(boxHelpSpectator != null){
            remove(boxHelpSpectator);
        }
    }

    @Override
    public void clearPlans(Object[] plans) {
        questionGroup.clearPlans(plans);
    }

    @Override
    public void showIconSupport50() {
        spriteHelp50.setEnableTouch(true);
        spriteHelp50.setVisible(true);
    }

    @Override
    public void hideIconSupport50() {
        spriteHelp50.setEnableTouch(false);
        spriteHelp50.setVisible(false);
    }

    @Override
    public void showIconSupportSpectator() {
        spriteHelpSpectator.setEnableTouch(true);
        spriteHelpSpectator.setVisible(true);
    }

    @Override
    public void hideIconSupportSpectator() {
        spriteHelpSpectator.setEnableTouch(false);
        spriteHelpSpectator.setVisible(false);
    }

    @Override
    public void showIconSupportCall() {
        spriteHelpCall.setEnableTouch(true);
        spriteHelpCall.setVisible(true);
    }

    @Override
    public void hideIconSupportCall() {
        spriteHelpCall.setEnableTouch(false);
        spriteHelpCall.setVisible(false);
    }

    @Override
    public void showWin(String messeage) {
        showTB(messeage);
    }

    @Override
    public void showLose(String messeage) {
        showTB(messeage);
    }

    public void showTB(String message){
        RoundRectangleShape rectangleShape = new RoundRectangleShape();
        rectangleShape.setSize(size.mul(0.7f));
        rectangleShape.centerOrigin(true);
        rectangleShape.setColor(new Color(53, 170, 202));
        rectangleShape.setRoundSize(30);
        rectangleShape.setStyle(Drawable.Style.STROKE);
        rectangleShape.setStrokeWidth(40);
        rectangleShape.setPosition(size.mul(0.5f));
        rectangleShape.setZOrder(10001);
        add(rectangleShape);

        RoundRectangleShape rectangleShapeBG = new RoundRectangleShape();
        rectangleShapeBG.setSize(size.mul(0.7f));
        rectangleShapeBG.centerOrigin(true);
        rectangleShapeBG.setColor(new Color(140, 206, 225));
        rectangleShapeBG.setRoundSize(30);
        rectangleShapeBG.setPosition(size.mul(0.5f));
        rectangleShapeBG.setZOrder(10000);
        add(rectangleShapeBG);

        Text text = new Text();
        text.setPosition(size.x*0.5f, size.y*0.4f);
        text.setSize(120);
        text.setTextStyle(BOLD);
        text.setText(message);
        text.centerOrigin(true);
        text.setZOrder(10000000);
        add(text);

        spriteHome.setZOrder(100000000);
        spriteHome.setPosition(size.x*0.49f, size.y*0.6f);
        spriteHome.setEnableTouch(true);
        spriteHome.setEnableAction(true);
        spriteHome.setVisible(true);

        GameDirector.getInstance().getScheduler().schedule(ScheduleAction.One(new ScheduleCallback() {
            @Override
            public void OnScheduleCallback(float dt) {
                close();
            }
        }, 4000));

    }



    @Override
    public void close() {
        activity.get().finish();
    }


    /// ---------- Interface OnTouchListener ---------
    @Override
    public void OnTouchBegin(Node node, Vector2D p) {
    }

    @Override
    public void OnTouchMove(Node node, Vector2D p) {
    }

    @Override
    public void OnTouchEnd(Node node, Vector2D p) {

        /// Home
        if(node.getId() == spriteHome.getId()){
            close();
        }

        /// Support 50
        if(node.getId() == spriteHelp50.getId()){
            gamePresenter.support50();
        }

        /// Support spectator
        if(node.getId() == spriteHelpSpectator.getId()){
            gamePresenter.supportSpectator();

        }

        if(node.getId() == spriteHelpCall.getId()){
            gamePresenter.supportCall();
        }
    }

    /// -------- Interface QuestionCallback ------
    @Override
    public void OnAnswer(String answer) {
        gamePresenter.answer(answer);
    }

    @Override
    public void OnCountDownEnd() {
        gamePresenter.lose();
    }
}
