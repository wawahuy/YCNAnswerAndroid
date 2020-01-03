package ml.huytools.ycnanswer.Models;

import ml.huytools.ycnanswer.Core.API.ApiProvider;

public class CreditModel {
    public static void getListCredit(ApiProvider.Async.Callback callback){
        ApiProvider.Async.GET("/goi-credit").Then(callback);
    }
}
