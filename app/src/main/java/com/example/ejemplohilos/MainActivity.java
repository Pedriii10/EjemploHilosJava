package com.example.ejemplohilos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

//Utilizacion de un hilo secundario, utilziando runnable y runOnUiThread

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tvCrono = findViewById(R.id.tvCrono);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int remaining = 10;
                while (remaining > 0) {
                    int finalRemaining = remaining;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvCrono.setText("" + finalRemaining);
                        }
                    });
                    remaining--;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvCrono.setText("Terminado");
                    }
                });
            }
        });
        t.start();
    }
}
