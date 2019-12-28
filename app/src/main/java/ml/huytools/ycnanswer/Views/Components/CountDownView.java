package ml.huytools.ycnanswer.Views.Components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

import java.util.Random;

import ml.huytools.ycnanswer.Core.Game.Actions.Action;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionDelay;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionDrawings.ActionCircleAngleBy;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionDrawings.ActionCircleAngleStartBy;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionDrawings.ActionCircleAngleStartTo;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionDrawings.ActionCircleAngleTo;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionDrawings.ActionCircleRadiusTo;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionDrawings.ActionColorTo;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionFunc;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionRepeat;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionSequence;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionSpawn;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionCubicBezier;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionRotateBy;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionScaleTo;
import ml.huytools.ycnanswer.Core.Game.Graphics.Color;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.CircleShape;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.Drawable;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.Text;
import ml.huytools.ycnanswer.Core.Game.Renderer;
import ml.huytools.ycnanswer.Core.Game.Scene;
import ml.huytools.ycnanswer.Core.Math.Vector2D;

public class CountDownView extends SurfaceView implements Renderer.Callback {
    private Scene scene;
    private Renderer renderer;

    /// -
    private int timeCurrent;
    private int timeCountDown;
    private Vector2D size;

    /// Game member
    Text textNumberTime;
    CircleShape circleBackground;
    CircleShape circleProgression;

    /// Game action
    Action actionCirclePBar;
    Action actionText;

    public CountDownView(Context context, AttributeSet attrs) {
        super(context, null);
        scene =  new Scene();
        renderer = new Renderer(this, scene);
        renderer.enableAutoRegisterDirector(this);
        timeCountDown = 100000;
        timeCurrent = timeCountDown;
    }


    @Override
    public void OnCreate(Vector2D size) {
        this.size = size;
        /// Background
        circleBackground = new CircleShape();
        circleBackground.setColor(new Color(255, 20, 29, 58));
        circleBackground.centerOrigin();
        circleBackground.setZOrder(10);

        /// Text
        textNumberTime = new Text();
        textNumberTime.setZOrder(100);
        textNumberTime.setColor(new Color(255, 255, 255));
        textNumberTime.setSize(40);
        textNumberTime.centerOrigin(true);
        textNumberTime.setText("0");

        /// Progression Bar
        circleProgression = new CircleShape();
        circleProgression.setColor(new Color(255,255, 102, 0));
        circleProgression.centerOrigin();
        circleProgression.setZOrder(30);
        circleProgression.setStrokeWidth(20);
        circleProgression.setStyle(Drawable.Style.STROKE);
        circleProgression.setStartAngle(270);

        /// Thêm vào scene
        scene.add(textNumberTime);
        scene.add(circleBackground);
        scene.add(circleProgression);

        /// Transform
        resumeTransform();

        /// Khởi tạo hành động
        createAction();
    }

    @Override
    public void OnResume(Vector2D size) {
        this.size = size;
        resumeTransform();
    }

    @Override
    public void OnDestroy() {
    }


    /**
     * Cập nhật lại vị trí thay đổi
     */
    public void resumeTransform(){
        // vị trí ở giữa
        Vector2D center = size.div(2);
        textNumberTime.setPosition(center);
        circleBackground.setPosition(center);
        circleProgression.setPosition(center);

        // Xây dựng hình chòn có bk = size/4
        int radius = (int)Math.min(center.x, center.y)/2;
        circleBackground.setRadius(radius + 11);
        circleProgression.setRadius(radius);
    }

    private void createAction(){
        /// Progression Bar
        actionCirclePBar = ActionSpawn.create(
                /// Effect color
                ActionSequence.create(
                        ActionColorTo.create(new Color(89, 168, 105), 0),
                        ActionCubicBezier.EaseOut(ActionColorTo.create(new Color(250, 20, 0), timeCountDown - 0))
                ),
                /// Effect rotate
                ActionSequence.create(
                        ActionCircleAngleTo.create(360, 0),
                        ActionCubicBezier.EaseIn(ActionCircleAngleTo.create(-360, 0)),
                        ActionCircleAngleTo.create(0, timeCountDown - 0)
                ),
                /// Effect scale
                ActionRepeat.create(
                        ActionSpawn.create(
                                ActionSequence.create(
                                        ActionCubicBezier.EaseIn(ActionScaleTo.create(new Vector2D(1.05f, 1.05f), 600)),
                                        ActionCubicBezier.EaseOut(ActionScaleTo.create(new Vector2D(0.95f, 0.95f), 400))
                                )
                        ),
                        timeCountDown/1000
                )
        );
        circleProgression.runAction(actionCirclePBar);

        /// Text & Call effect
        actionText = ActionSequence.create(
                ActionRepeat.create(
                        ActionSequence.create(
                                ActionSpawn.create(
                                        ActionDelay.create(1000)
                                ),
                                ActionFunc.create(tick())
                        ),
                        timeCountDown/1000
                )
        );
        textNumberTime.runAction(actionText);
    }


    private ActionFunc.Callback tick(){
        return new ActionFunc.Callback() {
            @Override
            public boolean OnCallback(Scene.Node node) {
                timeCurrent -= 1000;
                textNumberTime.setText(timeCurrent/1000+"");

                /// Create Effect Tick
                final int count = 8;
                for(int i=0; i<count; i++) {
                    CircleShape circleShape = new CircleShape();
                    circleShape.setRadius(circleProgression.getRadius()-5);
                    circleShape.setStrokeWidth(8);
                    circleShape.setAngleSwept(360);
                    circleShape.setStyle(Drawable.Style.STROKE);
                    circleShape.centerOrigin();
                    circleShape.setPosition(size.x / 2, size.y / 2);
                    circleShape.setZOrderUnder(circleBackground);
                    circleShape.setColor(new Color(120, 255, 0, 0));

                    int time = 2000 - i*120;
                    int r = (int)(size.x/2) - count*5 + i*5;
                    Color color = circleProgression.getColor().clone();
                    color.a = 50;
                    circleShape.runAction(
                            ActionSequence.create(
                                    ActionDelay.create(160*i),
                                    ActionSpawn.create(
                                            ActionSequence.create(
                                                    ActionColorTo.create(color, 0),
                                                    ActionCubicBezier.EaseOut(ActionColorTo.create(new Color(0, 255, 255, 255), time))
                                            ),
                                            ActionCubicBezier.EaseOut(ActionCircleRadiusTo.create(r, time)),
                                            ActionCubicBezier.EaseOut(ActionCircleAngleStartBy.create(-360 + new Random().nextInt(640), time)),
                                            ActionCubicBezier.EaseOut(ActionCircleAngleBy.create(-360, time))
                                    ),
                                    ActionFunc.create(new ActionFunc.Callback() {
                                        @Override
                                        public boolean OnCallback(final Scene.Node node) {
                                            scene.remove(node);
                                            return false;
                                        }
                                    })
                            )
                    );
                    scene.add(circleShape);
                }

                return false;
            }
        };
    }



    public int getTimeCurrent() {
        return timeCurrent;
    }

    public void setTimeCurrent(int timeCurrent) {
        this.timeCurrent = timeCurrent;
    }

    public int getTimeCountDown() {
        return timeCountDown;
    }

    public void setTimeCountDown(int timeCountDown) {
        this.timeCountDown = timeCountDown;
    }
}
