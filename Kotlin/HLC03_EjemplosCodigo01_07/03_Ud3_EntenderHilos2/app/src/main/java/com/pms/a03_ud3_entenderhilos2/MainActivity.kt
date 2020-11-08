package com.pms.a03_ud3_entenderhilos2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView

//Ejemplo Contador: con un hilo secundario estilo Java
/** */
class MainActivity : AppCompatActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtView = findViewById<View>(R.id.textView1) as TextView
    }

    /**
     * Método onClick del botón ComenzarContador con hilo secundario  Java
     */
    //método que pretende mostrar en un textView los números del 1 al 1000 --ABORTA--
    fun startCounter(view: View?) {
        // crea un hilo secundario para realizar la cuenta y mostrar contador en pantalla
        Thread {
            for (i in 0..1000) {
                txtView!!.text = i.toString()
                // pausa para dar tiempo a que se muestre el valor antes de
                // pasar al siguiente
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
    }
}