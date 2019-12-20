package ml.huytools.ycnanswer.Views;

import android.content.Context;
import android.media.MediaPlayer;

import ml.huytools.ycnanswer.Commons.ModelManager;
import ml.huytools.ycnanswer.Commons.Resource;
import ml.huytools.ycnanswer.Commons.Views.Animations.AnimationManager;
import ml.huytools.ycnanswer.Commons.Views.Image;
import ml.huytools.ycnanswer.R;

public class ResourceManager {
    private static ResourceManager ourInstance = null;

    public static ResourceManager getInstance(Context context) {
        if(ourInstance == null){
            ourInstance = new ResourceManager(context.getApplicationContext());
        }
        return ourInstance;
    }

    /// Audio
    public final MediaPlayer audioTimeout;

    /// Image
    public final Image imageMc;

    /// AnimationManager Frames
    /// public final ModelManager<AnimationManager.Frame> framesMc;


    private ResourceManager(Context context) {
        /// audio
        audioTimeout = MediaPlayer.create(context, R.raw.outtime);

        /// image
        imageMc = Image.LoadByResource(R.drawable.sprite_mc);

        /// frames
        /// framesMc = ModelManager.ParseJSON(AnimationManager.Frame.class, Resource.readRawTextFile(context, R.raw.frames_mc));
    }


}
