package ml.huytools.ycnanswer.Views.Interface;

import ml.huytools.ycnanswer.Core.MVP.EntityManager;
import ml.huytools.ycnanswer.Models.Entities.CategoriesEntity;

public interface CategoriesView {
    void setListCategories(EntityManager<CategoriesEntity> list);
    void showMessage(String message);
    void showLoading();
    void hideLoading();
    void openGame(int idCategories);
    void close();
}
