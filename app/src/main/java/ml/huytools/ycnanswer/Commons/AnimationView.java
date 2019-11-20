package ml.huytools.ycnanswer.Commons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/***
 * AnimationView.java
 * Author: Nguyen Gia Huy
 * Project: https://github.com/wawahuy/YCNAnswerAndroid
 * Start: 17/11/2019
 * Update: 20/11/2019
 *
 */
public class AnimationView {

    LinkedHashMap<String, byte[]> bitmapLinkedHashMap;
    float widthFrame;
    float heightFrame;
    int pos;
    int num;
    Context context;

    public AnimationView(Context context, int res, float widthFrame, float heightFrame, int[][] map){
        super();
        this.context = context;
        this.num = 0;
        this.pos = 0;

        /// Chuyen px qua dp = px*dpi
        float dpi = context.getResources().getDisplayMetrics().density;
        this.widthFrame = (int)(widthFrame*dpi);
        this.heightFrame = (int)(heightFrame*dpi);

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), res);
        bitmapLinkedHashMap = new LinkedHashMap<>();

        for(int row=0; row<map.length; row++){
            for(int col=0; col<map[row].length; col++){
                int v  = map[row][col];
                if(v == -1)
                    continue;
                bitmapLinkedHashMap.put(Integer.toString(v), cropFrame(bitmap, row, col));
                this.num++;
            }
        }
    }


    public Looper LoopFrame(final ImageView imageView, final long sleep, final int numLoop){
        Looper looper = new Looper(imageView, sleep, numLoop, this);
        return looper;
    }

    public void drawFrameCurrent(ImageView imageView){
        Glide.with(this.context).load(bitmapLinkedHashMap.get(Integer.toString(pos))).asBitmap().into(imageView);
    }

    public void drawFrameNext(ImageView imageView){
        if(pos < num){
            this.nextFrame();
        } else {
            this.resetPositionFrame();
        }
        this.drawFrameCurrent(imageView);
    }

    public void drawFrame(ImageView imageView, int pos){
        Glide.with(this.context).load(bitmapLinkedHashMap.get(Integer.toString(pos))).asBitmap().into(imageView);
    }

    public void nextFrame(){
        this.pos++;
    }

    public void setPositionFrame(int pos){
        this.pos = pos;
    }

    public void resetPositionFrame(){
        this.pos = 0;
    }


    private byte[] cropFrame(Bitmap bmp, int row, int col){
        int x = (int)(col*this.widthFrame);
        int y = (int)(row*this.heightFrame);

        /// Cat bitmap
        Bitmap resultBmp = Bitmap.createBitmap((int)this.widthFrame, (int)this.heightFrame, Bitmap.Config.ARGB_8888);
        new Canvas(resultBmp).drawBitmap(bmp, -x, -y, null);

        /// Chuyen Bitmap va byte[]
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        resultBmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


    public static class Looper extends AsyncTask<Void, Integer, Void> {

        ImageView imageView;
        long sleep;
        int numLoop;
        int posLoop;
        AnimationView animationView;

        Looper callafter;
        Callback cbafter;

        public Looper(ImageView imageView, long sleep, int numLoop, AnimationView animationView){
            this.imageView = imageView;
            this.sleep = sleep;
            this.numLoop = numLoop;
            this.posLoop = 0;
            this.animationView = animationView;
            this.callafter = null;
            this.cbafter = null;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            boolean next;
            do {
                publishProgress(posLoop);
                SystemClock.sleep(sleep);

                posLoop++;
                if(posLoop >= animationView.num){
                    posLoop = 0;
                }

                if(numLoop == -1 || numLoop > 1){
                    next = true;
                    if(numLoop != -1){
                        numLoop--;
                    }
                } else {
                    next = false;
                }
            } while (next);

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(callafter != null){
                callafter.execute();
                return;
            }

            if(cbafter!=null){
                cbafter.OnMessage();
                return;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            animationView.drawFrame(imageView, posLoop);
        }


        public Looper after(Looper looper){
            callafter = looper;
            return this;
        }

        public Looper after(Callback callback){
            this.cbafter = callback;
            return this;
        }

        public interface Callback {
            void OnMessage();
        }
    }
}
