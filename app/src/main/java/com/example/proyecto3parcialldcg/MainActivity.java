package com.example.proyecto3parcialldcg;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.util.Calendar;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText nameInput;
    EditText emailInput;
    EditText passwordInput;
    EditText confirmPasswordInput;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginuser);

        nameInput = findViewById(R.id.name_input);
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        confirmPasswordInput = findViewById(R.id.confirm_password_input);

        // Boton para logearse y asi definir si es cocinero o no, solo el admin puede crear usuarios
        Button cambiarVistaMenuEmpleadosButton = findViewById(R.id.CambiarVistaMenuEmpleados);
        cambiarVistaMenuEmpleadosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InicioActivity.class);
                startActivity(intent);
            }
        });

        Button cambiarVistaCliente = findViewById(R.id.btnMenuCliente);
        cambiarVistaCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Obtenemos la hora actual del dispositivo
                Calendar calendar = Calendar.getInstance();
                int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

                // Creamos un nuevo Intent basado en la hora actual
                Intent intent;
                if (hourOfDay >= 8 && hourOfDay < 12) {
                    intent = new Intent(MainActivity.this, MenuDesayunosActivity.class);
                } else if (hourOfDay >= 12 && hourOfDay < 18) {
                    intent = new Intent(MainActivity.this, MenuComidasActivity.class);
                } else if (hourOfDay >= 18 && hourOfDay < 23) {
                    intent = new Intent(MainActivity.this, MenuCenasActivity.class);
                } else {
                    intent = new Intent(MainActivity.this, MenuCenasActivity.class);
                }

                startActivity(intent);

            }
        });


        Button cambiarVistaCajadeCobro = findViewById(R.id.btnCobrarCliente);
        cambiarVistaCajadeCobro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CajadecobroActivity.class);
                startActivity(intent);
            }
        });

    }

    public void clickBtnListener(View view){
        String url = "http://192.168.1.109:8080/androidPHPSQL/insercion.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest resultadoPost = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "Usuario insertado correctamente", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "error " + error, Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nombre", "Juan");
                params.put("correo", "juan@ejemplo.com");
                return params;
            }
        };
        queue.add(resultadoPost);
    }

}