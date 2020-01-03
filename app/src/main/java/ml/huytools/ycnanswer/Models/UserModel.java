package ml.huytools.ycnanswer.Models;

import android.content.Context;
import android.content.SharedPreferences;

import ml.huytools.ycnanswer.Core.API.ApiOutput;
import ml.huytools.ycnanswer.Core.API.ApiParameters;
import ml.huytools.ycnanswer.Core.API.ApiProvider;
import ml.huytools.ycnanswer.Core.App;
import ml.huytools.ycnanswer.Models.Entities.UserEntity;

public class UserModel {
    static final String LOGIN_URI = "/login";
    static final String INFO_URI = "/user";
    static final String UPLOAD_AVATAR_URI = "/user/avatar";
    static private UserEntity userEntity;

    public static void login(String user, String password, ApiProvider.Async.Callback callback){
        ApiParameters parameters = new ApiParameters();
        parameters.add("taikhoan", user);
        parameters.add("matkhau", password );
        ApiProvider.Async.POST(LOGIN_URI).SetParams(parameters).Then(callback);
    }

    public static void getInfoCurrent(ApiProvider.Async.Callback callback){
        ApiProvider.Async.GET(INFO_URI).Then(callback);
    }

    public static void saveToken(String token){
        SharedPreferences sharedPreferences = App.getInstance().getContextApplication().getSharedPreferences("auth", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.commit();
    }

    public static String getTokenSaved(){
        SharedPreferences sharedPreferences = App.getInstance().getContextApplication().getSharedPreferences("auth", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", null);
    }

    public static void setUserGlobal(UserEntity userGlobal){
        userEntity = userGlobal;
    }

    public static UserEntity getUserGlobal(){
        return userEntity;
    }

    public static void uploadAvatar(byte[] data, ApiProvider.Async.Callback callback){
        ApiParameters parameters = new ApiParameters();
        parameters.add("file", "image", data);
        ApiProvider.Async.POST(UPLOAD_AVATAR_URI).SetParams(parameters).Then(callback);
    }
}
