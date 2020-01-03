package ml.huytools.ycnanswer.Presenters;

import org.json.JSONException;
import org.json.JSONObject;

import ml.huytools.ycnanswer.Core.API.ApiConfig;
import ml.huytools.ycnanswer.Core.API.ApiOutput;
import ml.huytools.ycnanswer.Core.API.ApiProvider;
import ml.huytools.ycnanswer.Core.API.JWTAuthenticate;
import ml.huytools.ycnanswer.Models.UserModel;
import ml.huytools.ycnanswer.Presenters.Interface.LoginPresenter;
import ml.huytools.ycnanswer.Views.Interface.LoginView;

public class LoginPresenterImpl implements LoginPresenter {
    LoginView view;

    public LoginPresenterImpl(LoginView loginView){
        this.view = loginView;
    }

    public void registerAuthorizationGlobal(String token){
        ApiConfig.setAuthenticate(new JWTAuthenticate(token));
    }

    @Override
    public void login(String username, String password) {
        ApiProvider.Async.Callback callback = new ApiProvider.Async.Callback() {
            @Override
            public void OnAPIResult(ApiOutput output, int requestCode) {
                view.hideLoading();
                view.showMessage(output.Message == null ? "Lỗi" : output.Message);

                if(output.Status){
                    JSONObject resData = (JSONObject)output.Data;
                    try {
                        String token = (String) resData.get("token");
                        UserModel.saveToken(token);
                        registerAuthorizationGlobal(token);
                        view.openMainGameActivity();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        view.showLoading();
        UserModel.login(username, password, callback);
    }

}
