package com.example.michele.lavoro.connessione;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.example.michele.lavoro.ConfermaPrenotazione;
import com.example.michele.lavoro.MainActivity;
import com.example.michele.lavoro.R;
import com.example.michele.lavoro.adapters.ListAdapterVisualizzaOrdinazioni;
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
 * Created by Michele on 21/07/2019.
 */

public class ModificaPortataAsync extends AsyncTask<String, Void, String> {

    private ProgressDialog loadingDialog;
    private Context context;
    private String portata;

    public ModificaPortataAsync(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = ProgressDialog.show(context, "Attendi",null, true, true);
        loadingDialog.setMessage("Connessione in corso");
        loadingDialog.setCancelable(false);
    }

    @Override
    protected String doInBackground(String... params) {
        String result = null;

        if(isNetworkAvailable(context)){
            HttpURLConnection con = null;
            //boolean redirect = false;
            try{
                // Parametri profilo execute come parametri
                portata= params[0];

                // Dati nickname e le opportune modifiche effettuate (inoltrati al server)
                String data = "?portata=" + URLEncoder.encode(portata, "UTF-8");

                // Link
                String link = "http://databaseandroid.altervista.org/php/Control/ModificaPortataControl.php" + data;
                Log.d("DEBUG:", link);

                // URL
                URL url = new URL(link);

                // HttpURLConnection
                con = (HttpURLConnection) url.openConnection();

                // Timeout per la connessione
                int timeOutInMillis = 3000;
                con.setConnectTimeout(timeOutInMillis);

                // Connection
                con.connect();

                // Timeout nel ricerever informazioni
                int socketTimeOutinMillis = 3000;
                con.setReadTimeout(socketTimeOutinMillis);

                // InputStream risultato server
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                if(con.getHeaderField("Location") == null)
                    result = bufferedReader.readLine();
                bufferedReader.close();

                return result;
            }  catch (SocketTimeoutException e) {
                return null;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                con.disconnect();
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        loadingDialog.dismiss();

        if (result != null) {
            //Log.d("DEBUG:", "VALUE-->"+result.toString());
            Boolean bool = new Gson().fromJson(result, Boolean.class);

            if(bool) {
                Toast.makeText(context, "Modifica Portata avvenuta correttamente!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("Numero", (Serializable) 2);
                context.startActivity(intent);

            } else
                Toast.makeText(context, "Modifica Portata non avvenuta: Errore!", Toast.LENGTH_SHORT).show();
        } else {
            String msg = "Connessione lenta o non funzionante";
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(context);
            builder.setCancelable(false);
            builder.setTitle("Timeout connessione");
            builder.setMessage(msg);

            builder.setPositiveButton("Riprova", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                    //new LoginAsync(context).execute(profilo);
                }
            });

            builder.setNegativeButton("Esci", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @Override
    public void onCancelled(){
        super.onCancelled();
        cancel(true);
    }

    private static boolean isNetworkAvailable(Context context){
        return  ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}

