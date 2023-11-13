package com.example.ejemplohilos;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

//Metodo AsynTask

public class MainActivity extends AppCompatActivity {

    private TextView tvCrono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCrono = findViewById(R.id.tvCrono);
        new CountDownTask().execute(10);
    }

    private class CountDownTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvCrono.setText("Comenzamos");
        }

        @Override
        protected String doInBackground(Integer... params) {
            int remaining = params[0];
            while (remaining > 0) {
                publishProgress(remaining);
                remaining--;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Terminado";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tvCrono.setText(String.valueOf(values[0]));
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            tvCrono.setText(result);
        }
    }
}
