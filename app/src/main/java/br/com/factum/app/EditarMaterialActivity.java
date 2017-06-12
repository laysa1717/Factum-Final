package br.com.factum.app;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.factum.app.database.DataBaseHelper;

public class EditarMaterialActivity extends AppCompatActivity {
    Context context;
    EditText edit_materia, edit_assunto, edit_note;
    int idMateria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_material);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        edit_materia=(EditText) findViewById(R.id.edit_materia);
        edit_assunto=(EditText) findViewById(R.id.edit_assunto);
        edit_note=(EditText) findViewById(R.id.edit_note);

        botaoFlutuante();
        Intent intent = getIntent();

        if(intent != null){
            idMateria= intent.getIntExtra("idMateria", 0);
            buscarDadosBanco();
        }
    }

    private void botaoFlutuante() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarEdicao();
            }
        });
    }
    public void salvarEdicao(){

        SQLiteDatabase db = new DataBaseHelper(this)
                .getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("materia", edit_materia.getText().toString());
        valores.put("assunto", edit_assunto.getText().toString());
        valores.put("note", edit_note.getText().toString());

        db.update("tbl_material", valores, "_id = ?" , new String[] {
                String.valueOf(idMateria)
        });

        Toast.makeText(this, "Atualizado com sucesso", Toast.LENGTH_LONG)
                .show();

        Intent intent = new Intent(context, DetalhesMaterialActivity.class);
        intent.putExtra("idMateria", idMateria);
        startActivity(intent);
    }

    private void buscarDadosBanco() {
        SQLiteDatabase db = new DataBaseHelper(this)
                .getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM tbl_material WHERE _id =?; "
                , new String[] {String.valueOf(idMateria)});


        if(cursor.getCount() >0){

            cursor.moveToFirst();

            String materia = cursor.getString(1);
            String assunto = cursor.getString(2);
            String nota = cursor.getString(3);

            edit_materia.setText(materia);
            edit_assunto.setText(assunto);
            edit_note.setText(nota);
            cursor.close();
        }
    }

}
