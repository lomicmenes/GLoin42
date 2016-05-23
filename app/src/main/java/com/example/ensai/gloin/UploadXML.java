package com.example.ensai.gloin;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ensai.gloin.Image;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.util.ArrayList;

public class UploadXML extends AsyncTask< Void , Void , Void > {

    Image image ;
    String name;
    //Context context;

    public UploadXML(Image image, String name){
        this.image =  image;
        this.name=  name;
        //this.context=context;
    }

    @Override
    protected Void doInBackground(Void... params) {

        ArrayList<NameValuePair> dataToSend = new ArrayList<>();
        //Log.d("XML", "Mon joli xml : " + xml);

        dataToSend.add(new BasicNameValuePair("seller" , image.getSeller()));
        dataToSend.add(new BasicNameValuePair("due_to_seller" , String.valueOf(image.getDueToSeller())));
        dataToSend.add(new BasicNameValuePair("nb_buyer" , String.valueOf(image.getNbBuyer())));
        dataToSend.add(new BasicNameValuePair("name" , name)) ;
        dataToSend.add(new BasicNameValuePair("post_profit" , String.valueOf(image.getProfit()))) ;
        dataToSend.add(new BasicNameValuePair("post_current_price" , String.valueOf(image.getCurrentPrice()))) ;
        dataToSend.add(new BasicNameValuePair("post_min_price" , String.valueOf(image.getMinPrice()))) ;
        dataToSend.add(new BasicNameValuePair("post_max_price" , String.valueOf(image.getMaxPrice()))) ;
        Log.d("UPLOAD XML", "name : " + name + "\n seller : " + image.getSeller() + "\n due_to_seller : " + String.valueOf(image.getDueToSeller()) +
                "\n post_current_price : " + String.valueOf(image.getCurrentPrice()) + " \n post_min_price : " + String.valueOf(image.getMinPrice()) +
                " \n post_max_price : " + String.valueOf(image.getMaxPrice()) +
                " \n post_profit : " + String.valueOf(image.getProfit()));

        HttpParams httpParams = getHttpParams();

        HttpClient client = new DefaultHttpClient(httpParams);
        HttpPost post = new HttpPost(VenteImageActivity.SERVER_ADDRESS + "altUploadXML.php" );

        try{
            post.setEntity(new UrlEncodedFormEntity(dataToSend));
            Log.d("upload","Va le XML associé à " + name);
            client.execute(post);
        }
        catch(Exception e){
            Log.e("connection", "pbl de co ");
        }


        return null ;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        //Toast.makeText(context.getApplicationContext(), " Le XML est uploadé", Toast.LENGTH_SHORT).show();


    }

    private HttpParams getHttpParams() {
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 1000 * 30);
        HttpConnectionParams.setSoTimeout(httpParams, 1000*30);
        return httpParams ;

    }
}