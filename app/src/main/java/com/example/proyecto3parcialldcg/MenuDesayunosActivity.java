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

public class MenuDesayunosActivity extends AppCompatActivity {
    private EditText txtNumeroMesaDesayuno;
    private EditText DesayunoA;
    private EditText DesayunoB;
    private EditText DesayunoC;
    private EditText DesayunoD;
    private Button ButonPedirDesayuno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu1);
        Button botonAntojo = findViewById(R.id.btnantojodesayuno);
        botonAntojo.setOnClickListener(v -> {
            Intent intent = new Intent(MenuDesayunosActivity.this, MenuAntojosActivity.class);
            startActivity(intent);
        });
        txtNumeroMesaDesayuno = findViewById(R.id.txtNumeroMesaAntojito);
        DesayunoA = findViewById(R.id. DesayunoA);
        DesayunoB = findViewById(R.id. DesayunoB);
        DesayunoC = findViewById(R.id. DesayunoC);
        DesayunoD = findViewById(R.id. DesayunoD);
        ButonPedirDesayuno = findViewById(R.id.ButonPedirDesayuno);

        ButonPedirDesayuno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tableNumber = txtNumeroMesaDesayuno.getText().toString();
                String desayunoAQuantity = DesayunoA.getText().toString();
                String desayunoBQuantity = DesayunoB.getText().toString();
                String desayunoCQuantity = DesayunoC.getText().toString();
                String desayunoDQuantity = DesayunoD.getText().toString();


                int desayunoA = Integer.parseInt(desayunoAQuantity);
                int desayunoB = Integer.parseInt(desayunoBQuantity);
                int desayunoC = Integer.parseInt(desayunoCQuantity);
                int desayunoD = Integer.parseInt(desayunoDQuantity);

                if (desayunoA != 0) {
                    insertData(tableNumber, "Desayuno A", desayunoAQuantity);
                }

                if (desayunoB != 0) {
                    insertData(tableNumber, "Desayuno B", desayunoBQuantity);
                }

                if (desayunoC != 0) {
                    insertData(tableNumber, "Desayuno C", desayunoCQuantity);
                }

                if (desayunoD != 0) {
                    insertData(tableNumber, "Desayuno D", desayunoDQuantity);
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
                        Toast.makeText(MenuDesayunosActivity.this, "Orden pedida con exito", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(MenuDesayunosActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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

