package com.example.michele.lavoro.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;

import com.example.michele.lavoro.R;
import com.example.michele.lavoro.entity.Ordinazioni;


/**
 * Created by Michele on 13/07/2019.
 */

public class RecyclerAdapterOrdinazioni extends RecyclerView.Adapter<RecyclerAdapterOrdinazioni.MyView> implements ItemMoveChiamataRecycler.ItemTouchHelperContract {
    private Ordinazioni data;
    private final AvviaDragListener AvviaDragListener;



    public RecyclerAdapterOrdinazioni(Ordinazioni data, AvviaDragListener avviaDragListener) {
        AvviaDragListener = avviaDragListener;
        this.data = data;
    }


    public class MyView extends RecyclerView.ViewHolder {
        private TextView nomeOrdinazine;
       // private TextView tipologiaOrdinazine;
        private ImageView scambia;
        private View rigaView;

        public MyView(View view) {
            super(view);
            rigaView = view;
            nomeOrdinazine = itemView.findViewById(R.id.nomeText);
            //tipologiaOrdinazine = itemView.findViewById(R.id.tipologiaText);
            scambia = itemView.findViewById(R.id.imageView);
        }


    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View moveOrdView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_move_ordinazioni, parent, false);
        return new MyView(moveOrdView);
    }

    @Override
    public void onBindViewHolder(final MyView riga, int i) {
        riga.nomeOrdinazine.setText(data.getOrdinazioniList().get(i).getNome());
        //riga.tipologiaOrdinazine.setText(data.getOrdinazioniList().get(i).getTipologia());

        riga.scambia.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() ==
                        MotionEvent.ACTION_DOWN) {
                    AvviaDragListener.requestDrag(riga);
                }
                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.getOrdinazioniList().size();
    }


    @Override
    public void onRowMoved(int inzioPosizione, int finePosizione) {
        if (inzioPosizione < finePosizione) {
            for (int i = inzioPosizione; i < finePosizione; i++) {
                Collections.swap(data.getOrdinazioniList(), i, i + 1);
            }
        } else {
            for (int i = inzioPosizione; i > finePosizione; i--) {
                Collections.swap(data.getOrdinazioniList(), i, i - 1);
            }
        }
        notifyItemMoved(inzioPosizione, finePosizione);
    }

    @Override
    public void onRowSelected(MyView griglia) {
        griglia.rigaView.setBackgroundResource(R.drawable.buttonshape_true);

    }

    @Override
    public void onRowClear(MyView griglia) {
        griglia.rigaView.setBackgroundResource(R.drawable.listshape);

    }
}

