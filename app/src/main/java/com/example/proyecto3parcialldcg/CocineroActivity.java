package com.example.proyecto3parcialldcg;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CocineroActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> listaPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistacocinero);

        // Recuperar el nombre del cocinero desde el intento
        String nombreCocinero = getIntent().getStringExtra("nombreCocinero");

        listView = findViewById(R.id.listView_pedidosCocinero);
        cargarPedidosCocinero();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String pedidoSeleccionado = listaPedidos.get(position);
            int idPedido = Integer.parseInt(pedidoSeleccionado.split(" \\| ")[0]);
            procesarPedidoCocinero(idPedido);
        });

    }


    private void cargarPedidosCocinero() {
        String url = "http://192.168.10.252:8080/androidPHPSQL/obtenerPedidosCocinero.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    listaPedidos = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject pedidoObject = response.getJSONObject(i);
                            int idPedido = pedidoObject.getInt("id");
                            String nombrePedido = pedidoObject.getString("NombrePedido");
                            int cantidad = pedidoObject.getInt("cantidad");
                            int numeroMesa = pedidoObject.getInt("numeroMesa");
                            listaPedidos.add(idPedido + " | " + nombrePedido + " - Cantidad: " + cantidad + " - Mesa: " + numeroMesa);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(CocineroActivity.this, android.R.layout.simple_list_item_1, listaPedidos);
                    listView.setAdapter(arrayAdapter);
                },
                error -> {
                    // Manejar el error
                }
        );

        queue.add(jsonArrayRequest);
    }

    private void procesarPedidoCocinero(int idPedido) {
        String url = "http://192.168.10.252:8080/androidPHPSQL/procesarPedidoCocinero.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> cargarPedidosCocinero(),
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
}






