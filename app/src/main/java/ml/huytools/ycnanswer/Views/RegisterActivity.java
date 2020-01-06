package ml.huytools.ycnanswer.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ml.huytools.ycnanswer.Core.API.ApiOutput;
import ml.huytools.ycnanswer.Core.API.ApiProvider;
import ml.huytools.ycnanswer.Models.UserModel;
import ml.huytools.ycnanswer.R;
import ml.huytools.ycnanswer.Views.ViewComponents.LoadingView;

public class RegisterActivity extends AppCompatActivity {

    TextView txvRegName, txvRegPass1, txvRegPass2;
    LoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txvRegName = findViewById(R.id.txvRegName);
        txvRegPass1 = findViewById(R.id.txvRegPass1);
        txvRegPass2 = findViewById(R.id.txvRegPass2);
    }

    public void OnReg(View view) {
        if (txvRegName.getText().toString().length() < 3) {
            showMessage("Tài khoản lớn hơn 3 kí tự!");
            return;
        }
        if (txvRegPass1.getText().toString().length() < 3) {
            showMessage("Mật khẩu lớn hơn 3 kí tự!");
            return;
        }
        if (!txvRegPass1.getText().toString().equals(txvRegPass2.getText().toString())) {
            showMessage("Mật khẩu không khớp!");
            return;
        }

        loadingView = LoadingView.create(this);

        ApiProvider.Async.Callback callback = new ApiProvider.Async.Callback() {
            @Override
            public void OnAPIResult(ApiOutput output, int requestCode) {
                loadingView.removeOnView();
                ///...
                /// Status, Data, Message....
                if (output.Message != null) {
                    showMessage(output.Message);
                }

                if (output.Status) {
                    finish();
                }
            }
        };
        UserModel.register(txvRegName.getText().toString(), txvRegPass1.getText().toString(), callback);
    }

    public  void OnBackLogin(View view){
        finish();
    }

    public void showMessage(String m) {
        Toast.makeText(this, m, Toast.LENGTH_LONG).show();
    }


}
