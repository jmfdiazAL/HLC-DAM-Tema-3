package com.pms.a04_ud3_entenderhilos3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

//Ejemplos de uso de hilos Java con post()
/*****************************************************/
public class MainActivity extends AppCompatActivity {

    static TextView txtView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtView = (TextView) findViewById(R.id.textView1);
    }



    /*************** con post()*********************/

    /**
     * onClick del botón ComenzarContador
     * método que muestra en un textView los números del 1 al 10  utilizando hilos Java y post()*
     * @param view: botón
     */
    //
    public void startCounter(View view) {
        // para resolver el problema en Android se requiere un bloque Runnable
        // adicional pasado al TextView mediante su método post()
        new Thread(new Runnable() {
            public void run() {
                for (int i = 1; i <= 10; i++) {
                    // almacenar el nuevo valor
                    final int contador = i;
                    // actualizar la interfaz de usuario (bloque Runnable
                    // adicional para el método .post())
                    txtView.post(new Runnable() {
                        public void run() {
                            // hilo de ejecución de la interfaz de usuario
                            txtView.setText(String.valueOf(contador));
                        }
                    });

                    // pausa para dar tiempo a que se muestre el valor antes de
                    // pasar al siguiente
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.d("Threading"    , e.getLocalizedMessage());
                    }
                }
            }
        }).start();
    }
}

