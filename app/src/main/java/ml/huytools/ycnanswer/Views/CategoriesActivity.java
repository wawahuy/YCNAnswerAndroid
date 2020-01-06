package ml.huytools.ycnanswer.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import ml.huytools.ycnanswer.Core.MVP.EntityManager;
import ml.huytools.ycnanswer.Models.Entities.CategoriesEntity;
import ml.huytools.ycnanswer.Presenters.CategoriesPresenterImpl;
import ml.huytools.ycnanswer.Presenters.Interface.CategoriesPresenter;
import ml.huytools.ycnanswer.R;
import ml.huytools.ycnanswer.Views.Interface.CategoriesView;
import ml.huytools.ycnanswer.Views.ViewComponents.LoadingView;

public class CategoriesActivity extends AppCompatActivity implements CategoriesView {
    public static final String ID_CATEGORIES_EX = "ID_CATEGORIES";

    CategoriesAdapter categoriesAdapter;
    RecyclerView recyclerView;
    CategoriesPresenter categoriesPresenter;
    LoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_categories);

        categoriesPresenter = new CategoriesPresenterImpl(this);
        categoriesPresenter.getListCategorises();

        categoriesAdapter = new CategoriesAdapter(this, categoriesPresenter);
        recyclerView = findViewById(R.id.recyCategoires);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(categoriesAdapter);
    }

    @Override
    public void setListCategories(EntityManager<CategoriesEntity> list) {
        categoriesAdapter.setCategoriesEntities(list);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        loadingView = LoadingView.create(this);
    }

    @Override
    public void hideLoading() {
        loadingView.removeOnView();
    }

    @Override
    public void openGame(int idCategories) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(ID_CATEGORIES_EX, idCategories);
        startActivity(intent);
    }

    @Override
    public void close() {
        /// Update
        finish();
        /// Open Back
        //. ---- update
    }
}
