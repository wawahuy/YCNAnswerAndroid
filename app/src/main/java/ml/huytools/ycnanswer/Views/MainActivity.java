package ml.huytools.ycnanswer.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import ml.huytools.ycnanswer.Core.API.ApiConfig;
import ml.huytools.ycnanswer.Core.App;
import ml.huytools.ycnanswer.Presenters.Interface.MainPresenter;
import ml.huytools.ycnanswer.Presenters.MainPresenterImpl;
import ml.huytools.ycnanswer.R;
import ml.huytools.ycnanswer.Views.Interface.MainView;

public class MainActivity extends AppCompatActivity implements MainView {

    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.getInstance().init(this);
        mainPresenter = new MainPresenterImpl(this);
        mainPresenter.init();

        /// init resource
        ResourceManager.getInstance(this);
    }


    @Override
    public void showActivityLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showActivityMainGame() {
        /// Test
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showMessage(String message) {
        if(message == null || message.trim() == ""){
            return;
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
