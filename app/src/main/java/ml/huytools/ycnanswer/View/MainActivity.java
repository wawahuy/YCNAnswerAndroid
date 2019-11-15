package ml.huytools.ycnanswer.View;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;

import org.json.JSONObject;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ml.huytools.ycnanswer.Common.Model;
import ml.huytools.ycnanswer.Model.CauHoi;
import ml.huytools.ycnanswer.R;

import static ml.huytools.ycnanswer.Common.Model.ParseJSON;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CauHoi cauHoi = new CauHoi();
        JSONObject jsonObject = cauHoi.toJson();
        CauHoi cauHoiNew = Model.ParseJSON(CauHoi.class, jsonObject);
        Log.v("Log", cauHoiNew.toJson().toString());

    }




}
