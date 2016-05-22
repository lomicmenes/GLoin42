package com.example.ensai.gloin;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ensai on 10/05/16.
 */
public class VenteImageActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMG = 1;
    private static final String SERVER_ADDRESS = "http://file-manager.000webhost.com/file-manager/index.php";
    String imgDecodableString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vente_image);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    public void chargerImgDepuisGallery(View v){
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
// Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.imgView);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

                Button venteButton = (Button) findViewById(R.id.buttonVenteImage);
                venteButton.setVisibility(View.VISIBLE);

            } else {
                Toast.makeText(this, "Pas d'image sélectionnée",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e("Image","erreur",e);
            Toast.makeText(this, "quelque chose ne va pas", Toast.LENGTH_LONG)
                    .show();
        }

    }

    public void venteImage(View v){
        ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            Log.i("CONNEXION", "Je suis connecté au réseau!");
            try{
                File direct = new File(Environment.getExternalStorageDirectory()
                        + "/AnhsirkDasarp");

                if (!direct.exists()) {
                    direct.mkdirs();
                }
//                  Uri uri = Uri.parse(DRIVE_URL);
//                HttpURLConnection connexion=(HttpURLConnection) url.openConnection();
//                Log.i("CONNEXION", "Connexion à : " + DRIVE_URL);
//                connexion.setReadTimeout(10000 /* milliseconds */);
//                connexion.setConnectTimeout(15000 /* milliseconds */);
//                connexion.setRequestMethod("GET");
//                connexion.setDoInput(true);
//                // Démarrer la requête
//                connexion.connect();
//                InputStream is = connexion.getInputStream();
                DownloadManager dManager= (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//                DownloadManager.Request request = new DownloadManager.Request(uri);

//                request.setAllowedNetworkTypes(
//                        DownloadManager.Request.NETWORK_WIFI
//                                | DownloadManager.Request.NETWORK_MOBILE)
//                        .setAllowedOverRoaming(false).setTitle("Demo")
//                        .setDescription("Something useful. No, really.")
//                        .setDestinationInExternalPublicDir("/AnhsirkDasarpFiles", "fileName.jpg");
//                dManager.enqueue(request);


            }catch(Exception e){
                Log.e("DOWNLOAD", e.getMessage());
            }
        } else {
            Toast.makeText(this,R.string.pasDeConnexion,Toast.LENGTH_SHORT).show();
            Log.w("CONNEXION", "Je ne suis pas connecté");
        }
    }


}
