package com.example.michele.lavoro.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.michele.lavoro.ConfermaPrenotazione;
import com.example.michele.lavoro.MainActivity;
import com.example.michele.lavoro.R;
import com.example.michele.lavoro.adapters.ItemDataSpinner;
import com.example.michele.lavoro.adapters.ListAdapterOrdinazione;
import com.example.michele.lavoro.adapters.SpinnerAdapter;
import com.example.michele.lavoro.connessione.MenuAsync;
import com.example.michele.lavoro.connessione.TavoliOccupatiAsync;
import com.example.michele.lavoro.entity.Menu;
import com.example.michele.lavoro.entity.Ordinazione;
import com.example.michele.lavoro.entity.Ordinazioni;
import com.example.michele.lavoro.entity.Tavoli;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PrenotazioneFragment extends android.support.v4.app.Fragment {
    private static View view;
    private String resultGson;
    private Spinner tipologia_spinner;
    private Button visualizza;
    private int numeroPietanze;
    private String[] nomePietanza;
    private ListView menu_list;
    private ArrayList<Ordinazione> List=new ArrayList<>();
    private Ordinazioni ordinazioni;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_prenotazione, container, false);
        menu_list = (ListView) view.findViewById(R.id.listView);


        String[] array = getResources().getStringArray(R.array.tipologia_arrays);

        // Spinner
        ArrayList<ItemDataSpinner> list=new ArrayList<>();
        list.add(new ItemDataSpinner(array[0],R.drawable.primi_piatti));
        list.add(new ItemDataSpinner(array[1],R.drawable.secondi_piatti));
        list.add(new ItemDataSpinner(array[2],R.drawable.bevande));


        //spinner
        tipologia_spinner = (Spinner) view.findViewById(R.id.tipologia_spinner);

        SpinnerAdapter adapter=new SpinnerAdapter((Activity) getContext(), R.layout.custom_spinner,R.id.txt,list);
        tipologia_spinner.setAdapter(adapter);



        /*    Ogni volta che viene scelto un elemento dello spinner viene invocato la sync con il server       */
        tipologia_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                ItemDataSpinner item = (ItemDataSpinner) parent.getItemAtPosition(pos);
                Menu menu=null;
                try {
                    resultGson = new MenuAsync(container.getContext()).execute(item.getText().toLowerCase()).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                menu = new Gson().fromJson(resultGson, Menu.class);
                numeroPietanze = menu.getMenuList().size();
                nomePietanza= new String[numeroPietanze];
                for(int i = 0; i < numeroPietanze; i++)
                    nomePietanza[i] = menu.getMenuList().get(i).getNome();
                menu_list.setAdapter(new ListAdapterOrdinazione(container.getContext(),nomePietanza, menu ,List));
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        visualizza = (Button) view.findViewById(R.id.buttonVisualizza);
        visualizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visualizza.setBackgroundResource(R.drawable.buttonshape_true);
                if (List.size()==0){
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                    alertDialog.setTitle("Attenzione");
                    alertDialog.setMessage("Non ci sono ordinazioni fatte dal menu");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    visualizza.setBackgroundResource(R.drawable.buttonshape);
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                }
                else {
                    Tavoli tavoli = null;
                    try {
                        resultGson = new TavoliOccupatiAsync(container.getContext()).execute().get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    tavoli = new Gson().fromJson(resultGson, Tavoli.class);
                    if (tavoli.getTavoloList() == null) {
                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                        alertDialog.setTitle("Attenzione");
                        alertDialog.setMessage("Non ci sono tavoli prenotati. Prenotalo prima.....");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        intent.putExtra("Numero", (Serializable) 0);
                                        startActivity(intent);
                                    }
                                });
                        alertDialog.show();

                    } else {
                        ordinazioni = new Ordinazioni(List);
                        ordinazioni.setOrdinazioniList(List);
                      /*for (int i = 0; i < ordinazioni.getOrdinazioniList().size(); i++)
                        Log.d("hhhhhhhh", "-> " + ordinazioni.getOrdinazioniList().get(i).getTipologia() + "   " + ordinazioni.getOrdinazioniList().get(i).getNome() + "  " +
                                ordinazioni.getOrdinazioniList().get(i).getQuantita() + "   " + ordinazioni.getOrdinazioniList().get(i).getExtra());*/
                        Intent intent = new Intent(getActivity(), ConfermaPrenotazione.class);
                        intent.putExtra("Ordine", (Serializable) ordinazioni);
                        startActivity(intent);
                    }
                }
            }
        });
        return view;
    }
}




