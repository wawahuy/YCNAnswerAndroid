package ml.huytools.ycnanswer.Views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import ml.huytools.ycnanswer.Models.Entities.UserEntity;
import ml.huytools.ycnanswer.Presenters.Interface.ProfilePresenter;
import ml.huytools.ycnanswer.Presenters.ProfilePresenterImpl;
import ml.huytools.ycnanswer.R;
import ml.huytools.ycnanswer.Views.GameComponents.Loading;
import ml.huytools.ycnanswer.Views.Interface.ProfileView;
import ml.huytools.ycnanswer.Views.ViewComponents.LoadingView;

public class ProfileActivity extends AppCompatActivity implements ProfileView {

    private static final int PICK_IMG = 209;
    Button btnChangeAvatar;
    ImageView imgAvatar;
    TextView txvName;
    TextView txvCredit;
    TextView txvEmail;
    TextView txvHighScore;
    LoadingView loadingView;

    byte[] imgData;

    ProfilePresenter profilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnChangeAvatar = findViewById(R.id.btnChangeAvatar);
        txvName = findViewById(R.id.txtProfileName);
        txvEmail = findViewById(R.id.txtProfileEmail);
        txvCredit = findViewById(R.id.txtProfileCredit);
        txvHighScore = findViewById(R.id.txtProfileHightScore);
        imgAvatar = findViewById(R.id.imgProfileAvatar);

        profilePresenter = new ProfilePresenterImpl(this);
        profilePresenter.loadProfile();
    }

    public void OnChangeAvatar(View view){
        profilePresenter.uploadAvatar(imgData);
    }

    public void OnOpenChangePassword(View view){
    }

    public void OnClickAvatar(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn hình"), PICK_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMG &&  resultCode == RESULT_OK){
            Uri uri = data.getData();
            Glide.with(this).load(uri).into(imgAvatar);
            btnChangeAvatar.setVisibility(View.VISIBLE);

            try {
                InputStream ip = getContentResolver().openInputStream(uri);
                ByteArrayOutputStream bt = new ByteArrayOutputStream();
                int bfSize = 1024;
                byte[] buffer = new byte[bfSize];
                int len =0;
                while((len = ip.read(buffer)) != -1){
                    bt.write(buffer, 0, len);
                }
                imgData = bt.toByteArray();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void hideButtonUpload() {
        btnChangeAvatar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showProfile(UserEntity userEntity) {
        txvName.setText("Tên đăng nhập: " + userEntity.tendangnhap);
        txvCredit.setText("Credit: " + userEntity.credit);
        txvEmail.setText("Email: "+userEntity.email);
        txvHighScore.setText("Điểm cao: "+userEntity.diemcaonhat);

        if(userEntity.AvatarUrl != null && userEntity.AvatarUrl != ""){
            Glide.with(this).load(userEntity.AvatarUrl).into(imgAvatar);
        }
    }
}
