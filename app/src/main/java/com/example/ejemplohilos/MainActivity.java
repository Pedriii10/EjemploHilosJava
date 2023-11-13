package com.example.ejemplohilos;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvCrono;
    private Button startButton, stopButton, restartButton;
    private CountDownTask countDownTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCrono = findViewById(R.id.tvCrono);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        restartButton = findViewById(R.id.restartButton);

        startButton.setOnClickListener(v -> startOrResumeTask());
        stopButton.setOnClickListener(v -> pauseTask());
        restartButton.setOnClickListener(v -> restartTask());
    }

    private void startOrResumeTask() {
        if (countDownTask == null || countDownTask.getStatus() == AsyncTask.Status.FINISHED) {
            countDownTask = new CountDownTask();
            countDownTask.execute(10);
        } else {
            countDownTask.resumeCounting();
        }
    }

    private void pauseTask() {
        if (countDownTask != null && countDownTask.getStatus() == AsyncTask.Status.RUNNING) {
            countDownTask.pauseCounting();
        }
    }

    private void restartTask() {
        if (countDownTask != null) {
            if (countDownTask.getStatus() == AsyncTask.Status.RUNNING) {
                countDownTask.cancel(true);
            }
        }
        countDownTask = new CountDownTask();
        countDownTask.execute(10);
        countDownTask.resumeCounting();
    }


    private int getCurrentRemainingValue() {
        String currentText = tvCrono.getText().toString();
        try {
            return Integer.parseInt(currentText);
        } catch (NumberFormatException e) {
            return 10;
        }
    }

    private class CountDownTask extends AsyncTask<Integer, Integer, String> {

        private boolean countingPaused = false;
        private final Object pauseLock = new Object();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... params) {
            int remaining = params[0];
            while (remaining > 0 && !isCancelled()) {
                synchronized (pauseLock) {
                    while (countingPaused) {
                        try {
                            pauseLock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
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

        public void pauseCounting() {
            countingPaused = true;
        }

        public void resumeCounting() {
            countingPaused = false;
            synchronized (pauseLock) {
                pauseLock.notifyAll();
            }
        }
    }
}
