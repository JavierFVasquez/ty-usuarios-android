package com.imaginamos.usuariofinal.taxisya.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.imaginamos.taxisya.activities.MapaActivitys;
import com.imaginamos.usuariofinal.taxisya.Model.ResultsItem;
import com.imaginamos.usuariofinal.taxisya.R;

import java.util.List;

/**
 * Created by javiervasquez on 16/12/17.
 */

public class AutocompleteAdapter extends ArrayAdapter<ResultsItem> {

    final String TAG = "AutocompleteCustomArrayAdapter.java";

    Context mContext;
    int layoutResourceId;
    List<ResultsItem> data = null;

    public AutocompleteAdapter(Context mContext, int layoutResourceId, List<ResultsItem> data) {

        super(mContext, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        try{

            if(convertView==null){
                LayoutInflater inflater = ((MapaActivitys) mContext).getLayoutInflater();
                convertView = inflater.inflate(layoutResourceId, parent, false);
            }

            ResultsItem objectItem = data.get(position);

            TextView TV_Name = (TextView) convertView.findViewById(R.id.TV_Name);
            TextView TV_Vecinity = (TextView) convertView.findViewById(R.id.TV_Vecinity);
            TV_Name.setText(objectItem.getName());
            TV_Vecinity.setText(objectItem.getVicinity());



        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;

    }
}