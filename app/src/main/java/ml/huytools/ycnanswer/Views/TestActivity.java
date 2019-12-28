package ml.huytools.ycnanswer.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;

import java.util.Random;

import ml.huytools.ycnanswer.Core.Game.Actions.ActionDelay;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionDrawings.ActionCircleAngleBy;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionDrawings.ActionCircleAngleStartBy;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionDrawings.ActionCircleRadiusTo;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionDrawings.ActionColorTo;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionFunc;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionRepeatForever;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionSequence;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionSpawn;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionCubicBezier;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionScaleBy;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionTimings.ActionScaleTo;
import ml.huytools.ycnanswer.Core.Game.Actions.ActionVisible;
import ml.huytools.ycnanswer.Core.Game.Graphics.Color;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.CircleShape;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.Drawable;
import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.Text;
import ml.huytools.ycnanswer.Core.Game.Graphics.Font;
import ml.huytools.ycnanswer.Core.Game.Renderer;
import ml.huytools.ycnanswer.Core.Game.Scene;
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

        public TestCustomSurfaceView(Context context, AttributeSet attrs) {
            super(context, attrs);
            setZOrderOnTop(true);
            getHolder().setFormat(PixelFormat.RGBA_8888);
            scene = new Scene();
            renderer = new Renderer(this, scene);
            renderer.enableAutoRegisterDirector(this);

            scene.getScheduler().schedule(ScheduleAction.Infinite(new ScheduleCallback() {
                @Override
                public void OnUpdate(float dt) {
                    Log.v("Log", "Object Node: "+scene.getSizeNode());
                }
            }, 100, 0));
        }

        @Override
        public void OnCreate(Vector2D vector2D) {

        }

        @Override
        public void OnResume(final Vector2D size) {

            Text text = new Text();
            text.setFont(Font.loadResource(R.font.f1));
            text.setText("012 Huy");
            text.setSize(200);
            text.setPosition(size.x/2, size.y/2);
            text.centerOrigin(true);
            text.setZOrder(999);
            //text.setTextStyle(Typeface.BOLD);
            scene.add(text);

            /// bubble
            for(int i=0; i<40; i++) {
                CircleShape circleShape = new CircleShape();
                circleShape.setRadius(10);
                circleShape.setStrokeWidth(10);
                circleShape.setAngleSwept(360);
                circleShape.setStyle(Drawable.Style.FILL);
                circleShape.centerOrigin();
                circleShape.setPosition((new Random()).nextInt()%(size.x), (new Random()).nextInt()%(size.y)  );

                Color start = new Color(155, (new Random()).nextInt()%125 + 125, (new Random()).nextInt()%125 + 125, (new Random()).nextInt()%125 + 125);
                Color end   = new Color(0  , (new Random()).nextInt()%125 + 125, (new Random()).nextInt()%125 + 125, (new Random()).nextInt()%125 + 125);
                int scale = (new Random()).nextInt()%10;
                Vector2D scaleTo = new Vector2D(scale, scale);

                circleShape.runAction(
                        ActionRepeatForever.create(
                                ActionSequence.create(
                                        ActionDelay.create((new Random()).nextInt()%100),
                                        ActionSpawn.create(
                                                ActionSequence.create(
                                                        ActionColorTo.create(start, 0),
                                                        ActionCubicBezier.EaseOut(ActionColorTo.create(end, 1000))
                                                ),
                                                ActionSequence.create(
                                                        ActionVisible.create(true),
                                                        ActionCubicBezier.EaseOut(ActionScaleBy.create(scaleTo, 1000)),
                                                        ActionVisible.create(false),
                                                        ActionScaleTo.create(new Vector2D(0, 0), 0)
                                                )
                                        ),
                                        ActionFunc.create(new ActionFunc.Callback() {
                                            @Override
                                            public boolean OnCallback(Scene.Node node) {
                                                node.setPosition((new Random()).nextInt()%(size.x), (new Random()).nextInt()%(size.y)  );
                                                return false;
                                            }
                                        })
                                )
                        )
                );

                scene.add(circleShape);
            }


            // rain
            final CircleShape circleP = new CircleShape();
            circleP.setRadius(200);
            circleP.setStrokeWidth(40);
            circleP.setAngleSwept(0);
            circleP.setStyle(Drawable.Style.STROKE);
            circleP.centerOrigin();
            circleP.setPosition(size.x/2, size.y/2  );
            circleP.setColor(new Color(255, 255, 255,0 ));

            ActionFunc.Callback createEffect = new ActionFunc.Callback() {
                @Override
                public boolean OnCallback(Scene.Node node) {
                    for(int i=0; i<6; i++) {
                        CircleShape circleShape = new CircleShape();
                        circleShape.setRadius(0);
                        circleShape.setScale(circleP.getScale().clone());
                        circleShape.setStrokeWidth(60);
                        circleShape.setStartAngle(circleP.getAngleSwept());
                        circleShape.setAngleSwept(360);
                        circleShape.setStyle(Drawable.Style.FILL);
                        circleShape.centerOrigin();
                        circleShape.setPosition(size.x / 2, size.y / 2);
                        circleShape.setZOrderUnder(node);
                        circleShape.setColor(new Color(120, 255, 0, 0));

                        circleShape.runAction(
                                ActionSequence.create(
                                        ActionDelay.create(50*i),
                                        ActionSpawn.create(
                                                ActionSequence.create(
                                                        ActionColorTo.create(new Color(60, 255, 0, 0), 0),
                                                        ActionColorTo.create(new Color(0, 255, 255, 255), 2000 + 90*i)
                                                ),
                                                ActionCubicBezier.EaseInOut(ActionCircleRadiusTo.create(500 + i*30, 2000 + 90*i))
                                        ),
                                        ActionFunc.create(new ActionFunc.Callback() {
                                            @Override
                                            public boolean OnCallback(final Scene.Node node) {
                                                scene.remove(node);
                                                return false;
                                            }
                                        })
                                )
                        );
                        scene.add(circleShape);
                    }
                    return false;
                }
            };

            circleP.runAction(ActionRepeatForever.create(
                    ActionSequence.create(
                            ActionSpawn.create(
                                    ActionSequence.create(
                                            ActionCubicBezier.EaseInOut(ActionCircleAngleBy.create(360, 1500)),
                                            ActionCubicBezier.EaseOut(ActionCircleAngleBy.create(-350, 500))
                                    ),
                                    ActionCubicBezier.EaseInOut(ActionCircleAngleStartBy.create(360, 2000)),
                                    ActionSequence.create(
                                            ActionCubicBezier.EaseIn(ActionScaleTo.create(new Vector2D(0.9f, 0.9f), 1000)),
                                            ActionFunc.create(createEffect),
                                            ActionCubicBezier.EaseOut(ActionScaleTo.create(new Vector2D(1.3f, 1.3f), 1000)),
                                            ActionFunc.create(createEffect)
                                    )
                            )
                    )
            ));

            scene.add(circleP);
        }

        @Override
        public void OnDestroy() {
        }

        @Override
        public void OnUpdate(float dt) {
            //circleShape.rotate(1);
        }
    }

}
