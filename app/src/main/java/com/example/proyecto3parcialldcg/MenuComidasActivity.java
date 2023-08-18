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

public class MenuComidasActivity extends AppCompatActivity {




    private EditText CantidadAlmuerzoA;
    private EditText CantidadAlmuerzoB;
    private EditText CantidadAlmuerzoC;
    private EditText CantidadAlmuerzoD;

    private EditText txtNumeroMesaAlmuerzo;
    private Button ButonPedirAlmuerzo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu2);


        Button botonAntojo = findViewById(R.id.btnantojitoalmuerzo);
        botonAntojo.setOnClickListener(v -> {
            Intent intent = new Intent(MenuComidasActivity.this, MenuAntojosActivity.class);
            startActivity(intent);
        });
        txtNumeroMesaAlmuerzo = findViewById(R.id.txtNumeroMesaAntojito);
        CantidadAlmuerzoA = findViewById(R.id.CantidadAlmuerzoA);
        CantidadAlmuerzoB = findViewById(R.id.CantidadAlmuerzoB);
        CantidadAlmuerzoC = findViewById(R.id.CantidadAlmuerzoC);
        CantidadAlmuerzoD = findViewById(R.id.CantidadAlmuerzoD);
        ButonPedirAlmuerzo = findViewById(R.id.ButonPedirDesayuno);

        ButonPedirAlmuerzo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tableNumber = txtNumeroMesaAlmuerzo.getText().toString();
                String cenaAQuantity = CantidadAlmuerzoA.getText().toString();
                String cenaBQuantity = CantidadAlmuerzoB.getText().toString();
                String cenaCQuantity = CantidadAlmuerzoC.getText().toString();
                String cenaDQuantity = CantidadAlmuerzoD.getText().toString();


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
        String url = "http://192.168.131.89:8080/androidPHPSQL/agregar_pedido.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(MenuComidasActivity.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {

                    Toast.makeText(MenuComidasActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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


