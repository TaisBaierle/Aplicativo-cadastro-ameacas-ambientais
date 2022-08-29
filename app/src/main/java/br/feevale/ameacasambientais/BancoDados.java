package br.feevale.ameacasambientais;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BancoDados {
    Context ctx;
    public static final String DATABASE_NAME = "AmeacasAmbientais.db";
    public static final Integer DATABASE_VERSION = 2;
    private SQLiteDatabase db;

    public BancoDados(Context ctx) {
        this.ctx = ctx;
        db = new AmeacasAmbientaisDatabaseHelper().getWritableDatabase();
    }

    public static class AmeacasAmbientaisTabela implements BaseColumns {
        public static final String NOME_TABELA = "AmeacasAmbientais";
        public static final String COLUNA_ENDERECO = "Endereco";
        public static final String COLUNA_DATA = "Data";
        public static String COLUNA_DESCRICAO = "Descricao";

        public static String getSQL() {
            String sql = "CREATE TABLE " + NOME_TABELA + " (" +
                    _ID + " INTEGER PRIMARY KEY, " +
                    COLUNA_ENDERECO + " TEXT, " +
                    COLUNA_DATA + " TEXT, " +
                    COLUNA_DESCRICAO + " TEXT)";
            return sql;
        }
    }

    public Long adicionarAmeacaAmbiental(AmeacasAmbientais ameaca) {
        ContentValues values = new ContentValues();
        values.put(AmeacasAmbientaisTabela.COLUNA_ENDERECO, ameaca.getEndereco());
        values.put(AmeacasAmbientaisTabela.COLUNA_DATA, ameaca.getData());
        values.put(AmeacasAmbientaisTabela.COLUNA_DESCRICAO, ameaca.getDescricao());

        return db.insert(AmeacasAmbientaisTabela.NOME_TABELA, null, values);
    }

    public Integer removerAmeacaAmbiental(AmeacasAmbientais ameaca) {
        String args[] = {ameaca.getId().toString()};
        return db.delete(AmeacasAmbientaisTabela.NOME_TABELA, AmeacasAmbientaisTabela._ID + "=?", args);
    }

    public Integer atualizarAmeacaAmbiental(AmeacasAmbientais ameaca) {
        String args[] = {ameaca.getId().toString()};
        ContentValues values = new ContentValues();
        values.put(AmeacasAmbientaisTabela.COLUNA_ENDERECO, ameaca.getEndereco());
        values.put(AmeacasAmbientaisTabela.COLUNA_DATA, ameaca.getData());
        values.put(AmeacasAmbientaisTabela.COLUNA_DESCRICAO, ameaca.getDescricao());
        return db.update(AmeacasAmbientaisTabela.NOME_TABELA, values, AmeacasAmbientaisTabela._ID + "=?", args);
    }

    @SuppressLint("Range")
    public AmeacasAmbientais getAmeacaAmbiental(Long id) {
        String cols[] = {AmeacasAmbientaisTabela._ID, AmeacasAmbientaisTabela.COLUNA_ENDERECO,
                AmeacasAmbientaisTabela.COLUNA_DATA, AmeacasAmbientaisTabela.COLUNA_DESCRICAO};
        String args[] = {id.toString()};
        Cursor cursor = db.query(AmeacasAmbientaisTabela.NOME_TABELA, cols,
                AmeacasAmbientaisTabela._ID + "=?", args, null, null, AmeacasAmbientaisTabela._ID);

        if (cursor.getCount() != 1) {
            return null;
        }
        cursor.moveToNext();
        AmeacasAmbientais ameaca = new AmeacasAmbientais();
        ameaca.setId(cursor.getLong(cursor.getColumnIndex(AmeacasAmbientaisTabela._ID)));
        ameaca.setEndereco(cursor.getString(cursor.getColumnIndex(AmeacasAmbientaisTabela.COLUNA_ENDERECO)));
        ameaca.setData(cursor.getString(cursor.getColumnIndex(AmeacasAmbientaisTabela.COLUNA_DATA)));
        ameaca.setDescricao(cursor.getString(cursor.getColumnIndex(AmeacasAmbientaisTabela.COLUNA_DESCRICAO)));

        return ameaca;
    }

    @SuppressLint("Range")
    public List<AmeacasAmbientais> getAmeacasAmbientais() {
        String cols[] = {AmeacasAmbientaisTabela._ID, AmeacasAmbientaisTabela.COLUNA_ENDERECO,
                AmeacasAmbientaisTabela.COLUNA_DATA, AmeacasAmbientaisTabela.COLUNA_DESCRICAO};
        Cursor cursor = db.query(AmeacasAmbientaisTabela.NOME_TABELA, cols,
                null, null, null, null, AmeacasAmbientaisTabela.COLUNA_DATA);
        List<AmeacasAmbientais> ameacas = new ArrayList<>();
        AmeacasAmbientais ameaca;

        while (cursor.moveToNext()) {
            ameaca = new AmeacasAmbientais();
            ameaca.setId(cursor.getLong(cursor.getColumnIndex(AmeacasAmbientaisTabela._ID)));
            ameaca.setEndereco(cursor.getString(cursor.getColumnIndex(AmeacasAmbientaisTabela.COLUNA_ENDERECO)));
            ameaca.setData(cursor.getString(cursor.getColumnIndex(AmeacasAmbientaisTabela.COLUNA_DATA)));
            ameaca.setDescricao(cursor.getString(cursor.getColumnIndex(AmeacasAmbientaisTabela.COLUNA_DESCRICAO)));
            ameacas.add(ameaca);
        }
        return ameacas;
    }

    private class AmeacasAmbientaisDatabaseHelper extends SQLiteOpenHelper {

        public AmeacasAmbientaisDatabaseHelper() {
            super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(AmeacasAmbientaisTabela.getSQL());
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int VersaoAntiga, int VersaoNova) {
            db.execSQL("DROP TABLE IF EXISTS " + AmeacasAmbientaisTabela.NOME_TABELA);
            onCreate(db);
        }
    }
}

