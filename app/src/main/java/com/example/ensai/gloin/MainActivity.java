package com.example.ensai.gloin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent (this ,VenteImageActivity.class) ;
        startActivity(intent) ;
        finish();
    }

    public void enregistrement (View v){
        Intent intent = new Intent (this ,Enregistrement.class) ;
        startActivity(intent) ;
    }

    public void validerMdp (View v){
        Intent intent = new Intent (this ,PagePrincipal.class) ;
        startActivity(intent) ;
    }



    //Commentaire inutile

}
