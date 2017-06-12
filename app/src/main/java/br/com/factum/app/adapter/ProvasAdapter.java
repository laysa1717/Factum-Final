package br.com.factum.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.factum.app.R;
import br.com.factum.app.model.Provas;
import br.com.factum.app.picasso.CircleTransform;


/**
 * Created by 16165863 on 15/05/2017.
 */

public class ProvasAdapter extends ArrayAdapter<Provas> {
    int resource;
    public ProvasAdapter(Context context, int resource, List<Provas> objects){
        super(context, resource, objects);
        this.resource =  resource;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            v = LayoutInflater.from(getContext())
                    .inflate(resource, null);
        }

        Provas item = getItem(position);

        if (item != null) {


            ImageView img_provas = (ImageView) v.findViewById(R.id.image_simulados);
            TextView nome_provas = (TextView) v.findViewById(R.id.text_nome_prova);
            TextView dificuldade_provas = (TextView) v.findViewById(R.id.text_difi);
           // TextView qtdQuestões = (TextView) v.findViewById(R.id.text_quant);


            img_provas.setImageResource(item.getImagemProva());
            nome_provas.setText(item.getNomeProva());
            dificuldade_provas.setText(item.getDescricaoProva());
           // qtdQuestões.setText(item.getQuestoes());

            Picasso.with(getContext())
                    .load(item.getImagemProva())
                    .transform(new CircleTransform())
                    .into(img_provas);




        }

        return v;
    }

}





