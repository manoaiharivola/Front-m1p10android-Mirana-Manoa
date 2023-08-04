package emds.example.com.vue;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import emds.example.com.R;
import emds.example.com.interfaces.RetrofitInterface;
import emds.example.com.modele.LoginResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        Button loginBtn = (Button) findViewById(R.id.button4);
        final EditText emailEdit = findViewById(R.id.editTextTextEmailAddress2);
        final EditText mdpEdit = findViewById(R.id.editTextTextPassword3);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> map = new HashMap<>();
                map.put("mail", emailEdit.getText().toString());
                map.put("mdp", mdpEdit.getText().toString());

                Call<LoginResult> call = retrofitInterface.executeLogin(map);
                call.enqueue(new Callback<LoginResult>() {
                    @Override
                    public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                        LoginResult result = response.body();
                        if(result.getStatus() == 200) {
                        /*AlertDialog.Builder builder1 = new AlertDialog.Builder(Login.this);
                        builder1.setTitle("Access Token");
                        builder1.setMessage(result.getAccessToken());

                        builder1.show();*/
                            System.out.println(result.getData());
                        } else if (result.getStatus()== 401) {
                            Toast.makeText(Login.this, "E-mail ou mot de passe incorrect !", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResult> call, Throwable t) {
                        Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}