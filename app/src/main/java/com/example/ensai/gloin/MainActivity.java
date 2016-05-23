package com.example.ensai.gloin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ElementDAOSQLite base;
    List<Element> elementList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        /*for ( Element element : elementList ) {

            /*Toast.makeText(this,""+element.getPseudo(),Toast.LENGTH_SHORT).show();
            Log.d("TAG", element.getPseudo());


        } */

        Runnable code = new Runnable() {
            @Override
            public void run() {
                base = new ElementDAOSQLite(getApplicationContext());
                elementList   = base.chargerElements();
                Log.e("MARCHE" , "c'est juste pour savoir si ca a effectuer ca ");
            }
        };
        new Thread(code).start();







    }

    public void enregistrement(View v) {
        Intent intent = new Intent(this, Enregistrement.class);
        startActivity(intent);
    }


    public void validerMdp(View v) {
        EditText motdepasse = (EditText) findViewById(R.id.motdepasseM);
        EditText pseudo = (EditText) findViewById(R.id.pseudoM);
        Log.i("affichage  ", pseudo.getText().toString() + " "  +motdepasse.getText().toString()) ;
        boolean validation = false;
        String mdp ;
        boolean validatio;

        validatio = base.pseudoInTheBase(pseudo.getText().toString());

        Log.i("debug", "" + validatio + " ");
        if (validatio) {
            if (motdepasse.getText().toString().equals(base.findTheMdp(pseudo.getText().toString()))) {
                Intent intent = new Intent(this, PagePrincipal.class);
                intent.putExtra("pseudo" ,pseudo.getText().toString() );
                startActivity(intent);
            } else {
                Toast.makeText(this, "Mot de Passe faux REMEMBER ", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, " Votre pseudo n'est pas dans la base ", Toast.LENGTH_SHORT).show();


        }


        //Commentaire inutile

    }


}
