package com.example.proyecto3parcialldcg;

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


public class MenuAntojosActivity extends AppCompatActivity {
    private EditText cantidadAntojoA;
    private EditText cantidadAntojoB;
    private EditText cantidadAntojoC;
    private EditText txtNumeroMesaAntojito;
    private Button ButonPedirAntojo;

    private Button btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuantojos);

        txtNumeroMesaAntojito = findViewById(R.id.txtNumeroMesaAntojito);
        cantidadAntojoA = findViewById(R.id.txtAntojitoA);
        cantidadAntojoB = findViewById(R.id.txtAntojitoB);
        cantidadAntojoC = findViewById(R.id.txtAntojitoC);
        ButonPedirAntojo = findViewById(R.id.btnSolicitarAntojito);

        Button btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ButonPedirAntojo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tableNumber = txtNumeroMesaAntojito.getText().toString();
                String antojoAcantidad = cantidadAntojoA.getText().toString();
                String antojoBcantidad  = cantidadAntojoB.getText().toString();
                String antojoCcantidad  = cantidadAntojoC.getText().toString();


                int antojoA = Integer.parseInt(antojoAcantidad);
                int antojoB = Integer.parseInt(antojoBcantidad);
                int antojoC = Integer.parseInt(antojoCcantidad);

                if (antojoA != 0) {
                    insertData(tableNumber, "AntojitoA", antojoAcantidad );
                }

                if (antojoB != 0) {
                    insertData(tableNumber, "AntojitoB", antojoBcantidad );
                }

                if (antojoC != 0) {
                    insertData(tableNumber, "AntojitoC", antojoCcantidad );
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

                        Toast.makeText(MenuAntojosActivity.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {

                    Toast.makeText(MenuAntojosActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
