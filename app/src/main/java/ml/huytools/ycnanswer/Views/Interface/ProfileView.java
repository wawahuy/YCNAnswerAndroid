package ml.huytools.ycnanswer.Views.Interface;

import ml.huytools.ycnanswer.Models.Entities.UserEntity;

public interface ProfileView {
    void showLoading();
    void hideLoading();
    void showMessage(String message);
    void hideButtonUpload();
    void showProfile(UserEntity userEntity);
}
