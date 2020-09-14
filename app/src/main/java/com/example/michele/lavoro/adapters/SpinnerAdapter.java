package com.example.michele.lavoro.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.michele.lavoro.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michele on 04/07/2019.
 */

public class SpinnerAdapter extends ArrayAdapter<ItemDataSpinner> {
    int groupid;
    ArrayList<ItemDataSpinner> list;
    LayoutInflater inflater;

    public SpinnerAdapter(Activity context, int groupid, int id, ArrayList<ItemDataSpinner> list){
        super(context,id,list);
        this.list=list;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid=groupid;
    }

    public View getView(int position, View convertView, ViewGroup parent ){
        View itemView=inflater.inflate(groupid,parent,false);
        ImageView imageView=(ImageView)itemView.findViewById(R.id.img);
        imageView.setImageResource(list.get(position).getImageId());
        TextView textView=(TextView)itemView.findViewById(R.id.txt);
        textView.setText(list.get(position).getText());
        return itemView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent){
        return getView(position,convertView,parent);

    }
}

