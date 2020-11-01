package com.pms.a10_ud3_descargaimagen;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    //Tag para el control de trazas de LOG
    private static String APP_TAG = "DownloadData";
    //Constante String con la URL de la imagen a descargar
    //private static String IMG_URL="https://expocodetech.com/wp-content/uploads/2014/09/ECTDownloadData01-600x300.png";
    private static String IMG_URL="http://pngimg.com/uploads/android_logo/android_logo_PNG27.png";

    //Boton de Descargar Imagen
    public Button btnDownload;
    //Image View para mostrar la Imagen Descargada
    public ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.mImageView);

    }
    // onclick() del botón 'Descargar Imagen'
    public void descargaImagen(View v){
        //ejecuta una tarea en segundo plano (en hilo secundario)con el parámetro IMG_URL
        new DownloadImageTask().execute(IMG_URL);
    }

    /**
     * Clase que deriva de AsyncTask para implementar tareas asíncornas en segundo plano
     * con tres parámetros: String, Integer y Bitmap
     */

    private class DownloadImageTask extends AsyncTask<String, Integer, Bitmap> {
        /**
         * Método que ejecuta la tarea asíncrona en un hilo secundario en segundo plano
         * @param urls: dirección dedescarga de la imagen
         * @return: imagen descargada
         */
        @Override
        protected Bitmap doInBackground(String... urls) {
            return downloadImagen(urls[0]);
        }

        /**
         * Método que se ejecuta el hilo UI tras finalizar doImBackground()
         * y puede actualizar la imagen del ImageView para mostrar la imagen descargada
         * @param result: valor devuelto por doInBackground
         */
        protected void onPostExecute(Bitmap result) {
            mImageView.setImageBitmap(result);
        }

        private Bitmap downloadImagen(String baseUrl) {
            Bitmap myBitmap = null;
            try
            {
                //Se define el objeto URL
                URL url = new URL(baseUrl);
                //Se obtiene y configura un objeto de conexión HttpURLConnection
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                //Recibimos la respuesta de la petición en formato InputStream
                InputStream input = connection.getInputStream();

                //Decodificamos el InputStream a un objeto BitMap
                myBitmap = BitmapFactory.decodeStream(input);

            }
            //se captura de forma genérica las posibles excepciones
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return myBitmap;
        }
    }
}
