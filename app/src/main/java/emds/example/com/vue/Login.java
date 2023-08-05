package emds.example.com.vue;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;
import java.util.List;

import emds.example.com.R;
import emds.example.com.interfaces.RetrofitInterface;
import emds.example.com.modele.LoginResult;
import emds.example.com.receiver.NetworkChangeReceiver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    private boolean isConnected = true;
    private NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver(isConnected);

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Login.this);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnected();

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, filter);

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
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

                if (activeNetwork != null && activeNetwork.isConnected()) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("mail", emailEdit.getText().toString());
                    map.put("mdp", mdpEdit.getText().toString());

                    Call<LoginResult> call = retrofitInterface.executeLogin(map);
                    call.enqueue(new Callback<LoginResult>() {
                        @Override
                        public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                            LoginResult result = response.body();
                            if (result.getStatus() == 200) {
                                Object data = result.getData();
                                if (data instanceof List) {
                                    List<LinkedTreeMap<String, String>> dataList = (List<LinkedTreeMap<String, String>>) data;
                                    if (!dataList.isEmpty()) {
                                        LinkedTreeMap<String, String> dataMap = dataList.get(0);
                                        String accessToken = dataMap.get("access_token");
                                        if (accessToken != null) {
                                            SharedPreferences.Editor editor = preferences.edit();
                                            editor.putString("access_token", accessToken);
                                            editor.apply();
                                        }
                                    }
                                }
                            } else if (result.getStatus() == 401) {
                                Toast.makeText(Login.this, "E-mail ou mot de passe incorrect !", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResult> call, Throwable t) {
                            Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(Login.this, "Pas de connexion Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
        isConnected = false;
    }
}