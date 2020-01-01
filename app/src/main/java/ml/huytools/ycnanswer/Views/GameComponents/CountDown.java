package ml.huytools.ycnanswer.Views.GameComponents;

import android.util.Log;

import ml.huytools.ycnanswer.Core.Game.Actions.Action;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionDelay;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionDrawings.ActionCircleAngleTo;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionDrawings.ActionCircleRadiusTo;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionDrawings.ActionColorTo;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionFunc;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionRepeat;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionSequence;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionSpawn;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionCubicBezier;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionMoveTo;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionRotateBy;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionScaleTo;
import ml.huytools.ycnanswer.Core.Game.Event.OnTouchListener;
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

    /// -- Object Game ----
    Vector2D size;
    Text textNumberTime;
    CircleShape circleBackground;
    CircleShape circleProgression;
    ScheduleAction scheduleActionTick;
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

        if(tSecond <= 0){
            start();
            return;
        }

        textNumberTime.setText(String.valueOf(tSecond));
        createEffectCircle();
    }

    public void createEffectCircle(){
        if(size == null){
            return;
        }

        int timeCurrent = (int)(timeCountDown - System.currentTimeMillis() + timeStartCountDown)/1000;
        final int count = 4;
        for(int i=0; i<count; i++) {
            Color color = new Color(50, circleProgression.getColor());
            CircleShape circleShape = new CircleShape();
            circleShape.setRadius(circleProgression.getRadius()-5);
            circleShape.setStrokeWidth(i*4 + 8);
            circleShape.setAngleSwept(359);
            circleShape.setStyle(Drawable.Style.STROKE);
            circleShape.centerOrigin();
            circleShape.setPosition(size.x / 2, size.y / 2);
            circleShape.setZOrderUnder(circleBackground);
            circleShape.setColor(color);

            int timeEffect = 2000 - i*50 - timeCurrent*3;
            int radiusTo = (int)(size.x/2) - count*3 + i*3;
            Color colorTo = new Color(0, 255, 255, 255);

            /// Sequences
            Action actionDelay  = ActionDelay.create(180*i);
            Action actionDelete = ActionFunc.create(new ActionFunc.Callback() {
                @Override
                public boolean OnCallback(final Node node) {
                    remove(node);
                    return false;
                }
            });

            /// Spawn
            Action actionColor  = ActionCubicBezier.EaseOut(ActionColorTo.create(colorTo, timeEffect));
            Action actionRadius = ActionCubicBezier.EaseOut(ActionCircleRadiusTo.create(radiusTo, timeEffect));
            Action actionRotate = ActionCubicBezier.EaseOut(ActionRotateBy.create(720, timeEffect));
            Action actionSpawn = ActionSpawn.create(actionRadius, actionColor, actionRotate);

            /// All
            Action action = ActionSequence.create(actionDelay, actionSpawn, actionDelete);
            circleShape.runAction(action);
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
        setVisible(true);
        setEnableAction(true);
        timeStartCountDown = System.currentTimeMillis();
        textNumberTime.setText(String.valueOf(timeCountDown/1000));
        registerScheduleTick();
        restartActionBar();
    }

    public void die(){
        setVisible(false);
        setEnableAction(false);
        if(scheduleActionTick != null){
            GameDirector.getInstance().getScheduler().remove(scheduleActionTick);
        }
    }

}
