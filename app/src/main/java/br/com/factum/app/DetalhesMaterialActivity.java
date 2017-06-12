package br.com.factum.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.com.factum.app.database.DataBaseHelper;

public class DetalhesMaterialActivity extends AppCompatActivity {
    Integer idMateria;
    Context context;
    TextView view_materia, view_assunto, view_note;
    Button btn_youtube;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_material);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        view_materia = (TextView) findViewById(R.id.text_view_materia);
        view_assunto = (TextView) findViewById(R.id.text_view_assunto);
        view_note = (TextView) findViewById(R.id.text_view_note);

        btn_youtube = (Button) findViewById(R.id.btn_Youtube);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if(intent != null){
            idMateria=  intent.getIntExtra("idMateria", 0);
            SQLiteDatabase db = new DataBaseHelper(this).getReadableDatabase();

            Cursor cursor = db.rawQuery("select * from tbl_material where _id=?;",
                    new String[]{idMateria.toString()});

            if(cursor.getCount()>0){
                cursor.moveToFirst();
                String materia = cursor.getString(1);
                String assunto = cursor.getString(2);
                String note = cursor.getString(3);
                view_materia.setText(materia);
                view_assunto.setText(assunto);
                view_note.setText(note);
                cursor.close();
            }
        }
    }

    public void abrirYoutube(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query="+ view_assunto.getText())));
        Log.i("Video", "Video Playing....");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_material, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_Editar:
                Intent intent = new Intent(this, EditarMaterialActivity.class);
                intent.putExtra("idMateria", idMateria);
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
        db.delete("tbl_material", "_id=?", new String[]{idMateria.toString()});
        Toast.makeText(this, "Excuido com sucesso", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, MaterialActivity.class));
    }

}
