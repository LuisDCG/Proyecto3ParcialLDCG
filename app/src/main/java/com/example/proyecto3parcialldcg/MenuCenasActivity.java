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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MenuCenasActivity extends AppCompatActivity {

    private EditText editTextNumber;
    private EditText editTextA;
    private EditText editTextB;
    private EditText editTextC;
    private EditText editTextD;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu3);


        editTextNumber = findViewById(R.id.txtNumeroMesaAntojito);
        editTextA = findViewById(R.id.CantidadAlmuerzoA);
        editTextB = findViewById(R.id.CantidadAlmuerzoB);
        editTextC = findViewById(R.id.CantidadAlmuerzoC);
        editTextD = findViewById(R.id.CantidadAlmuerzoD);
        buttonSubmit = findViewById(R.id.ButonPedirDesayuno);

        Button botonAntojo = findViewById(R.id.botonantojo);
        botonAntojo.setOnClickListener(v -> {
            Intent intent = new Intent(MenuCenasActivity.this, MenuAntojosActivity.class);
            startActivity(intent);
        });


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tableNumber = editTextNumber.getText().toString();
                String cenaAQuantity = editTextA.getText().toString();
                String cenaBQuantity = editTextB.getText().toString();
                String cenaCQuantity = editTextC.getText().toString();
                String cenaDQuantity = editTextD.getText().toString();


                int cenaA = Integer.parseInt(cenaAQuantity);
                int cenaB = Integer.parseInt(cenaBQuantity);
                int cenaC = Integer.parseInt(cenaCQuantity);
                int cenaD = Integer.parseInt(cenaDQuantity);

                if (cenaA != 0) {
                    insertData(tableNumber, "Cena A", cenaAQuantity);
                }

                if (cenaB != 0) {
                    insertData(tableNumber, "Cena B", cenaBQuantity);
                }

                if (cenaC != 0) {
                    insertData(tableNumber, "Cena C", cenaCQuantity);
                }

                if (cenaD != 0) {
                    insertData(tableNumber, "Cena D", cenaDQuantity);
                }
            }
        });



    }

    private void insertData(final String tableNumber, final String nombrePedido, final String cantidad) {
        String url = "http://192.168.10.252:8080/androidPHPSQL/agregar_pedido.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(MenuCenasActivity.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {

                    Toast.makeText(MenuCenasActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("NombrePedido", nombrePedido);
                params.put("cantidad", cantidad);
                params.put("numeroMesa", tableNumber);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}