package com.example.ejemplohilos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;


// Al ejecutar este codigo, la interfaz de usuario queda bloqueada hasta que el bucle termine


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tvCrono = (TextView)findViewById(R.id.tvCrono);
        int remaining = 10;
        tvCrono.setText("Comenzamos");
        while(remaining > 0) {
            tvCrono.setText("" + remaining); remaining--;
            try {
                Thread.sleep(1000);
            }catch(InterruptedException e){}
        }
        tvCrono.setText("Terminado");
    }
}