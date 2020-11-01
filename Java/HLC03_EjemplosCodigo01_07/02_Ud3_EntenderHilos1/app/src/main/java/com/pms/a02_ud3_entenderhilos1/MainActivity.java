package com.pms.a02_ud3_entenderhilos1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static TextView txtView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtView = (TextView) findViewById(R.id.textView1);
    }

    /**
     * método onClick del botón ComenzarContador
     */
    //este método que pretende mostrar en un textView los números del 1 al 1000
    public void startCounter(View view) {
        for (int i = 0; i <= 1000; i++) {
            txtView.setText(String.valueOf(i));

      /*      // pausa para dar tiempo a que se muestre el valor antes de pasar al
            // siguiente (esta estrategia provoca un fallo, se necesita un hilo secundario)
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Log.d("Threading", e.getLocalizedMessage());
            }
*/
            // si no se intentara hacer esta pausa, el programa terminaría bien
            // pero sólo mostraría el 1000
        }
    }
}
