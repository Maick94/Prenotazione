package com.example.michele.lavoro.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.michele.lavoro.R;

import com.example.michele.lavoro.entity.Menu;
import com.example.michele.lavoro.entity.Ordinazione;

import java.util.ArrayList;


/**
 * Created by Michele on 04/07/2019.
 */

public class ListAdapterOrdinazione extends ArrayAdapter {
    private final Context context;
    private Menu m;
    private final String[] nomePietanza;
    private ArrayList<Ordinazione> List;
    private Ordinazione op;



    public ListAdapterOrdinazione(Context context, String[] nomePietanza ,Menu m , ArrayList<Ordinazione> List) {
        super(context, R.layout.custom_list_ordinazione, nomePietanza);
        this.context = context;
        this.nomePietanza = nomePietanza;
        this.m= m;
        this.List=List;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.custom_list_ordinazione, parent, false);
        ImageView icona = (ImageView) rowView.findViewById(R.id.imageView);
        String pathName = "@drawable/"+m.getMenuList().get(position).getImage();
        icona.setImageResource(context.getResources().getIdentifier(pathName, null, context.getPackageName()));

        final TextView nome = (TextView) rowView.findViewById(R.id.textViewName);
        nome.setText(nomePietanza[position]);

        final TextView prezzo = (TextView) rowView.findViewById(R.id.textViewPrezzo);
        prezzo.setText(m.getMenuList().get(position).getPrezzo()+" EUR");

        final Button visualizzaDialog = (Button) rowView.findViewById(R.id.buttonAggiungi);
        visualizzaDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visualizzaDialog.setBackgroundResource(R.drawable.buttonshape_true);
                AlertDialog.Builder dialogView = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View rowView = inflater.inflate(R.layout.dialog_ordinazione, parent, false);
                final EditText extra = (EditText) rowView.findViewById(R.id.edtextra);
                final EditText quantita = (EditText) rowView.findViewById(R.id.edtquantita);
                final Button aggiungi = (Button) rowView.findViewById(R.id.conferma);
                dialogView.setView(rowView);
                final AlertDialog dialog = dialogView.create();
                dialog.show();
                aggiungi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        aggiungi.setBackgroundResource(R.drawable.buttonshape_true);
                        String e;
                        int q;
                        if (extra.getText().toString().equals(""))
                            e="No";
                        else
                            e=extra.getText().toString();
                        q=Integer.parseInt(quantita.getText().toString());
                        op=new Ordinazione(m.getMenuList().get(position).getTipologia(),m.getMenuList().get(position).getNome(),q,e);
                        List.add(op);
                        /*for (int i=0;i<List.size();i++)
                            Log.d("DEBUG:", "-> "+  List.get(i).getTipologia()+"   "+  List.get(i).getNome()+"  "+
                                    List.get(i).getQuantita()+"   "+ List.get(i).getExtra());*/
                        visualizzaDialog.setBackgroundResource(R.drawable.buttonshape);
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