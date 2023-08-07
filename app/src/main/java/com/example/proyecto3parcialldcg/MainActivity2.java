package com.example.proyecto3parcialldcg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {


    private ListView myListView;
    private ArrayList<String> listaItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        myListView = findViewById(R.id.myListView);
        listaItems = new ArrayList<>();
        listaItems.add("Item 1");
        listaItems.add("Item 2");
        listaItems.add("Item 3");


        CustomListAdapter adapter = new CustomListAdapter(this, listaItems);
        myListView.setAdapter(adapter);


        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity2.this, "Botón Atrás presionado", Toast.LENGTH_SHORT).show();
            }
        });
    }

}


