package com.example.ensai.gloin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
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
        String motdepasse =  findViewById(R.id.motdepasse).toString();
        String pseudo =  findViewById(R.id.pseudo).toString();
        boolean validation =false  ;

        boolean validatio ;
        validatio = base.pseudoInTheBase(pseudo) ;
        Toast.makeText(this , ""+validatio,Toast.LENGTH_SHORT).show();
        if (validatio) {
            if (motdepasse.equals(base.findTheMdp(pseudo))) {
                Intent intent = new Intent(this, PagePrincipal.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Mot de Passe faux REMEMBER ", Toast.LENGTH_SHORT).show();
            }
        }
        else { Toast.makeText(this , " Votre pseudo n'est pas dans la base ", Toast.LENGTH_SHORT).show();

       
    }




    //Commentaire inutile

    }}
