package br.com.factum.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.factum.app.adapter.ProvasAdapter;
import br.com.factum.app.model.Provas;

public class EscolherSimuladoActivity extends AppCompatActivity {
    Integer idProva;
    ListView list_view_provas;
    List<Provas> lstProva = new ArrayList<>();
    Context context;
    ProvasAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_simulado);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view_provas = (ListView) findViewById(R.id.list_view_provas);

        context = this;


        lstProva.add(new Provas(1," Matemática",R.drawable.matematica20, "Toque para responder"));
        lstProva.add(new Provas(2," Física",R.drawable.teste, "Toque para responder"));
        lstProva.add(new Provas(3," Biologia",R.drawable.bio, "Toque para responder"));
        lstProva.add(new Provas(4," Quimica",R.drawable.qui, "Toque para responder"));
        /*lstProva.add(new Provas(5," Português",R.drawable.port, "Toque para responder"));
        lstProva.add(new Provas(6," Historia",R.drawable.hist, "Toque para responder"));
        lstProva.add(new Provas(7," Geografia",R.drawable.geo, "Toque para responder"));
        lstProva.add(new Provas(8," Inglês",R.drawable.ingles, "Toque para responder"));
        lstProva.add(new Provas(9," Espanhol",R.drawable.esp, "Toque para responder"));
        lstProva.add(new Provas(10," Sociologia",R.drawable.socio, "Toque para responder"));
        lstProva.add(new Provas(11," Filosofia",R.drawable.filo, "Toque para responder"));*/




        list_view_provas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                idProva = lstProva.get(position).getIdProva();

                Intent intent = new Intent(context, QuestoesActivity.class);

                intent.putExtra("idProva",idProva);
                startActivity(intent);
            }
        });


        adapter = new ProvasAdapter(
                context,
                R.layout.list_view_simulados,
                lstProva
        );

        list_view_provas.setAdapter(adapter);
    }









}


