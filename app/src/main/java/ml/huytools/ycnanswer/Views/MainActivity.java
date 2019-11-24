package ml.huytools.ycnanswer.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import ml.huytools.ycnanswer.Commons.ApiProvider;
import ml.huytools.ycnanswer.Commons.Model;
import ml.huytools.ycnanswer.Models.CauHoi;
import ml.huytools.ycnanswer.R;

public class MainActivity extends AppCompatActivity {
//////// NO Model - View - Presenter
//////// UPDATE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configAPI();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }


    void configAPI(){
        /// Config Host
        ApiProvider.SetHost("http://192.168.1.130:8000/api");
    }


}
