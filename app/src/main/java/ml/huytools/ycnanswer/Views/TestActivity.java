package ml.huytools.ycnanswer.Views;

import androidx.appcompat.app.AppCompatActivity;

import ml.huytools.ycnanswer.Commons.Graphics.Scene;
import ml.huytools.ycnanswer.Commons.Graphics.Sprite;
import ml.huytools.ycnanswer.Commons.Graphics.Texture;
import ml.huytools.ycnanswer.Commons.Image;
import ml.huytools.ycnanswer.R;
import ml.huytools.ycnanswer.Views.GameViews.Components.FPSDebugView;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.view.TextureView;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener, Runnable {

    private TextureView myTexture;
    Scene scene;
    Sprite sprite;
    FPSDebugView fpsDebugView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        myTexture = new TextureView(this);
        myTexture.setSurfaceTextureListener(this);
        setContentView(myTexture);

        fpsDebugView = new FPSDebugView(this, null);
        fpsDebugView.unregisterLoop();
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        scene = new Scene();
        sprite = new Sprite(new Texture(Image.LoadByResource(R.drawable.sprite_mc)));
        scene.add(sprite);
        sprite.setPosition(i/2, i1/2);
        sprite.centerOrigin();
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }

    @Override
    public void run() {
        while (true){
            Canvas canvas = myTexture.lockCanvas();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);

            sprite.rotate(1);
            scene.render(canvas);

            fpsDebugView.OnUpdate(0);
            fpsDebugView.OnDraw(canvas);

            myTexture.unlockCanvasAndPost(canvas);
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
