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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MenuCenasActivity extends AppCompatActivity {

    private static final int ANTOJO_REQUEST_CODE = 1;

    private EditText editTextNumber;
    private NumberPicker CantidadCenaA;
    private NumberPicker CantidadCenaB;
    private NumberPicker CantidadCenaC;
    private NumberPicker CantidadCenaD;
    private Button buttonSubmit;
    private Button btnRegresar2;
    private int antojoAQuantity = 0;
    private int antojoBQuantity = 0;
    private int antojoCQuantity = 0;
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
            startActivityForResult(intent, ANTOJO_REQUEST_CODE);
        });

        Button botonMesa = findViewById(R.id.ButonMesas);
        botonMesa.setOnClickListener(v -> {
            Intent intent = new Intent(MenuCenasActivity.this, MesasActivity.class);
            startActivity(intent);
        });

        btnRegresar2 = findViewById(R.id.btnRegresar2);
        btnRegresar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuCenasActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tableNumber = editTextNumber.getText().toString();

                int cenaAQuantity = CantidadCenaA.getValue();
                int cenaBQuantity = CantidadCenaB.getValue();
                int cenaCQuantity = CantidadCenaC.getValue();
                int cenaDQuantity = CantidadCenaD.getValue();

                int cenaAPrecio = 35;
                int cenaBPrecio = 50;
                int cenaCPrecio = 37;
                int cenaDPrecio = 25;

                int antojoAPrecio = 20;
                int antojoBPrecio = 36;
                int antojoCPrecio = 22;

                // Calcula el valor total de las comidas que se seleccionan 4 COMIDAS
                int totalCenaA = cenaAQuantity * cenaAPrecio;
                int totalCenaB = cenaBQuantity * cenaBPrecio;
                int totalCenaC = cenaCQuantity * cenaCPrecio;
                int totalCenaD = cenaDQuantity * cenaDPrecio;

                // Calcula el valor total de los antojos que se seleccionan 3 ANTOJOS
                int totalAntojoA = antojoAQuantity * antojoAPrecio;
                int totalAntojoB = antojoBQuantity * antojoBPrecio;
                int totalAntojoC = antojoCQuantity * antojoCPrecio;

                // Envía las cenas seleccionadas al servidor
                if (cenaAQuantity != 0) {
                    insertData(tableNumber, "Cena A", String.valueOf(cenaAQuantity), String.valueOf(totalCenaA));
                }
                if (cenaBQuantity != 0) {
                    insertData(tableNumber, "Cena B", String.valueOf(cenaBQuantity), String.valueOf(totalCenaB));
                }
                if (cenaCQuantity != 0) {
                    insertData(tableNumber, "Cena C", String.valueOf(cenaCQuantity), String.valueOf(totalCenaC));
                }
                if (cenaDQuantity != 0) {
                    insertData(tableNumber, "Cena D", String.valueOf(cenaDQuantity), String.valueOf(totalCenaD));
                }

                // Envía los antojitos seleccionados al servidor
                if (antojoAQuantity != 0) {
                    insertData(tableNumber, "Antojo A", String.valueOf(antojoAQuantity), String.valueOf(totalAntojoA));
                }
                if (antojoBQuantity != 0) {
                    insertData(tableNumber, "Antojo B", String.valueOf(antojoBQuantity), String.valueOf(totalAntojoB));
                }
                if (antojoCQuantity != 0) {
                    insertData(tableNumber, "Antojo C", String.valueOf(antojoCQuantity), String.valueOf(totalAntojoC));
                }

                int cenaAValue = 35;
                int cenaBValue = 50;
                int cenaCValue = 37;
                int cenaDValue = 25;
                int antojoAValue = 20;
                int antojoBValue = 36;
                int antojoCValue = 22;

                // Calcula el total
                cenaAQuantity *= cenaAValue;
                cenaBQuantity *= cenaBValue;
                cenaCQuantity *= cenaCValue;
                cenaDQuantity *= cenaDValue;
                antojoAQuantity *= antojoAValue;
                antojoBQuantity *= antojoBValue;
                antojoCQuantity *= antojoCValue;

                int[] quantityArray = {cenaAQuantity, cenaBQuantity, cenaCQuantity, cenaDQuantity, antojoAQuantity, antojoBQuantity, antojoCQuantity};

                int multiplicacionTotal = 0;
                for (int quantity : quantityArray) {
                    multiplicacionTotal += quantity;
                }

                String mensaje = "Total del Pedido: $" + multiplicacionTotal;
                Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();

                if (multiplicacionTotal != 0) {
                    insertData2(String.valueOf(multiplicacionTotal));
                }
                resetControls();

                int numeroMesa = Integer.parseInt(tableNumber);
                actualizarEstadoMesa(numeroMesa);
            }
        });


    }


    private void insertData2(final String tableNumber2) {
        String url = "http://192.168.1.109:8080/androidPHPSQL/agregar_total.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(MenuCenasActivity.this, "¡TOTAL AGREGADO!", Toast.LENGTH_SHORT).show();

                        /*
                        CantidadCenaA.setValue(CantidadCenaA.getMinValue());
                        CantidadCenaB.setValue(CantidadCenaB.getMinValue());
                        CantidadCenaC.setValue(CantidadCenaC.getMinValue());
                        CantidadCenaD.setValue(CantidadCenaD.getMinValue());
                        editTextNumber.setText(""); */

                    }
                },
                error -> {

                    Toast.makeText(MenuCenasActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("total", tableNumber2);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }




    private void insertData(final String tableNumber, final String nombrePedido, final String cantidad, final String precio) {
        String url = "http://192.168.1.109:8080/androidPHPSQL/agregar_pedido.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MenuCenasActivity.this, "¡Pedido de " + nombrePedido + " realizado!", Toast.LENGTH_SHORT).show();
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
                params.put("totalPedido", precio);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ANTOJO_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                antojoAQuantity = data.getIntExtra("antojoA", 0);
                antojoBQuantity = data.getIntExtra("antojoB", 0);
                antojoCQuantity = data.getIntExtra("antojoC", 0);
            }
        }
    }

    private void resetControls() {
        CantidadCenaA.setValue(CantidadCenaA.getMinValue());
        CantidadCenaB.setValue(CantidadCenaB.getMinValue());
        CantidadCenaC.setValue(CantidadCenaC.getMinValue());
        CantidadCenaD.setValue(CantidadCenaD.getMinValue());
        editTextNumber.setText("");
    }


    private void actualizarEstadoMesa(int numeroMesa) {

        String SERVER_URL = "http://192.168.1.109:8080/androidPHPSQL/actualizarestado_mesasNO.php";

        // Construir la URL con el número de mesa
        String url = SERVER_URL + "?numeroMesa=" + numeroMesa;

        // Crear una cola de solicitudes Volley
        RequestQueue queue = Volley.newRequestQueue(this);

        // Crear la solicitud HTTP GET
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Manejar la respuesta del servidor si es necesario
                        Toast.makeText(MenuCenasActivity.this, "¡Mesa " + numeroMesa + " Reservada!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar el error
                    }
                });

        // Agregar la solicitud a la cola
        queue.add(stringRequest);
    }




}