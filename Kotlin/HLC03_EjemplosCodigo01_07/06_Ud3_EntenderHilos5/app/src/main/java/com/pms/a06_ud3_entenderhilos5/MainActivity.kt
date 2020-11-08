package com.pms.a06_ud3_entenderhilos5

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView

//Ejemplo contador usando la clase Asynctask
class MainActivity : AppCompatActivity() {
    var task: DoCountingTask? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //referencia al TextView del layout
        txtView = findViewById<View>(R.id.textView1) as TextView
    }

    //clase que extiende a AsyncTask para implementar una tarea en seguno plano
    inner class DoCountingTask : AsyncTask<Void?, Int?, Void?>() {
        protected override fun doInBackground(vararg p0: Void?): Void? {
            for (i in 1..10) {
                // método que reporta el progreso de la tarea en segundo plano
                publishProgress(i)
                //retardo en el hilo secundario
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    Log.d("Threading", e.localizedMessage)
                }
            }
            return null
        }

        //método que informa del progreso de la trea en segundo plano
        protected override fun onProgressUpdate(vararg progress: Int?) {
            txtView!!.text = progress[0].toString()
            Log.d("Threading", "actualizando...")
        }
    }

    /**
     * Método onClick botón 'Comenzar contador'
     * @param view: botón
     */
    fun startCounter(view: View?) {

        //crea el objeto tarea asíncrona DoCountingTask y lo ejecuta
        task = DoCountingTask().execute() as DoCountingTask
    }

    companion object {
        var txtView: TextView? = null
    }
}