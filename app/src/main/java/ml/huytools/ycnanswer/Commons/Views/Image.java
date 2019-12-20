package ml.huytools.ycnanswer.Commons.Views;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import ml.huytools.ycnanswer.Commons.App;
import ml.huytools.ycnanswer.Commons.Math.Vector2D;

public class Image {

    Bitmap bitmap;

    private Image(){
    }

    public static Image LoadByResource(int id){
        Image image = new Image();
        image.bitmap = (Bitmap) BitmapFactory.decodeResource(App.getInstance().getResources() , id);
        return image;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public Image crop(Vector2D position, Vector2D size){
        Image image = new Image();
        Bitmap resultBmp = Bitmap.createBitmap((int)size.x, (int)size.y, Bitmap.Config.ARGB_8888);
        new Canvas(resultBmp).drawBitmap(bitmap, -position.x, -position.y, null);
        image.bitmap = resultBmp;
        return image;
    }

    /**
     *
     * @param scale
     *          [1, 1] normal
     * @return
     */
    public Image scale(Vector2D scale){
        Image image = new Image();
        float w = bitmap.getWidth()*scale.x;
        float h = bitmap.getHeight()*scale.y;
        image.bitmap = Bitmap.createScaledBitmap(bitmap, (int)w, (int)h, false);
        return image;
    }

    public void free(){
        bitmap.recycle();
        bitmap = null;
    }
}
