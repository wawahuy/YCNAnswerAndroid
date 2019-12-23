package ml.huytools.ycnanswer.Commons.Graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ml.huytools.ycnanswer.Commons.CustomSurfaceView;

/***
 * Thử nghiệm xây dựng đối tượng kết xuất bằng OpenGL ES 2.0
 * Thay thế SurfaceView , TextureView về vấn đề hiệu xuất
 *
 */
public class Renderer extends GLSurfaceView implements GLSurfaceView.Renderer {

    public Renderer(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRenderer(this);
        requestRender();
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
    }
}
