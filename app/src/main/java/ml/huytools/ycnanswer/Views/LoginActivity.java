package ml.huytools.ycnanswer.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ml.huytools.ycnanswer.Presenters.Interface.LoginPresenter;
import ml.huytools.ycnanswer.Presenters.LoginPresenterImpl;
import ml.huytools.ycnanswer.R;
import ml.huytools.ycnanswer.Views.Interface.LoginView;
import ml.huytools.ycnanswer.Views.ViewComponents.LoadingView;

public class LoginActivity extends AppCompatActivity implements LoginView {
    LoginPresenter loginPresenter;
    LoadingView loadingView;

    EditText txtUser;
    EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenterImpl(this);

        txtUser = findViewById(R.id.txtUser);
        txtPassword = findViewById(R.id.txtPassword);
    }

    public void OnLogin(View view){
        String username = txtUser.getText().toString();
        String password = txtPassword.getText().toString();
        loginPresenter.login(username, password);
    }

    public void OnRegister(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void showLoading() {
        hideLoading();
        loadingView = LoadingView.create(this);
    }

    @Override
    public void hideLoading() {
        if(loadingView != null){
            loadingView.removeOnView();
            loadingView = null;
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void openMainGameActivity() {
        Intent intent = new Intent(this, MainGameActivity.class);
        startActivity(intent);
        finish();
    }
}
