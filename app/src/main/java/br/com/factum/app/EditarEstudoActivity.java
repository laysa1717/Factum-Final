package br.com.factum.app;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.factum.app.database.DataBaseHelper;

public class EditarEstudoActivity extends AppCompatActivity {
    static EditText edit_data_edit;
    Spinner spn_materia;
    Context context;
    Integer idEstudo;
    Integer idMateria;
    List<String> lstMateria = new ArrayList<>();
    List<Integer> listaIdMateria = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_estudo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        spn_materia = (Spinner) findViewById(R.id.spn_material);
        edit_data_edit = (EditText) findViewById(R.id.edit_data_edit);
        buscarDados();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                lstMateria
        );
        adapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spn_materia.setAdapter(adapter);

        Intent intent = getIntent();

        if(intent!=null){
            Log.d("blabla", "Entrou");
            idEstudo = intent.getIntExtra("idEstudo",0);

            SQLiteDatabase db = new DataBaseHelper(context).getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM tbl_estudo WHERE _id =?; "
                    , new String[] {String.valueOf(idEstudo)});

            if(cursor.getCount() >0){

                cursor.moveToFirst();

                String data = cursor.getString(1);
                Integer idMateria = cursor.getInt(2);
                spn_materia.setSelection(idMateria);

                edit_data_edit.setText(data);
                cursor.close();
            }
        }
        selecionarAtividade();
        botaoFlutuante();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    private void selecionarAtividade() {
        spn_materia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idMateria = listaIdMateria.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void buscarDados(){
        SQLiteDatabase db = new DataBaseHelper(context).getReadableDatabase();

        listaIdMateria.add(0);
        lstMateria.add("Selecione uma matéria");

        String select = "SELECT * FROM tbl_material";

        Cursor cursor = db.rawQuery(select, null);

        if(cursor.getCount()> 0){
            cursor.moveToFirst();

            for(int i = 0 ; i <cursor.getCount(); i++){
                listaIdMateria.add(cursor.getInt(0));
                lstMateria.add(cursor.getString(1) + "/" + cursor.getString(2));
                cursor.moveToNext();
            }
        }
        cursor.close();
    }

    public void salvarEdicao(){

        SQLiteDatabase db = new DataBaseHelper(this)
                .getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("data", edit_data_edit.getText().toString());
        valores.put("material", idMateria);

        db.update("tbl_estudo", valores, "_id = ?" , new String[] {
                String.valueOf(idEstudo)
        });

        Toast.makeText(this, "Atualizado com sucesso", Toast.LENGTH_LONG)
                .show();

        Intent intent = new Intent(context, DetalhesEstudoActivity.class);
        intent.putExtra("idEstudo", idEstudo);
        startActivity(intent);
    }

    private void botaoFlutuante() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {salvarEdicao();}
        });
    }

    public void abrirCalendario(View view){
        DialogFragment calFrag = new EditarEstudoActivity.DatePickerFragment();
        calFrag.show(getSupportFragmentManager(), "datepicker");
    }
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){


            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        public void onDateSet(DatePicker view, int year, int month, int day) {
            String dataFormatada = String.format("%02d/%02d/%d", day, month+1, year);
            edit_data_edit.setText(dataFormatada);
        }
    }
}
