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

public class MenuComidasActivity extends AppCompatActivity {

    private static final int ANTOJO_REQUEST_CODE = 1;

    private NumberPicker CantidadComidaA;
    private NumberPicker CantidadComidaB;
    private NumberPicker CantidadComidaC;
    private NumberPicker CantidadComidaD;

    private EditText txtNumeroMesaComida;
    private Button ButonPedirComida;

    private Button btnRegresar2;
    private int antojoAQuantity = 0;
    private int antojoBQuantity = 0;
    private int antojoCQuantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu2);


        Button botonAntojo = findViewById(R.id.btnantojitoalmuerzo);
        botonAntojo.setOnClickListener(v -> {
            Intent intent = new Intent(MenuComidasActivity.this, MenuAntojosActivity.class);
            startActivityForResult(intent, ANTOJO_REQUEST_CODE);
        });

        Button botonMesa = findViewById(R.id.ButonMesas);
        botonMesa.setOnClickListener(v -> {
            Intent intent = new Intent(MenuComidasActivity.this, MesasActivity.class);
            startActivity(intent);
        });

        btnRegresar2 = findViewById(R.id.btnRegresar2);
        btnRegresar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuComidasActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        txtNumeroMesaComida = findViewById(R.id.txtNumeroMesaComida);
        CantidadComidaA = findViewById(R.id.CantidadComidaA);
        CantidadComidaB = findViewById(R.id.CantidadComidaB);
        CantidadComidaC = findViewById(R.id.CantidadComidaC);
        CantidadComidaD = findViewById(R.id.CantidadComidaD);
        ButonPedirComida = findViewById(R.id.ButonPedirComida);

        CantidadComidaA.setMinValue(0); // Valor mínimo
        CantidadComidaA.setMaxValue(100); // Valor máximo
        CantidadComidaA.setValue(0); // Valor inicial
        CantidadComidaA.setWrapSelectorWheel(false); // Si deseas que se detenga al llegar al límite

        CantidadComidaB.setMinValue(0); // Valor mínimo
        CantidadComidaB.setMaxValue(100); // Valor máximo
        CantidadComidaB.setValue(0); // Valor inicial
        CantidadComidaB.setWrapSelectorWheel(false); // Si deseas que se detenga al llegar al límite

        CantidadComidaC.setMinValue(0); // Valor mínimo
        CantidadComidaC.setMaxValue(100); // Valor máximo
        CantidadComidaC.setValue(0); // Valor inicial
        CantidadComidaC.setWrapSelectorWheel(false); // Si deseas que se detenga al llegar al límite

        CantidadComidaD.setMinValue(0); // Valor mínimo
        CantidadComidaD.setMaxValue(100); // Valor máximo
        CantidadComidaD.setValue(0); // Valor inicial
        CantidadComidaD.setWrapSelectorWheel(false); // Si deseas que se detenga al llegar al límite



        ButonPedirComida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tableNumber = txtNumeroMesaComida.getText().toString();

                int comidaAQuantity = CantidadComidaA.getValue();
                int comidaBQuantity = CantidadComidaB.getValue();
                int comidaCQuantity = CantidadComidaC.getValue();
                int comidaDQuantity = CantidadComidaD.getValue();

                int comidaAPrecio = 40;
                int comidaBPrecio = 33;
                int comidaCPrecio = 60;
                int comidaDPrecio = 54;

                int antojoAPrecio = 20;
                int antojoBPrecio = 36;
                int antojoCPrecio = 22;

                // Calcula el valor total de las comidas que se seleccionan 4 COMIDAS
                int totalComidaA = comidaAQuantity * comidaAPrecio;
                int totalComidaB = comidaBQuantity * comidaBPrecio;
                int totalComidaC = comidaCQuantity * comidaCPrecio;
                int totalComidaD = comidaDQuantity * comidaDPrecio;

                // Calcula el valor total de los antojos que se seleccionan 3 ANTOJOS
                int totalAntojoA = antojoAQuantity * antojoAPrecio;
                int totalAntojoB = antojoBQuantity * antojoBPrecio;
                int totalAntojoC = antojoCQuantity * antojoCPrecio;



                // Envía las comidas seleccionadas al servidor
                if (comidaAQuantity != 0) {
                    insertData(tableNumber, "Comida A", String.valueOf(comidaAQuantity), String.valueOf(totalComidaA));
                }
                if (comidaBQuantity != 0) {
                    insertData(tableNumber, "Comida B", String.valueOf(comidaBQuantity), String.valueOf(totalComidaB));
                }
                if (comidaCQuantity != 0) {
                    insertData(tableNumber, "Comida C", String.valueOf(comidaCQuantity), String.valueOf(totalComidaC));
                }
                if (comidaDQuantity != 0) {
                    insertData(tableNumber, "Comida D", String.valueOf(comidaDQuantity), String.valueOf(totalComidaD));
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

                int comidaAValue = 40; // Debes cambiar estos valores a los correctos
                int comidaBValue = 33;
                int comidaCValue = 60;
                int comidaDValue = 54;
                int antojoAValue = 20;
                int antojoBValue = 36;
                int antojoCValue = 22;

                // Calcula el total
                comidaAQuantity *= comidaAValue;
                comidaBQuantity *= comidaBValue;
                comidaCQuantity *= comidaCValue;
                comidaDQuantity *= comidaDValue;
                antojoAQuantity *= antojoAValue;
                antojoBQuantity *= antojoBValue;
                antojoCQuantity *= antojoCValue;

                int[] quantityArray = {comidaAQuantity, comidaBQuantity, comidaCQuantity, comidaDQuantity, antojoAQuantity, antojoBQuantity, antojoCQuantity};

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

                        Toast.makeText(MenuComidasActivity.this, "¡TOTAL AGREGADO!", Toast.LENGTH_SHORT).show();

                        /*
                        CantidadCenaA.setValue(CantidadCenaA.getMinValue());
                        CantidadCenaB.setValue(CantidadCenaB.getMinValue());
                        CantidadCenaC.setValue(CantidadCenaC.getMinValue());
                        CantidadCenaD.setValue(CantidadCenaD.getMinValue());
                        editTextNumber.setText(""); */

                    }
                },
                error -> {

                    Toast.makeText(MenuComidasActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(MenuComidasActivity.this, "¡Pedido de " + nombrePedido + " realizado!", Toast.LENGTH_SHORT).show();
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
        CantidadComidaA.setValue(CantidadComidaA.getMinValue());
        CantidadComidaB.setValue(CantidadComidaB.getMinValue());
        CantidadComidaC.setValue(CantidadComidaC.getMinValue());
        CantidadComidaD.setValue(CantidadComidaD.getMinValue());
        txtNumeroMesaComida.setText("");
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
                        Toast.makeText(MenuComidasActivity.this, "¡Mesa " + numeroMesa + " Reservada!", Toast.LENGTH_SHORT).show();
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