package com.example.ensai.gloin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ensai on 10/05/16.
 */
public class PagePrincipal extends AppCompatActivity {

    int toto =10 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);


        if(true){
            Toast.makeText(this,"Bienvenue sur notre site. Voici un petit cadeau ",Toast.LENGTH_SHORT).show();
            Toast.makeText(this,"Votre compte a été crédité de : "+ toto +" Gloins ",Toast.LENGTH_SHORT).show();
        }

        TextView result = (TextView) findViewById(R.id.nbrGloin);
        result.setText("vous avez :" + toto + " Gloins");
    }


}
