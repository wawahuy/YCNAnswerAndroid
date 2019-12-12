package ml.huytools.ycnanswer.Commons.Views;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import ml.huytools.ycnanswer.Commons.Math.Vector2D;

public class Image {

    Bitmap bitmap;

    private Image(){
    }

    public static Image LoadByResource(Resources resource, int id){
        Image image = new Image();
        image.bitmap = (Bitmap) BitmapFactory.decodeResource(resource , id);
        return image;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public Bitmap crop(Vector2D position, Vector2D size){
        Bitmap resultBmp = Bitmap.createBitmap((int)size.x, (int)size.y, Bitmap.Config.ARGB_8888);
        new Canvas(resultBmp).drawBitmap(bitmap, -position.x, -position.y, null);
        return resultBmp;
    }

}
