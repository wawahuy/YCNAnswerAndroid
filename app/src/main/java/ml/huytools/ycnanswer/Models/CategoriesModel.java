package ml.huytools.ycnanswer.Models;

import ml.huytools.ycnanswer.Core.API.ApiProvider;

public class CategoriesModel {
    static final String CATEGORIES_URI = "/linh-vuc";

    public static void getList(ApiProvider.Async.Callback callback){
        ApiProvider.Async.GET(CATEGORIES_URI).Then(callback);
    }
}
