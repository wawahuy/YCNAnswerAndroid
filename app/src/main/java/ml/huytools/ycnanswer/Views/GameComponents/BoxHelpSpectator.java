package ml.huytools.ycnanswer.Views.GameComponents;

import java.util.LinkedHashMap;

import ml.huytools.ycnanswer.Core.Game.Actions.Action;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionDrawings.ActionColorTo;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionFunc;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionRepeatForever;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionSequence;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionSpawn;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionCubicBezier;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionMoveTo;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionScaleTo;
import ml.huytools.ycnanswer.Core.Game.GameDirector;
import ml.huytools.ycnanswer.Core.Game.Graphics.Color;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.RectangleShape;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.Text;
import ml.huytools.ycnanswer.Core.Game.Scenes.Node;
import ml.huytools.ycnanswer.Core.Game.Scenes.NodeGroup;
import ml.huytools.ycnanswer.Core.Game.Schedules.ScheduleAction;
import ml.huytools.ycnanswer.Core.Game.Schedules.ScheduleCallback;
import ml.huytools.ycnanswer.Core.Math.Vector2D;

public class BoxHelpSpectator extends NodeGroup {
    final String[] nameR = new String[]{"A", "B", "C", "D"};
    RectangleShape[] rect;

    public BoxHelpSpectator(final LinkedHashMap<String, Integer> lp){
        rect = new RectangleShape[4];
        for(int i=0; i<4; i++) {
            Text text = new Text();
            text.setText(nameR[i]);
            text.setSize(20);
            text.centerOrigin(true);
            text.setPosition(i * 50 + 10, 30);
            text.setColor(new Color(255, 255, 255));
            add(text);

            final RectangleShape rectangleShape = new RectangleShape();
            rectangleShape.setSize(new Vector2D(20, -1));
            rectangleShape.setPosition(new Vector2D(i * 50, 0));
            ///rectangleShape.setOrigin(new Vector2D(0, -100));
            rectangleShape.setColor(new Color(255, 255, 10, 10));
            rectangleShape.runAction(
                    ActionRepeatForever.create(
                            ActionSequence.create(
                                    ActionCubicBezier.EaseInOut(ActionScaleTo.create(new Vector2D(1, 200), 1000 + 100 * i)),
                                    ActionCubicBezier.EaseInOut(ActionScaleTo.create(new Vector2D(1, 0), 1000 + 100 * i))
                            )
                    )
            );
            rect[i] = rectangleShape;
            add(rectangleShape);
        }

        GameDirector.getInstance().getScheduler().scheduleOnThreadGame(ScheduleAction.One(new ScheduleCallback() {
            @Override
            public void OnScheduleCallback(float dt) {
                for(int i=0; i<4; i++) {

                    rect[i].runAction(
                            ActionSpawn.create(
                                    ActionCubicBezier.EaseOut(ActionScaleTo.create(new Vector2D(1, lp.get(nameR[i]).intValue()*2), 200)),
                                    ActionCubicBezier.EaseOut(ActionColorTo.create(new Color(255, 255, 0), 200))
                            )
                    );

                }
            }
        }, 2000));
    }

}
