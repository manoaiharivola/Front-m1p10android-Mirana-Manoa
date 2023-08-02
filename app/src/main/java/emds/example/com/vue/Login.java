package emds.example.com.vue;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import emds.example.com.R;

public class Login extends AppCompatActivity {
    Button btnCreerUnCompte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        btnCreerUnCompte = (Button) findViewById(R.id.button3);
        btnCreerUnCompte.setOnClickListener((v) -> {
            startActivity(new Intent(Login.this, Inscription.class));
        });
    }
}