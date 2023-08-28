package com.example.proyecto3parcialldcg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class CambiarContraActivity2 extends AppCompatActivity {

    EditText oldPasswordInput;
    EditText newPasswordInput;
    String nombreCocinero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cambiarcontrasenia2);


        oldPasswordInput = findViewById(R.id.old_password_input);
        newPasswordInput = findViewById(R.id.new_password_input);

        nombreCocinero = getIntent().getStringExtra("nombreCocinero");
        Toast toast = Toast.makeText(this, "¡Hola, " + nombreCocinero + "! Bienvenido(a)", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        Button cambiarContraseñaButton = findViewById(R.id.change_password_button);
        cambiarContraseñaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = oldPasswordInput.getText().toString();
                String newPassword = newPasswordInput.getText().toString();

                if (oldPassword.isEmpty() || newPassword.isEmpty()) {
                    Toast.makeText(CambiarContraActivity2.this, "Ingresa la contraseña actual y la nueva contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                cambiarContraseñaEnBD2(oldPassword, newPassword);
            }
        });

    }


    private void cambiarContraseñaEnBD2(String oldPassword, String newPassword) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.109:8080/androidPHPSQL/cambiar_contrasena2.php";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            Toast.makeText(CambiarContraActivity2.this, "Contraseña cambiada exitosamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CambiarContraActivity2.this, "No se pudo cambiar la contraseña", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CambiarContraActivity2.this, "Error en la petición: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nombreCocinero", nombreCocinero);
                params.put("oldPassword", oldPassword);
                params.put("newPassword", newPassword);
                return params;
            }
        };

        queue.add(postRequest);
    }
}