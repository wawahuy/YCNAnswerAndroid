package ml.huytools.ycnanswer.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import ml.huytools.ycnanswer.Core.API.ApiConfig;
import ml.huytools.ycnanswer.Core.App;
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
        ApiConfig.setHostname("http://192.168.1.130:8000/api");


    }


}
