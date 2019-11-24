package ml.huytools.ycnanswer.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import ml.huytools.ycnanswer.Commons.APIProvider;
import ml.huytools.ycnanswer.R;

public class MainActivity extends AppCompatActivity {
//////// NO Model - View - Presenter
//////// UPDATE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configAPI();

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    void configAPI(){
        /// Config Host
        APIProvider.SetHost("http://192.168.1.130:8000/api");
    }


}
