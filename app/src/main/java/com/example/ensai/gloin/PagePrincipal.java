package com.example.ensai.gloin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by ensai on 10/05/16.
 */
public class PagePrincipal extends AppCompatActivity {


    ElementDAOSQLite base ;




    int gloin =100 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        base =  new ElementDAOSQLite(this);
        List<Element>  elements = base.chargerElements() ;
        Intent intent = getIntent();
        String pseudo = intent.getStringExtra("pseudo");
        Log.i("passeINfo", pseudo) ;
         gloin =base.trouverGloin(pseudo);


        base.changerGloin(pseudo , 20);

        gloin=base.trouverGloin(pseudo);
        Log.i("passegloin", ""+gloin ) ;



        TextView result = (TextView) findViewById(R.id.nbrGloin);
        result.setText("vous avez :" + gloin + " Gloins");
    }






}
