package com.example.ensai.gloin;

import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by ensai on 10/05/16.
 */
public class PagePrincipal extends AppCompatActivity {


    ElementDAOSQLite base ;
    ImageDAOSQLITE baseImage ;

    List<Image> images;




    int gloin =100 ;
    String pseudo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Intent intent = getIntent();
        pseudo = intent.getStringExtra("pseudo");
        Log.i("passeINfo", pseudo) ;


        Runnable code = new Runnable() {
            @Override
            public void run() {
                base = new ElementDAOSQLite(getApplicationContext());
                baseImage = new ImageDAOSQLITE(getApplicationContext());
                gloin= base.trouverGloin(pseudo);
                images = baseImage.chargerImagesDepuisPseudo(pseudo);
                Log.i("passegloin", ""+gloin ) ;
                Log.i("THREAD", "C'est juste pour savoir si on empêche les frames de sauter ");
                for (Image image : images) {
                    Log.i("thug", "images en base " + image.getName()+ " price "+ image.getPrice());

                }
                TextView result = (TextView) findViewById(R.id.nbrGloin);
                result.setText("Vous avez :" + gloin + " Gloins");

                if (images != null) {
                    Log.d("GLOINS", "On fait une mise à jour");
                    new MettreAJour().execute();
                }
            }
        };
        new Thread(code).start();

        //new  MettreAJour().execute() ;
    }


    private class MettreAJour extends AsyncTask < Void , Void , Integer    > {

        public MettreAJour() {}

        private int ajout;

        @Override
        protected Integer doInBackground(Void... params) {


            ajout = 0;
            Image downloadedImage = null ;

            if (images.size()!=0) {
                Log.i("RETOUR GLOIN", "On a des images en base");

                // code pour recuperer les donnnees dans le xml
                for (Image image : images) {

                    String url = AchatImageActvity.SERVER_ADRESS + "/pictures/"+image.getName() +".xml" ;
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
                        downloadedImage = new Image(title, seller, profit,minPrice,maxPrice,currentPrice,nbBuyer,dueToSeller  );
                        //Log.d("XMLParsing","\n prix courant : " + currentPrice);

                    }catch (Exception e){
                        Log.e("ATG", e.getMessage());
                    }
                    if (downloadedImage.getCurrentPrice()!= 0) {

                        int price = image.getPrice();
                        int newPrice = downloadedImage.getCurrentPrice();
                        ajout = ajout + price - newPrice;
                        Log.d("thug" , "ajout qu'on fait "+ ajout ) ;
                        baseImage.UpdatePrice(image.getName(), image.getPseudo(), newPrice);
                        Log.d("UPDATE BASE IMAGE", "Ancien prix de l'image " + image.getName() + " : " + price);
                        Log.d("UPDATE BASE IMAGE","Nouveau prix de l'image "+ image.getName() + " : "+ newPrice);
                    }

                }

            }
            return ajout;
        }

        @Override
        protected void onPostExecute(Integer ajout ) {
            super.onPostExecute(ajout);

            if (ajout !=0){
                base.changerGloin(pseudo ,ajout);
                Toast.makeText(getApplicationContext() ,"Vous avez gagné :" + ajout + " Gloin ",Toast.LENGTH_SHORT).show();
            }


        }
    }

    public void acheter (View v) {
        if (gloin ==0){ Toast.makeText(this, "tu n'as plus de Gloin :( passe à la boutique pour en racheter" ,Toast.LENGTH_SHORT).show();}
        else {
            Intent intent = new Intent(this, AchatImageActvity.class);
            intent.putExtra("pseudo", pseudo);
            startActivity(intent);
            Toast.makeText(this, " tu essais d'acheter ", Toast.LENGTH_SHORT).show();
        }

    }

    public void vendre (View v){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        Intent intent = new Intent(this, VenteImageActivity.class);
        intent.putExtra("pseudo", pseudo );
        startActivity(intent);

    }
}
