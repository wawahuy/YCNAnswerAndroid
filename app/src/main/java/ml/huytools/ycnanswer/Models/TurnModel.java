package ml.huytools.ycnanswer.Models;

import ml.huytools.ycnanswer.Core.API.ApiProvider;

public class TurnModel {
    public static void getListTurn(ApiProvider.Async.Callback callback){
        ApiProvider.Async.GET("/luot-choi").Then(callback);
    }
}
