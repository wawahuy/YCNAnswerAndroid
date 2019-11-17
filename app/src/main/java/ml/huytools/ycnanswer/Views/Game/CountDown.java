package ml.huytools.ycnanswer.Views.Game;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import ml.huytools.ycnanswer.Commons.AnimationView;
import ml.huytools.ycnanswer.R;
import ml.huytools.ycnanswer.Views.AssetConfig;

public class CountDown {

    ImageView imageViewCountDown;
    AnimationView[] animationView;
    AnimationView.Looper looper1, looper2;

    public CountDown(Context context, ImageView imageViewCountDown){
        this.imageViewCountDown = imageViewCountDown;

        animationView = new AnimationView[2];
        animationView[0] = new AnimationView(context, R.drawable.sprite_cd_1,
                AssetConfig.COUNT_DOWN_WIDTH_FRAME,
                AssetConfig.COUNT_DOWN_HEIGHT_FRAME,
                AssetConfig.COUNT_DOWN_MAP_FRAME1);

        animationView[1] = new AnimationView(context, R.drawable.sprite_cd_2,
                AssetConfig.COUNT_DOWN_WIDTH_FRAME,
                AssetConfig.COUNT_DOWN_HEIGHT_FRAME,
                AssetConfig.COUNT_DOWN_MAP_FRAME2);
    }

    public void start(){
        if(looper1 != null){
            looper1.cancel(true);
            looper2.cancel(true);
        }
        looper1 = animationView[1].LoopFrame(imageViewCountDown, 1000, 14);
        looper2 = animationView[0].LoopFrame(imageViewCountDown, 1000, 16);
        looper1.after(looper2).execute();
        looper2.after(new AnimationView.Looper.Callback() {
            @Override
            public void OnMessage() {
            }
        });
    }
}
