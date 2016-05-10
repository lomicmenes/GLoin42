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
    }

    public void enregistrement (View v){
        Intent intent = new Intent (this ,Enregistrement.class) ;
        startActivity(intent) ;
    }



    //Commentaire inutile

}
