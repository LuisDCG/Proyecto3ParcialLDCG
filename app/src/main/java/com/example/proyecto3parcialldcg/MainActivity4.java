package com.example.proyecto3parcialldcg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        // Crear el array de opciones de cena
        Cena[] opcionesCenas = {
                new Cena("Cena A", "Bebida: tomate de arbol", "Comida: Ensalada", "Postre: mil hojas"),
                new Cena("Cena B", "Bebida: malteada de chocolate", "Comida: Pizza", "Postre: fresas en crema"),
                new Cena("Cena C", "Bebida: Cocoa caliente", "Comida: omelette", "Postre: dulce de mora"),
                new Cena("Cena D", "Bebida: malteada de fresa", "Comida: waffles", "Postre: fruta picada")
        };

        // Ejemplo de cómo acceder a los datos del array y mostrarlos en los TextViews
        TextView itemATextView = findViewById(R.id.itemA);
        itemATextView.setText(opcionesCenas[0].getNombre());

        TextView bebidaATextView = findViewById(R.id.bebidaA);
        bebidaATextView.setText(opcionesCenas[0].getBebida());

        // Repite lo mismo para las otras opciones de cena...
    }

    }

class Cena {
    private String nombre;
    private String bebida;
    private String comida;
    private String postre;

    public Cena(String nombre, String bebida, String comida, String postre) {
        this.nombre = nombre;
        this.bebida = bebida;
        this.comida = comida;
        this.postre = postre;
    }

    public String getNombre() {
        return nombre;
    }

    public String getBebida() {
        return bebida;
    }

    public String getComida() {
        return comida;
    }

    public String getPostre() {
        return postre;
    }

}

