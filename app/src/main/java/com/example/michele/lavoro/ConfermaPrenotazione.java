package com.example.michele.lavoro;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.michele.lavoro.adapters.ItemMoveChiamataRecycler;
import com.example.michele.lavoro.adapters.RecyclerAdapterOrdinazioni;
import com.example.michele.lavoro.adapters.AvviaDragListener;
import com.example.michele.lavoro.connessione.InserisciPortataAsync;
import com.example.michele.lavoro.connessione.TavoliOccupatiAsync;
import com.example.michele.lavoro.entity.Ordinazioni;
import com.example.michele.lavoro.entity.Portata;
import com.example.michele.lavoro.entity.Tavoli;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class ConfermaPrenotazione extends AppCompatActivity implements AvviaDragListener {

    private RecyclerView recyclerView;
    private RecyclerAdapterOrdinazioni Adapter;
    private Ordinazioni o;
    private ItemTouchHelper touchHelper;

    private String resultGson;
    private Tavoli tavoli;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        o= (Ordinazioni) getIntent().getSerializableExtra("Ordine");


        try {
            resultGson = new TavoliOccupatiAsync(ConfermaPrenotazione.this).execute().get();
            tavoli = new Gson().fromJson(resultGson,  Tavoli.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_conferma_prenotazione);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);



        Adapter = new RecyclerAdapterOrdinazioni(o,this);

        ItemTouchHelper.Callback callback = new ItemMoveChiamataRecycler(Adapter);
        touchHelper  = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(Adapter);

        final Button visualizzaDialog = (Button) findViewById(R.id.confermaButton);
        visualizzaDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ConfermaPrenotazione.this);
                LayoutInflater inflater = (LayoutInflater) ConfermaPrenotazione.this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.dialog_servito, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog alertDialog = dialogBuilder.create();



                visualizzaDialog.setBackgroundResource(R.drawable.buttonshape_true);



                final Spinner tavolo= (Spinner) dialogView.findViewById(R.id.tavoloSpinner);

                ArrayList<String> arraySpinner = new ArrayList<String>();
                ArrayAdapter<String> adapter;
                for (int i=0;i<tavoli.getTavoloList().size();i++){
                    arraySpinner.add(tavoli.getTavoloList().get(i).getNome());
                    adapter = new ArrayAdapter<String>(ConfermaPrenotazione.this, android.R.layout.simple_spinner_item, arraySpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    tavolo.setAdapter(adapter);
                }


                final Button  chiudiOrdinazione= (Button) dialogView.findViewById(R.id.inviaButton);
                chiudiOrdinazione.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chiudiOrdinazione.setBackgroundResource(R.drawable.buttonshape);
                        String nomeTavolo=tavolo.getSelectedItem().toString();
                        String ordinazioni="";

                        for (int i=0;i<o.getOrdinazioniList().size();i++) {
                            String s = o.getOrdinazioniList().get(i).getTipologia()+"-"+o.getOrdinazioniList().get(i).getNome()+"-"+o.getOrdinazioniList().get(i).getQuantita()+"-"+o.getOrdinazioniList().get(i).getExtra();
                            if(ordinazioni=="")
                                      ordinazioni=s;
                            else
                               ordinazioni= ordinazioni+"/ "+s;
                        }




                        Portata portata=new Portata(nomeTavolo,ordinazioni);
                        String insersciPortata = new Gson().toJson(portata, Portata.class);
                        new InserisciPortataAsync(ConfermaPrenotazione.this).execute(insersciPortata);
                        alertDialog.dismiss();

                    }
                });
                 alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        visualizzaDialog.setBackgroundResource(R.drawable.buttonshape);
                    }
                });






                alertDialog.show();




            }

        });











    }






    @Override
    public void requestDrag(RecyclerView.ViewHolder viewHolder) {
        touchHelper.startDrag(viewHolder);
    }
}
