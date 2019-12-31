package ml.huytools.ycnanswer.Views.GameComponents;

import android.app.Activity;
import android.widget.RelativeLayout;

import ml.huytools.ycnanswer.Core.Game.Commons.Sleeper;
import ml.huytools.ycnanswer.Core.Game.GameDirector;
import ml.huytools.ycnanswer.Core.Game.Graphics.Color;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.Text;
import ml.huytools.ycnanswer.Core.Game.Scenes.NodeGroup;
import ml.huytools.ycnanswer.Core.Game.Schedules.ScheduleAction;
import ml.huytools.ycnanswer.Core.Game.Schedules.ScheduleCallback;
import ml.huytools.ycnanswer.Core.Game.Schedules.Scheduler;
import ml.huytools.ycnanswer.Core.Math.Vector2D;
import ml.huytools.ycnanswer.Views.Removing.Components.FPSDebugView;

public class FPSDebug extends Text {
    int fps = 0;

    public FPSDebug(){
        setColor(new Color(150, 255, 255,255));
        centerOrigin(true);
        setSize(25);

        ///
        GameDirector gameDirector = GameDirector.getInstance();
        Scheduler scheduler = gameDirector.getScheduler();

        /// Count FPS
        ScheduleCallback scheduleFPS = new ScheduleCallback() {
            @Override
            public void OnScheduleCallback(float dt) {
                fps++;
            }
        };
        scheduler.scheduleOnThreadGame(ScheduleAction.Infinite(scheduleFPS, 0, 0));

        /// Show FPS
        ScheduleCallback scheduleShow = new ScheduleCallback() {
            @Override
            public void OnScheduleCallback(float dt) {
                setText(fps*4 + " FPS");
                fps = 0;
            }
        };
        scheduler.schedule(ScheduleAction.Infinite(scheduleShow, 250, 0));
    }


    public void setBoundingSize(Vector2D size){
        setPosition(size.x/2, size.y/2);
    }

}
