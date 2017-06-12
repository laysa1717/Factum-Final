package br.com.factum.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 16165880 on 12/04/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static  final String NOME_BANCO  = "factum.db";
    private static final int VERSAO = 1;

    public DataBaseHelper(Context context){super (context, NOME_BANCO, null, VERSAO);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tbl_material("+
                "_id INTEGER primary key,"+
                "materia TEXT not null,"+
                "assunto TEXT not null,"+
                "note TEXT);");
        db.execSQL("create table tbl_estudo("+
                "_id INTEGER primary key," +
                "data TEXT not null," +
                "material INTEGER not null," +
                "pendencia TINYINT not null, "+
                "FOREIGN KEY(material) REFERENCES tbl_material(_id));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
