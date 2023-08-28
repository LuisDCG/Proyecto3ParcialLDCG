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

public class MenuDesayunosActivity extends AppCompatActivity {

    private static final int ANTOJO_REQUEST_CODE = 1;
    private EditText txtNumeroMesaDesayuno;
    private NumberPicker CantidadDesayunoA;
    private NumberPicker CantidadDesayunoB;
    private NumberPicker CantidadDesayunoC;
    private NumberPicker CantidadDesayunoD;
    private Button ButonPedirDesayuno;

    private Button btnRegresar2;
    private int antojoAQuantity = 0;
    private int antojoBQuantity = 0;
    private int antojoCQuantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu1);

        txtNumeroMesaDesayuno = findViewById(R.id.txtNumeroMesaAntojito);
        CantidadDesayunoA = findViewById(R.id. CantidadDesayunoA);
        CantidadDesayunoB = findViewById(R.id. CantidadDesayunoB);
        CantidadDesayunoC = findViewById(R.id. CantidadDesayunoC);
        CantidadDesayunoD = findViewById(R.id. CantidadDesayunoD);
        ButonPedirDesayuno = findViewById(R.id.ButonPedirDesayuno);

        CantidadDesayunoA.setMinValue(0); // Valor mínimo
        CantidadDesayunoA.setMaxValue(100); // Valor máximo
        CantidadDesayunoA.setValue(0); // Valor inicial
        CantidadDesayunoA.setWrapSelectorWheel(false); // Si deseas que se detenga al llegar al límite

        CantidadDesayunoB.setMinValue(0); // Valor mínimo
        CantidadDesayunoB.setMaxValue(100); // Valor máximo
        CantidadDesayunoB.setValue(0); // Valor inicial
        CantidadDesayunoB.setWrapSelectorWheel(false); // Si deseas que se detenga al llegar al límite

        CantidadDesayunoC.setMinValue(0); // Valor mínimo
        CantidadDesayunoC.setMaxValue(100); // Valor máximo
        CantidadDesayunoC.setValue(0); // Valor inicial
        CantidadDesayunoC.setWrapSelectorWheel(false); // Si deseas que se detenga al llegar al límite

        CantidadDesayunoD.setMinValue(0); // Valor mínimo
        CantidadDesayunoD.setMaxValue(100); // Valor máximo
        CantidadDesayunoD.setValue(0); // Valor inicial
        CantidadDesayunoD.setWrapSelectorWheel(false); // Si deseas que se detenga al llegar al límite

        Button botonAntojo = findViewById(R.id.btnantojodesayuno);
        botonAntojo.setOnClickListener(v -> {
            Intent intent = new Intent(MenuDesayunosActivity.this, MenuAntojosActivity.class);
            startActivityForResult(intent, ANTOJO_REQUEST_CODE);
        });

        btnRegresar2 = findViewById(R.id.btnRegresar2);
        btnRegresar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuDesayunosActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button botonMesa = findViewById(R.id.ButonMesas);
        botonMesa.setOnClickListener(v -> {
            Intent intent = new Intent(MenuDesayunosActivity.this, MesasActivity.class);
            startActivity(intent);
        });

        ButonPedirDesayuno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tableNumber = txtNumeroMesaDesayuno.getText().toString();

                int desayunoAQuantity = CantidadDesayunoA.getValue();
                int desayunoBQuantity = CantidadDesayunoB.getValue();
                int desayunoCQuantity = CantidadDesayunoC.getValue();
                int desayunoDQuantity = CantidadDesayunoD.getValue();

                int desayunoAPrecio = 18;
                int desayunoBPrecio = 13;
                int desayunoCPrecio = 25;
                int desayunoDPrecio = 20;

                int antojoAPrecio = 20;
                int antojoBPrecio = 36;
                int antojoCPrecio = 22;

                // Calcula el valor total de las comidas que se seleccionan 4 desayunos
                int totalDesayunoA = desayunoAQuantity * desayunoAPrecio;
                int totalDesayunoB = desayunoBQuantity * desayunoBPrecio;
                int totalDesayunoC = desayunoCQuantity * desayunoCPrecio;
                int totalDesayunoD = desayunoDQuantity * desayunoDPrecio;

                // Calcula el valor total de los antojos que se seleccionan 3 desayunos
                int totalAntojoA = antojoAQuantity * antojoAPrecio;
                int totalAntojoB = antojoBQuantity * antojoBPrecio;
                int totalAntojoC = antojoCQuantity * antojoCPrecio;

                // Envía las cenas seleccionadas al servidor
                if (desayunoAQuantity != 0) {
                    insertData(tableNumber, "Desayuno A", String.valueOf(desayunoAQuantity), String.valueOf(totalDesayunoA));
                }

                if (desayunoBQuantity != 0) {
                    insertData(tableNumber, "Desayuno B", String.valueOf(desayunoBQuantity), String.valueOf(totalDesayunoB));
                }

                if (desayunoCQuantity != 0) {
                    insertData(tableNumber, "Desayuno C", String.valueOf(desayunoCQuantity), String.valueOf(totalDesayunoC));
                }

                if (desayunoDQuantity != 0) {
                    insertData(tableNumber, "Desayuno D", String.valueOf(desayunoDQuantity), String.valueOf(totalDesayunoD));
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

                // Asignar valores a las variables quantity
                int cenaAValue = 18;
                int cenaBValue = 13;
                int cenaCValue = 25;
                int cenaDValue = 20;
                int antojoAValue = 20;
                int antojoBValue = 36;
                int antojoCValue = 22;

                // Multiplicar los valores obtenidos por los valores asignados
                desayunoAQuantity *= cenaAValue;
                desayunoBQuantity *= cenaBValue;
                desayunoCQuantity *= cenaCValue;
                desayunoDQuantity *= cenaDValue;
                antojoAQuantity *= antojoAValue;
                antojoBQuantity *= antojoBValue;
                antojoCQuantity *= antojoCValue;

                // Crear un arreglo para almacenar los valores
                int[] quantityArray = {desayunoAQuantity, desayunoBQuantity, desayunoCQuantity, desayunoDQuantity, antojoAQuantity, antojoBQuantity, antojoCQuantity};

                // Realizar una multiplicación utilizando un bucle
                int multiplicacionTotal = 0;
                for (int quantity : quantityArray) {
                    multiplicacionTotal += quantity;
                }

                // Mostrar el resultado en un Toast
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

                        Toast.makeText(MenuDesayunosActivity.this, "¡TOTAL AGREGADO!", Toast.LENGTH_SHORT).show();

                        /*
                        CantidadCenaA.setValue(CantidadCenaA.getMinValue());
                        CantidadCenaB.setValue(CantidadCenaB.getMinValue());
                        CantidadCenaC.setValue(CantidadCenaC.getMinValue());
                        CantidadCenaD.setValue(CantidadCenaD.getMinValue());
                        editTextNumber.setText(""); */

                    }
                },
                error -> {

                    Toast.makeText(MenuDesayunosActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(MenuDesayunosActivity.this, "¡Pedido realizado!", Toast.LENGTH_SHORT).show();

                        CantidadDesayunoA.setValue(CantidadDesayunoA.getMinValue());
                        CantidadDesayunoB.setValue(CantidadDesayunoB.getMinValue());
                        CantidadDesayunoC.setValue(CantidadDesayunoC.getMinValue());
                        CantidadDesayunoD.setValue(CantidadDesayunoD.getMinValue());
                        txtNumeroMesaDesayuno.setText("");

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
        CantidadDesayunoA.setValue(CantidadDesayunoA.getMinValue());
        CantidadDesayunoB.setValue(CantidadDesayunoB.getMinValue());
        CantidadDesayunoC.setValue(CantidadDesayunoC.getMinValue());
        CantidadDesayunoD.setValue(CantidadDesayunoD.getMinValue());
        txtNumeroMesaDesayuno.setText("");
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
                        Toast.makeText(MenuDesayunosActivity.this, "¡Mesa " + numeroMesa + " Reservada!", Toast.LENGTH_SHORT).show();
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

