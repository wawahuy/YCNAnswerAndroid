package ml.huytools.ycnanswer.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.SurfaceView;

import ml.huytools.ycnanswer.Core.Game.Graphics.Color;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.CircleShape;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.Drawable;
import ml.huytools.ycnanswer.Core.Game.Graphics.Image;
import ml.huytools.ycnanswer.Core.Game.Renderer;
import ml.huytools.ycnanswer.Core.Game.Scene;
import ml.huytools.ycnanswer.Core.Game.Graphics.Sprite;
import ml.huytools.ycnanswer.Core.Game.Graphics.Texture;
import ml.huytools.ycnanswer.Core.Game.Schedules.ScheduleAction;
import ml.huytools.ycnanswer.Core.Game.Schedules.ScheduleCallback;
import ml.huytools.ycnanswer.Core.Math.Vector2D;
import ml.huytools.ycnanswer.R;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test);

//        /**
//         * Auth
//         */
//        App app = App.getInstance();
//        app.init(this);
//        ApiConfig.setHostname("http://192.168.1.130:8000");
//        ApiConfig.setAuthenticate(new JWTAuthenticate("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9sb2NhbGhvc3Q6ODAwMFwvYXBpXC9sb2dpbiIsImlhdCI6MTU3NzI4MTI3MywiZXhwIjoxNTc3Mjg0ODczLCJuYmYiOjE1NzcyODEyNzMsImp0aSI6IndITFFNd1NORHhRSW5Tc2QiLCJzdWIiOjEsInBydiI6IjhmMDhhYmExZWJiZDA3ZTJjYWJjNzdmYjgzZmJhZDM5MDZiY2QyOGMifQ.NSJQtKnEZ8xEvg7_ekywtDel_U9plmWTyaISpe5YGeI"));
//
//        // Đặt hostname cho API
//        ApiParameters apiParameters = new ApiParameters();
//        ApiProvider.Async.GET("/api/test/get").SetParams(apiParameters).Then(new ApiProvider.Async.Callback() {
//                    @Override
//                    public void OnAPIResult(ApiOutput output, int requestCode) {
//                        Log.v("Log", output.DataString);
//                    }
//        });

        setContentView(new TestCustomSurfaceView(this, null));
    }

    class TestCustomSurfaceView extends SurfaceView implements Renderer.Callback, ScheduleCallback {
        Renderer renderer;
        Scene scene;
        Sprite sprite;
        CircleShape circleShape;

        public TestCustomSurfaceView(Context context, AttributeSet attrs) {
            super(context, attrs);
            setZOrderOnTop(true);
            getHolder().setFormat(PixelFormat.RGBA_8888);
            scene = new Scene();
            renderer = new Renderer(this, scene);
            renderer.enableAutoRegisterDirector(this);
        }

        @Override
        public void OnCreate() {
            Texture texture = new Texture(Image.LoadByResource(R.drawable.sprite_mc).createScale(new Vector2D(0.1f, 0.1f)));
            sprite = new Sprite(texture);
            sprite.centerOrigin();

            circleShape = new CircleShape();
            circleShape.setColor(new Color(255, 0, 0));
            circleShape.setRadius(400);
            circleShape.setStrokeWidth(10);
            circleShape.setStyle(Drawable.Style.STROKE);
            circleShape.alwaysCenterOrigin();

            scene.add(circleShape);
            scene.add(sprite);

            /// Create Scheduler
            scene.getScheduler().scheduleOnThreadGame(ScheduleAction.Infinite(this,20, 0));
        }

        @Override
        public void OnResume(Vector2D size) {
            circleShape.setPosition(size.x/2, size.y/2);
            sprite.setPosition(size.x/2, size.y/2);
        }

        @Override
        public void OnDestroy() {
        }

        @Override
        public void OnUpdate(float dt) {
            sprite.rotate(1);

            if(circleShape.getEndAngle() - circleShape.getStartAngle() >= 360){
                circleShape.setStartAngle(0);
                circleShape.setEndAngle(0);
            }
            circleShape.setEndAngle(circleShape.getEndAngle() + 1);
        }
    }

}
