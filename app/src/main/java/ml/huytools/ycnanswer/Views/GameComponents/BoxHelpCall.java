package ml.huytools.ycnanswer.Views.GameComponents;

import android.content.Context;

import ml.huytools.ycnanswer.Core.Game.Actions.ActionFunc;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionRepeat;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionSequence;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionCubicBezier;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionRotateBy;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionScaleBy;
import ml.huytools.ycnanswer.Core.Game.Graphics.Color;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.Text;
import ml.huytools.ycnanswer.Core.Game.Graphics.Sprite;
import ml.huytools.ycnanswer.Core.Game.Graphics.Texture;
import ml.huytools.ycnanswer.Core.Game.Scenes.Node;
import ml.huytools.ycnanswer.Core.Game.Scenes.NodeGroup;
import ml.huytools.ycnanswer.Core.Math.Vector2D;
import ml.huytools.ycnanswer.Views.ResourceManager;

public class BoxHelpCall extends NodeGroup {

    Text text;

    public BoxHelpCall(final String c, Context context){
        text = new Text();
        text.setText("Tôi nghĩ đáp án là " + c);
        text.setPosition(75, 130);
        text.setColor(new Color(255, 255, 255, 255));
        text.setSize(30);
        text.centerOrigin(true);


        Sprite sprite = new Sprite();
        sprite.setTexture(new Texture(ResourceManager.getInstance(context).imageCall));
        sprite.scaleDraw(new Vector2D(150, 100), Sprite.ScaleType.FitXY);
        sprite.centerOrigin(true);
        sprite.runAction(
                ActionSequence.create(
                        ActionRepeat.create(
                                ActionSequence.create(
                                        ActionCubicBezier.EaseOut(ActionScaleBy.create(new Vector2D(0.1f, 0.1f), 300)),
                                        ActionCubicBezier.EaseOut(ActionScaleBy.create(new Vector2D(-0.1f, -0.1f), 300))
                                )
                                , 4
                        ),
                        ActionFunc.create(new ActionFunc.Callback() {
                                              @Override
                                              public boolean OnCallback(Node node) {
                                                  add(text);
                                                  return false;
                                              }
                                          }
                        )
                )
        );
        add(sprite);
    }

}
