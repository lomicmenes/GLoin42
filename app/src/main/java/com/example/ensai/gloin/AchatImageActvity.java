package com.example.ensai.gloin;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by ensai on 22/05/16.
 */
public class AchatImageActvity extends AppCompatActivity {

    ElementDAOSQLite base ;
    ImageDAOSQLITE baseImage ;

    public static final String SERVER_ADRESS = "http://axel-gloin.netai.net/" ;


    ImageView downloadedImage ;
    Image image;
    EditText name ;
    Bitmap b ;
    Document XMLdoc;
    String pseudo;
    int prixAchat = 0;
    Runnable code;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achat_image);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        baseImage = new ImageDAOSQLITE(getApplicationContext());
        downloadedImage = (ImageView) findViewById(R.id.imgView);
        Intent intent = getIntent();
        pseudo = intent.getStringExtra("pseudo");

        code = new Runnable() {
            @Override
            public void run() {
                base = new ElementDAOSQLite(getApplicationContext());
                base.changerGloin(pseudo , -prixAchat);
                Log.e("MARCHE AChAT", "c'est juste pour savoir si ca a effectuer ca ");


            }
        };





    }


    public void acheterImage(View v) {
        name = (EditText) findViewById(R.id.nomImage);
        String nameString = name.getText().toString();
        if (!(nameString.equals(""))) {


            try {
                new DownloadImage(nameString).execute();
            } catch (Exception e) {
                Log.e("downLoad", "On a mal download");
            }
           try {
                Log.d("DOWNLOAD XML", "ça devrait commencer pour le fichier " + nameString + ".xml");
                new DownloadXML(nameString).execute();
            }catch (Exception e1){
                Log.e("DOWNLOAD XML", e1.getMessage());
            }



        } else {
            Toast.makeText(this, " SI TU METS PAS DE NOM CA VA PAS MARCHE BANANE !", Toast.LENGTH_SHORT).show();
        }
    }
    private void saveImageToGallery(ImageView imageView , String name ,  Bitmap b){
        Log.i("IMAGE", "On sauve l'image dans la gallery");
        imageView.setDrawingCacheEnabled(true);
        MediaStore.Images.Media.insertImage(this.getContentResolver(), b,name , "description");
    }


    public class DownloadImage extends AsyncTask<Void, Void, Bitmap>{
        String namee ;
       HttpURLConnection connection = null ;

        public DownloadImage(String namee ){
            this.namee = namee ;
            Log.i("DOWNLOAD IMAGE", "Le nom de l'image est : "+namee);
        }

        @Override
        protected Bitmap doInBackground(Void... params) {

            String url = SERVER_ADRESS + "/pictures/"+namee +".JPG" ;

            try{
                URL ad = new URL(url);
                connection = (HttpURLConnection) ad.openConnection();
                /*connection = new URL(url).openConnection();*/

                connection.setReadTimeout(1000 * 3);
                connection.setConnectTimeout(1000 * 3);



                return BitmapFactory.decodeStream((InputStream) connection.getContent() , null ,null);

            }
            catch (Exception e){
                Log.e("pbl load im", "pbl URL");
            }
            finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap!=null){
                b = bitmap ;
                downloadedImage.setImageBitmap(bitmap);
                try {

                        saveImageToGallery(downloadedImage, name.getText().toString(), b);
                        Toast.makeText(getApplicationContext(), "image sauvée en galerie", Toast.LENGTH_SHORT).show();


                }
                catch (Exception e){
                    Log.e("THUG" , "Tu n'as pas sauver l'image das la galerie");
                }
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
            Document doc = null;
            HttpURLConnection connection=null;
            try{
                connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setReadTimeout(1000 * 10);
                connection.setConnectTimeout(1000*10);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                InputStream is = (InputStream) connection.getContent();
                try {
                    doc = dBuilder.parse(is);
                }catch(IOException e){
                    Log.e("DOWNLOAD XML",e.getMessage());
                }
                boolean isEmpty = (doc==null);
                Log.d("DOWNLOAD XML", "Mon doc est-il vide ?" + isEmpty);


            }
            catch (Exception e){
                Log.e("pbl load im", "pbl URL");
            }finally {
                if(connection!=null){
                    connection.disconnect();
                }
            }

            return doc;
        }

        @Override
        protected void onPostExecute(Document doc) {
            super.onPostExecute(doc);

            if (doc!=null) {
                XMLdoc = doc;
                Log.d("DOWNLOAD", "Début de la post execution");
                try {
                    NodeList sellerNode = doc.getElementsByTagName("seller");
                    String seller = sellerNode.item(0).getTextContent();
                    Log.d("XMLParsing", "Nom du vendeur : " + seller);
                    NodeList titleNode = doc.getElementsByTagName("title");
                    String title = titleNode.item(0).getTextContent();
                    Log.d("XMLParsing", "Nom de l'image : " + title);
                    NodeList profitNode = doc.getElementsByTagName("profit");
                    int profit = Integer.valueOf(profitNode.item(0).getTextContent());
                    Log.d("XMLParsing", "Profit désiré : " + profit);
                    NodeList minPriceNode = doc.getElementsByTagName("minPrice");
                    int minPrice = Integer.valueOf(minPriceNode.item(0).getTextContent());
                    Log.d("XMLParsing", "Prix min: " + minPrice);
                    NodeList maxPriceNode = doc.getElementsByTagName("maxPrice");
                    int maxPrice = Integer.valueOf(maxPriceNode.item(0).getTextContent());
                    Log.d("XMLParsing", "Prix max: " + maxPrice);
                    NodeList currentPriceNode = doc.getElementsByTagName("currentPrice");
                    int currentPrice = Integer.valueOf(currentPriceNode.item(0).getTextContent());
                    Log.d("XMLParsing", "Prix courant: " + currentPrice);
                    NodeList nbBuyerNode = doc.getElementsByTagName("nbBuyer");
                    int nbBuyer = Integer.valueOf(nbBuyerNode.item(0).getTextContent());
                    Log.d("XMLParsing", "Nombre d'acheteur: " + nbBuyer);
                    NodeList dueToSellerNode = doc.getElementsByTagName("dueToSeller");
                    int dueToSeller = Integer.valueOf(dueToSellerNode.item(0).getTextContent());
                    Log.d("XMLParsing", "Dû au vendeur : " + dueToSeller);
                    image = new Image(title, seller, profit,minPrice,maxPrice,currentPrice,nbBuyer,dueToSeller  );
                    //Log.d("XMLParsing","\n prix courant : " + currentPrice);
                    Log.d("SAUVER IMAGE","On essaye...");
                    Log.d("META IMAGE","Titre : "+ title + " prix : " + currentPrice + " pseudo : "+ pseudo);
                    try{
                        baseImage.ajouterImage(title, currentPrice, pseudo);
                    }catch(NullPointerException e){
                        Log.e("BORDEL", e.getMessage());
                    }
                    Image upDatedImage = image.clone();
                    upDatedImage.update();
                    prixAchat=currentPrice;
                    new Thread(code).start();

                    try {
                        new UploadXML(upDatedImage, upDatedImage.getName()).execute();
                    }
                    catch (Exception e) {
                        Log.e("upLoad", "On a mal upload");
                    }

                } catch (NullPointerException e1) {
                    Log.e("DOWNLOAD XML", e1.getMessage());
                }
            }
            else{
                Toast.makeText(getApplicationContext() , "sorry wrong name try again !  ", Toast.LENGTH_SHORT).show();
            }
        }


    }



}

