package com.example.michele.lavoro.fragment;

import android.content.Context;


import android.content.Intent;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.michele.lavoro.MainActivity;
import com.example.michele.lavoro.R;

import com.example.michele.lavoro.adapters.ListAdapterTavoloPrenotazione;
import com.example.michele.lavoro.connessione.TavoliLiberiAsync;

import com.example.michele.lavoro.connessione.TavoliOccupatiAsync;
import com.example.michele.lavoro.entity.Tavoli;
import com.example.michele.lavoro.entity.Tavolo;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Michele on 14/07/2019.
 */

public class TavoloPrenotazioneFragment extends android.support.v4.app.Fragment {
    private static View view;
    private Context context;
    private String resultGson;


    private int numeroTavoli;

    private String[] nomeTavolo;
    private int[] numPostiTavolo;

    private ListView tavoli_list;
    /*private ArrayList<Tavolo> List=new ArrayList<>();*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // View
        view = inflater.inflate(R.layout.fragment_tavolo_prenotazione, container, false);
        // Context
        context = getActivity();

        tavoli_list = (ListView) view.findViewById(R.id.listView);


        /************************************/
        /*Tavoli tavoli2=null;

        try {
            resultGson = new TavoliOccupatiAsync(container.getContext()).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        tavoli2 = new Gson().fromJson(resultGson, Tavoli.class);


        Log.d ("prova","sdsfsf"+tavoli2.getTavoloList().get(0));*/




        Tavoli tavoli=null;

        try {
            resultGson = new TavoliLiberiAsync(container.getContext()).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        tavoli = new Gson().fromJson(resultGson, Tavoli.class);


        if(tavoli.getTavoloList()==null){
            view = inflater.inflate(R.layout.fragment_tavolo2_prenotazione, container, false);
            final Button indirizza = (Button) view.findViewById(R.id.indirizza);
            indirizza.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    indirizza.setBackgroundResource(R.drawable.buttonshape_true);
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("Numero", (Serializable) 3);
                    context.startActivity(intent);
                }
            });


        }else{
            numeroTavoli = tavoli.getTavoloList().size();
            nomeTavolo= new String[numeroTavoli];
            numPostiTavolo= new int[numeroTavoli];


            for(int i = 0; i < numeroTavoli; i++){   //ok
                nomeTavolo[i] = tavoli.getTavoloList().get(i).getNome();
                numPostiTavolo[i] = tavoli.getTavoloList().get(i).getNum_posti();
            }
            tavoli_list.setAdapter(new ListAdapterTavoloPrenotazione(container.getContext(), nomeTavolo, numPostiTavolo, tavoli));

        }
        return view;
    }


    @Override
    public void setUserVisibleHint(boolean visibile) {
        super.setUserVisibleHint(visibile);
        if (visibile) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
}
