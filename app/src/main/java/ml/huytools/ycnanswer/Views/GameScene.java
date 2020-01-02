package ml.huytools.ycnanswer.Views;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

import ml.huytools.ycnanswer.Core.Game.Event.OnTouchListener;
import ml.huytools.ycnanswer.Core.Game.Graphics.Image;
import ml.huytools.ycnanswer.Core.Game.Graphics.Sprite;
import ml.huytools.ycnanswer.Core.Game.Graphics.Texture;
import ml.huytools.ycnanswer.Core.Game.Scenes.Node;
import ml.huytools.ycnanswer.Core.Game.Scenes.Scene;
import ml.huytools.ycnanswer.Core.MVP.EntityManager;
import ml.huytools.ycnanswer.Core.Math.Vector2D;
import ml.huytools.ycnanswer.Models.Entities.ConfigQuestionEntity;
import ml.huytools.ycnanswer.Models.Entities.QuestionEntity;
import ml.huytools.ycnanswer.Presenters.GamePresenterImpl;
import ml.huytools.ycnanswer.Presenters.Interface.GamePresenter;
import ml.huytools.ycnanswer.Views.GameComponents.BoxMoney;
import ml.huytools.ycnanswer.Views.GameComponents.CountDown;
import ml.huytools.ycnanswer.Views.GameComponents.FPSDebug;
import ml.huytools.ycnanswer.Views.GameComponents.QuestionGroup;
import ml.huytools.ycnanswer.Views.GameComponents.SpotLight;
import ml.huytools.ycnanswer.Views.GameComponents.TableScore;
import ml.huytools.ycnanswer.Views.Interface.GameView;
import ml.huytools.ycnanswer.Views.ViewComponents.LoadingView;

public class GameScene extends Scene implements GameView, OnTouchListener {
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
        add(spriteChair);

        /// PC
        Image imagePC = resourceManager.imagePC;
        Texture texturePC = new Texture(imagePC);
        spritePC = new Sprite(texturePC);
        spritePC.setVisible(false);
        add(spritePC);

        /// Help 50
        Image imageHelp50 = resourceManager.imageHelp50;
        Texture textureHelp50 = new Texture(imageHelp50);
        spriteHelp50 = new Sprite(textureHelp50);
        spriteHelp50.setEnableAction(false);
        spriteHelp50.setVisible(false);
        add(spriteHelp50);

        /// Help spectator
        Image imageHelpSpectator = resourceManager.imageHelpSpectator;
        Texture textureHelpSpectator = new Texture(imageHelpSpectator);
        spriteHelpSpectator = new Sprite(textureHelpSpectator);
        spriteHelpSpectator.setEnableAction(false);
        spriteHelpSpectator.setVisible(false);
        add(spriteHelpSpectator);

        /// Help call
        Image imageHelpCall = resourceManager.imageHelpCall;
        Texture textureHelpCall = new Texture(imageHelpCall);
        spriteHelpCall = new Sprite(textureHelpCall);
        spriteHelpCall.setEnableAction(false);
        spriteHelpCall.setVisible(false);
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
        questionGroup = new QuestionGroup();
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

        /// Help
        Vector2D sizeIcon = new Vector2D(halfW*0.11f, halfW*0.11f);
        float yIcon = halfH*0.02f;

        float xHelpCall = w - sizeIcon.x*2;
        spriteHelpCall.setPosition(xHelpCall, yIcon);
        spriteHelpCall.scaleDraw(sizeIcon, Sprite.ScaleType.FitXY);

        float xHelp50 = w - sizeIcon.x*3.5f;
        spriteHelp50.setPosition(xHelp50, yIcon);
        spriteHelp50.scaleDraw(sizeIcon, Sprite.ScaleType.FitXY);

        float xHelpSpectator = w - sizeIcon.x*5;
        spriteHelpSpectator.setPosition(xHelpSpectator, yIcon);
        spriteHelpSpectator.scaleDraw(sizeIcon, Sprite.ScaleType.FitXY);

        float xHome = sizeIcon.x;
        spriteHome.setPosition(xHome, yIcon);
        spriteHome.scaleDraw(sizeIcon, Sprite.ScaleType.FitXY);
    }

    @Override
    public void visibleAll(boolean status){
        for(Node node:getGroup().getListNode()){
            node.setVisible(status);
        }
    }

    @Override
    public void showLoading() {
        loadingView = LoadingView.create(activity.get());
    }

    @Override
    public void updateTextLoading(String text) {
        loadingView.setText(text);
    }

    @Override
    public void hideLoading() {
        loadingView.removeOnView();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(activity.get().getBaseContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setDataTableScore(EntityManager<ConfigQuestionEntity> configQuestionEntities) {
        tableScore.initData(configQuestionEntities);
    }

    @Override
    public void showQuestion(QuestionEntity questionEntity) {
        questionGroup.showQuestion(questionEntity);
        countDown.start();
    }

    @Override
    public void close() {
        activity.get().finish();
    }


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

    }
}
