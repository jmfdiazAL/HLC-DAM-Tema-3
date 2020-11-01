package com.pms.a05_ud3_entenderhilos4;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static TextView txtView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtView = (TextView) findViewById(R.id.textView1);
    }

    /******************** Handler***********************/

    //---utilizada para actualizar la UI de la actividad principal
    static Handler UIactualiza = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            byte[] buffer = (byte[]) msg.obj;

            //---convierte el array de byte a string---
            String strRecibido = new String(buffer);

            //---Muestra el texto recibido en el TextView---
            txtView.setText(strRecibido);
            Log.d("Threading", "corriendo");
        }
    };

    /**
     * Método onClick del botón ComenzarContador
     * muestra en un textView los números del 1 al 10 usando Hilos Java y Handler
     */
    public void startCounter(View view) {
        //crea un hilo secundario
        new Thread(new Runnable() {
            public void run() {
                for (int i = 1; i <= 10; i++) {

                    // actualiza actividad principal
                    MainActivity.UIactualiza.obtainMessage(
                            0, String.valueOf(i).getBytes()).sendToTarget();
                    // --- introduce un retraso en hilo secundario
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.d("Threading", e.getLocalizedMessage());
                    }
                }
            }
            //iniicia o lanza el hilo secundario
        }).start();
    }


}


