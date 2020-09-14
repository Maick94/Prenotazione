package com.example.michele.lavoro.adapters;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.michele.lavoro.R;
import com.example.michele.lavoro.connessione.ModificaTavoloAsync;
import com.example.michele.lavoro.entity.Tavoli;
import com.example.michele.lavoro.entity.Tavolo;
import com.google.gson.Gson;


import java.util.ArrayList;



/**
 * Created by Michele on 16/07/2019.
 */

public class ListAdapterTavoloPrenotazione extends ArrayAdapter {
    private final Context context;
    private final String[] nomeTavolo;
    private final int[] numPostoTavolo;
    private Tavoli t;

    private Tavolo ta;




    public ListAdapterTavoloPrenotazione(Context context, String[] nomeTavolo, int[] numPostoTavolo, Tavoli t ) {
        super(context, R.layout.custom_list_tavolo, nomeTavolo);  //questo e importente nome pietaza altrimenti non esce la lista
        this.context = context;
        this.nomeTavolo = nomeTavolo;
        this.numPostoTavolo = numPostoTavolo;
        this.t= t;


    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View rowView = inflater.inflate(R.layout.custom_list_tavolo, parent, false);


        final TextView nome = (TextView) rowView.findViewById(R.id.textViewName);
        nome.setText(nomeTavolo[position]);
        final TextView titolo= (TextView) rowView.findViewById(R.id.textViewTitolo);
        titolo.setText("Numero posti tavolo");
        final TextView num = (TextView) rowView.findViewById(R.id.textViewPosti);
        num.setText(Integer.toString(numPostoTavolo[position]));


        final Button visualizzaDialog = (Button) rowView.findViewById(R.id.buttonEsegui);
        visualizzaDialog.setText("Prenota");
        visualizzaDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogView = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View rowView = inflater.inflate(R.layout.dialog_tavolo, parent, false);

                final Spinner posti = (Spinner) rowView.findViewById(R.id.spinner);

                ArrayList<String> arraySpinner = new ArrayList<String>();
                ArrayAdapter<String> adapter;
                for (int i=1;i<=t.getTavoloList().get(position).getNum_posti();i++){
                    arraySpinner.add(Integer.toString(i));
                    adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arraySpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    posti.setAdapter(adapter);
                }
                visualizzaDialog.setBackgroundResource(R.drawable.buttonshape_true);







                final Button conferma = (Button) rowView.findViewById(R.id.conferma);



                dialogView .setView(rowView);
                final AlertDialog dialog = dialogView .create();
                dialog.show();


                conferma.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int n=Integer.parseInt(posti.getSelectedItem().toString());
                        t.getTavoloList().get(position).setNum_posti_occupati(n);
                        ta=new Tavolo(t.getTavoloList().get(position).getNome(),t.getTavoloList().get(position).getNum_posti(),t.getTavoloList().get(position).getNum_posti_occupati());

                        String prenota = new Gson().toJson(ta, Tavolo.class);
                        new ModificaTavoloAsync(context).execute(prenota);
                        visualizzaDialog.setBackgroundResource(R.drawable.buttonshape);
                        conferma.setBackgroundResource(R.drawable.buttonshape_true);



                        //Log.d("aaaaa","bbb"+ta.getNome()+" "+ta.getNum_posti()+" "+ta.getNum_posti_occupati());

                        dialog.dismiss();





                    }
                });

                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        visualizzaDialog.setBackgroundResource(R.drawable.buttonshape);
                    }
                });






            }
        });




        return rowView;
    }

















}