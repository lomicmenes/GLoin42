package com.example.ensai.gloin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by ensai on 23/05/16.
 */
public class ImageDAOSQLITE extends SQLiteOpenHelper {


    private static final int DATA_BASE_VERSION = 1 ;
    private static final String DATA_BASE_NAME ="MA BASE IMAGE ";



    public ImageDAOSQLITE(Context context){
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IMAGE (ImageName TEXT PRIMARY KEY , Price INTEGER , PSEUDO TEXT  )");

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    public void ajouterImage(String name, int price, String pseudo) {
        Log.i("IMAGE META", "On ajoute une image dans SQLITE");
        Log.d("IMAGE META VALEURS", "Prix : "+ price);
        ContentValues values = new ContentValues();
        values.put("ImageName" ,name);
        values.put("Price" ,(Integer) price);
        values.put("pseudo", pseudo);
        getWritableDatabase().insert("IMAGE", null, values);
    }

    public List<Image> chargerImagesDepuisPseudo(String pseudo) {
        Log.d("IMAGEDAO", "On tente de charger les metadonnées internes sur les images");
        List<Image> images = new ArrayList<Image>();
        try {
            Cursor cursor = getReadableDatabase().rawQuery("SELECT ImageName,  Price , PSEUDO FROM IMAGE WHERE pseudo =? ", new String[]{pseudo});
            while (cursor.moveToNext()) {
                Log.i("RETOUR GLOIN", "Passage effectif dans la base image pour récupérer la liste des images");
                Image image = new Image();
                image.setName(cursor.getString(0));
                image.setPrice(cursor.getInt(1));
                image.setPseudo(cursor.getString(2));
                images.add(image);

            /*Toast.makeText(this, ""+element.getPseudo(),Toast.LENGTH_SHORT).show();*/
                Log.i("TAG", "Pseudo " + image.getPseudo() + " nom de l'image " + image.getName());
            }
            cursor.close();
        }catch (Exception e){
            Log.e("thug", " il a rien dans la base "+e.getMessage());

        }




        return images;
    }


    public  void UpdatePrice (String nameImage , String pseudo, int newPrice){
        getWritableDatabase().execSQL(" UPDATE IMAGE SET PRICE  = " + newPrice + " WHERE PSEUDO = ? and ImageName = ?  ;", new String[]{ pseudo , nameImage});
    }






}
