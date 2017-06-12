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

public class CadastroEstudoActivity extends AppCompatActivity {
    static EditText edit_data;
    Spinner spn_material;
    Context context;
    List<String> lstMateria = new ArrayList<>();
    List<Integer> listaIdMateria = new ArrayList<>();
    Integer idMateria = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_estudo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        edit_data=(EditText) findViewById(R.id.edit_data);
        spn_material=(Spinner) findViewById(R.id.spn_material);
        buscarDados();



        //Criar adapter para o spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, //contexto
                android.R.layout.simple_spinner_item,
                lstMateria
        );

        adapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);

        //Definindo o adapter no spinner
        spn_material.setAdapter(adapter);

        selecionarAtividade();

        botaoFlutuante();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    private void selecionarAtividade() {
        spn_material.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    public void abrirCalendario(View view){
            DialogFragment calFrag = new DatePickerFragment();
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
            edit_data.setText(dataFormatada);
        }
    }

    private void botaoFlutuante() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit_data.getText()==null || idMateria==null){
                    Toast.makeText(context, "Preencha todos os campos.",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("aaa", "nao entrou");
                    //Criando banco para escrita
                    SQLiteDatabase db = new
                            DataBaseHelper(context).getWritableDatabase();

                    ContentValues cv = new ContentValues();
                    cv.put("data", edit_data.getText().toString());
                    cv.put("material", idMateria.toString());
                    cv.put("pendencia", 0);
                    db.insert("tbl_estudo",null, cv);

                    Toast.makeText(context, "Salvo com sucesso.",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, EstudoActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}
