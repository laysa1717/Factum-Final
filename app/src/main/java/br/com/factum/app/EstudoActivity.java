package br.com.factum.app;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.factum.app.adapter.EstudoAdapter;
import br.com.factum.app.database.DataBaseHelper;
import br.com.factum.app.model.Estudo;

public class EstudoActivity extends AppCompatActivity {
    ListView lst_view_estudo;
    List<Estudo> lstEstudo = new ArrayList<>();
    List<Integer> listaIdEstudo = new ArrayList<>();
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=this;
        lst_view_estudo = (ListView) findViewById(R.id.lst_estudo);
        botaoFlutuante();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        buscarDados();
        estudoAdapter();
        lst_view_estudo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer idEstudo = listaIdEstudo.get(position);
                Intent intent = new Intent(context, DetalhesEstudoActivity.class);
                intent.putExtra("idEstudo", idEstudo);
                startActivity(intent);
            }
        });
    }

    private void botaoFlutuante() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CadastroEstudoActivity.class);
                startActivity(intent);
            }
        });
    }
    private void buscarDados(){
        SQLiteDatabase db = new DataBaseHelper(context).getReadableDatabase();

        String select = "SELECT estudo._id, estudo.data, material.materia, material.assunto FROM tbl_estudo as estudo INNER JOIN tbl_material as material ON estudo.material=material._id";
        //String select = "SELECT * from tbl_estudo";

        Cursor cursor = db.rawQuery(select, null);

        if(cursor.getCount()> 0){
            cursor.moveToFirst();
            for(int i = 0 ; i <cursor.getCount(); i++){
                listaIdEstudo.add(cursor.getInt(0));
                lstEstudo.add(new Estudo(cursor.getString(1), cursor.getString(2), cursor.getString(3), false));

                cursor.moveToNext();
            }
        }else {
            Log.d("aaaa", "aaaaaa");
        }
        cursor.close();

    }
    private void estudoAdapter() {

        EstudoAdapter adapter = new EstudoAdapter(
                this,
                R.layout.list_view_item_estudo,
                lstEstudo
        );

        lst_view_estudo.setAdapter(adapter);

    }

}
