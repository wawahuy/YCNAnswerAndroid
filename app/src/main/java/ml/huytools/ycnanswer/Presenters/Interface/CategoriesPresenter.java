package ml.huytools.ycnanswer.Presenters.Interface;

import ml.huytools.ycnanswer.Models.Entities.CategoriesEntity;

public interface CategoriesPresenter {
    void getListCategorises();
    void selectCategorises(CategoriesEntity categoriesEntity);
}
