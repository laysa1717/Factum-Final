package br.com.factum.app;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

public class PendentesActivity extends AppCompatActivity {
    ListView list_view_pendentes;
    List<Estudo> lstpendentes = new ArrayList<>();
    List<Integer> lstIdEstudo = new ArrayList<>();
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendentes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=this;

        list_view_pendentes = (ListView) findViewById(R.id.list_view_pendentes);
        buscarDados();
        estudoAdapter();

        list_view_pendentes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer idEstudo = lstIdEstudo.get(position);
                Intent intent = new Intent(context, DetalhesEstudoActivity.class);
                intent.putExtra("idEstudo", idEstudo);
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void buscarDados() {
        SQLiteDatabase db = new DataBaseHelper(context).getReadableDatabase();
        String select = "SELECT estudo._id, estudo.data, material.materia, material.assunto FROM tbl_estudo as estudo " +
                "INNER JOIN tbl_material as material " +
                "ON estudo.material=material._id " +
                "WHERE estudo.pendencia=0";

        Cursor cursor = db.rawQuery(select, null);

        if(cursor.getCount()> 0){
            cursor.moveToFirst();
            for(int i = 0 ; i <cursor.getCount(); i++){
                lstIdEstudo.add(cursor.getInt(0));
                lstpendentes.add(new Estudo(cursor.getString(1), cursor.getString(2), cursor.getString(3), false));
                cursor.moveToNext();
            }
        }else {
        }
        cursor.close();
    }
    private void estudoAdapter() {

        EstudoAdapter adapter = new EstudoAdapter(
                this,
                R.layout.list_view_item_estudo,
                lstpendentes
        );

        list_view_pendentes.setAdapter(adapter);

    }

}
