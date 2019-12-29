package ml.huytools.ycnanswer.Views.Components;

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
import ml.huytools.ycnanswer.Core.Game.GameDirector;
import ml.huytools.ycnanswer.Core.Game.Graphics.Color;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.CircleShape;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.Drawable;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.Text;
import ml.huytools.ycnanswer.Core.Game.Scenes.Node;
import ml.huytools.ycnanswer.Core.Game.Scenes.NodeGroup;
import ml.huytools.ycnanswer.Core.Game.Schedules.ScheduleAction;
import ml.huytools.ycnanswer.Core.Game.Schedules.ScheduleCallback;
import ml.huytools.ycnanswer.Core.Game.Schedules.Scheduler;
import ml.huytools.ycnanswer.Core.Math.Vector2D;

public class CountDown extends NodeGroup implements ScheduleCallback {
    final int BAR_WIDTH = 20;
    final int TEXT_SIZE = 40;

    /// -- Object Graphics ----
    Vector2D size;
    Text textNumberTime;
    CircleShape circleBackground;
    CircleShape circleProgression;

    /// -- Scheduler --
    ScheduleAction scheduleActionTick;

    /// -- Action --
    Action actionCirclePBar;

    /// -- Data ---
    int  timeCountDown;
    long timeStartCountDown;

    public CountDown(){
        timeCountDown = 30000;

        /// Background
        circleBackground = new CircleShape();
        circleBackground.setColor(new Color(255, 20, 29, 58));
        circleBackground.centerOrigin();
        circleBackground.setZOrder(10);

        /// Text
        textNumberTime = new Text();
        textNumberTime.setZOrder(100);
        textNumberTime.setColor(new Color(255, 255, 255));
        textNumberTime.setSize(TEXT_SIZE);
        textNumberTime.centerOrigin(true);

        /// Progression Bar
        circleProgression = new CircleShape();
        circleProgression.setColor(new Color(255,255, 102, 0));
        circleProgression.centerOrigin();
        circleProgression.setZOrder(30);
        circleProgression.setStrokeWidth(BAR_WIDTH);
        circleProgression.setStyle(Drawable.Style.STROKE);
        circleProgression.setStartAngle(270);

        /// Add to group
        this.add(textNumberTime);
        this.add(circleBackground);
        this.add(circleProgression);

        /// Init action
        initActionOnCircleBar();
    }

    /**
     * Cập nhật lại vị trí, kích cở
     * @param size
     *          Kích thước tối đa
     */
    public void setSizeBounding(Vector2D size){
        this.size = size;
        /// Update position node to center
        Vector2D center = size.div(2);
        textNumberTime.setPosition(center);
        circleBackground.setPosition(center);
        circleProgression.setPosition(center);

        /// Update radius circle bar and background
        int radius = (int)Math.min(center.x, center.y)/2;
        circleProgression.setRadius(radius);
        circleBackground.setRadius(radius + 11);
    }

    /**
     * Khởi tạo hành động cho bar
     */
    public void initActionOnCircleBar(){
        /// Chuyển bar đến màu
        Color  colorTo     = new Color(250, 20, 0);
        Action actionColor = ActionCubicBezier.EaseOut(ActionColorTo.create(colorTo, timeCountDown));

        /// Xoay góc quét về 0
        Action angleSwept  = ActionCubicBezier.EaseIn(ActionCircleAngleTo.create(0, timeCountDown));

        /// Hiệu ứng scale
        int    countDownN  = timeCountDown/1000;
        Action scaleBarOut = ActionCubicBezier.EaseIn(ActionScaleTo.create(new Vector2D(1.05f, 1.05f), 600));
        Action scaleBarIn  = ActionCubicBezier.EaseOut(ActionScaleTo.create(new Vector2D(0.95f, 0.95f), 400));
        Action scaleBar    = ActionRepeat.create(ActionSequence.create(scaleBarOut, scaleBarIn), countDownN);

        /// Tổng hợp song song hiệu ứng
        actionCirclePBar = ActionSpawn.create(actionColor, angleSwept, scaleBar);
        circleProgression.runAction(actionCirclePBar);
    }

    /**
     * Restart hành động trên progress bar
     */
    public void restartActionBar(){
        circleProgression.setColor(new Color(89, 168, 105));
        circleProgression.setAngleSwept(-360);
        actionCirclePBar.restart();
    }

    /**
     * Đăng ký sự kiện tick
     */
    public void registerScheduleTick(){
        /// Lấy scheduler do GameDirector cung cấp
        Scheduler scheduler = GameDirector.getInstance().getScheduler();

        /// Xóa scheduler trước đó
        if(scheduleActionTick != null){
            scheduler.remove(scheduleActionTick);
        }
        scheduleActionTick = ScheduleAction.N(this, timeCountDown/1000, 1000, 0);
        scheduler.schedule(scheduleActionTick);
    }

    /**
     * Sự kiện tick
     * @param dt
     */
    @Override
    public void OnScheduleCallback(float dt) {
        long t = timeCountDown - System.currentTimeMillis() + timeStartCountDown;
        int  tSecond = (int)(t/1000);
        textNumberTime.setText(String.valueOf(tSecond));
        createEffectCircle();
    }

    public void createEffectCircle(){
        /// -----------
        /// Need Update Style Code
        //  -----------
        long timeCurrent = timeCountDown - System.currentTimeMillis() + timeStartCountDown;
        /// Create Effect Tick
        final int count = 4;
        for(int i=0; i<count; i++) {
            CircleShape circleShape = new CircleShape();
            circleShape.setRadius(circleProgression.getRadius()-5);
            circleShape.setStrokeWidth(i*4 + 8);
            circleShape.setAngleSwept(358);
            circleShape.setStyle(Drawable.Style.STROKE);
            circleShape.centerOrigin();
            circleShape.setPosition(size.x / 2, size.y / 2);
            circleShape.setZOrderUnder(circleBackground);
            circleShape.setColor(new Color(120, 255, 0, 0));

            int time = 2000 - i*50 - (timeCountDown/1000 - (int)timeCurrent/1000)*3;
            int r = (int)(size.x/2) - count*3 + i*3;
            Color color = circleProgression.getColor().clone();
            color.a = 50;
            circleShape.setColor(color);

            circleShape.runAction(
                    ActionSequence.create(
                            ActionDelay.create(180*i),
                            ActionSpawn.create(
                                    ActionSequence.create(
                                            ActionColorTo.create(color, 0),
                                            ActionCubicBezier.EaseOut(ActionColorTo.create(new Color(0, 255, 255, 255), time))
                                    ),
                                    ActionCubicBezier.EaseOut(ActionCircleRadiusTo.create(r, time)),
                                    ActionCubicBezier.EaseOut(ActionRotateBy.create(720, time))
                            ),
                            ActionFunc.create(new ActionFunc.Callback() {
                                @Override
                                public boolean OnCallback(final Node node) {
                                    remove(node);
                                    return false;
                                }
                            })
                    )
            );
            add(circleShape);
        }
    }

    public int getTimeCountDown() {
        return timeCountDown;
    }

    public void setTimeCountDown(int timeCountDown) {
        this.timeCountDown = timeCountDown;
    }

    public void start(){
        timeStartCountDown = System.currentTimeMillis();
        registerScheduleTick();
        restartActionBar();
    }
}
