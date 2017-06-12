package br.com.factum.app;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import br.com.factum.app.database.DataBaseHelper;

public class DetalhesEstudoActivity extends AppCompatActivity {
    TextView view_data, view_materia, view_assunto, view_pendecia;
    CheckBox chk_pendencia;
    Context context;
    Integer idEstudo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_estudo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        view_data = (TextView) findViewById(R.id.view_data);
        view_materia= (TextView) findViewById(R.id.view_materia);
        view_assunto = (TextView) findViewById(R.id.view_assunto);
        view_pendecia = (TextView) findViewById(R.id.view_pendencia);
        chk_pendencia = (CheckBox) findViewById(R.id.chk_pendencia);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        if(intent != null){
            idEstudo=intent.getIntExtra("idEstudo", 0);
            SQLiteDatabase db = new DataBaseHelper(this).getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT estudo._id, estudo.data, material.materia, material.assunto, estudo.pendencia " +
                    "FROM tbl_estudo as estudo " +
                    "INNER JOIN tbl_material as material " +
                    "ON estudo.material=material._id " +
                    "WHERE estudo._id=?;",
                    new String[]{idEstudo.toString()});

            if(cursor.getCount()>0){
                cursor.moveToFirst();
                String data = cursor.getString(1);
                String materia = cursor.getString(2);
                String assunto = cursor.getString(3);
                view_data.setText(data);
                view_materia.setText(materia);
                view_assunto.setText(assunto);
                if(cursor.getInt(4)==1){
                    chk_pendencia.setChecked(true);
                    view_pendecia.setText("Conteúdo marcado como estudado.");
                }else{
                    chk_pendencia.setChecked(false);
                    view_pendecia.setText("Marcar como conteúdo estudado?");
                }
                cursor.close();
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_estudo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_Editar:
                Intent intent = new Intent(this, EditarEstudoActivity.class);
                intent.putExtra("idEstudo", idEstudo);
                startActivity(intent);

                break;

            case R.id.action_Excluir:
                confirmarExcluir();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void confirmarExcluir(){
        new AlertDialog.Builder(this).setTitle("Deletar")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Deseja realmente excluir este item?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        excluirMateria();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    public void excluirMateria(){
        SQLiteDatabase db = new DataBaseHelper(this).getWritableDatabase();
        db.delete("tbl_estudo", "_id=?", new String[]{idEstudo.toString()});
        Toast.makeText(this, "Excuido com sucesso", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, EstudoActivity.class));
    }

    public void atualizarPendencia(View view) {
        boolean checked = ((CheckBox)view).isChecked();
        switch (view.getId()){
            case R.id.chk_pendencia:
                if(checked){
                    SQLiteDatabase db = new DataBaseHelper(this).getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("pendencia", 1);
                    //// TODO: 06/06/2017 A laysa quer ir embora, tem que terminar o update!!!
                    db.update("tbl_estudo", cv, "_id = ?" , new String[]{
                            String.valueOf(idEstudo)});
                }else{
                    SQLiteDatabase db = new DataBaseHelper(this).getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("pendencia", 0);
                    db.update("tbl_estudo", cv, "_id = ?" , new String[]{
                            String.valueOf(idEstudo)});
                }
                break;
        }
    }
}
