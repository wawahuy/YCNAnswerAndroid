package ml.huytools.ycnanswer.Presenters;

import org.json.JSONException;
import org.json.JSONObject;

import ml.huytools.ycnanswer.Core.API.ApiOutput;
import ml.huytools.ycnanswer.Core.API.ApiProvider;
import ml.huytools.ycnanswer.Models.Entities.UserEntity;
import ml.huytools.ycnanswer.Models.UserModel;
import ml.huytools.ycnanswer.Presenters.Interface.ProfilePresenter;
import ml.huytools.ycnanswer.Views.Interface.ProfileView;

public class ProfilePresenterImpl implements ProfilePresenter {
    ProfileView profileView;

    public ProfilePresenterImpl(ProfileView profileView) {
        this.profileView = profileView;
    }

    @Override
    public void uploadAvatar(byte[] data) {
        ApiProvider.Async.Callback callback = new ApiProvider.Async.Callback() {
            @Override
            public void OnAPIResult(ApiOutput output, int requestCode) {
                profileView.showMessage(output.Message == null ? "Lỗi" : output.Message);
                if(output.Status){
                    profileView.hideButtonUpload();
                }
                loadNetProfile();
            }
        };

        profileView.showLoading();
        UserModel.uploadAvatar(data, callback);
    }

    @Override
    public void loadProfile() {
        profileView.showProfile(UserModel.getUserGlobal());
    }

    private void loadNetProfile(){
        ApiProvider.Async.Callback callback = new ApiProvider.Async.Callback() {
            @Override
            public void OnAPIResult(ApiOutput output, int requestCode) {
                profileView.hideLoading();
                if(output.Status){
                    UserModel.setUserGlobal((UserEntity) output.toModel(UserEntity.class));
                    profileView.showProfile(UserModel.getUserGlobal());
                }
            }
        };
        UserModel.getInfoCurrent(callback);
    }
}
