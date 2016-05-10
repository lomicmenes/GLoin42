package com.example.ensai.gloin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by ensai on 06/05/16.
 */
public class Enregistrement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enregistrement);



    }
    public void validerEnregistrement (View v){
        boolean motDePasseCorrect;
        boolean rempli ;
        EditText motdepasse = (EditText) findViewById(R.id.motdepasse);
        EditText motdepasse2 = (EditText) findViewById(R.id.motdepasse2);
        EditText pseudo = (EditText) findViewById(R.id.pseudo);
        EditText mail = (EditText) findViewById(R.id.mail);
        EditText numero = (EditText) findViewById(R.id.numero);
        rempli = (!(motdepasse.getText().toString().equals("") ) && !(pseudo.getText().toString().equals("")) && !(mail.getText().toString().equals(""))  && !(numero .getText().toString().equals("")));

        motDePasseCorrect = ((String)motdepasse.getText().toString()).equals(motdepasse2.getText().toString());

        if (motDePasseCorrect && rempli) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else Toast.makeText(this ,"erreurs sur les mots de passe ou champs       non rempli",Toast.LENGTH_LONG).show();






}
}
