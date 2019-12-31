package ml.huytools.ycnanswer.Views;

import android.content.Context;
import android.graphics.Rect;

import java.util.Random;

import ml.huytools.ycnanswer.Core.Game.Graphics.Image;
import ml.huytools.ycnanswer.Core.Game.Graphics.Sprite;
import ml.huytools.ycnanswer.Core.Game.Graphics.Texture;
import ml.huytools.ycnanswer.Core.Game.Scenes.Scene;
import ml.huytools.ycnanswer.Core.Math.Vector2D;
import ml.huytools.ycnanswer.Views.GameComponents.CountDown;
import ml.huytools.ycnanswer.Views.GameComponents.FPSDebug;
import ml.huytools.ycnanswer.Views.GameComponents.SpotLight;

public class GameScene extends Scene {
    private Vector2D size;
    private Context context;
    ResourceManager resourceManager;

    ///--
    FPSDebug fpsDebug;
    CountDown countDown;
    SpotLight spotLight;

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


        /// Test
        countDown.start();

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
    }
}
