package ml.huytools.ycnanswer.Views.GameComponents;

import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.PolygonShape;
import ml.huytools.ycnanswer.Core.Game.Scenes.NodeGroup;
import ml.huytools.ycnanswer.Core.Math.Vector2D;

public class SpotLight extends NodeGroup {

    public SpotLight(){
        SpotLightChild sl1 = new SpotLightChild(0, 0);
        SpotLightChild sl2 = new SpotLightChild(0, 0);
        SpotLightChild sl3 = new SpotLightChild(0, 0);
        SpotLightChild sl4 = new SpotLightChild(0, 0);

        add(sl1);
        add(sl2);
        add(sl3);
        add(sl4);
    }

    public void setBoundingSize(Vector2D size){

    }

    public class SpotLightChild extends PolygonShape {
        public SpotLightChild(int angleStart, int angleEnd){

        }

        public void setBoundingSize(Vector2D size){
        }
    }
}
