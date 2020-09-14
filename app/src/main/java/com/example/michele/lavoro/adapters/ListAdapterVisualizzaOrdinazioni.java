package com.example.michele.lavoro.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.michele.lavoro.MainActivity;
import com.example.michele.lavoro.R;
import com.example.michele.lavoro.connessione.ModificaPortataAsync;
import com.example.michele.lavoro.connessione.ModificaTavoloAsync;
import com.example.michele.lavoro.connessione.VisualizzaPortataAsync;
import com.example.michele.lavoro.entity.Ordinazioni;
import com.example.michele.lavoro.entity.Portata;
import com.example.michele.lavoro.entity.Tavoli;
import com.example.michele.lavoro.entity.Tavolo;
import com.example.michele.lavoro.fragment.VisualizzaPrenotazioniFragment;
import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

/**
 * Created by Michele on 20/07/2019.
 */

public class ListAdapterVisualizzaOrdinazioni extends ArrayAdapter {
    private final Context context;
    private String []tipologia;
    private String[] nome;
    private int[] quantita;
    private String[] extra;
    private Ordinazioni ordinazioni;
    private String nomeTavolo;
    private Portata portata;













  /*passare tutti i valori della stringa divisa*/
    public ListAdapterVisualizzaOrdinazioni(Context context, String []tipologia, String[] nome, int[] quantita, String[] extra, Ordinazioni ordinazioni, String nomeTavolo) {
        super(context, R.layout.custom_list_tavolo,tipologia);
        this.context = context;
        this.tipologia=tipologia;
        this.nome=nome;
        this.quantita=quantita;
        this.extra=extra;
        this.ordinazioni=ordinazioni;
        this.nomeTavolo=nomeTavolo;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        final View rowView = inflater.inflate(R.layout.custom_list_prenotazione, parent, false);

        final TextView t = (TextView) rowView.findViewById(R.id.textViewTipologia);
        t.setText(tipologia[position]);
        final TextView n = (TextView) rowView.findViewById(R.id.textViewNome);
        n.setText(nome[position]);
        final TextView q = (TextView) rowView.findViewById(R.id.textViewQuantita);
        q.setText("" + quantita[position]);
        final TextView e = (TextView) rowView.findViewById(R.id.textViewExtra);
        e.setText(extra[position]);


        final Button chiudiPrenotazione = (Button) rowView.findViewById(R.id.buttonChiudi);
        chiudiPrenotazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chiudiPrenotazione.setBackgroundResource(R.drawable.buttonshape_true);


                ordinazioni.getOrdinazioniList().remove(ordinazioni.getOrdinazioniList().get(position));




                String str = "";
                for (int i=0 ; i <ordinazioni.getOrdinazioniList().size(); i++) {
                    str = str+ordinazioni.getOrdinazioniList().get(i).getTipologia() + "-" + ordinazioni.getOrdinazioniList().get(i).getNome() + "-" + Integer.toString(ordinazioni.getOrdinazioniList().get(i).getQuantita()) + "-" + ordinazioni.getOrdinazioniList().get(i).getExtra() + "/";
                }
                portata = new Portata(nomeTavolo, str);
                String chiudi = new Gson().toJson(portata, Portata.class);
                new ModificaPortataAsync(context).execute(chiudi);


















            }
        });

        return rowView;
    }


}

















