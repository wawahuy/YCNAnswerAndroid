package ml.huytools.ycnanswer.Views.GameViews.CountDown;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

public class CountDown extends GLSurfaceView implements GLSurfaceView.Renderer {

    private int time;
    Triangles triangles;

    public CountDown(Context context, AttributeSet attrs){
        super(context);
        time = 0;
        triangles = new Triangles();

        setZOrderOnTop(true);
        setEGLConfigChooser(8,8,8,8,16,0);
        getHolder().setFormat(PixelFormat.RGBA_8888);
        setRenderer(this);

    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }


    @Override
    public void onSurfaceChanged(GL10 gl, int i, int i1) {
        gl.glViewport(0, 0, getWidth(), getHeight());

        float ratio;
        float zNear = .1f;
        float zFar = 1000f;
        float fieldOfView = (float) Math.toRadians(30);
        float size;

        gl.glEnable(GL10.GL_NORMALIZE);
        ratio = (float) i / (float) i1;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        size = zNear * (float) (Math.tan((double) (fieldOfView / 2.0f)));
        gl.glFrustumf(-size, size, -size / ratio, size / ratio, zNear, zFar);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
        gl.glDisable(GL10.GL_DITHER);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        gl.glClearColor(0, 0, 0, 0);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glFrontFace(GL10.GL_CCW);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        // gl.glTranslatef(1.0f, 0.0f, 0.0f);
        // gl.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);

        gl.glColor4f(1, 1,1 , 1);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        triangles.draw(gl);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
