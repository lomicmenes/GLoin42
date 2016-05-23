package com.example.ensai.gloin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by ensai on 10/05/16.
 */
public class PagePrincipal extends AppCompatActivity {


    ElementDAOSQLite base ;
    ImageDAOSQLITE baseImage ;





    int gloin =100 ;
    String pseudo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        base =  new ElementDAOSQLite(this);
        List<Element>  elements = base.chargerElements() ;
        Intent intent = getIntent();
        pseudo = intent.getStringExtra("pseudo");
        Log.i("passeINfo", pseudo) ;


        gloin= base.trouverGloin(pseudo);
        Log.i("passegloin", ""+gloin ) ;


        TextView result = (TextView) findViewById(R.id.nbrGloin);
        result.setText("vous avez :" + gloin + " Gloins");


       //new  MettreAJour().execute() ;



    }


    private class MettreAJour extends AsyncTask < Void , Void , Integer    > {


        public MettreAJour() {}

        private int ajout ;
        List<Image> images = baseImage.chargerImageDepuisPseudo(pseudo) ;


        @Override
        protected Integer doInBackground(Void... params) {

            ajout = 0 ;

            // code pour recuperer les donnnees dans le xml
            for (Image image : images){
                ajout  = ajout +image.getPrice() ; /*- le truc recuperer  */
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
            startActivity(intent);
            Toast.makeText(this, " tu essais d'acheter ", Toast.LENGTH_SHORT).show();
        }

    }

    public void vendre (View v){
        Intent intent = new Intent(this, VenteImageActivity.class);
        intent.putExtra("pseudo", pseudo );
        startActivity(intent);

    }






}
