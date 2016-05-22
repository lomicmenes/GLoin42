package com.example.ensai.gloin;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ensai on 22/05/16.
 */
public class AchatImageActvity extends AppCompatActivity {

    private static final String SERVER_ADRESS = "http://axel-gloin.netai.net/" ;


    ImageView downloadedImage ;
    EditText name ;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achat_image);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }


    public void achatImage(View v) {
        name = (EditText) findViewById(R.id.nomImage);
        if (!(name.getText().toString().equals(""))) {


            try {
                new DownloadImage(name.getText().toString()).execute();
            } catch (Exception e) {
                Log.e("downLoad", "On a mal download");
            }
        } else {
            Toast.makeText(this, " SI TU METS PAS DE NOM CA VA PAS MARCHE BANANE !", Toast.LENGTH_SHORT).show();
        }
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
               downloadedImage.setImageBitmap(bitmap);
            }
        }
    }
    private HttpParams getHttpParams() {
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 1000 * 30);
        HttpConnectionParams.setSoTimeout(httpParams, 1000*30);
        return httpParams ;

    }


}

