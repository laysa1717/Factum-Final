package br.com.factum.app;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import br.com.factum.app.adapter.PerguntasAdapter;
import br.com.factum.app.conexao.HttpConnection;
import br.com.factum.app.model.Perguntas;

public class SimuladosActivity extends AppCompatActivity {

    ListView list_view_questoes;
    List<Perguntas> lstsimulado = new ArrayList<>();
    Context context;
    PerguntasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulados);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        list_view_questoes=(ListView) findViewById(R.id.list_view_questoes);

         adapter = new PerguntasAdapter(
                context,
                R.layout.list_view_item_questoes,
                lstsimulado
        );
        list_view_questoes.setAdapter(adapter);

        new perguntaTask().execute();

    }

    private class perguntaTask extends AsyncTask<Void, Void, Void> {

        String retornoJson;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Void doInBackground(Void... params) {


            String link = "http://10.107.144.52/conexaoandroid2.php";

            retornoJson = HttpConnection.get(link);

            Log.d("doInBackground", retornoJson);

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Gson gson = new Gson();

            //fazendo conversão
            Perguntas[] perguntas = gson.fromJson(retornoJson, Perguntas[].class);

            adapter.clear();
            adapter.addAll(perguntas);

        }
    }

}
