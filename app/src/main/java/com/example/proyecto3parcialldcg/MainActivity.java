package com.example.proyecto3parcialldcg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los datos ingresados por el usuario
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // Verificar las credenciales (esto es solo un ejemplo, debes reemplazarlo con tu lógica real)
                if (username.equals("usuario") && password.equals("contraseña")) {
                    // Inicio de sesión exitoso, puedes redirigir a otra actividad o mostrar un mensaje
                    Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                } else {
                    // Inicio de sesión fallido, muestra un mensaje de error
                    Toast.makeText(MainActivity.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

