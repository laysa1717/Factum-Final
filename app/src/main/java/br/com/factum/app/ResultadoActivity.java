package br.com.factum.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultadoActivity extends AppCompatActivity {
    int acerto;
    int erro;
    int nota;
    int totaldeperguntas;
    ImageView imagem;
    TextView txt_msg, txt_nota, txt_certo, txt_erro;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;


        imagem = (ImageView) findViewById(R.id.image_view) ;
        txt_msg = (TextView) findViewById(R.id.txt_msg);
        txt_nota = (TextView) findViewById(R.id.txt_nota);
        txt_certo = (TextView) findViewById(R.id.txt_certo);
        txt_erro = (TextView) findViewById(R.id.txt_erro);


        verificarNota();




        Intent intent = getIntent();
        Bundle dados = intent.getExtras();
       totaldeperguntas = dados.getInt("total");
        erro = dados.getInt("erro");
        acerto = dados.getInt("acerto");



        txt_nota.setText(String.valueOf(totaldeperguntas));

        txt_erro.setText(String.valueOf(erro));

        txt_certo.setText(String.valueOf(acerto));



    }


        private void verificarNota(){
            if(acerto <= 5){
                txt_msg.setText(String.valueOf("Estude Mais!!!!!"));
                imagem.setImageResource(R.drawable.erro);
            }else if(acerto >= 6){
                txt_msg.setText(String.valueOf("Você foi muito bem!!!!"));
                imagem.setImageResource(R.drawable.feliz);
            }

        }

    public void voltarHome(View view) {
        Intent i = new Intent(ResultadoActivity.this, PrincipalActivity.class);
        startActivity(i);
    }
}




