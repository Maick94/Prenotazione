package com.example.michele.lavoro.fragment;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.michele.lavoro.ConfermaPrenotazione;
import com.example.michele.lavoro.MainActivity;
import com.example.michele.lavoro.R;
import com.example.michele.lavoro.adapters.ListAdapterOrdinazione;
import com.example.michele.lavoro.adapters.ListAdapterVisualizzaOrdinazioni;
import com.example.michele.lavoro.connessione.MenuAsync;
import com.example.michele.lavoro.connessione.TavoliOccupatiAsync;
import com.example.michele.lavoro.connessione.TavoliPortataAsync;
import com.example.michele.lavoro.connessione.VisualizzaPortataAsync;
import com.example.michele.lavoro.entity.Menu;
import com.example.michele.lavoro.entity.Ordinazione;
import com.example.michele.lavoro.entity.Ordinazioni;
import com.example.michele.lavoro.entity.Portata;
import com.example.michele.lavoro.entity.Portate;
import com.example.michele.lavoro.entity.Tavoli;
import com.example.michele.lavoro.entity.Tavolo;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class VisualizzaPrenotazioniFragment extends android.support.v4.app.Fragment {
    private static View view;
    private Context context;
    private String resultGson;




    private int numeroOrdinazioni;
    private String[] tipologia;
    private String[] nome;
    private int[] quantita;
    private String[] extra;

    private ListView portate_list;











    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // View
        view = inflater.inflate(R.layout.fragment_visualizza_prenotazioni, container, false);
        // Context
        context = getActivity();

        portate_list = (ListView) view.findViewById(R.id.listView);


        //connessione per prendere i tavoli occupati con la prenotazione per spinner
        Portate portate=null;

        try {
            resultGson = new TavoliPortataAsync(container.getContext()).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        portate = new Gson().fromJson(resultGson, Portate.class);



        //non ci sono tavoli occupati di conseguenza non ci sono prenotazioni di piatti
        if(portate.getPortateList()==null){
            view = inflater.inflate(R.layout.fragment_visualizza2_prenotazioni, container, false);
            final Button indirizza = (Button) view.findViewById(R.id.indirizza);
            indirizza.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    indirizza.setBackgroundResource(R.drawable.buttonshape_true);
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("Numero", (Serializable) 1);
                    context.startActivity(intent);
                }
            });

        }else {
            final Spinner tavolo = (Spinner) view.findViewById(R.id.spinner);
            ArrayList<String> arraySpinner = new ArrayList<String>();
            ArrayAdapter<String> adapter;
            //riempio lo spinner con il nome dei tavoli occupati
            int i = 0;
            for (i = 0; i < portate.getPortateList().size(); i++) {
                if (portate.getPortateList().get(i).getOrdinazioni() == "") {
                    portate.getPortateList().remove(i);
                    i = i - 1;

                }
            }
            Log.d("kkkk","sssss"+i);
            if (i <=0) {
                view = inflater.inflate(R.layout.fragment_visualizza2_prenotazioni, container, false);
                final Button indirizza = (Button) view.findViewById(R.id.indirizza);
                indirizza.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        indirizza.setBackgroundResource(R.drawable.buttonshape_true);
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("Numero", (Serializable) 1);
                        context.startActivity(intent);
                    }
                });
            } else {

                for (int j = 0; j < portate.getPortateList().size(); j++) {
                    arraySpinner.add(portate.getPortateList().get(j).getNomeTavolo());
                    adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arraySpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    tavolo.setAdapter(adapter);
                }


                //la scelta dello spinner
                tavolo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        String nomeTavolo = tavolo.getSelectedItem().toString();
                        Tavolo t = new Tavolo(tavolo.getSelectedItem().toString(), 0, 0);


                        //stringa contenente l'oggetto Gson da inviare al server
                        String visualizza = new Gson().toJson(t, Tavolo.class);

                        //connessione per prendere l'oggetto portata
                        Portata portata = null;
                        try {
                            resultGson = new VisualizzaPortataAsync(container.getContext()).execute(visualizza).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                        portata = new Gson().fromJson(resultGson, Portata.class);

                        Log.d("aaaaa", "ggggg" + portata.getOrdinazioni());


                        String[] portate = portata.getOrdinazioni().split("/");
                    /*for(int i=0; i<portate.length; i++)
                                       Log.d("kkkkk","hhhh"+"portate["+i+"] "+portate[i]);*/



                      /* Log.d("aaaaakhjkhk", "ggggghjkh" + portate.length);

                        for (int i = 0; i < portate.length; i++) {
                            Log.d("fdfdfg","wwwww"+portate[i]);
                        }*/

                        Ordinazioni or;
                        ArrayList<Ordinazione> list = new ArrayList<>();
                        for (int i = 0; i < portate.length; i++) {
                            if (!portate[i].equals("")) {
                                String[] str = portate[i].split("-");
                                Ordinazione o = new Ordinazione(str[0], str[1], Integer.parseInt(str[2]), str[3]);
                                list.add(o);
                            }

                        }
                        or = new Ordinazioni(list);
                     /*for(int i=0; i<or.getOrdinazioniList().size(); i++)
                               Log.d("aaaa","bbb"+or.getOrdinazioniList().get(i).getNome());*/

                        numeroOrdinazioni = or.getOrdinazioniList().size();
                        tipologia = new String[numeroOrdinazioni];
                        nome = new String[numeroOrdinazioni];
                        quantita = new int[numeroOrdinazioni];
                        extra = new String[numeroOrdinazioni];


                        for (int i = 0; i < numeroOrdinazioni; i++) {   //ok
                            tipologia[i] = or.getOrdinazioniList().get(i).getTipologia();
                            nome[i] = or.getOrdinazioniList().get(i).getNome();
                            quantita[i] = or.getOrdinazioniList().get(i).getQuantita();
                            extra[i] = or.getOrdinazioniList().get(i).getExtra();
                        }


                        portate_list.setAdapter(new ListAdapterVisualizzaOrdinazioni(container.getContext(), tipologia, nome, quantita, extra, or, nomeTavolo));
                    /*Log.d("container1","ssss "+container.getContext());*/


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {

                    }

                });


            }
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
