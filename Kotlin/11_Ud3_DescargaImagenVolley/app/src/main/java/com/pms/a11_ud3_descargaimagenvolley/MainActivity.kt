package com.pms.a11_ud3_descargaimagenvolley

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    //URL de la imagen
    //private final String IMG_URL = "https://expocodetech.com/wp-content/uploads/2014/09/ECTDownloadData01-600x300.png";
    private val IMG_URL = "http://www.pngall.com/wp-content/uploads/2016/03/Volleyball-PNG-Picture.png"

    //identificador de la tarea de descarga de imagen
    private val IMG_TAG = "imageTAG"

    //cola de solicitudes de -- Volley--
    private var requestQueue: RequestQueue? = null

    //visor de imágenes
    private var mImageView: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //asigna el visor de la imagen
        mImageView = findViewById<View>(R.id.mImageView) as ImageView
        //cola de solicitudes y manejador de respuestas de -- Volley--
        requestQueue = Volley.newRequestQueue(this)
    }

    /************************************************************************************************
     * onclick() del botón 'Descargar Imagen'
     * @param v
     */
    fun descargaImagen(v: View?) {

        //1) receptor para el bitmap a descargar
        val rlistener: Response.Listener<Bitmap> = Response.Listener { response -> //muestra la imagen en el visor
            mImageView!!.setImageBitmap(response)
        }

        //2)receptor para posibles errores, como no conexión a Internet
        val errorListener = Response.ErrorListener { error ->
            Toast.makeText(this@MainActivity, "Error en descarga", Toast.LENGTH_SHORT).show()
            error.printStackTrace()
        }

        //3)compone la petición de descarga de la imagen como tarea de Volley
        val imageRequest = ImageRequest(
                IMG_URL,
                rlistener,
                0,
                0,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.RGB_565,
                errorListener)

        //y la identifica con una etiqueta (por ejemplo, para poder cancelarla nominalmente)
        imageRequest.tag = IMG_TAG

        //4) pone la petición en la cola de tareas en segundo plano
        requestQueue!!.add(imageRequest)
    }

    //Llamado cuando la Activity se va a destruir
    override fun onStop() {
        super.onStop()
        //si se inició la cola de peticiones
        if (requestQueue != null) {
            //cancela la descarga de la imagen
            requestQueue!!.cancelAll(IMG_TAG)
        }
    }
}