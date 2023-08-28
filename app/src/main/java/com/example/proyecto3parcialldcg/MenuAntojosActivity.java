package com.example.proyecto3parcialldcg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

public class MenuAntojosActivity extends AppCompatActivity {
    private NumberPicker cantidadAntojoA;
    private NumberPicker cantidadAntojoB;
    private NumberPicker cantidadAntojoC;
    private Button btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuantojos);

        cantidadAntojoA = findViewById(R.id.txtAntojitoA);
        cantidadAntojoB = findViewById(R.id.txtAntojitoB);
        cantidadAntojoC = findViewById(R.id.txtAntojitoC);

        // Configuraciones para los NumberPickers
        configureNumberPicker(cantidadAntojoA);
        configureNumberPicker(cantidadAntojoB);
        configureNumberPicker(cantidadAntojoC);

        btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();

                // Solo enviamos los antojitos que tienen una cantidad seleccionada
                if (cantidadAntojoA.getValue() > 0) {
                    resultIntent.putExtra("antojoA", cantidadAntojoA.getValue());
                }
                if (cantidadAntojoB.getValue() > 0) {
                    resultIntent.putExtra("antojoB", cantidadAntojoB.getValue());
                }
                if (cantidadAntojoC.getValue() > 0) {
                    resultIntent.putExtra("antojoC", cantidadAntojoC.getValue());
                }

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

    }

    private void configureNumberPicker(NumberPicker np) {
        np.setMinValue(0);
        np.setMaxValue(100);
        np.setValue(0);
        np.setWrapSelectorWheel(false);
    }
}