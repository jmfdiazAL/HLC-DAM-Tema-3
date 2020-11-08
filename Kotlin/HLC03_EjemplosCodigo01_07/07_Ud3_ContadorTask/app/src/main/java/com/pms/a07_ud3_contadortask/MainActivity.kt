package com.pms.a07_ud3_contadortask

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var mSleep = 0
    private var mMax = 0
    private var textView: TextView? = null
    private var counterTask: CounterTask? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // cadencia de la cuenta
        mSleep = 500
        // tope del contador
        mMax = 15
        // marcador
        textView = findViewById<View>(R.id.textView1) as TextView
    }

    /**
     * método onClick del botón Para cuenta
     * @param v
     */
    fun stopCounter(v: View?) {
        // si cuenta no ha sido iniciada o cancelada
        if (counterTask != null && !counterTask!!.isCancelled) // la cancela
            counterTask!!.cancel(true)
    }

    /**
     * método onClick del botón Comenzar cuenta
     * @param v
     */
    fun startCounter(v: View?) {
        // si cuenta no iniciada nunca, o ha sido cancelada
        if (counterTask == null || counterTask!!.isCancelled) {
            // la arranca con los parámetros indicados en execute()
            counterTask = CounterTask()
            counterTask!!.execute(mSleep, mMax)
        }
    }

    /**
     * clase AsyncTask  para realizar la tarea en segundo plano
     * indicando el tipo de parámetros requeridos
     */
    private inner class CounterTask : AsyncTask<Int?, Int?, Int>() {
        /**
         * Método que inicia la tarea en segundo plano con los parámetros pasados a su
         * método execute(), y se devuelve el resultado a onPostExecute()
         * @param intParam  parámetros de ejecución de la tarea
         * @return resultado de la tarea
         */
        protected override fun doInBackground(vararg intParam: Int?): Int? {
            var sleep = intParam[0]
            var max = intParam[1]
            // inicio del progreso del contador
            var progreso = 0
            // por debajo del tope
            while (progreso < max!!) {
                // si cuenta cancelada
                if (isCancelled) // sale sin finalizar
                    return progreso else {
                    // envía el nuevo valor calculado
                    publishProgress(progreso++)
                    // simula la cadencia durmiendo el hilo secundario
                    try {
                        Thread.sleep(mSleep.toLong())
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            } //fin while
            // cuenta finalizada y devuelve el valor
            return progreso
        }

        /**
         * Método se recibe los valores de progreso enviados por publishProgress(), y
         * se actualiza la interfaz con ellos
         * @param progress recibe los valores del progreso enviados por publishProgress()
         */
        protected override fun onProgressUpdate(vararg progress: Int?) {
            // recoge el único valor enviado
            var contador = progress[0]
            // actualiza la interfaz
            textView!!.text = "" + contador
        }

        /**
         * Método que recibe el valor devuelto por doInBackground()
         * y se ejecuta en Interfaz de usuario al finalizar la tarea
         * @param result valores que provienen de finalizar doInBackgound()
         */
        override fun onPostExecute(result: Int) {

            //código que se ejecuta al finalizar la tarea en segundo plano
            Toast.makeText(applicationContext, "Tarea finalizada!",
                    Toast.LENGTH_SHORT).show()
        }

        /**
         * Método que se ejecuta al cancelar la tarea
         */
        override fun onCancelled() {
            Toast.makeText(applicationContext, "Tarea cancelada!",
                    Toast.LENGTH_SHORT).show()
            //tarea no iniciada
            counterTask = null
        }
    }
}