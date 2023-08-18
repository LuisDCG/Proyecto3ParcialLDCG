package com.example.proyecto3parcialldcg;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrarEmpleadosActivity extends AppCompatActivity {

    EditText nameInput;

    EditText emailInput;
    EditText passwordInput;
    EditText confirmPasswordInput;
    Spinner employeeTypeSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrarempleados);

        nameInput = findViewById(R.id.name_input);
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        confirmPasswordInput = findViewById(R.id.confirm_password_input);
        employeeTypeSpinner = findViewById(R.id.employee_type_spinner);

        Button botonRegresar = findViewById(R.id.botonRegresar);
        botonRegresar.setOnClickListener(v -> {
            Intent intent = new Intent(RegistrarEmpleadosActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Botón Ingresar
        Button botonIngresar = findViewById(R.id.botonIngresar);
        botonIngresar.setOnClickListener(v -> {
            Intent intent = new Intent(RegistrarEmpleadosActivity.this, InicioActivity.class);
            startActivity(intent);
        });

        // Botón Registrar
        Button botonRegistrar = findViewById(R.id.register_button);
        botonRegistrar.setOnClickListener(v -> {
            // Obtener los valores
            String nombre = nameInput.getText().toString();
            String correo = emailInput.getText().toString();
            String contrasena = passwordInput.getText().toString();
            String confirmarContrasena = confirmPasswordInput.getText().toString();
            String tipo = employeeTypeSpinner.getSelectedItem().toString();

            // Validar las entradas
            if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || confirmarContrasena.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!contrasena.equals(confirmarContrasena)) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                return;
            }

            // Registrar el empleado
            registrarEmpleado(nombre, correo, contrasena, tipo);
        });
    }

    private void registrarEmpleado(String nombre, String correo, String contrasena, String tipo) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.200.2:8080/androidPHPSQL/registrar_empleado.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response.trim());
                        String status = jsonResponse.getString("status");
                        if (status.equals("success")) {
                            // El empleado fue registrado exitosamente
                            Toast.makeText(getApplicationContext(), "Empleado registrado con éxito", Toast.LENGTH_SHORT).show();
                        } else {
                            // Hubo un error al registrar al empleado
                            String error = jsonResponse.optString("error", "Error desconocido");
                            Toast.makeText(getApplicationContext(), "Error al registrar empleado: " + error, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Error en el análisis de respuesta: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    // Error en la petición
                    Toast.makeText(getApplicationContext(), "Error en la petición: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nombre", nombre);
                params.put("correo", correo);
                params.put("contrasena", contrasena);
                params.put("tipo", tipo);
                return params;
            }
        };

        queue.add(postRequest);
    }

}