package com.pms.a02_ud3_entenderhilos1

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtView = findViewById<View>(R.id.textView1) as TextView
    }

    /**
     * método onClick del botón ComenzarContador
     */
    //este método que pretende mostrar en un textView los números del 1 al 1000
    fun startCounter(view: View?) {
        for (i in 0..1000) {
            txtView!!.text = i.toString()
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
            /*
            // pausa para dar tiempo a que se muestre el valor antes de pasar al
            // siguiente (esta estrategia provoca un fallo, se necesita un hilo secundario)
            try {
<<<<<<< Updated upstream
                Thread.sleep(1000);
            } catch (e: InterruptedException) {
                Log.d("Threading", e.getLocalizedMessage());
            }
=======
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                Log.d("Threading", e.localizedMessage)
            }
            */

>>>>>>> Stashed changes
            // si no se intentara hacer esta pausa, el programa terminaría bien
            // pero sólo mostraría el 1000
            */
        }
    }

    companion object {
        var txtView: TextView? = null
    }
}