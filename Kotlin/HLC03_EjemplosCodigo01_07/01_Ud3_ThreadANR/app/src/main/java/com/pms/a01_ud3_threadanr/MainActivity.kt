package com.pms.a01_ud3_threadanr

import android.app.Activity
import android.widget.TextView
import android.os.Bundle
import com.pms.a01_ud3_threadanr.R
import com.pms.a01_ud3_threadanr.MainActivity.Clavetask
import android.os.AsyncTask
import android.util.Log
import android.view.View
import java.math.BigInteger
import java.util.*

class MainActivity : Activity() {
    var e: BigInteger? = null
    var d: BigInteger? = null
    var n: BigInteger? = null
    var p: BigInteger? = null
    var q: BigInteger? = null
    var totient: BigInteger? = null
    var tamClave = 8192 //1024,2048,4096,8192
    var finalMsg: TextView? = null

    /** Called when the activity is first created.  */
    public override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        finalMsg = findViewById<View>(R.id.textView2) as TextView
    }

    /***********
     *
     * @param v
     */
    fun onClickMainThread(v: View?) {
        finalMsg!!.text = ""
        val time = generaClavesRSA()
        finalMsg!!.text = "Clave Generada en $time milisegundos"
        Log.d("ThreadANR", "Termina tarea?")
    }

    /*******
     *
     * @param v
     */
    fun onClickNewThread(v: View?) {
        finalMsg!!.text = ""
        Thread {
            val time = generaClavesRSA()
            finalMsg!!.post { finalMsg!!.text = "Clave Generada en $time milisegundos" }
        }.start()
        Log.d("ThreadANR", "Hebra creada y vuelve hebra principal")
    }

    /*********
     *
     * @param v
     */
    fun onClickNewAsThread(v: View?) {
        finalMsg!!.text = ""
        Clavetask().execute()
        Log.d("ThreadANR", "Hebra creada y vuelve hebra principal")
    }

    private inner class Clavetask : AsyncTask<Void?, Void?, Long>() {
        protected override fun doInBackground(vararg p0: Void?): Long? {
            Log.d("ThreadANR", "Asyntask Creada y vuelve hebra principal")
            return generaClavesRSA()
        }

        override fun onPostExecute(time: Long) {
            finalMsg!!.text = "Clave Generada en $time milisegundos"
            Log.d("ThreadANR", "onPostExecute")
        }
    }

    fun generaClavesRSA(): Long {
        val ini = System.currentTimeMillis()
        generaPrimos() //Genera p y q
        generaClaves() //Genera e y d
        val fin = System.currentTimeMillis()
        Log.d("ThreadANR", "Clave Creada y Finalizada")
        return fin - ini
    }

    fun generaPrimos() {
        p = BigInteger(tamClave / 2, 10, Random())
        do q = BigInteger(tamClave / 2, 10, Random()) while (q!!.compareTo(p) == 0)
    }

    fun generaClaves() {
        // n = p * q
        n = p!!.multiply(q)
        // toltient = (p-1)*(q-1)
        totient = p!!.subtract(BigInteger.valueOf(1))
        totient = totient?.multiply(q!!.subtract(BigInteger.valueOf(1)))
        // Elegimos un e coprimo de y menor que n
        do e = BigInteger(tamClave, Random()) while (e!!.compareTo(totient) != -1 ||
                e!!.gcd(totient).compareTo(BigInteger.valueOf(1)) != 0)
        // d = e^1 mod totient
        d = e!!.modInverse(totient)
    }
}