package com.pms.a03_ud3_entenderhilos2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

//Ejemplo Contador: con un hilo secundario estilo Java
/*******************************************************************/
public class MainActivity extends AppCompatActivity {

    static TextView txtView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtView = (TextView) findViewById(R.id.textView1);
    }

    /**
     * Método onClick del botón ComenzarContador con hilo secundario  Java
     */
    //método que pretende mostrar en un textView los números del 1 al 1000 --ABORTA--
    public void startCounter(View view) {
        // crea un hilo secundario para realizar la cuenta y mostrar contador en pantalla
        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i <= 1000; i++) {
                    txtView.setText(String.valueOf(i));
                    // pausa para dar tiempo a que se muestre el valor antes de
                    // pasar al siguiente
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.d("Threading", e.getLocalizedMessage());
                    }
                }
            }
            //inicia o lanza el hilo
        }).start();
    }

}

