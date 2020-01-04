package ml.huytools.ycnanswer.Models;

import org.json.JSONArray;

import ml.huytools.ycnanswer.Core.API.ApiParameters;
import ml.huytools.ycnanswer.Core.API.ApiProvider;

public class TurnModel {
    public static void getListTurn(ApiProvider.Async.Callback callback){
        ApiProvider.Async.GET("/luot-choi").Then(callback);
    }

    public static void addTurn(JSONArray jsonArray, ApiProvider.Async.Callback callback){
        ApiParameters apiParameters = new ApiParameters();
        apiParameters.add("data", jsonArray.toString());
        ApiProvider.Async.POST("/luot-choi/new").SetParams(apiParameters).Then(callback);
    }
}
