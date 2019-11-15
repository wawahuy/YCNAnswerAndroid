package ml.huytools.ycnanswer.View;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.EditText;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TestSurfaceView tsv = new TestSurfaceView(this);

        EditText editBox = new EditText(this);
        editBox.setText("Hello Matron");

        setContentView(tsv);



        addContentView(editBox, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }


    class TestSurfaceView extends GLSurfaceView {

        public TestSurfaceView(Context context) {
            super(context);

            // Create an OpenGL ES 2.0 context
            setEGLContextClientVersion(3);

            MyGLRenderer renderer = new MyGLRenderer();

            // Set the Renderer for drawing on the GLSurfaceView
            setRenderer(renderer);
        }
    }


    public class MyGLRenderer implements GLSurfaceView.Renderer {

        Triangle t;

        public void onSurfaceCreated(GL10 unused, EGLConfig config) {
            // Set the background frame color
            GLES30.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
            t = new Triangle();
        }

        public void onDrawFrame(GL10 unused) {
            // Redraw background color
            GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
            t.draw();
        }

        public void onSurfaceChanged(GL10 unused, int width, int height) {
            GLES30.glViewport(0, 0, width, height);
        }
    }


    public class Triangle {

        private FloatBuffer vertexBuffer;

        // number of coordinates per vertex in this array
        static final int COORDS_PER_VERTEX = 3;
        float triangleCoords[] = {   // in counterclockwise order:
                0.0f,  0.622008459f, 0.0f, // top
                -0.5f, -0.311004243f, 0.0f, // bottom left
                0.5f, -0.311004243f, 0.0f  // bottom right
        };

        private final String vertexShaderCode =
                "attribute vec4 vPosition;" +
                        "void main() {" +
                        "  gl_Position = vPosition;" +
                        "}";

        private final String fragmentShaderCode =
                "precision mediump float;" +
                        "uniform vec4 vColor;" +
                        "void main() {" +
                        "  gl_FragColor = vColor;" +
                        "}";


        // Set color with red, green, blue and alpha (opacity) values
        float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };
        int mProgram;

        public Triangle() {
            // initialize vertex byte buffer for shape coordinates
            ByteBuffer bb = ByteBuffer.allocateDirect(
                    // (number of coordinate values * 4 bytes per float)
                    triangleCoords.length * 4);
            // use the device hardware's native byte order
            bb.order(ByteOrder.nativeOrder());

            // create a floating point buffer from the ByteBuffer
            vertexBuffer = bb.asFloatBuffer();
            // add the coordinates to the FloatBuffer
            vertexBuffer.put(triangleCoords);
            // set the buffer to read the first coordinate
            vertexBuffer.position(0);


            int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER,
                    vertexShaderCode);
            int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER,
                    fragmentShaderCode);

            // create empty OpenGL ES Program
            mProgram = GLES30.glCreateProgram();

            // add the vertex shader to program
            GLES30.glAttachShader(mProgram, vertexShader);

            // add the fragment shader to program
            GLES30.glAttachShader(mProgram, fragmentShader);

            // creates OpenGL ES program executables
            GLES30.glLinkProgram(mProgram);
        }

        int loadShader(int type, String shaderCode){

            int shader = GLES30.glCreateShader(type);

            // add the source code to the shader and compile it
            GLES30.glShaderSource(shader, shaderCode);
            GLES30.glCompileShader(shader);

            return shader;
        }


        private int positionHandle;
        private int colorHandle;

        private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
        private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

        public void draw() {
            // Add program to OpenGL ES environment
            GLES30.glUseProgram(mProgram);

            // get handle to vertex shader's vPosition member
            positionHandle = GLES30.glGetAttribLocation(mProgram, "vPosition");

            // Enable a handle to the triangle vertices
            GLES30.glEnableVertexAttribArray(positionHandle);

            // Prepare the triangle coordinate data
            GLES30.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                    GLES30.GL_FLOAT, false,
                    vertexStride, vertexBuffer);

            // get handle to fragment shader's vColor member
            colorHandle = GLES30.glGetUniformLocation(mProgram, "vColor");

            // Set color for drawing the triangle
            GLES30.glUniform4fv(colorHandle, 1, color, 0);

            // Draw the triangle
            GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexCount);

            // Disable vertex array
            GLES30.glDisableVertexAttribArray(positionHandle);
        }
    }
}
