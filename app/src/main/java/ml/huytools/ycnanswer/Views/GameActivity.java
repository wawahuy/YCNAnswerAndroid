package ml.huytools.ycnanswer.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import ml.huytools.ycnanswer.Commons.ModelManager;
import ml.huytools.ycnanswer.Models.CauHoi;
import ml.huytools.ycnanswer.Presenters.GamePresenter;
import ml.huytools.ycnanswer.R;

public class GameActivity extends AppCompatActivity
        implements GamePresenter.View {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ModelManager<CauHoi> cauHoiModelManager = new ModelManager<>();
        cauHoiModelManager.add(new CauHoi());
        cauHoiModelManager.add(new CauHoi());
        Log.v("Log", ModelManager.ParseJSON(CauHoi.class, cauHoiModelManager.toJson()).toJson());

    }
}
