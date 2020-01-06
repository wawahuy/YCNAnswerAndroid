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
    public final Image imageHelp50;
    public final Image imageHelpSpectator;
    public final Image imageHelpCall;
    public final Image imageHome;
    public final Image imageCall;

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
        imageHelp50 = Image.LoadByResource(R.drawable.sprite_help_50_enable);
        imageHelpSpectator = Image.LoadByResource(R.drawable.sprite_help_guest_enable);
        imageHelpCall = Image.LoadByResource(R.drawable.sprite_help_call_enable);
        imageHome = Image.LoadByResource(R.drawable.sprite_home);
        imageCall = Image.LoadByResource(R.drawable.sprite_call);

        /// frames
        /// framesMc = EntityManager.ParseJSON(AnimationManager.Frame.class, Resource.readRawTextFile(context, R.raw.frames_mc));
    }


}
