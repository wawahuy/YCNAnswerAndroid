package ml.huytools.ycnanswer.Presenters;

import android.util.Log;

import ml.huytools.ycnanswer.Core.API.ApiOutput;
import ml.huytools.ycnanswer.Core.API.ApiProvider;
import ml.huytools.ycnanswer.Core.MVP.EntityManager;
import ml.huytools.ycnanswer.Models.CategoriesModel;
import ml.huytools.ycnanswer.Models.Entities.CategoriesEntity;
import ml.huytools.ycnanswer.Presenters.Interface.CategoriesPresenter;
import ml.huytools.ycnanswer.Views.Interface.CategoriesView;

public class CategoriesPresenterImpl implements CategoriesPresenter {
    CategoriesView categoriesView;

    public CategoriesPresenterImpl(CategoriesView categoriesView) {
        this.categoriesView = categoriesView;
    }

    @Override
    public void getListCategorises(){
        categoriesView.showLoading();
        ApiProvider.Async.Callback callback = new ApiProvider.Async.Callback() {
            @Override
            public void OnAPIResult(ApiOutput output, int requestCode) {
                categoriesView.hideLoading();
                if(output.Status){
                    if(!output.isDJObject()){
                            EntityManager<CategoriesEntity> list = output.toModelManager(CategoriesEntity.class);
                            categoriesView.setListCategories(list);
                            return;
                    }
                }

                categoriesView.showMessage(output.Message == null ? "Lỗi" : output.Message);
                categoriesView.close();
            }
        };

        CategoriesModel.getList(callback);
    }

    @Override
    public void selectCategorises(CategoriesEntity categoriesEntity) {
        categoriesView.openGame(categoriesEntity.id);
    }
}
