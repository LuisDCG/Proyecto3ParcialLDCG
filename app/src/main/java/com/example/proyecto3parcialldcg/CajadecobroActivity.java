package com.example.proyecto3parcialldcg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CajadecobroActivity extends AppCompatActivity {

    private ListView listView5;
    private ArrayList<String> listaPedidos2;
    private ArrayAdapter<String> adapter;

    private EditText txtNumeroMesaCajadeCobro;

    private TextView textView_totalordeneslistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistacajadecobro);

        listView5 = findViewById(R.id.listView_totalesPedidos);

        txtNumeroMesaCajadeCobro = findViewById(R.id.txtNumeroMesaCajadeCobro);
        textView_totalordeneslistview = findViewById(R.id.textView_totalordeneslistview);


        Button botonBuscarCaja = findViewById(R.id.BotonCajaBuscar);
        botonBuscarCaja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarPedidos();

            }
        });


        Button botonCobrarCaja = findViewById(R.id.BotonCobrar);
        botonCobrarCaja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String tableNumber = txtNumeroMesaCajadeCobro.getText().toString();
                //int numeroMesa = Integer.parseInt(tableNumber);
                int numeroMesa = Integer.parseInt(tableNumber);
                actualizarEstadoMesa2(numeroMesa);
                eliminarOrdenesDeMesa(numeroMesa);
                //limpiar viewlist y edittext
                resetControls();

            }
        });


        Button cambiarVistaPrincipal = findViewById(R.id.btnRegresarCajadecobro);
        cambiarVistaPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }



    private void cargarPedidos() {
        String url = "http://192.168.1.109:8080/androidPHPSQL/listapedidos.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        final EditText editTextNumeroMesa = findViewById(R.id.txtNumeroMesaCajadeCobro); // Obtener la referencia al EditText

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listaPedidos2 = new ArrayList<>();
                        int numeroMesaFiltrado = Integer.parseInt(editTextNumeroMesa.getText().toString()); // Obtener el número de mesa ingresado

                        int totalSum = 0;

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject pedidoObject = response.getJSONObject(i);
                                int idPedido = pedidoObject.getInt("id");
                                String nombrePedido = pedidoObject.getString("NombrePedido");
                                int cantidad = pedidoObject.getInt("cantidad");
                                int numeroMesa = pedidoObject.getInt("numeroMesa");
                                int precioPedido = pedidoObject.getInt("totalPedido");
                                int estadoPedido = pedidoObject.getInt("estado");

                                if (estadoPedido == 3 && numeroMesa == numeroMesaFiltrado) {
                                    //listaPedidos.add(idPedido + " | " + nombrePedido + " - Cantidad: " + cantidad + " - Mesa: " + numeroMesa + " - Precio: " + precioPedido); //original
                                    listaPedidos2.add(nombrePedido + " - Cant: " + cantidad + " - Mesa: " + numeroMesa + " | Precio: $" + precioPedido);
                                    totalSum += precioPedido; // Agregar el precio al total
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(CajadecobroActivity.this, android.R.layout.simple_list_item_1, listaPedidos2);
                        listView5.setAdapter(arrayAdapter);
                        TextView textViewTotal = findViewById(R.id.textView_totalordeneslistview); // Cambia "textViewTotal" por el ID correcto de tu TextView
                        textViewTotal.setText("Total: $" + totalSum);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar el error... por el momento esperamos
                    }
                }
        );

        queue.add(jsonArrayRequest);
    }




    private void eliminarOrdenesDeMesa(final int numeroMesa) {
        String url = "http://192.168.1.109:8080/androidPHPSQL/pedido_eliminar.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest resultadoPost = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(CajadecobroActivity.this, "¡Su cuenta ha sido cobrada!", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CajadecobroActivity.this, "error " + error, Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("numeroMesa", String.valueOf(numeroMesa));
                return params;
            }
        };

        queue.add(resultadoPost);

    }

    private void actualizarEstadoMesa2(int numeroMesa) {

        String SERVER_URL = "http://192.168.1.109:8080/androidPHPSQL/actualizarestado_mesasSI.php";

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
                        Toast.makeText(CajadecobroActivity.this, "¡Mesa " + numeroMesa + " Liberada!", Toast.LENGTH_SHORT).show();
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


    private void resetControls() {
        listView5 = findViewById(R.id.listView_totalesPedidos);
        ArrayList<String> listaDeElementos = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaDeElementos);
        listView5.setAdapter(adapter);
        txtNumeroMesaCajadeCobro.setText("");
        txtNumeroMesaCajadeCobro.setHint("Número de mesa");
        textView_totalordeneslistview.setText("");
    }

    private void limpiarListView() {
        adapter.clear();
    }

}


