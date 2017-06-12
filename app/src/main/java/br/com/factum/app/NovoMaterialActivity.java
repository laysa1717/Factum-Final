package br.com.factum.app;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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

public class NovoMaterialActivity extends AppCompatActivity {
    Context context;
    EditText edit_materia, edit_assunto, edit_note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_material);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        edit_materia=(EditText) findViewById(R.id.edit_materia);
        edit_assunto=(EditText) findViewById(R.id.edit_assunto);
        edit_note=(EditText) findViewById(R.id.edit_note);
        botaoFlutuante();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void botaoFlutuante() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Criando banco para escrita
                SQLiteDatabase db = new DataBaseHelper(context).getWritableDatabase();

                ContentValues cv = new ContentValues();
                cv.put("materia", edit_materia.getText().toString());
                cv.put("assunto", edit_assunto.getText().toString());
                cv.put("note", edit_note.getText().toString());
                db.insert("tbl_material",null, cv);

                Toast.makeText(context, "Salvo com sucesso.",
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, MaterialActivity.class);
                startActivity(intent);
            }
        });

    }

}
