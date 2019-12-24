package ml.huytools.ycnanswer.Views;

import androidx.appcompat.app.AppCompatActivity;

import ml.huytools.ycnanswer.Commons.API.ApiConfig;
import ml.huytools.ycnanswer.Commons.API.ApiIntercept;
import ml.huytools.ycnanswer.Commons.API.ApiOutput;
import ml.huytools.ycnanswer.Commons.API.ApiProvider;
import ml.huytools.ycnanswer.Commons.App;
import ml.huytools.ycnanswer.R;

import android.os.Bundle;

import java.net.HttpURLConnection;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        /**
         * Khởi động thư viện
         */
        App.getInstance().init(this);

        // Đặt hostname cho API
        ApiConfig.setHostname("https://google.com");
        ApiProvider.Async.POST("/test").AddParam("test", "test").Then(new ApiProvider.Async.Callback() {
                    @Override
                    public void OnAPIResult(ApiOutput output, int requestCode) {

                    }
        });
    }


}
