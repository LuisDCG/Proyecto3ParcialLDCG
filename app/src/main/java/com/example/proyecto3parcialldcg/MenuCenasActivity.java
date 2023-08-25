package com.example.proyecto3parcialldcg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
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
    private NumberPicker CantidadCenaA;
    private NumberPicker CantidadCenaB;
    private NumberPicker CantidadCenaC;
    private NumberPicker CantidadCenaD;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu3);


        editTextNumber = findViewById(R.id.txtNumeroMesaAntojito);
        CantidadCenaA = findViewById(R.id.CantidadAlmuerzoA);
        CantidadCenaB = findViewById(R.id.CantidadAlmuerzoB);
        CantidadCenaC = findViewById(R.id.CantidadAlmuerzoC);
        CantidadCenaD = findViewById(R.id.CantidadAlmuerzoD);
        buttonSubmit = findViewById(R.id.ButonPedirDesayuno);

        CantidadCenaA.setMinValue(0); // Valor mínimo
        CantidadCenaA.setMaxValue(100); // Valor máximo
        CantidadCenaA.setValue(0); // Valor inicial
        CantidadCenaA.setWrapSelectorWheel(false); // Si deseas que se detenga al llegar al límite

        CantidadCenaB.setMinValue(0); // Valor mínimo
        CantidadCenaB.setMaxValue(100); // Valor máximo
        CantidadCenaB.setValue(0); // Valor inicial
        CantidadCenaB.setWrapSelectorWheel(false); // Si deseas que se detenga al llegar al límite

        CantidadCenaC.setMinValue(0); // Valor mínimo
        CantidadCenaC.setMaxValue(100); // Valor máximo
        CantidadCenaC.setValue(0); // Valor inicial
        CantidadCenaC.setWrapSelectorWheel(false); // Si deseas que se detenga al llegar al límite

        CantidadCenaD.setMinValue(0); // Valor mínimo
        CantidadCenaD.setMaxValue(100); // Valor máximo
        CantidadCenaD.setValue(0); // Valor inicial
        CantidadCenaD.setWrapSelectorWheel(false); // Si deseas que se detenga al llegar al límite

        Button botonAntojo = findViewById(R.id.botonantojo);
        botonAntojo.setOnClickListener(v -> {
            Intent intent = new Intent(MenuCenasActivity.this, MenuAntojosActivity.class);
            startActivity(intent);
        });


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tableNumber = editTextNumber.getText().toString();
                //String cenaAQuantity = editTextA.getText().toString();

                //String cenaBQuantity = editTextB.getText().toString();
                //String cenaCQuantity = editTextC.getText().toString();
                //String cenaDQuantity = editTextD.getText().toString();

                int cenaAQuantity = CantidadCenaA.getValue();
                int cenaBQuantity = CantidadCenaB.getValue();
                int cenaCQuantity = CantidadCenaC.getValue();
                int cenaDQuantity = CantidadCenaD.getValue();

                //int cenaA = Integer.parseInt(cenaAQuantity);
                //int cenaB = Integer.parseInt(cenaBQuantity);
                //int cenaC = Integer.parseInt(cenaCQuantity);
                //int cenaD = Integer.parseInt(cenaDQuantity);

                if (cenaAQuantity != 0) {
                    insertData(tableNumber, "Cena A", String.valueOf(cenaAQuantity));
                }

                if (cenaBQuantity != 0) {
                    insertData(tableNumber, "Cena B", String.valueOf(cenaBQuantity));
                }

                if (cenaCQuantity != 0) {
                    insertData(tableNumber, "Cena C", String.valueOf(cenaCQuantity));
                }

                if (cenaDQuantity != 0) {
                    insertData(tableNumber, "Cena D", String.valueOf(cenaDQuantity));
                }
            }
        });



    }

    private void insertData(final String tableNumber, final String nombrePedido, final String cantidad) {
        String url = "http://192.168.1.109:8080/androidPHPSQL/agregar_pedido.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(MenuCenasActivity.this, "¡Pedido realizado!", Toast.LENGTH_SHORT).show();

                        CantidadCenaA.setValue(CantidadCenaA.getMinValue());
                        CantidadCenaB.setValue(CantidadCenaB.getMinValue());
                        CantidadCenaC.setValue(CantidadCenaC.getMinValue());
                        CantidadCenaD.setValue(CantidadCenaD.getMinValue());
                        editTextNumber.setText("");

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