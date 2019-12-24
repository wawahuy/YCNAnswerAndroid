package ml.huytools.ycnanswer.Views;

import androidx.appcompat.app.AppCompatActivity;

import ml.huytools.ycnanswer.Commons.API.APIConfig;
import ml.huytools.ycnanswer.Commons.API.APIInject;
import ml.huytools.ycnanswer.Commons.API.APIOutput;
import ml.huytools.ycnanswer.Commons.API.APIProvider;
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
        APIConfig.setHostname("https://google.com");
        APIConfig.addInject(new APIInject() {
            @Override
            public void OnHeaderInject(HttpURLConnection connection) {
                connection.setRequestProperty ("Authorization", "");
            }

            @Override
            public void OnResult(APIOutput apiOutput) {

            }
        });

        APIProvider.Async.GET("/").Then(new APIProvider.Async.Callback() {
            @Override
            public void OnAPIResult(APIOutput output, int requestCode) {

            }
        });
    }


}
