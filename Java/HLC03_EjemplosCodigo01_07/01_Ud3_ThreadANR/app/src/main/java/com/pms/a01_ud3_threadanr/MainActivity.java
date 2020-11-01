package com.pms.a01_ud3_threadanr;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.Random;

public class MainActivity extends Activity {

    BigInteger e,d,n,p,q,totient;
    int tamClave=8192;//1024,2048,4096,8192
    TextView finalMsg=null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        finalMsg = (TextView)findViewById(R.id.textView2);

    }


    /***********
     *
     * @param v
     */
    public void onClickMainThread(View v) {
        finalMsg.setText("");
        long time= generaClavesRSA();

        finalMsg.setText("Clave Generada en "+time+" milisegundos");
        Log.d("ThreadANR","Termina tarea?");
    }
    /*******
     *
     * @param v
     */
    public void onClickNewThread(View v) {
        finalMsg.setText("");

        new Thread(new Runnable() {
            public void run() {
                final long time = generaClavesRSA();

                finalMsg.post(new Runnable(){
                    public void run(){
                        finalMsg.setText("Clave Generada en "+time+" milisegundos");
                    }
                });
            }
        }).start();
        Log.d("ThreadANR","Hebra creada y vuelve hebra principal");
    }
    /*********
     *
     * @param v
     */
    public void onClickNewAsThread(View v) {
        finalMsg.setText("");

        new Clavetask().execute();
        Log.d("ThreadANR","Hebra creada y vuelve hebra principal");
    }

    private class Clavetask extends AsyncTask<Void, Void, Long> {

        @Override
        protected Long doInBackground(Void... params) {
            Log.d("ThreadANR","Asyntask Creada y vuelve hebra principal");
            return generaClavesRSA();
        }


        protected void onPostExecute(Long time) {
            finalMsg.setText("Clave Generada en "+time+" milisegundos");
            Log.d("ThreadANR","onPostExecute");

        }


    }

    public long generaClavesRSA() {

        long ini = System.currentTimeMillis();
        generaPrimos(); //Genera p y q
        generaClaves(); //Genera e y d
        long fin = System.currentTimeMillis();
        Log.d("ThreadANR","Clave Creada y Finalizada");

        return fin-ini;
    }

    public void generaPrimos()	{
        p = new BigInteger(tamClave/2, 10, new Random());

        do q = new BigInteger(tamClave/2, 10, new Random());
        while(q.compareTo(p)==0);
    }


    public void generaClaves(){
        // n = p * q
        n = p.multiply(q);
        // toltient = (p-1)*(q-1)
        totient = p.subtract(BigInteger.valueOf(1));
        totient = totient.multiply(q.subtract(BigInteger.valueOf(1)));
        // Elegimos un e coprimo de y menor que n
        do e = new BigInteger(tamClave, new Random());

        while((e.compareTo(totient) != -1) ||
                (e.gcd(totient).compareTo(BigInteger.valueOf(1)) != 0));
        // d = e^1 mod totient
        d = e.modInverse(totient);
    }




}
