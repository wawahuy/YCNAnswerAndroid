package ml.huytools.ycnanswer.Views;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import ml.huytools.ycnanswer.Models.CauHoi;
import ml.huytools.ycnanswer.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CauHoi cauHoi = new CauHoi();


    }


}
