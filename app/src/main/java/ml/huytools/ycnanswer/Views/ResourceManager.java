package ml.huytools.ycnanswer.Views;

import android.content.Context;
import android.media.MediaPlayer;

import ml.huytools.ycnanswer.Core.Game.Graphics.Image;
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
    public final Image imageBackgroundGame;
    public final Image imageChair;
    public final Image imagePC;

    /// AnimationManager Frames
    /// public final EntityManager<AnimationManager.Frame> framesMc;


    private ResourceManager(Context context) {
        /// audio
        audioTimeout = MediaPlayer.create(context, R.raw.outtime);

        /// image
        imageMc = Image.LoadByResource(R.drawable.sprite_mc);
        imageBackgroundGame = Image.LoadByResource(R.drawable.sprite_bg_game);
        imageChair = Image.LoadByResource(R.drawable.chair);
        imagePC = Image.LoadByResource(R.drawable.sprite_pc);

        /// frames
        /// framesMc = EntityManager.ParseJSON(AnimationManager.Frame.class, Resource.readRawTextFile(context, R.raw.frames_mc));
    }


}
