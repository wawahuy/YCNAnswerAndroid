package ml.huytools.ycnanswer.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import ml.huytools.ycnanswer.Core.API.ApiOutput;
import ml.huytools.ycnanswer.Core.API.ApiProvider;
import ml.huytools.ycnanswer.Models.CreditModel;
import ml.huytools.ycnanswer.Models.Entities.CreditEntity;
import ml.huytools.ycnanswer.Models.Entities.UserEntity;
import ml.huytools.ycnanswer.Models.UserModel;
import ml.huytools.ycnanswer.R;
import ml.huytools.ycnanswer.Views.ViewComponents.LoadingView;

public class RankActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RankAdapter rankAdapter;
    List<UserEntity> userEntitiesList;
    LoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        recyclerView = findViewById(R.id.recycleRank);
        rankAdapter = new RankAdapter(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(rankAdapter);

        loadListRank();
    }

    public void loadListRank() {
        loadingView = LoadingView.create(this);
        ApiProvider.Async.Callback callback = new ApiProvider.Async.Callback() {
            @Override
            public void OnAPIResult(ApiOutput output, int requestCode) {
                loadingView.removeOnView();
                showMessage(output.Message);
                if (output.Status) {
                    userEntitiesList = output.toModelManager(UserEntity.class);
                    rankAdapter.setList(userEntitiesList);
                }
            }
        };

        UserModel.getListRank(callback);
    }

    public void showMessage(String m) {
        if (m != null)
            Toast.makeText(this, m, Toast.LENGTH_LONG).show();
    }
}
