package ml.huytools.ycnanswer.Views;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ml.huytools.ycnanswer.Core.Game.Renderer;
import ml.huytools.ycnanswer.Core.Math.Vector2D;
import ml.huytools.ycnanswer.R;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_sf);
        ((GameSurfaceView)findViewById(R.id.gamesf)).create(this);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed(); commented this line in order to disable back press
    }

    public static class GameSurfaceView extends SurfaceView implements Renderer.Callback {
        GameScene gameScene;
        Renderer renderer;

        public GameSurfaceView(Context context, AttributeSet attrs) {
            super(context, attrs);

            ///
            gameScene = new GameScene();

            ///
            renderer = new Renderer(this, gameScene);
            renderer.enableAutoRegisterDirector(this);
            renderer.transparent();
        }

        public void create(Activity activity){
            gameScene.create(activity);
        }

        @Override
        public void OnCreate(Vector2D size) {
            gameScene.initSizeScreen(size);
        }

        @Override
        public void OnResume(Vector2D size) {
            gameScene.initSizeScreen(size);
        }

        @Override
        public void OnDestroy() {
        }
    }

    /// -------------- Full screen, hide navigation bar ---------
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
