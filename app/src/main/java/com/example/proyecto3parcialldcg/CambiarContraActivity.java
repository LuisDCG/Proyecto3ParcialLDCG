package com.example.proyecto3parcialldcg;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class CambiarContraActivity extends AppCompatActivity {
    EditText oldPasswordInput;
    EditText newPasswordInput;
    String nombreMesero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cambiarcontrasenia);

        oldPasswordInput = findViewById(R.id.old_password_input);
        newPasswordInput = findViewById(R.id.new_password_input);

        nombreMesero = getIntent().getStringExtra("nombreMesero");
        Toast toast = Toast.makeText(this, "¡Hola, " + nombreMesero + "! Bienvenido(a)", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        Button cambiarContraseñaButton = findViewById(R.id.change_password_button);
        cambiarContraseñaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = oldPasswordInput.getText().toString();
                String newPassword = newPasswordInput.getText().toString();

                if (oldPassword.isEmpty() || newPassword.isEmpty()) {
                    Toast.makeText(CambiarContraActivity.this, "Ingresa la contraseña actual y la nueva contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }


                cambiarContraseñaEnBD(oldPassword, newPassword);
            }
        });
    }

    private void cambiarContraseñaEnBD(String oldPassword, String newPassword) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.131.89:8080/androidPHPSQL/cambiar_contrasena.php";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            Toast.makeText(CambiarContraActivity.this, "Contraseña cambiada exitosamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CambiarContraActivity.this, "No se pudo cambiar la contraseña", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CambiarContraActivity.this, "Error en la petición: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nombreMesero", nombreMesero);
                params.put("oldPassword", oldPassword);
                params.put("newPassword", newPassword);
                return params;
            }
        };

        queue.add(postRequest);
    }
}
