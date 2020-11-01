package com.pms.a08_ud3_asynctaskarchivos;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    DoBackgroundTask descargaTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    /**
     * Clase personalizada para realizar la tarea en segundo plano
     * Parámetros:
     * URL: parámetro para doInBackground
     * Integer: parámetro para onProgressUpdate
     * Long: parámetro para onPostExecute y devuelto por doInBackground
     */
    private class DoBackgroundTask extends AsyncTask<URL, Integer, Long> {
        /**
         * Se ejecuta en un hilo de ejecución secundario, en segundo plano
         * Simula la descarga de archivos de ciertas URLs
         * @param urls: url de los archivos a descargar
         * @return: total de bytes descargados
         */
        protected Long doInBackground(URL... urls) {
            int count = urls.length;
            long totalBytesDownloaded = 0;
            ///*********************************************************************/
            //***Poned CÓDIGO QUE FALTA sobre isCancelled() en el lugar apropiado***/

            for (int i = 0; i < count; i++) {
                totalBytesDownloaded += DownloadFile(urls[i]);
                // calcula el porcentaje descargado  reportando el progreso a onProgressUpdate()
                publishProgress((int) (((i + 1) / (float) count) * 100));
            }
            return totalBytesDownloaded;
        }

        /**
         * Método que se e invoca en el hilo de ejecución de la interfaz de usuario y se
         * llama cada vez que se ejecuta el método publishProgress()
         * @param progress valor obtenido en publishProgress
         */
        @Override
        protected void onProgressUpdate(Integer... progress) {
            Log.d("Descargando Archivos", String.valueOf(progress[0])
                    + "% descargado");
            Toast.makeText(getBaseContext(),
                    String.valueOf(progress[0]) + "% descargado",
                    Toast.LENGTH_LONG).show();
        }

        /**
         * Método que se invoca en el hilo de ejecución de la interfaz de usUario y se llama
         * cuando el método doInBackground() ha terminado de ejecutarse
         * @param result valor ecibido de doInBackground()
         */
        @Override
        protected void onPostExecute(Long result) {
            // para que startDescarga pueda iniciarla de nuevo
            descargaTask = null;

            Toast.makeText(getBaseContext(),
                    "Descargados " + result + " bytes", Toast.LENGTH_LONG)
                    .show();
        }
    }

    /**
     * Método que simula la descarga de un archivo -- tarea de larga duración
     * @param url : url del archivo a descargar
     * @return. valor devuelto (bytes descargados)
     */
    private int DownloadFile(URL url) {

        try {
            //simula el tiempo necesario para descargar el archivo---
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // devuelve el tamaño en bytes simulado de la descarga
        return 100;
    }

    /**
     * Método onClick del botón comenzar descarga
     * @param view: botón comenzar descarga
     */
    public void startDescarga(View view) {

        //**********************************************************/
        //Si la descarga no se ha iniciado nunca o ha sido cancelada
        //********** PONED CÓDIGO QUE FALTA *********/

        try {
            new DoBackgroundTask().execute(new URL(
                    "http://www.amazon.com/somefiles.pdf"), new URL(
                    "http://www.wrox.com/somefiles.pdf"), new URL(
                    "http://www.google.com/somefiles.pdf"), new URL(
                    "http://www.learn2develop.net/somefiles.pdf"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método onClick del botón parar descarga: cancela la descarga de archivos
     * @param view: botón parar descarga
     */
    public void stopDescarga(View view) {
        //*********************************************/
        //***** poned CÓDIGO QUE FALTA ******/
    }

}
