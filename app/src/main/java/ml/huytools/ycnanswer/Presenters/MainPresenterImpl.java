package ml.huytools.ycnanswer.Presenters;

import ml.huytools.ycnanswer.Core.API.ApiConfig;
import ml.huytools.ycnanswer.Core.API.ApiOutput;
import ml.huytools.ycnanswer.Core.API.ApiProvider;
import ml.huytools.ycnanswer.Core.API.JWTAuthenticate;
import ml.huytools.ycnanswer.Models.User;
import ml.huytools.ycnanswer.Presenters.Interface.MainPresenter;
import ml.huytools.ycnanswer.Views.Interface.MainView;

public class MainPresenterImpl implements MainPresenter {
    MainView mainView;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void init() {
        ApiConfig.setHostname("http://192.168.1.130:8000/api");
        checkLogged();
    }

    public void checkLogged(){
        String token = User.getTokenSaved();

        if(token != null && token != ""){
            registerAuthorizationGlobal(token);
            ApiProvider.Async.Callback callback = new ApiProvider.Async.Callback() {
                @Override
                public void OnAPIResult(ApiOutput output, int requestCode) {
                    mainView.showMessage(output.Message == null ? "Lỗi" : output.Message);

                    if(output.Status){
                        mainView.showActivityMainGame();
                    } else {
                        User.saveToken(null);
                        mainView.showActivityLogin();
                    }
                }
            };
            User.getInfoCurrent(callback);

            return;
        }

        mainView.showActivityLogin();
    }

    public void registerAuthorizationGlobal(String token){
        ApiConfig.setAuthenticate(new JWTAuthenticate(token));
    }

}
