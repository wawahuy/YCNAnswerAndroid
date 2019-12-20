package ml.huytools.ycnanswer.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;

import ml.huytools.ycnanswer.Commons.APIProvider;
import ml.huytools.ycnanswer.Commons.Views.Animations.AnimationData;
import ml.huytools.ycnanswer.Commons.App;
import ml.huytools.ycnanswer.Commons.Views.Animations.AnimationManager;
import ml.huytools.ycnanswer.R;

public class MainActivity extends AppCompatActivity {
//////// NO Model - View - Presenter
//////// UPDATE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /// Config Global
        App.getInstance().init(this);
        APIConfig();


        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    void APIConfig(){
        /// Config Host
        APIProvider.SetHost("http://192.168.1.130:8000/api");


    }


}
