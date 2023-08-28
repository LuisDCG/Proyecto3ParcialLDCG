package com.example.proyecto3parcialldcg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MesasActivity extends AppCompatActivity {

    private ArrayList<String> listaMesas;
    private ArrayList<String> listaMesas2;
    private ListView listView;

    private ListView listView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesas);


        listView = findViewById(R.id.listView_estadoMesas);
        listView2 = findViewById(R.id.listView_estadoMesasNO);

        cargarMesasDisponibles();
        cargarMesasNODisponibles();

        Button cambiarVistaMenu2 = findViewById(R.id.btnRegresarMesas);
        cambiarVistaMenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void cargarMesasDisponibles() {
        String url = "http://192.168.1.109:8080/androidPHPSQL/listamesas.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listaMesas = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject pedidoObject = response.getJSONObject(i);
                                int idPedido = pedidoObject.getInt("id");
                                int numeroMesa = pedidoObject.getInt("numeroMesa");
                                int estadoMesa = pedidoObject.getInt("estado");

                                if(estadoMesa==0){
                                    listaMesas.add(idPedido + " | " + " - Mesa: " + numeroMesa + " - ¡Mesa Libre! ");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MesasActivity.this, android.R.layout.simple_list_item_1, listaMesas);
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


    private void cargarMesasNODisponibles() {
        String url = "http://192.168.1.109:8080/androidPHPSQL/listamesas.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listaMesas2 = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject pedidoObject = response.getJSONObject(i);
                                int idPedido = pedidoObject.getInt("id");
                                int numeroMesa = pedidoObject.getInt("numeroMesa");
                                int estadoMesa = pedidoObject.getInt("estado");

                                if(estadoMesa==1){
                                    listaMesas2.add(idPedido + " | " + " - Mesa: " + numeroMesa + " - ¡Mesa Reservada! ");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MesasActivity.this, android.R.layout.simple_list_item_1, listaMesas2);
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

}