package ml.huytools.ycnanswer.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import ml.huytools.ycnanswer.Commons.API.APIConfig;
import ml.huytools.ycnanswer.Commons.API.APIProvider;
import ml.huytools.ycnanswer.Commons.App;
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


        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    void APIConfig(){
        /// Config Host
        APIConfig.setHostname("http://192.168.1.130:8000/api");


    }


}
