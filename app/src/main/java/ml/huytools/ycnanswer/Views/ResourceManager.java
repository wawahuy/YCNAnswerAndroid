package ml.huytools.ycnanswer.Views;

import android.content.Context;
import android.media.MediaPlayer;

import ml.huytools.ycnanswer.R;

public class ResourceManager {
    private static ResourceManager ourInstance = null;

    public static ResourceManager getInstance(Context context) {
        if(ourInstance == null){
            ourInstance = new ResourceManager(context.getApplicationContext());
        }
        return ourInstance;
    }

    public final MediaPlayer audioTimeout;

    private ResourceManager(Context context) {
        audioTimeout = MediaPlayer.create(context, R.raw.outtime);
    }


}
