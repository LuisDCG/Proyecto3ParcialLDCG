package com.example.proyecto3parcialldcg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    // Declaración de variables
    private ListView myListView;
    private ArrayList<String> listaItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Inicializar la lista y agregar algunos elementos de ejemplo
        myListView = findViewById(R.id.myListView);
        listaItems = new ArrayList<>();
        listaItems.add("Item 1");
        listaItems.add("Item 2");
        listaItems.add("Item 3");

        // Crear el adaptador para la lista y asignarlo
        CustomListAdapter adapter = new CustomListAdapter(this, listaItems);
        myListView.setAdapter(adapter);

        // Configurar el botón para mostrar un mensaje cuando se haga clic
        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity2.this, "Botón Atrás presionado", Toast.LENGTH_SHORT).show();
                // Aquí puedes agregar el código para manejar el evento de clic del botón Atrás
                // Por ejemplo, puedes cerrar la actividad o realizar alguna otra acción.
            }
        });
    }

}


