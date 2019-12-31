package ml.huytools.ycnanswer.Views;

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
import ml.huytools.ycnanswer.Views.GameComponents.BoxQuestion;
import ml.huytools.ycnanswer.Views.GameComponents.CountDown;
import ml.huytools.ycnanswer.Views.GameComponents.FPSDebug;
import ml.huytools.ycnanswer.Views.GameComponents.SpotLight;
import ml.huytools.ycnanswer.Views.GameComponents.TableScore;

public class GameScene extends Scene {
    private Vector2D size;
    private Context context;
    ResourceManager resourceManager;

    ///--
    FPSDebug fpsDebug;
    CountDown countDown;
    SpotLight spotLight;
    TableScore tableScore;

    public GameScene(Context context) {
        super();
        this.context = context;
        resourceManager = ResourceManager.getInstance(context);

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

    }
}
