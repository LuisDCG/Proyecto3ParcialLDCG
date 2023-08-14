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
import java.util.HashMap;
import java.util.Map;


public class MeseroActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> listaPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistamesero);

        String nombreMesero = getIntent().getStringExtra("nombreMesero");
        listView = findViewById(R.id.listView_pedidos);
        cargarPedidos();

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


        Button butonRegistrar = findViewById(R.id.btnCambiarContrasena);
        butonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeseroActivity.this, CambiarContraActivity.class);
                intent.putExtra("nombreMesero", nombreMesero); // Envía el nombre del mesero a la otra actividad
                startActivity(intent);
            }
        });

    }




    private void actualizarEstadoPedido(int idPedido) {
        String url = "http://192.168.10.252:8080/androidPHPSQL/actualizarEstadoPedido.php";
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
        String url = "http://192.168.10.252:8080/androidPHPSQL/aceptarPedido.php";
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
        String url = "http://192.168.10.252:8080/androidPHPSQL/listapedidos.php";
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

}