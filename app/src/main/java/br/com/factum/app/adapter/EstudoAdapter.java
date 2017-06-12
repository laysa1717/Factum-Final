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
import br.com.factum.app.model.Estudo;

/**
 * Created by 16165880 on 22/05/2017.
 */

public class EstudoAdapter extends ArrayAdapter<Estudo> {
    int resource;

    public EstudoAdapter(Context context, int resource, List<Estudo> objects){
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

        Estudo estudo = getItem(position);

        TextView item_data = (TextView) v.findViewById(R.id.item_data_estudo);
        TextView item_materia = (TextView) v.findViewById(R.id.item_materia_estudo);
        TextView item_assunto = (TextView) v.findViewById(R.id.item_assunto_estudo);

        item_data.setText(estudo.getData());
        item_materia.setText(estudo.getMateria());
        item_assunto.setText(estudo.getAssunto());
        return v;
    }
}
