package com.example.michele.lavoro.connessione;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.example.michele.lavoro.MainActivity;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Michele on 17/07/2019.
 */

public class InserisciPortataAsync extends AsyncTask<String, Void, String> {

    ProgressDialog loadingDialog;
    Context context;

    public InserisciPortataAsync(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = ProgressDialog.show(context, "Attendi",null, true, true);
    }

    @Override
    protected String doInBackground(String... params) {
        String data;
        String link;
        BufferedReader bufferedReader;
        String result;

        try{
            String portata=params[0];
            data = "?portata=" + URLEncoder.encode(portata, "UTF-8");
            link = "http://databaseandroid.altervista.org/php/Control/RegistraPortataControl.php" + data;
            Log.d("DEBUG:", link);
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            return result;
        } catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        loadingDialog.dismiss();
        if (result != null) {
            Boolean bool = new Gson().fromJson(result, Boolean.class);
            //Log.d("DEBUG:", "value--->"+bool);
            if(bool){
                Toast.makeText(context, "Ordinazione registrata correttamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("Numero", (Serializable) 2);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "Errore: Ordinazione non registrata controllare collegamento a internet.", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
