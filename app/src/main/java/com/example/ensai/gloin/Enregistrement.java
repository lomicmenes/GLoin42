package com.example.ensai.gloin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
        Intent intent = new Intent (this ,MainActivity.class) ;
        startActivity(intent) ;


        

}
}
