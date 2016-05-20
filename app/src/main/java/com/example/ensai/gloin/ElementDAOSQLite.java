package com.example.ensai.gloin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by ensai on 10/05/16.
 */
public class ElementDAOSQLite extends SQLiteOpenHelper  {

    private static final int DATA_BASE_VERSION = 1 ;
    private static final String DATA_BASE_NAME ="MA BASE " ;
    SQLiteDatabase db ;

    public ElementDAOSQLite(Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db ) {
        db.execSQL("CREATE TABLE UTILISATEUR (Pseudo TEXT PRIMARY KEY , motDePasse TEXT , numero TEXT , mail TEXT , Gloin INTEGER )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void sauvegarderElements(List<Element> elements) {
        getWritableDatabase().delete("UTILISATEUR", null, null);
        for (Element element : elements) {
            ajouterElement(element);
        }
    }


    public List<Element> chargerElements() {
        List<Element> elements = new ArrayList<Element>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT Pseudo, MotDePasse, Numero ,Mail ,Gloin FROM UTILISATEUR", null);
        while (cursor.moveToNext()) {
            Element element = new Element();
            element.setPseudo(cursor.getString(0));
            element.setMotDePasse(cursor.getString(1));
            element.setNumero(cursor.getString(2));
            element.setMail(cursor.getString(3));
            element.setGloin(cursor.getInt(4));
            elements.add(element);
            /*Toast.makeText(this, ""+element.getPseudo(),Toast.LENGTH_SHORT).show();*/
            Log.i("TAG", "Pseudo " + element.getPseudo() + " mdp "+ element.getMotDePasse());
        }
        cursor.close();

        return elements;
    }


    public void ajouterElement(Element element) {
        ContentValues values = new ContentValues();
        values.put("pseudo", element.getPseudo());
        values.put("MotDePasse", element.getMotDePasse());
        values.put("numero" , element.getNumero());
        values.put("mail", element.getMail()) ;
        values.put("Gloin", element.getGloin());


        getWritableDatabase().insert("UTILISATEUR", null, values);
    }

    public boolean  pseudoInTheBase (String pseudo ){
        boolean validation ;
        int count ;
        Cursor cursor = getReadableDatabase().rawQuery(" SELECT MotDePasse FROM UTILISATEUR WHERE PSEUDO = ? ", new String []  {pseudo});
        Log.d("peudo in the base ", cursor.toString() + " compyr " + cursor.getCount());
        if (cursor.getCount()==1){
            validation = true ;
        }
        else {validation= false;}
        cursor.close();

        return validation;
    }

    public String findTheMdp (String pseudo){
        String motDePasse ;
        Cursor cursor = getReadableDatabase().rawQuery(" SELECT MotDePasse FROM UTILISATEUR WHERE PSEUDO = ? ;", new String []  {pseudo});
        Log.d("find the mot de paxa ", cursor.toString()+ " compyr " +cursor.getCount());
        cursor.moveToNext();
        motDePasse = cursor.getString(0);
        cursor.close();
        return motDePasse ;
    }


    public int trouverGloin (String pseudo){
        int gloin ;
        Cursor cursor = getReadableDatabase().rawQuery(" SELECT gloin FROM UTILISATEUR WHERE PSEUDO = ? ", new String []  {pseudo});
        cursor.moveToNext();
        gloin = cursor.getInt(0);
        cursor.close();
        return gloin ;
    }


    public void changerGloin (String pseudo, int minus){
        int gloin ;
        Cursor cursor = getReadableDatabase().rawQuery(" SELECT gloin FROM UTILISATEUR WHERE PSEUDO = ? ;", new String []  {pseudo});
        cursor.moveToNext();
        gloin = cursor.getInt(0);
        gloin= gloin - minus ;
        getReadableDatabase().rawQuery(" UPDATE UTILISATEUR SET Gloin  =" + gloin + " WHERE PSEUDO = ? ;", new String[]{pseudo});
        cursor.close();

    }





}
