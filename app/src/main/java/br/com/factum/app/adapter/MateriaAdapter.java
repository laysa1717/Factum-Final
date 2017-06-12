package br.com.factum.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.factum.app.R;
import br.com.factum.app.model.Materia;


/**
 * Created by 16165880 on 03/04/2017.
 */

public class MateriaAdapter extends ArrayAdapter<Materia> {

    int resource;


    public MateriaAdapter(Context context, int resource, List<Materia> objects){
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if(v==null){
            v = LayoutInflater.from(getContext()).inflate(resource, null);
        }

        Materia materia = getItem(position);

        TextView item_materia = (TextView) v.findViewById(R.id.item_materia);
        TextView item_assunto = (TextView) v.findViewById(R.id.item_assunto);

        item_materia.setText(materia.getMateria());
        item_assunto.setText(materia.getAssunto());
        return v;

    }
}
