package ml.huytools.ycnanswer.Commons.Views;

import android.graphics.Canvas;

import ml.huytools.ycnanswer.Commons.Annotation.JsonName;
import ml.huytools.ycnanswer.Commons.Math.Vector2D;
import ml.huytools.ycnanswer.Commons.Model;
import ml.huytools.ycnanswer.Commons.ModelManager;

public class Animation extends AbstractAnimation {
    Image image;
    ModelManager<Frame> frames;
    Vector2D positionRender;
    int positionFrame;

    public Animation(Image image, ModelManager<Frame> frames){
        this.image = image;
        this.positionFrame = 0;
    }

    public void setPositionFrameRender(int... positionFrameRender){
    }


    @Override
    protected boolean OnUpdateAnimation(float per) {
        return false;
    }

    @Override
    public void OnDraw(Canvas canvas) {
    }

    public static class Frame extends Model {
        @JsonName
        public float x;

        @JsonName
        public float y;

        @JsonName
        public float w;

        @JsonName
        public float h;
    }
}
