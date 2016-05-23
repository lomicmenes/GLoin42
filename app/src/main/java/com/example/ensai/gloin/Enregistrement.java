package com.example.ensai.gloin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by ensai on 06/05/16.
 */
public class Enregistrement extends AppCompatActivity {


    ElementDAOSQLite base ;
    public static final int REGISTER_GIFT=1000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enregistrement);



        Runnable code = new Runnable() {
            @Override
            public void run() {
                base = new ElementDAOSQLite(getApplicationContext());
                Log.e("MARCHE", "c'est juste pour savoir si ca a effectuer ca ");
            }
        };
        new Thread(code).start();

    }
    public void validerEnregistrement (View v){


        boolean motDePasseCorrect;
        boolean rempli ;
        boolean alreadyInBase ;
        EditText motdepasse = (EditText) findViewById(R.id.motdepasse);
        EditText motdepasse2 = (EditText) findViewById(R.id.motdepasse2);
        EditText pseudo = (EditText) findViewById(R.id.pseudo);
        EditText mail = (EditText) findViewById(R.id.mail);
        EditText numero = (EditText) findViewById(R.id.numero);
        rempli = (!(motdepasse.getText().toString().equals("") ) && !(pseudo.getText().toString().equals("")) && !(mail.getText().toString().equals(""))  && !(numero .getText().toString().equals("")));

        motDePasseCorrect = ((String)motdepasse.getText().toString()).equals(motdepasse2.getText().toString());
        alreadyInBase = base.pseudoInTheBase(pseudo.getText().toString());


        if (motDePasseCorrect && rempli && !alreadyInBase) {


           ;Element element = new Element(pseudo.getText().toString(),motdepasse.getText().toString(),numero.getText().toString(),mail.getText().toString(), REGISTER_GIFT);

            base.ajouterElement(element);
            Toast.makeText(this,"bienvenue "+element.getPseudo()+ " votre compte a été crédité de "+REGISTER_GIFT +"Gloins ",Toast.LENGTH_SHORT).show();


            Intent intent = new Intent(this, PagePrincipal.class);
            intent.putExtra("pseudo" ,pseudo.getText().toString() );
            startActivity(intent);
            finish();

        }

            else {
            if (alreadyInBase) {
                Toast.makeText(this, "pseudo déjà utilisé", Toast.LENGTH_LONG).show();
            }
            else {
            Toast.makeText(this, "erreurs sur les mots de passe ou champs non rempli", Toast.LENGTH_LONG).show();
            }
        }



}

}
