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
    private NumberPicker editTextA;
    private NumberPicker editTextB;
    private NumberPicker editTextC;
    private NumberPicker editTextD;
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

        editTextA.setMinValue(0); // Valor mínimo
        editTextA.setMaxValue(100); // Valor máximo
        editTextA.setValue(0); // Valor inicial
        editTextA.setWrapSelectorWheel(false); // Si deseas que se detenga al llegar al límite

        editTextB.setMinValue(0); // Valor mínimo
        editTextB.setMaxValue(100); // Valor máximo
        editTextB.setValue(0); // Valor inicial
        editTextB.setWrapSelectorWheel(false); // Si deseas que se detenga al llegar al límite

        editTextC.setMinValue(0); // Valor mínimo
        editTextC.setMaxValue(100); // Valor máximo
        editTextC.setValue(0); // Valor inicial
        editTextC.setWrapSelectorWheel(false); // Si deseas que se detenga al llegar al límite

        editTextD.setMinValue(0); // Valor mínimo
        editTextD.setMaxValue(100); // Valor máximo
        editTextD.setValue(0); // Valor inicial
        editTextD.setWrapSelectorWheel(false); // Si deseas que se detenga al llegar al límite

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

                int cenaAQuantity = editTextA.getValue();
                int cenaBQuantity = editTextA.getValue();
                int cenaCQuantity = editTextA.getValue();
                int cenaDQuantity = editTextA.getValue();

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

                        Toast.makeText(MenuCenasActivity.this, "Pedido realizado!", Toast.LENGTH_SHORT).show();

                        editTextA.setValue(editTextA.getMinValue());
                        editTextB.setValue(editTextA.getMinValue());
                        editTextC.setValue(editTextA.getMinValue());
                        editTextD.setValue(editTextA.getMinValue());
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