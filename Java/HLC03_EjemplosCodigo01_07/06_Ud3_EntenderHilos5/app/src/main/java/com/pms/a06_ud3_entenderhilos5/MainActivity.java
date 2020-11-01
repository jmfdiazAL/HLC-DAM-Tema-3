package com.pms.a06_ud3_entenderhilos5;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

//Ejemplo contador usando la clase Asynctask
public class MainActivity extends AppCompatActivity {

    static TextView txtView;
    DoCountingTask task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //referencia al TextView del layout
        txtView = (TextView) findViewById(R.id.textView1);
    }


    //clase que extiende a AsyncTask para implementar una tarea en seguno plano
    private class DoCountingTask extends AsyncTask<Void, Integer, Void> {
        protected Void doInBackground(Void... params) {
            for (int i = 1; i <= 10; i++) {
                // método que reporta el progreso de la tarea en segundo plano
                publishProgress(i);
                //retardo en el hilo secundario
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.d("Threading", e.getLocalizedMessage());
                }

            }
            return null;
        }
        //método que informa del progreso de la trea en segundo plano
        protected void onProgressUpdate(Integer... progress) {
            txtView.setText(progress[0].toString());
            Log.d("Threading", "actualizando...");
        }
    }

    /**
     * Método onClick botón 'Comenzar contador'
     * @param view: botón
     */
    public void startCounter(View view) {

        //crea el objeto tarea asíncrona DoCountingTask y lo ejecuta
        task = (DoCountingTask) new DoCountingTask().execute();
    }

}

