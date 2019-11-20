package ml.huytools.ycnanswer.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import ml.huytools.ycnanswer.Commons.Model;
import ml.huytools.ycnanswer.Models.CauHoi;
import ml.huytools.ycnanswer.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }


}
