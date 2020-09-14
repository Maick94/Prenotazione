package com.example.michele.lavoro.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.michele.lavoro.R;
import com.example.michele.lavoro.connessione.MenuAsync;
import com.example.michele.lavoro.connessione.ModificaTavoloAsync;
import com.example.michele.lavoro.connessione.VisualizzaPortataAsync;
import com.example.michele.lavoro.entity.Menu;
import com.example.michele.lavoro.entity.Portata;
import com.example.michele.lavoro.entity.Tavoli;
import com.example.michele.lavoro.entity.Tavolo;
import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

/**
 * Created by Michele on 19/07/2019.
 */

public class ListAdapterTavoloVisualizzazione extends ArrayAdapter {
    private final Context context;
    private final String[] nomeTavolo;
    private final int[] numPostoTavolo;
    private final int[] numPostoTavoloOccupati;

    private String resultGson;




    private Tavoli t;

    private Tavolo ta;




    public ListAdapterTavoloVisualizzazione(Context context, String[] nomeTavolo, int[] numPostoTavolo, int[]numPostoTavoloOccupati, Tavoli t ) {
        super(context, R.layout.custom_list_tavolo, nomeTavolo);  //questo e importente nome pietaza altrimenti non esce la lista
        this.context = context;
        this.nomeTavolo = nomeTavolo;
        this.numPostoTavolo = numPostoTavolo;
        this.numPostoTavoloOccupati = numPostoTavoloOccupati;
        this.t= t;


    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        final View rowView = inflater.inflate(R.layout.custom_list_tavolo, parent, false);


        final TextView nome = (TextView) rowView.findViewById(R.id.textViewName);
        nome.setText(nomeTavolo[position]);
        final TextView titolo= (TextView) rowView.findViewById(R.id.textViewTitolo);
        titolo.setText("Numero posti occupati");
        final TextView num = (TextView) rowView.findViewById(R.id.textViewPosti);
        num.setText(Integer.toString(numPostoTavoloOccupati[position])+"/"+Integer.toString(numPostoTavolo[position]));


        final Button liberaTavolo = (Button) rowView.findViewById(R.id.buttonEsegui);
        liberaTavolo.setText("Libera");
        liberaTavolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                liberaTavolo.setBackgroundResource(R.drawable.buttonshape_true);
                t.getTavoloList().get(position).setNum_posti_occupati(0);
                ta=new Tavolo(t.getTavoloList().get(position).getNome(),t.getTavoloList().get(position).getNum_posti(),t.getTavoloList().get(position).getNum_posti_occupati());
                String libera = new Gson().toJson(ta, Tavolo.class);

                //connessione per verificare se il tavolo contine prenotazioni
                Portata portata=null;
                try{
                    resultGson=new VisualizzaPortataAsync(context).execute(libera).get();
                } catch (InterruptedException e) {
                     e.printStackTrace();
                } catch (ExecutionException e) {
                     e.printStackTrace();
                }
                portata = new Gson().fromJson(resultGson, Portata.class);
                if (portata.getOrdinazioni()==null)    //se la portata provenienete dal db è vuota significa che non ci sono piatti
                    new ModificaTavoloAsync(context).execute(libera);
                else if (portata.getOrdinazioni().equals(""))
                        new ModificaTavoloAsync(context).execute(libera);
                    else{
                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                        alertDialog.setTitle("Attenzione");
                    alertDialog.setMessage("Ci sono ancora dei piatti da servire impossibile liberare tavolo");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    liberaTavolo.setBackgroundResource(R.drawable.buttonshape);
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
        });





        return rowView;
    }

















}