package com.example.proyecto3parcialldcg;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
public class InicioActivity extends AppCompatActivity {
    EditText nameInput;
    EditText passwordInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);


        /*Button butonRegistrar = findViewById(R.id.botonRegistrar);
        butonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioActivity.this, RegistrarEmpleadosActivity.class);
                startActivity(intent);
            }
        }); */

        nameInput = findViewById(R.id.editTextText);
        passwordInput = findViewById(R.id.editTextTextPassword);

        Button botonIngresar = findViewById(R.id.button);
        botonIngresar.setOnClickListener(v -> {
            String nombre = nameInput.getText().toString();
            String contrasena = passwordInput.getText().toString();

            if (nombre.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Nombre y contraseña son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validar las credenciales del usuario
            logearUsuario(nombre, contrasena);
        });
    }
    private void logearUsuario(String nombre, String contrasena) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.200.2:8080/androidPHPSQL/login_empleado.php";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response.trim());
                        String status = jsonResponse.getString("status");
                        if (status.equals("success")) {
                            String tipo = jsonResponse.getString("tipo");
                            Intent intent;
                            switch (tipo.toLowerCase()) {
                                case "cocinero":
                                    intent = new Intent(this, CocineroActivity.class);
                                    // Aquí agregamos el nombre como un extra en el Intent para cocinero
                                    intent.putExtra("nombreCocinero", nombre);
                                    break;
                                case "mesero":
                                    intent = new Intent(this, MeseroActivity.class);
                                    // Aquí agregamos el nombre como un extra en el Intent para mesero
                                    intent.putExtra("nombreMesero", nombre);
                                    break;
                                case "administrador":
                                    intent = new Intent(this, RegistrarEmpleadosActivity.class);
                                    break;
                                default:
                                    Toast.makeText(getApplicationContext(), "Tipo de empleado no reconocido", Toast.LENGTH_SHORT).show();
                                    return;
                            }
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Error en las credenciales", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Error en el análisis de respuesta: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(getApplicationContext(), "Error en la petición: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nombre", nombre);
                params.put("contrasena", contrasena);
                return params;
            }
        };

        queue.add(postRequest);
    }



}
