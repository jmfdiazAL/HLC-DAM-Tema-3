package com.pms.a10_ud3_descargaimagen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    //Boton de Descargar Imagen
    var btnDownload: Button? = null

    //Image View para mostrar la Imagen Descargada
    var mImageView: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mImageView = findViewById<View>(R.id.mImageView) as ImageView
    }

    // onclick() del botón 'Descargar Imagen'
    fun descargaImagen(v: View?) {
        //ejecuta una tarea en segundo plano (en hilo secundario)con el parámetro IMG_URL
        DownloadImageTask().execute(IMG_URL)
    }

    /**
     * Clase que deriva de AsyncTask para implementar tareas asíncornas en segundo plano
     * con tres parámetros: String, Integer y Bitmap
     */
    private inner class DownloadImageTask : AsyncTask<String?, Int?, Bitmap?>() {
        /**
         * Método que ejecuta la tarea asíncrona en un hilo secundario en segundo plano
         * @param urls: dirección dedescarga de la imagen
         * @return: imagen descargada
         */
        protected override fun doInBackground(vararg urls: String?): Bitmap? {
            return downloadImagen(urls[0]!!)
        }

        /**
         * Método que se ejecuta el hilo UI tras finalizar doImBackground()
         * y puede actualizar la imagen del ImageView para mostrar la imagen descargada
         * @param result: valor devuelto por doInBackground
         */
        override fun onPostExecute(result: Bitmap?) {
            mImageView!!.setImageBitmap(result)
        }

        private fun downloadImagen(baseUrl: String): Bitmap? {
            var myBitmap: Bitmap? = null
            try {
                //Se define el objeto URL
                val url = URL(baseUrl)
                //Se obtiene y configura un objeto de conexión HttpURLConnection
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                //Recibimos la respuesta de la petición en formato InputStream
                val input = connection.inputStream

                //Decodificamos el InputStream a un objeto BitMap
                myBitmap = BitmapFactory.decodeStream(input)
            } //se captura de forma genérica las posibles excepciones
            catch (e: Exception) {
                e.printStackTrace()
            }
            return myBitmap
        }
    }

    companion object {
        //Tag para el control de trazas de LOG
        private const val APP_TAG = "DownloadData"

        //Constante String con la URL de la imagen a descargar
        //private static String IMG_URL="https://expocodetech.com/wp-content/uploads/2014/09/ECTDownloadData01-600x300.png";
        private const val IMG_URL = "http://pngimg.com/uploads/android_logo/android_logo_PNG27.png"
    }
}