package br.com.factum.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import br.com.factum.app.R;
import br.com.factum.app.model.Perguntas;
import br.com.factum.app.model.Resposta;

/**
 * Created by 16165863 on 15/05/2017.
 */

public class PerguntasAdapter extends ArrayAdapter<Perguntas> {

    int resource;
    //HashMap<Integer, String> resp = new HashMap<Integer, String>();
    //boolean[] result;
    public boolean[] result;

    public PerguntasAdapter(Context context, int resource, List<Perguntas> objects){
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v;
        ViewHolder holder;

        if (convertView == null) {
            v = LayoutInflater.from(getContext())
                    .inflate(resource, null);
            holder = new ViewHolder(v);
            v.setTag(holder);

        }else{
            v = convertView;
            holder = (ViewHolder) v.getTag();
        }

        Perguntas item = getItem(position);

        if (item != null) {


            holder.txt_pergunta.setText(item.getPergunta());

            Resposta[] respostas = item.getRespostas();


            holder.radio1.setText(respostas[0].getTexto());
            holder.radio1.setTag(respostas[0].getFal_ver());

            holder.radio2.setText(respostas[1].getTexto());
            holder.radio2.setTag(respostas[1].getFal_ver());

            holder.radio3.setText(respostas[2].getTexto());
            holder.radio3.setTag(respostas[2].getFal_ver());

            holder.radio4.setText(respostas[3].getTexto());
            holder.radio4.setTag(respostas[3].getFal_ver());



            View.OnClickListener clickRadio = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    RadioButton r = (RadioButton) v;
                    int fal_ver = (int) r.getTag();
                    if(fal_ver==1){
                        result[position] = true;
                    }else if(fal_ver == 0){
                        result[position] = false;
                    }

                }
            };


            holder.radio1.setOnClickListener(clickRadio);

            holder.radio2.setOnClickListener(clickRadio);

            holder.radio3.setOnClickListener(clickRadio);

            holder.radio4.setOnClickListener(clickRadio);

        }
        return v;
    }


    public class ViewHolder {

        final  TextView txt_pergunta;
        final  RadioButton radio1;
        final  RadioButton radio2;
        final  RadioButton radio3;
        final  RadioButton radio4;

        public ViewHolder(View view) {
            txt_pergunta = (TextView) view.findViewById(R.id.txt_pergunta_id);
            radio1 = (RadioButton) view.findViewById(R.id.radio1);
            radio2 = (RadioButton) view.findViewById(R.id.radio2);
            radio3 = (RadioButton) view.findViewById(R.id.radio3);
            radio4 = (RadioButton) view.findViewById(R.id.radio4);
        }

    }




}
