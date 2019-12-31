package ml.huytools.ycnanswer.Core.Game.Graphics;

import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import ml.huytools.ycnanswer.Core.Game.Scenes.Node;
import ml.huytools.ycnanswer.Core.Math.Vector2D;

public class Sprite extends Node {
    public enum ScaleType { FitXY, FitCenter }
    Texture texture;
    Paint paint;
    Rect rect;
    boolean center;
    ScaleType scaleType;


    public Sprite(){
        rect = new Rect(0, 0, 0, 0);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public Sprite(Texture texture){
        rect = new Rect(0, 0, 0, 0);
        paint = new Paint();
        paint.setAntiAlias(true);
        setTexture(texture);
    }

    public void setTexture(Texture texture){
        this.texture = texture;
        this.paint.setShader(texture.getBitmapShader());
        rect.right = (int)texture.getSize().x;
        rect.bottom = (int)texture.getSize().y;
        hasUpdateDraw = true;
        computeOrigin();
    }

    public Texture getTexture() {
        return texture;
    }

    public void setRect(Rect rect){
        this.rect.top = rect.top;
        this.rect.left = rect.left;
        this.rect.right = rect.right;
        this.rect.bottom = rect.bottom;
        hasUpdateDraw = true;
        computeOrigin();
    }

    public final Rect getRect(Rect rect){
        return rect;
    }

    public void centerOrigin(boolean status){
        center = status;
    }

    private void computeOrigin(){
        /// Origin
        if(center){
            /// (right - left)*0.5f , (bottom - top)*0.5f
            setOrigin(rect.right*0.5f - rect.left*0.5f, rect.bottom*0.5f - rect.top*0.5f);
            hasUpdateDraw = true;
        }
    }


    /***
     * Việc này dẫn đến Rect trở lại kích cở texture
     * @param size
     * @param scaleType
     */
    public void scaleDraw(Vector2D size, ScaleType scaleType) {
        rect.right = (int)texture.getSize().x;
        rect.bottom = (int)texture.getSize().y;
        float scaleX = texture.getSize().x/size.x;
        float scaleY = texture.getSize().y/size.y;
        switch (scaleType){
            case FitCenter:
                float scale = Math.min(scaleX, scaleY);
                setScale(1/scale, 1/scale);
                break;
            case FitXY:
                setScale(1/scaleX, 1/scaleY);
                break;
        }
        computeOrigin();
    }

    @Override
    public void OnDraw(Canvas canvas) {
        canvas.drawRect(rect, paint);
    }
}
