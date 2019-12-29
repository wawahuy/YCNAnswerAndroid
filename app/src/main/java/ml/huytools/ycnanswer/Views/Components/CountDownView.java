package ml.huytools.ycnanswer.Views.Components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

import java.util.Random;

import ml.huytools.ycnanswer.Core.Game.Actions.Action;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionDelay;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionDrawings.ActionCircleAngleBy;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionDrawings.ActionCircleAngleStartBy;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionDrawings.ActionCircleAngleTo;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionDrawings.ActionCircleRadiusTo;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionDrawings.ActionColorTo;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionFunc;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionRepeat;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionSequence;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionSpawn;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionCubicBezier;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionScaleTo;
import ml.huytools.ycnanswer.Core.Game.Graphics.Color;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.CircleShape;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.Drawable;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.Text;
import ml.huytools.ycnanswer.Core.Game.Renderer;
import ml.huytools.ycnanswer.Core.Game.Scenes.Node;
import ml.huytools.ycnanswer.Core.Game.Scenes.Scene;
import ml.huytools.ycnanswer.Core.Math.Vector2D;

public class CountDownView extends SurfaceView implements Renderer.Callback {
    private Renderer renderer;
    private CountDown countDown;


    public CountDownView(Context context, AttributeSet attrs) {
        super(context, null);
        countDown = new CountDown();
        countDown.start();
        renderer = new Renderer(this, countDown);
        renderer.enableAutoRegisterDirector(this);
    }


    @Override
    public void OnCreate(Vector2D size) {
        countDown.setSizeBounding(size);
    }

    @Override
    public void OnResume(Vector2D size) {
        countDown.setSizeBounding(size);
    }

    @Override
    public void OnDestroy() {

    }
}
