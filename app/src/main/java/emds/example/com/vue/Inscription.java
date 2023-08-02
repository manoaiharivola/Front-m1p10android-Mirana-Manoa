package emds.example.com.vue;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import emds.example.com.R;

public class Inscription extends AppCompatActivity {
    Button btnSInscrire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_inscription);

        getSupportActionBar().setTitle("Créer un compte");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSInscrire = (Button) findViewById(R.id.button);
        btnSInscrire.setOnClickListener((v) -> {
            startActivity(new Intent(Inscription.this, Login.class));
        });
    }
}