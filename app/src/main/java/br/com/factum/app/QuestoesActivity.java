package br.com.factum.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import br.com.factum.app.adapter.PerguntasAdapter;
import br.com.factum.app.conexao.HttpConnection;
import br.com.factum.app.model.Perguntas;
import br.com.factum.app.model.Resposta;

public class QuestoesActivity extends AppCompatActivity {

    ProgressBar prg;

    int valor = 0;
    ListView list_view_questoes;
    List<Perguntas> lstSimulado = new ArrayList<>();
    PerguntasAdapter adapter;
    Context context;
    int position = 0;

    public boolean[] result;


    Perguntas[] LstPerguntas;

    //controlador das perguntas
    int index_pergunta = 0;


    ProgressDialog progressDialog;
    int acerto = 0;
    int erro = 0;
    TextView txt_pergunta;
    RadioButton radio1;
    RadioButton radio2;
    RadioButton radio3;
    RadioButton radio4;

    //RadioGroup em que todos os radioButtons estão inseridos
    RadioGroup radio_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questoes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prg = (ProgressBar) findViewById(R.id.progressBar);
        configurarBotaoFlutuante();

        context = this;

        //list_view_questoes = (ListView)findViewById(R.id.list_view_questoes);
        txt_pergunta = (TextView) findViewById(R.id.txt_pergunta_id);
        radio1 = (RadioButton) findViewById(R.id.radio1);
        radio2 = (RadioButton) findViewById(R.id.radio2);
        radio3 = (RadioButton) findViewById(R.id.radio3);
        radio4 = (RadioButton) findViewById(R.id.radio4);
        radio_group = (RadioGroup) findViewById(R.id.radio_group);


          if(verificarOnline() ==  true) {
                adapter = new PerguntasAdapter(
                        context,
                        R.layout.list_view_item_questoes,
                        lstSimulado
                );
             //   list_view_questoes.setAdapter(adapter);


                new perguntaTask().execute();

          }else if(verificarOnline() == false){
              new AlertDialog.Builder(context)
                      .setTitle("Desconectado")
                      .setMessage("Você está sem rede, não e possivel fazer o simulado")
                      .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              Intent intent = new Intent(context, PrincipalActivity.class);
                              startActivity(intent);
                          }
                      })
                      .create()
                      .show();

          }

        }


    private class perguntaTask extends AsyncTask<Void, Void, Void> {

        String retornoJson;


        @Override
        protected void onPreExecute() {
           progressDialog = ProgressDialog.show(QuestoesActivity.this, "Aguarde", null, true, true);

            super.onPreExecute();
        }


        @Override
        protected Void doInBackground(Void... params) {

            Intent intent = getIntent();

            Integer idProva = intent.getIntExtra("idProva", 0);




            String link = "http://outsidethebx.com.br/factum/conexaoandroid2.php?id="+idProva.toString();


            Log.d("doInBackground", link);

            retornoJson = HttpConnection.get(link);

            Log.d("doInBackground", retornoJson);

            return null;

        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();


            Gson gson = new Gson();
            LstPerguntas = gson.fromJson(retornoJson, Perguntas[].class);


            verificarPergunta();



            /*adapter.clear();
            adapter.addAll(perguntas);
            adapter.result = new boolean[perguntas.length];*/

        }
    }

    private void configurarBotaoFlutuante() {


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //verificar se chegou na ultima pergunta
                if(index_pergunta == LstPerguntas.length-1){
                    calcularAcertos();
                    return;
                }




                //verificar se preencher alguma resposta
                if(radio1.isChecked()){

                    LstPerguntas[index_pergunta].getRespostas()[0].setSelecionado(true);


                }else if(radio2.isChecked()){

                    LstPerguntas[index_pergunta].getRespostas()[1].setSelecionado(true);

                }else if(radio3.isChecked()){

                    LstPerguntas[index_pergunta].getRespostas()[2].setSelecionado(true);

                }else if(radio4.isChecked()){

                    LstPerguntas[index_pergunta].getRespostas()[3].setSelecionado(true);

                }else{
                    new AlertDialog.Builder(context)
                            .setTitle("Preencha")
                            .setIcon(R.drawable.factum1)
                            .setMessage("Responda a questão para passar para a próxima questão")
                            .create()
                            .show();
                    index_pergunta--;
                }


                index_pergunta++;


                //Limpar todos os Radio Buttons
                radio_group.clearCheck();



                verificarPergunta();






            }
        });
    }
    public boolean verificarOnline() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return manager.getActiveNetworkInfo() != null &&
                manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }



    private void verificarPergunta(){

        Perguntas item = LstPerguntas[index_pergunta];



        txt_pergunta.setText(item.getPergunta());

        Resposta[] respostas = item.getRespostas();

        radio1.setText(respostas[0].getTexto());
        radio1.setTag(respostas[0].getFal_ver());

        radio2.setText(respostas[1].getTexto());
        radio2.setTag(respostas[1].getFal_ver());

        radio3.setText(respostas[2].getTexto());
        radio3.setTag(respostas[2].getFal_ver());

        radio4.setText(respostas[3].getTexto());
        radio4.setTag(respostas[3].getFal_ver());

    }
    private void calcularAcertos() {
        int total = LstPerguntas.length;
        int QtdAcertos = 0;
        int erro = 0;
        //percorre a lista de perguntas
        for(Perguntas item : LstPerguntas){
            //percorre a lista de respostas
            for(Resposta r : item.getRespostas()){
                //verifica a opção selecionada pelo usuario
                if(r.getFal_ver() == 1 && r.isSelecionado()){

                    QtdAcertos ++;
                }else if (r.getFal_ver() == 0 && r.isSelecionado()){
                    erro ++;
                }
            }

        }

        Log.d("calcularAcertos", QtdAcertos+"");

       Intent intent = new Intent(context, ResultadoActivity.class);

        intent.putExtra("acerto", QtdAcertos);
        intent.putExtra("total", total);
        intent.putExtra("erro", erro);

       startActivity(intent);

    }

}


