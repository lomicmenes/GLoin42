package com.example.ensai.gloin;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ensai.gloin.XML.XMLWriter;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by ensai on 10/05/16.
 */
public class VenteImageActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMG = 1;

    private static final String SERVER_ADDRESS = "http://axel-gloin.netai.net/";
    String imgDecodableString;
    ImageView imageToUpLoad ;
    EditText nomImage ;
    EditText profit ;
    EditText minPrice ;
    EditText maxPrice ;
    String pseudo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vente_image);
        Intent intent = getIntent();
        pseudo = intent.getStringExtra("pseudo");
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(this, new  String[] {Manifest.permission.INTERNET},1);
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

                imageToUpLoad = imgView ;

                Button venteButton = (Button) findViewById(R.id.buttonVenteImage);
                venteButton.setVisibility(View.VISIBLE);

                EditText editText =(EditText) findViewById(R.id.nomImage);

                nomImage = editText ;
               editText.setVisibility(View.VISIBLE);

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

       /* buildXml();*/
        Bitmap image = ((BitmapDrawable) imageToUpLoad.getDrawable() ).getBitmap();
        try {
            new UpLoadImage(image, nomImage.getText().toString()).execute();
        }
        catch (Exception e) {
            Log.e("upLoad", "On a mal upload");
        }
    }

    private void buildXml() {
        
        profit = (EditText) findViewById(R.id.profit);
        minPrice = (EditText) findViewById(R.id.minPrice);
        maxPrice = (EditText) findViewById(R.id.maxPrice);
        nomImage = (EditText) findViewById(R.id.nomImage);

        int profitInt = Integer.valueOf(profit.getText().toString());
        int minPriceInt = Integer.valueOf(minPrice.getText().toString());
        int maxPriceInt = Integer.valueOf(maxPrice.getText().toString());
        String nomImageString = nomImage.getText().toString();

        Image image = new Image(nomImageString, profitInt, minPriceInt, maxPriceInt);

        try {
            String imageXml = XMLWriter.writeUsingXMLSerializer(image);
        } catch (Exception e) {
            Log.e("XML", e.getMessage());
        }
    }

    private class UpLoadImage extends AsyncTask< Void , Void , Void >{

        String name ;
        Bitmap image ;

        public UpLoadImage(Bitmap image , String name){
            this.name =  name ;
            this.image =  image;

        }

        @Override
        protected Void doInBackground(Void... params) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG , 100 ,byteArrayOutputStream) ;
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("image" , encodedImage)  );
            dataToSend.add(new BasicNameValuePair("name" , name)) ;

            HttpParams httpParams = getHttpParams();

            HttpClient client = new DefaultHttpClient(httpParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "uploadImg.php" );

            try{
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                Log.d("upload","Va uploader l'image " + name);
                client.execute(post);
            }
            catch(Exception e){
                Log.e("connection", "pbl de co ");
            }


            return null ;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Toast.makeText(getApplicationContext() ," l'image est upLOad" , Toast.LENGTH_SHORT).show();


        }

        private HttpParams getHttpParams() {
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 1000 * 30);
            HttpConnectionParams.setSoTimeout(httpParams, 1000*30);
            return httpParams ;

        }
    }


}
