package br.com.factum.app;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.factum.app.adapter.MateriaAdapter;
import br.com.factum.app.database.DataBaseHelper;
import br.com.factum.app.model.Materia;

public class MaterialActivity extends AppCompatActivity {
    ListView list_view_materia;
    List<Materia> lstMateria = new ArrayList<>();
    List<Integer> listaIdMateria = new ArrayList<>();
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        list_view_materia=(ListView) findViewById(R.id.list_view_materia);

        buscarDados();
        materialAdapter();
        botaoFlutuante();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view_materia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer idMateria = listaIdMateria.get(position);
                Intent intent = new Intent(context, DetalhesMaterialActivity.class);
                intent.putExtra("idMateria", idMateria);
                startActivity(intent);
            }
        });


    }

    private void botaoFlutuante() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NovoMaterialActivity.class);
                startActivity(intent);
            }
        });
    }

    private void materialAdapter() {
        MateriaAdapter adapter = new MateriaAdapter(
                this,
                R.layout.list_view_item_materia,
                lstMateria
        );

        list_view_materia.setAdapter(adapter);
    }

    private void buscarDados() {
        SQLiteDatabase db = new DataBaseHelper(context).getReadableDatabase();

        String select = "SELECT * FROM tbl_material;";

        Cursor cursor = db.rawQuery(select, null);

        if(cursor.getCount()> 0){
            cursor.moveToFirst();

            for(int i = 0 ; i <cursor.getCount(); i++){
                listaIdMateria.add(cursor.getInt(0));
                lstMateria.add(new Materia(cursor.getString(1), cursor.getString(2), cursor.getString(3)));

                cursor.moveToNext();
            }
        }
        cursor.close();
    }
}
