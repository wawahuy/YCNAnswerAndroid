package ml.huytools.ycnanswer.Models;

import ml.huytools.ycnanswer.Core.API.ApiProvider;

public class QuestionModel  {
    static final String QUESTION_BY_CATE_URI = "/cau-hoi/lay-qua-linh-vuc/";
    public static final int QUESTION_BY_CATE_CODE = 1001;

    public static void getQuestionByCategoriesID(int idCategories, ApiProvider.Async.Callback callback){
        ApiProvider.Async
                .GET(QUESTION_BY_CATE_URI + idCategories)
                .SetRequestCode(QUESTION_BY_CATE_CODE)
                .Then(callback);
    }
}
