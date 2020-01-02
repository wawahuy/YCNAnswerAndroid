package ml.huytools.ycnanswer.Views.GameComponents;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

import ml.huytools.ycnanswer.Core.Game.Event.OnTouchListener;
import ml.huytools.ycnanswer.Core.Game.Graphics.Color;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.RectangleShape;
import ml.huytools.ycnanswer.Core.Game.Graphics.Image;
import ml.huytools.ycnanswer.Core.Game.Graphics.Sprite;
import ml.huytools.ycnanswer.Core.Game.Graphics.Texture;
import ml.huytools.ycnanswer.Core.Game.Scenes.Node;
import ml.huytools.ycnanswer.Core.Game.Scenes.Scene;
import ml.huytools.ycnanswer.Core.MVP.EntityManager;
import ml.huytools.ycnanswer.Core.Math.Vector2D;
import ml.huytools.ycnanswer.Core.Resource;
import ml.huytools.ycnanswer.Models.Entities.CHDiemCauHoi;
import ml.huytools.ycnanswer.R;
import ml.huytools.ycnanswer.Views.GameComponents.BoxMoney;
import ml.huytools.ycnanswer.Views.GameComponents.BoxQuestion;
import ml.huytools.ycnanswer.Views.GameComponents.CountDown;
import ml.huytools.ycnanswer.Views.GameComponents.FPSDebug;
import ml.huytools.ycnanswer.Views.GameComponents.QuestionGroup;
import ml.huytools.ycnanswer.Views.GameComponents.SpotLight;
import ml.huytools.ycnanswer.Views.GameComponents.TableScore;
import ml.huytools.ycnanswer.Views.ResourceManager;

public class GameScene extends Scene {
    private Vector2D size;
    private Context context;
    ResourceManager resourceManager;

    ///-- Node --
    Sprite spriteChair;
    Sprite spritePC;

    ///-- Group --
    FPSDebug fpsDebug;
    CountDown countDown;
    SpotLight spotLight;
    TableScore tableScore;
    QuestionGroup questionGroup;
    BoxMoney boxMoney;

    public GameScene(Context context) {
        super();
        this.context = context;
        resourceManager = ResourceManager.getInstance(context);

        /// Chair
        Image imageChair = resourceManager.imageChair;
        Texture textureChair = new Texture(imageChair);
        spriteChair = new Sprite(textureChair);
        add(spriteChair);

        /// PC
        Image imagePC = resourceManager.imagePC;
        Texture texturePC = new Texture(imagePC);
        spritePC = new Sprite(texturePC);
        add(spritePC);

        /// FPS
        fpsDebug = new FPSDebug();
        fpsDebug.setPosition(0, 0);
        fpsDebug.setBoundingSize(new Vector2D(200, 100));
        add(fpsDebug);

        /// CountDown
        countDown = new CountDown();
        countDown.die();
        add(countDown);

        /// SpotLight Group
        spotLight = new SpotLight();
        spotLight.setZOrder(100);
        add(spotLight);

        /// Table Score
        tableScore = new TableScore();
        tableScore.setZOrder(90);
        add(tableScore);

        /// Question
        questionGroup = new QuestionGroup();
        add(questionGroup);

        /// Money
        boxMoney = new BoxMoney();
        add(boxMoney);

        /// Test
        countDown.start();
        tableScore.initData(EntityManager.ParseJSON(CHDiemCauHoi.class, Resource.readRawTextFile(R.raw.test_cau_hinh_diem_cau_hoi)));
    }

    public void initSizeScreen(Vector2D size) {
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
    }
}
