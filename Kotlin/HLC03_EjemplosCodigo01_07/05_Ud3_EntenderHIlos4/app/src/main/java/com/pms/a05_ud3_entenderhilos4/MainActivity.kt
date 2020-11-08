package com.pms.a05_ud3_entenderhilos4

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    /**
     * Called when the activity is first created.
     */
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtView = findViewById<View>(R.id.textView1) as TextView
    }

    /**
     * Método onClick del botón ComenzarContador
     * muestra en un textView los números del 1 al 10 usando Hilos Java y Handler
     */
    fun startCounter(view: View?) {
        //crea un hilo secundario
        Thread {
            for (i in 1..10) {

                // actualiza actividad principal
                UIactualiza.obtainMessage(
                        0, i.toString().toByteArray()).sendToTarget()
                // --- introduce un retraso en hilo secundario
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    Log.d("Threading", e.localizedMessage)
                }
            }
        }.start()
    }

    companion object {
        var txtView: TextView? = null

        /******************** Handler */ //---utilizada para actualizar la UI de la actividad principal
        var UIactualiza: Handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                val buffer = msg.obj as ByteArray

                //---convierte el array de byte a string---
                val strRecibido = String(buffer)

                //---Muestra el texto recibido en el TextView---
                txtView!!.text = strRecibido
                Log.d("Threading", "corriendo")
            }
        }
    }
}