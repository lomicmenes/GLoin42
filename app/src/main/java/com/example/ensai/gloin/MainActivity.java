package com.example.ensai.gloin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ElementDAOSQLite base ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        base = new ElementDAOSQLite(this );

    }

    public void enregistrement (View v){
        Intent intent = new Intent (this ,Enregistrement.class) ;
        startActivity(intent) ;
    }



    public void validerMdp (View v){
        boolean validation =false  ;

        boolean validatio ;
        validatio = base.PseudoInTheBase("rpg") ;
        Toast.makeText(this , ""+validatio,Toast.LENGTH_SHORT).show();

        if(validation) {

            Intent intent = new Intent(this, PagePrincipal.class);
            startActivity(intent);
        }
    }




    //Commentaire inutile

}
