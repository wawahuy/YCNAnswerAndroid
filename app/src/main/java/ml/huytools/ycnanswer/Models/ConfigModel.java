package ml.huytools.ycnanswer.Models;

import ml.huytools.ycnanswer.Core.API.ApiProvider;

public class ConfigModel {
    static final String CONFIG_URI = "/cau-hinh";

    public static void getConfig(ApiProvider.Async.Callback callback){
        ApiProvider.Async.GET(CONFIG_URI).Then(callback);
    }

}
