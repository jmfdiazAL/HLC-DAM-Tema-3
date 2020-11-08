package com.pms.a08_ud3_asynctaskarchivos

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import java.net.MalformedURLException
import java.net.URL

class MainActivity : AppCompatActivity() {
    var descargaTask: DoBackgroundTask? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * Clase personalizada para realizar la tarea en segundo plano
     * Parámetros:
     * URL: parámetro para doInBackground
     * Integer: parámetro para onProgressUpdate
     * Long: parámetro para onPostExecute y devuelto por doInBackground
     */
    inner class DoBackgroundTask : AsyncTask<URL?, Int?, Long>() {
        /**
         * Se ejecuta en un hilo de ejecución secundario, en segundo plano
         * Simula la descarga de archivos de ciertas URLs
         * @param urls: url de los archivos a descargar
         * @return: total de bytes descargados
         */
        protected override fun doInBackground(vararg urls: URL?): Long? {
            val count = urls.size
            var totalBytesDownloaded: Long = 0
            ///*********************************************************************/
            //***Poned CÓDIGO QUE FALTA sobre isCancelled() en el lugar apropiado***/
            for (i in 0 until count) {
                totalBytesDownloaded += DownloadFile(urls[i]!!).toLong()
                // calcula el porcentaje descargado  reportando el progreso a onProgressUpdate()
                publishProgress(((i + 1) / count.toFloat() * 100).toInt())
            }
            return totalBytesDownloaded
        }

        /**
         * Método que se e invoca en el hilo de ejecución de la interfaz de usuario y se
         * llama cada vez que se ejecuta el método publishProgress()
         * @param progress valor obtenido en publishProgress
         */
        protected override fun onProgressUpdate(vararg progress: Int?) {
            Log.d("Descargando Archivos", progress[0].toString() + "% descargado")
            Toast.makeText(baseContext,
                    progress[0].toString() + "% descargado",
                    Toast.LENGTH_LONG).show()
        }

        /**
         * Método que se invoca en el hilo de ejecución de la interfaz de usUario y se llama
         * cuando el método doInBackground() ha terminado de ejecutarse
         * @param result valor ecibido de doInBackground()
         */
        override fun onPostExecute(result: Long) {
            // para que startDescarga pueda iniciarla de nuevo
            descargaTask = null
            Toast.makeText(baseContext,
                    "Descargados $result bytes", Toast.LENGTH_LONG)
                    .show()
        }
    }

    /**
     * Método que simula la descarga de un archivo -- tarea de larga duración
     * @param url : url del archivo a descargar
     * @return. valor devuelto (bytes descargados)
     */
    private fun DownloadFile(url: URL): Int {
        try {
            //simula el tiempo necesario para descargar el archivo---
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        // devuelve el tamaño en bytes simulado de la descarga
        return 100
    }

    /**
     * Método onClick del botón comenzar descarga
     * @param view: botón comenzar descarga
     */
    fun startDescarga(view: View?) {

        //**********************************************************/
        //Si la descarga no se ha iniciado nunca o ha sido cancelada
        //********** PONED CÓDIGO QUE FALTA *********/
        try {
            DoBackgroundTask().execute(URL(
                    "http://www.amazon.com/somefiles.pdf"), URL(
                    "http://www.wrox.com/somefiles.pdf"), URL(
                    "http://www.google.com/somefiles.pdf"), URL(
                    "http://www.learn2develop.net/somefiles.pdf"))
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
    }

    /**
     * Método onClick del botón parar descarga: cancela la descarga de archivos
     * @param view: botón parar descarga
     */
    fun stopDescarga(view: View?) {
        //*********************************************/
        //***** poned CÓDIGO QUE FALTA ******/
    }
}