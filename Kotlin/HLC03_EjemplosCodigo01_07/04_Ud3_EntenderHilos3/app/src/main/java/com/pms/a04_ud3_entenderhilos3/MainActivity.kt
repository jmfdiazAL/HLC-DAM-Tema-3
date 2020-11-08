package com.pms.a04_ud3_entenderhilos3

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView

//Ejemplos de uso de hilos Java con post()
/** */
class MainActivity : AppCompatActivity() {
    /** Called when the activity is first created.  */
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtView = findViewById<View>(R.id.textView1) as TextView
    }
    /*************** con post() */
    /**
     * onClick del botón ComenzarContador
     * método que muestra en un textView los números del 1 al 10  utilizando hilos Java y post()*
     * @param view: botón
     */
    //
    fun startCounter(view: View?) {
        // para resolver el problema en Android se requiere un bloque Runnable
        // adicional pasado al TextView mediante su método post()
        Thread {
            for (i in 1..10) {
                // almacenar el nuevo valor
                // actualizar la interfaz de usuario (bloque Runnable
                // adicional para el método .post())
                txtView!!.post { // hilo de ejecución de la interfaz de usuario
                    txtView!!.text = i.toString()
                }

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