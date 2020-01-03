package ml.huytools.ycnanswer.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import java.util.List;

import ml.huytools.ycnanswer.Core.API.ApiOutput;
import ml.huytools.ycnanswer.Core.API.ApiProvider;
import ml.huytools.ycnanswer.Models.CreditModel;
import ml.huytools.ycnanswer.Models.Entities.CreditEntity;
import ml.huytools.ycnanswer.R;
import ml.huytools.ycnanswer.Views.GameComponents.Loading;
import ml.huytools.ycnanswer.Views.ViewComponents.LoadingView;

public class CreditActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CreditAdapter creditAdapter;
    List<CreditEntity> creditEntityList;
    LoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        recyclerView = findViewById(R.id.recycleCredit);
        creditAdapter = new CreditAdapter(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(creditAdapter);

        loadListCredit();
    }

    public void loadListCredit() {
        loadingView = LoadingView.create(this);
        ApiProvider.Async.Callback callback = new ApiProvider.Async.Callback() {
            @Override
            public void OnAPIResult(ApiOutput output, int requestCode) {
                loadingView.removeOnView();
                showMessage(output.Message);
                if(output.Status){
                    creditEntityList = output.toModelManager(CreditEntity.class);
                    creditAdapter.setList(creditEntityList);
                }
            }
        };

        CreditModel.getListCredit(callback);
    }


    public void showMessage(String m) {
        if (m != null)
            Toast.makeText(this, m, Toast.LENGTH_LONG).show();
    }

}
