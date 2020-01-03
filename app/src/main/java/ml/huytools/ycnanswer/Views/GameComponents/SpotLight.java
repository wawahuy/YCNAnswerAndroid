package ml.huytools.ycnanswer.Views.GameComponents;

import android.graphics.LinearGradient;
import android.graphics.Shader;

import ml.huytools.ycnanswer.Core.Game.Actions.Action;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionDrawings.ActionColorTo;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionRepeatForever;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionSequence;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionCubicBezier;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionRotateTo;
import ml.huytools.ycnanswer.Core.Game.GameDirector;
import ml.huytools.ycnanswer.Core.Game.Graphics.Color;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.PolygonShape;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.RectangleShape;
import ml.huytools.ycnanswer.Core.Game.Scenes.NodeGroup;
import ml.huytools.ycnanswer.Core.Game.Schedules.ScheduleAction;
import ml.huytools.ycnanswer.Core.Game.Schedules.ScheduleCallback;
import ml.huytools.ycnanswer.Core.Math.CubicBezier;
import ml.huytools.ycnanswer.Core.Math.Vector2D;

public class SpotLight extends NodeGroup {
    final int timeEffect = 2000;
    SpotLightChild[] spotLights;
    FlickerAmbientFight flickerAmbientFight;

    public SpotLight(){
        spotLights = new SpotLightChild[4];
        spotLights[0] = new SpotLightChild( 45, -45);
        spotLights[1] = new SpotLightChild(-45,  45);
        spotLights[2] = new SpotLightChild( 45, -45);
        spotLights[3] = new SpotLightChild(-45,  45);

        for(SpotLightChild spotLightChild: spotLights){
            add(spotLightChild);
        }

        flickerAmbientFight = new FlickerAmbientFight();
        add(flickerAmbientFight);
    }

    public void setBoundingSize(Vector2D size){
        int r1 = 10;
        int r2 = 120;
        int h  = (int)size.y/2;
        int xc = (int)size.x/7;

        spotLights[0].setPosition(new Vector2D(xc, -5));
        spotLights[1].setPosition(new Vector2D(xc*2, -5));
        spotLights[2].setPosition(new Vector2D(size.x-xc*2, -5));
        spotLights[3].setPosition(new Vector2D(size.x-xc, -5));

        for(SpotLightChild spotLightChild: spotLights){
            spotLightChild.init(r1, r2, h);
        }

        flickerAmbientFight.init(size);
    }

    public void runEffectFlickerAmbient(){
        for(SpotLightChild spotLightChild: spotLights){
            spotLightChild.runEffect(timeEffect);
        }
        flickerAmbientFight.runEffect(timeEffect);
    }

    public void runEffectSlow(){
        for(SpotLightChild spotLightChild: spotLights){
            spotLightChild.runEffectSlow(timeEffect/2);
        }
    }

    /**
     * SpotLight
     */
    public static class SpotLightChild extends PolygonShape {
        final int timeRotate = 1000;
        final int timeRotateFast = 200;
        final CubicBezier timeTiming = new CubicBezier(0.58f, 0, 0.58f, 1);
        ActionCubicBezier actionStart, actionEnd;

        public SpotLightChild(int angleStart, int angleEnd){
            setColor(new Color(40, 255, 255, 255));
            actionStart = ActionCubicBezier.create(ActionRotateTo.create(angleStart, timeRotate), timeTiming);
            actionEnd = ActionCubicBezier.create(ActionRotateTo.create(angleEnd, timeRotate), timeTiming);
            runAction(ActionRepeatForever.create(ActionSequence.create(actionStart, actionEnd)));
        }

        public void init(int r1, int r2, int h){
            clearPoint();
            addPoint(new Vector2D(-r1, 0));
            addPoint(new Vector2D( r1, 0));
            addPoint(new Vector2D( r2, h));
            addPoint(new Vector2D(-r2, h));
            setColorShader(new LinearGradient(0, 0, 0, h,0xffffffff, 0x00ffffff, Shader.TileMode.MIRROR));
        }

        public void runEffect(int timeEffect){
            setTimeRotate(timeRotateFast);

            /// Schedule end effect
            ScheduleCallback scheduleCallback = new ScheduleCallback() {
                @Override
                public void OnScheduleCallback(float dt) {
                    setTimeRotate(timeRotate);
                }
            };
            ScheduleAction scheduleAction = ScheduleAction.One(scheduleCallback, timeEffect);
            GameDirector.getInstance().getScheduler().schedule(scheduleAction);
        }

        public void runEffectSlow(int timeEffect){
            setTimeRotate(timeRotate*2);

            /// Schedule end effect
            ScheduleCallback scheduleCallback = new ScheduleCallback() {
                @Override
                public void OnScheduleCallback(float dt) {
                    setTimeRotate(timeRotate);
                }
            };
            ScheduleAction scheduleAction = ScheduleAction.One(scheduleCallback, timeEffect);
            GameDirector.getInstance().getScheduler().schedule(scheduleAction);
        }

        public void setTimeRotate(int time){
            actionStart.setTime(time);
            actionEnd.setTime(time);
        }
    }

    /**
     * Flicker Ambient fight
     */
    public static class FlickerAmbientFight extends RectangleShape {
        final int timeFlick = 200;
        Action action;

        public FlickerAmbientFight(){
            setVisible(false);
            setEnableAction(false);

            Action actionLight = ActionColorTo.create(new Color(40, 255, 255, 255), 0);
            Action actionNoLight = ActionCubicBezier.EaseOut(ActionColorTo.create(new Color(0, 255, 255, 255), timeFlick));
            action = ActionRepeatForever.create(ActionSequence.create(actionLight, actionNoLight));
            runAction(action);
        }

        public void init(Vector2D size){
            setSize(size);
        }

        public void runEffect(int timeEffect){
            setVisible(true);
            setEnableAction(true);
            action.restart();

            /// Schedule end effect
            ScheduleCallback scheduleCallback = new ScheduleCallback() {
                @Override
                public void OnScheduleCallback(float dt) {
                    setVisible(false);
                    setEnableAction(false);
                }
            };
            ScheduleAction scheduleAction = ScheduleAction.One(scheduleCallback, timeEffect);
            GameDirector.getInstance().getScheduler().schedule(scheduleAction);
        }
    }

}
