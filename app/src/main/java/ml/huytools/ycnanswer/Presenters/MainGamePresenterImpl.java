package ml.huytools.ycnanswer.Presenters;

import ml.huytools.ycnanswer.Core.API.ApiOutput;
import ml.huytools.ycnanswer.Core.API.ApiProvider;
import ml.huytools.ycnanswer.Models.Entities.UserEntity;
import ml.huytools.ycnanswer.Models.UserModel;
import ml.huytools.ycnanswer.Presenters.Interface.MainGamePresenter;
import ml.huytools.ycnanswer.Views.Interface.MainGameView;

public class MainGamePresenterImpl implements MainGamePresenter {
    MainGameView mainGameView ;

    public MainGamePresenterImpl(MainGameView mainGameView) {
        this.mainGameView = mainGameView;
    }

    @Override
    public void loadProfile() {
        if(UserModel.getUserGlobal() !=null){
            mainGameView.showProfile(UserModel.getUserGlobal());
        } else {
            loadNetProfile();
        }
    }

    @Override
    public void logout() {
        UserModel.setUserGlobal(null);
        UserModel.saveToken(null);
        mainGameView.openActivityLogin();
    }

    private void loadNetProfile(){
        mainGameView.showLoading();
        ApiProvider.Async.Callback callback = new ApiProvider.Async.Callback() {
            @Override
            public void OnAPIResult(ApiOutput output, int requestCode) {
                mainGameView.hideLoading();
                mainGameView.showMessage(output.Message == null ? "Lỗi" : output.Message);
                if(output.Status){
                    UserModel.setUserGlobal((UserEntity) output.toModel(UserEntity.class));
                    mainGameView.showProfile(UserModel.getUserGlobal());
                }
            }
        };
        UserModel.getInfoCurrent(callback);
    }
}
