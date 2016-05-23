package com.example.ensai.gloin;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.w3c.dom.Document;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by ensai on 22/05/16.
 */
public class AchatImageActvity extends AppCompatActivity {

    private static final String SERVER_ADRESS = "http://axel-gloin.netai.net/" ;


    ImageView downloadedImage ;
    EditText name ;
    Bitmap b ;
    Document XMLdoc;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achat_image);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        downloadedImage = (ImageView) findViewById(R.id.imgView);

    }


    public void acheterImage(View v) {
        name = (EditText) findViewById(R.id.nomImage);
        if (!(name.getText().toString().equals(""))) {


            try {
                new DownloadImage(name.getText().toString()).execute();
            } catch (Exception e) {
                Log.e("downLoad", "On a mal download");
            }
            try {
                if (b != null) {
                    saveImageToGallery(downloadedImage, name.getText().toString(), b);
                    Toast.makeText(this, "image sauvé en galerie", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e){
                Log.e("THUG" , "ta pas sauver l'iamge das la galerei");
            }

        } else {
            Toast.makeText(this, " SI TU METS PAS DE NOM CA VA PAS MARCHE BANANE !", Toast.LENGTH_SHORT).show();
        }
    }
    private void saveImageToGallery(ImageView imageView , String name ,  Bitmap b){
        imageView.setDrawingCacheEnabled(true);
        MediaStore.Images.Media.insertImage(this.getContentResolver(), b,name , "description");
    }


    private class DownloadImage extends AsyncTask<Void, Void, Bitmap>{

        String name ;

        public DownloadImage(String name ){
            this.name = name ;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {

            String url = SERVER_ADRESS + "/pictures/"+name +".JPG" ;

            try{
                URLConnection connection = new URL(url).openConnection();
                connection.setReadTimeout(1000 * 3);
                connection.setConnectTimeout(1000*3);

                return BitmapFactory.decodeStream((InputStream) connection.getContent() , null ,null);

            }
            catch (Exception e){
                Log.e("pbl load im", "pbl URL");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap!=null){
                b = bitmap ;
                downloadedImage.setImageBitmap(bitmap);
            }
            else{
                Toast.makeText(getApplicationContext() , "sorry wrong name try again !  ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class DownloadXML extends AsyncTask<Void, Void, Document>{

        String name ;

        public DownloadXML(String name ){
            this.name = name ;
        }

        @Override
        protected Document doInBackground(Void... params) {

            String url = SERVER_ADRESS + "/pictures/"+name +".xml" ;

            try{
                URLConnection connection = new URL(url).openConnection();
                connection.setReadTimeout(1000 * 3);
                connection.setConnectTimeout(1000*3);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse((InputStream) connection.getContent());

                return doc;

            }
            catch (Exception e){
                Log.e("pbl load im", "pbl URL");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Document doc) {
            super.onPostExecute(doc);
            if (doc!=null){
                XMLdoc = doc;

            }
            else{
                Toast.makeText(getApplicationContext() , "sorry wrong name try again !  ", Toast.LENGTH_SHORT).show();
            }
        }


    }



}

