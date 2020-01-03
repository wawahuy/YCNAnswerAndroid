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
import ml.huytools.ycnanswer.Models.Entities.TurnEntity;
import ml.huytools.ycnanswer.Models.TurnModel;
import ml.huytools.ycnanswer.R;
import ml.huytools.ycnanswer.Views.ViewComponents.LoadingView;

public class TurnActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TurnAdapter turnAdapter;
    List<TurnEntity> turnEntityList;
    LoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn);

        recyclerView = findViewById(R.id.recycleTurn);
        turnAdapter = new TurnAdapter(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(turnAdapter);

        loadListTurn();

    }

    public void loadListTurn() {
        loadingView = LoadingView.create(this);
        ApiProvider.Async.Callback callback = new ApiProvider.Async.Callback() {
            @Override
            public void OnAPIResult(ApiOutput output, int requestCode) {
                loadingView.removeOnView();
                showMessage(output.Message);
                if (output.Status) {
                    turnEntityList = output.toModelManager(TurnEntity.class);
                    turnAdapter.setList(turnEntityList);
                }
            }
        };

        TurnModel.getListTurn(callback);
    }
    public void showMessage(String m) {
        if (m != null)
            Toast.makeText(this, m, Toast.LENGTH_LONG).show();
    }
}
