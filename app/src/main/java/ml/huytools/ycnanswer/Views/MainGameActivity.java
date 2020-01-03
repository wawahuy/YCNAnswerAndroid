package ml.huytools.ycnanswer.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import ml.huytools.ycnanswer.Models.Entities.UserEntity;
import ml.huytools.ycnanswer.Presenters.Interface.MainGamePresenter;
import ml.huytools.ycnanswer.Presenters.MainGamePresenterImpl;
import ml.huytools.ycnanswer.R;
import ml.huytools.ycnanswer.Views.Interface.MainGameView;
import ml.huytools.ycnanswer.Views.ViewComponents.LoadingView;

public class MainGameActivity extends AppCompatActivity implements MainGameView {
    MainGamePresenter mainGamePresenter;
    LoadingView loadingView;

    TextView txtName;
    TextView txtCredit;
    ImageView imgAvatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        txtName = findViewById(R.id.txtUserName);
        txtCredit = findViewById(R.id.txtUserCredit);
        imgAvatar = findViewById(R.id.imgUserAvatar);

        mainGamePresenter = new MainGamePresenterImpl(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainGamePresenter.loadProfile();
    }

    public void OnPlay(View view){
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
    }

    public void OnOpenScore(View view){
    }

    public void OnOpenRanking(View view){
    }

    public void OnOpenExit(View view){
        mainGamePresenter.logout();
    }

    public void OnOpenShop(View view){
    }

    public void OnOpenProfile(View view){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void showMessage(String  message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        loadingView = LoadingView.create(this);
    }

    @Override
    public void hideLoading() {
        loadingView.removeOnView();
    }

    @Override
    public void showProfile(UserEntity userEntity) {
        if(userEntity.AvatarUrl != null && userEntity.AvatarUrl != ""){
            Glide.with(this).load(userEntity.AvatarUrl).into(imgAvatar);
        }

        txtName.setText(userEntity.tendangnhap);
        txtCredit.setText("$" + String.valueOf(userEntity.credit));
    }

    @Override
    public void openActivityLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
