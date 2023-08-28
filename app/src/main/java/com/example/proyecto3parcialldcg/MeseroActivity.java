package com.example.proyecto3parcialldcg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class MeseroActivity extends AppCompatActivity {

    private ListView listView;

    private ListView listView2;

    private ListView listView3;
    private ArrayList<String> listaPedidos;

    private ArrayList<String> listaPedidos2;

    private Button btnRegresar2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistamesero);

        String nombreMesero = getIntent().getStringExtra("nombreMesero");
        listView = findViewById(R.id.listView_pedidos);
        listView2 = findViewById(R.id.listView_totales);
        listView3 = findViewById(R.id.listView_ordeneslistas);
        cargarPedidos(); //LLAMA AL METODO PARA QUE SE EJECUTE
        cargarTotal();
        cargarPedidosMesero();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pedidoSeleccionado = listaPedidos.get(position);
                int idPedido = Integer.parseInt(pedidoSeleccionado.split(" \\| ")[0]);

                // Actualizar estado
                actualizarEstadoPedido(idPedido);
                // Agregar datos en AceptarPedidoMesero
                aceptarPedido(idPedido, nombreMesero);
            }
        });

        listView3.setOnItemClickListener((parent, view, position, id) -> {
            String pedidoSeleccionado2 = listaPedidos2.get(position);
            int idPedido3 = Integer.parseInt(pedidoSeleccionado2.split(" \\| ")[0]);
            procesarPedidoMesero(idPedido3);
        });

        Button butonRegistrar = findViewById(R.id.btnCambiarContrasena);
        butonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeseroActivity.this, CambiarContraActivity.class);
                intent.putExtra("nombreMesero", nombreMesero); // Envía el nombre del mesero a la otra actividad
                startActivity(intent);
            }
        });

        btnRegresar2 = findViewById(R.id.btnRegresar2);
        btnRegresar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeseroActivity.this, InicioActivity.class);
                startActivity(intent);
            }
        });
    }

    private void actualizarEstadoPedido(int idPedido) {
        String url = "http://192.168.1.109:8080/androidPHPSQL/actualizarEstadoPedido.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> cargarPedidos(), // Recargamos los pedidos después de actualizar
                error -> { /* Manejar el error */ }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idPedido", String.valueOf(idPedido));
                return params;
            }
        };

        Volley.newRequestQueue(this).add(postRequest);
    }


    private void aceptarPedido(int idPedido, String nombreMesero) {
        String url = "http://192.168.1.109:8080/androidPHPSQL/aceptarPedido.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> { /* No es necesario manejar respuesta :) Giño de Luis demetrio */ },
                error -> { /* Manejar el error */ }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idPedido", String.valueOf(idPedido));
                params.put("nombreMesero", nombreMesero);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(postRequest);
    }


    private void cargarPedidos() {
        String url = "http://192.168.1.109:8080/androidPHPSQL/listapedidos.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listaPedidos = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject pedidoObject = response.getJSONObject(i);
                                int idPedido = pedidoObject.getInt("id");
                                String nombrePedido = pedidoObject.getString("NombrePedido");
                                int cantidad = pedidoObject.getInt("cantidad");
                                int numeroMesa = pedidoObject.getInt("numeroMesa");
                                int estadoPedido = pedidoObject.getInt("estado");

                                if(estadoPedido==0){
                                    listaPedidos.add(idPedido +" | "+nombrePedido + " - Cantidad: " + cantidad + " - Mesa: " + numeroMesa);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MeseroActivity.this, android.R.layout.simple_list_item_1, listaPedidos);
                        listView.setAdapter(arrayAdapter);
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


    private void cargarTotal() {
        String url = "http://192.168.1.109:8080/androidPHPSQL/listatotal.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int lastTotal = 0; // Variable para almacenar el último total
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject totalObject = response.getJSONObject(i);
                                int idTotal = totalObject.getInt("id");
                                int total = totalObject.getInt("total");

                                if (idTotal > 0) {
                                    lastTotal = total; // Actualizar el último total
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // Mostrar el último total en la lista
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                                MeseroActivity.this,
                                android.R.layout.simple_list_item_1,
                                Collections.singletonList("El total del pedido anterior es: $" + lastTotal)
                        );
                        listView2.setAdapter(arrayAdapter);
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

    private void cargarPedidosMesero() {
        String url = "http://192.168.1.109:8080/androidPHPSQL/obtenerPedidosMesero.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    listaPedidos2 = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject pedidoObject = response.getJSONObject(i);
                            int idPedido = pedidoObject.getInt("id");
                            String nombrePedido = pedidoObject.getString("NombrePedido");
                            int cantidad = pedidoObject.getInt("cantidad");
                            int numeroMesa = pedidoObject.getInt("numeroMesa");
                            listaPedidos2.add(idPedido + " | " + nombrePedido + "| Cantidad: " + cantidad + "| Mesa: " + numeroMesa + " ORDEN LISTA");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MeseroActivity.this, android.R.layout.simple_list_item_1, listaPedidos2);
                    listView3.setAdapter(arrayAdapter);
                },
                error -> {
                    // Manejar el error
                }
        );

        queue.add(jsonArrayRequest);
    }


    private void procesarPedidoMesero(int idPedido2) {
        String url = "http://192.168.1.109:8080/androidPHPSQL/procesarPedidoMesero.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> cargarPedidosMesero(),
                error -> { /* Manejar el error */ }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idPedido", String.valueOf(idPedido2));
                return params;
            }
        };

        Volley.newRequestQueue(this).add(postRequest);
    }


}




