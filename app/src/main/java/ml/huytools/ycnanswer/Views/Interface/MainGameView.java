package ml.huytools.ycnanswer.Views.Interface;

import ml.huytools.ycnanswer.Models.Entities.UserEntity;

public interface MainGameView {
    void showMessage(String message);
    void showLoading();
    void hideLoading();
    void showProfile(UserEntity userEntity);
    void openActivityLogin();
}
