package com.pms.a09_ud3_asynctask_bp

import android.support.v7.app.AppCompatActivity
import com.pms.a09_ud3_asynctask_bp.MainActivity.TareaAsincrona
import android.os.Bundle
import com.pms.a09_ud3_asynctask_bp.R
import android.os.AsyncTask
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var progressBar //barra de progreso
            : ProgressBar? = null
    var tarea = TareaAsincrona()
    var contador: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Referencia al la barra de progreso de la interfaz
        progressBar = findViewById<View>(R.id.progressBar) as ProgressBar
    }

    /**
     * Simula una tarea que dura 1 segundo
     */
    private fun tareaSegundo() {
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            println("ERROR: " + e.message)
        }
    }

    /**
     * Método onClick botón Iniciar Tarea
     *
     * @param v botón Iniciar tarea
     */
    fun iniciaTarea(v: View?) {
        //*********¡¡¡OJO!! hay que controlar que no hay una tarea ya iniciada,
        //*  en otro caso aborta programa
        tarea.execute()
    }

    /**
     * Método  onClick botón cancelar Tarea
     *
     * @param v botón cancelar tarea
     */
    fun cancelaTarea(v: View?) {
        //cancela la tarea
        //**** ¡¡OJO!! Hay que controlar que esté en ejecución, en otro caso aborta el programa
        tarea.cancel(true)
    }

    /**
     * Clase que hereda de AsyncTask para gestionar tareas asíncronas
     * Primer parámetro --- tipo recibido por doInBackground
     * Segundo parámetro --- tipo recibido por onProgressUpdate() y usado por publishProgress()
     * Tercer parámetro --- tipo devuelto por doInBackground y recibido por onPostExecute
     */
    inner class TareaAsincrona : AsyncTask<Void?, Int?, Boolean>() {
        //1º -Se ejecuta onPreExecute() en  hilo UI y antes del método doInBackground
        /**
         * Inicializa la barra de progreso
         */
        override fun onPreExecute() {
            super.onPreExecute()
            progressBar!!.max = 100 //valor máximo
            progressBar!!.progress = 0 //inicialización
        }
        // 2º- Se ejecuta doInBackground() en un hilo secundario y debe contener el código pesado
        // Puede ejecutar el método publissProgres() para comunicar el progreso de la tarea.
        // Ese progreso lo comunica a onProgressUpdate()
        //Recibe un parámtro del primer tipo y devuelve un parámetro del tercer tipo
        /**
         * Ejecuta una tarea en segundo plano
         *
         * @param nada: parámetros pasados al ejecutarse
         * @return: true --> si completa tarea, false --> si cancela tarea
         */
        protected override fun doInBackground(vararg nada: Void?): Boolean? {
            for (i in 1..10) {
                tareaSegundo()
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    println("ERROR: " + e.message)
                }

                //comunicar el progreso al hilo UI mediante la llamada a onProgressUpdate
                publishProgress(i * 10)
                //comprueba si el usuario cancela la tarea
                if (isCancelled) return false
            }
            return true
        }
        //3º- Se ejecuta en el hilo UI y es llamado cuando se ejecuta el método publishProgress()
        //Recibe un parámetro del segundo tipo, que es progreso y que le manda publishProgresss()
        /**
         * Informa del progreso
         *
         * @param vi: actualiza la barra de progreso
         */
        protected override fun onProgressUpdate(vararg vi: Int?) {
            super.onProgressUpdate(*vi)
            //actualiza el progreso de la progressbar con el valor de publishProgress
            progressBar!!.progress = vi[0]!!
        }
        //4º Se ejecuta en hilo UI tras finalizar la ejecución de doInBackground
        // Recibe un parámetro del tercer tipo, que es el valor devuelto por doInBackground
        /**
         * Muestra una notificación en tostada al finalizar la tarea
         *
         * @param resultado: valor recibido de doInBackground
         */
        override fun onPostExecute(resultado: Boolean) {
            super.onPostExecute(resultado)
            if (resultado) Toast.makeText(applicationContext,
                    "Tarea larga completada", Toast.LENGTH_SHORT).show()
        }

        /**
         * Se ejecuta cuando la tarea asíncrona es cancelada con cancel(true)
         * En este caso no se ejecuta onPostExecute
         *
         * @param cancelado: valor devuelto por doInBackground
         */
        override fun onCancelled(cancelado: Boolean) {
            if (!cancelado) //(cancelado == false
                Toast.makeText(applicationContext,
                        "Tarea larga cancelada", Toast.LENGTH_SHORT).show()
        }
    }
}