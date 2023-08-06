package emds.example.com.vue;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import emds.example.com.R;
import emds.example.com.interfaces.RetrofitInterface;
import emds.example.com.modele.APIResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Inscription extends AppCompatActivity {

    Button btnSInscrire;
    EditText nomEdit;
    EditText prenomEdit;
    EditText pseudoEdit;
    EditText dateDeNaissanceEdit;
    RadioButton radioBtnHomme;
    EditText mailEdit;
    EditText mdpEdit;

    private LoadingCompass loadingCompass;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_inscription);

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        getSupportActionBar().setTitle("Créer un compte");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*s'inscrire*/
        btnSInscrire = (Button) findViewById(R.id.button);
        nomEdit = findViewById(R.id.editTextTextPersonName);
        prenomEdit = findViewById(R.id.editTextTextPersonName2);
        pseudoEdit = findViewById(R.id.editTextTextPersonName3);
        dateDeNaissanceEdit = findViewById(R.id.editTextDate);
        radioBtnHomme = findViewById(R.id.radioButton9);
        mailEdit = findViewById(R.id.editTextTextEmailAddress3);
        mdpEdit = findViewById(R.id.editTextTextPassword);

        loadingCompass = new LoadingCompass(this);
        /*btnSInscrire.setOnClickListener((v) -> {
            startActivity(new Intent(Inscription.this, Login.class));
        });*/
        btnSInscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

                if (activeNetwork != null && activeNetwork.isConnected()) {
                    String nom = nomEdit.getText().toString().trim();
                    String prenom = prenomEdit.getText().toString().trim();
                    String pseudo = pseudoEdit.getText().toString().trim();
                    String dateNaissance = dateDeNaissanceEdit.getText().toString().trim();
                    String sexe = "M";
                    String mail = mailEdit.getText().toString().trim();
                    String mdp = mdpEdit.getText().toString().trim();

                    if(!nom.isEmpty()&&!prenom.isEmpty()&&!pseudo.isEmpty()&&!dateNaissance.isEmpty()&&!mail.isEmpty()&&!mdp.isEmpty()) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("nom", nom);
                        map.put("prenom", prenom);
                        map.put("pseudo", pseudo);
                        map.put("date_naissance", dateNaissance);
                        if(!radioBtnHomme.isChecked()) {
                            sexe = "F";
                        }
                        map.put("sexe", sexe);
                        map.put("mail", mail);
                        map.put("mdp", mdp);


                        loadingCompass.show();
                        Call<APIResult> call = retrofitInterface.executeInscription(map);
                        call.enqueue(new Callback<APIResult>() {
                            @Override
                            public void onResponse(Call<APIResult> call, Response<APIResult> response) {
                                APIResult result = response.body();
                                Handler handler = new Handler();
                                Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        loadingCompass.cancel();
                                        if (result.getStatus() == 201) {
                                            startActivity(new Intent(Inscription.this, Login.class));
                                            Toast.makeText(Inscription.this, "Votre compte a été créé ! Vous pouvez maintenant vous connecter avec votre compte.", Toast.LENGTH_LONG).show();
                                        } else if (result.getStatus() == 401) {
                                            Toast.makeText(Inscription.this, "Ce compte existe déjà !", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(Inscription.this, "Erreur !", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                };
                                if (result.getStatus() == 201) {
                                    handler.postDelayed(runnable, 4000);
                                } else {
                                    handler.postDelayed(runnable, 2000);
                                }
                            }

                            @Override
                            public void onFailure(Call<APIResult> call, Throwable t) {
                                Toast.makeText(Inscription.this, "Erreur serveur !", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        if (nom.isEmpty()) {
                            nomEdit.setError("Ce champ est obligatoire !");
                        }

                        if (prenom.isEmpty()) {
                            prenomEdit.setError("Ce champ est obligatoire !");
                        }

                        if (pseudo.isEmpty()) {
                            pseudoEdit.setError("Ce champ est obligatoire !");
                        }

                        if (dateNaissance.isEmpty()) {
                            dateDeNaissanceEdit.setError("Ce champ est obligatoire !");
                        }

                        if (mail.isEmpty()) {
                            mailEdit.setError("Ce champ est obligatoire !");
                        }

                        if (mdp.isEmpty()) {
                            mdpEdit.setError("Ce champ est obligatoire !");
                        }
                    }
                } else {
                    Toast.makeText(Inscription.this, "Pas de connexion Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}