package br.com.ggsoftware.avisoperiodico;

/**
 * Created by f3861879 on 09/03/17.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LembreteDAO {

    private static String table_name = "lembrete";
    private static Context ctx;
    private String[] columns = { "id", "lembrete", "dataHora", "tipo", "tipoPeriodicidade", "periodicidade" };

    @SuppressWarnings("static-access")
    public LembreteDAO(Context ctx) {
        this.ctx = ctx;
    }

    public boolean insert(LembreteVO lembreteVO) {
        SQLiteDatabase db = new DB(ctx).getWritableDatabase();
        ContentValues ctv = new ContentValues();

        ctv.put("lembrete", lembreteVO.getLembrete());
        ctv.put("dataHora", lembreteVO.getDataHora());
        ctv.put("tipo", lembreteVO.getTipo());
        ctv.put("tipoPeriodicidade", lembreteVO.getTipoPeriodicidade());
        ctv.put("periodicidade", lembreteVO.getPeriodicidade());

        return (db.insert(table_name, null, ctv) > 0);
    }

    public boolean delete(LembreteVO lembreteVO) {
        SQLiteDatabase db = new DB(ctx).getWritableDatabase();
        return (db.delete(table_name, "id=?", new String[] { lembreteVO.getId().toString() }) > 0);
    }

    public boolean update(LembreteVO lembreteVO) {

        SQLiteDatabase db = new DB(ctx).getWritableDatabase();
        ContentValues ctv = new ContentValues();

        ctv.put("lembrete", lembreteVO.getLembrete());
        ctv.put("dataHora", lembreteVO.getDataHora());
        ctv.put("tipo", lembreteVO.getTipo());
        ctv.put("tipoPeriodicidade", lembreteVO.getTipoPeriodicidade());
        ctv.put("periodicidade", lembreteVO.getPeriodicidade());
        return (db.update(table_name, ctv, "id=?", new String[] { lembreteVO.getId().toString() }) > 0);
    }

    public LembreteVO buscaPorId(Integer id) {

        SQLiteDatabase db = new DB(ctx).getWritableDatabase();
        Cursor rs = db.query(table_name, columns, "id=?", new String[] { id.toString() }, null, null, null);

        LembreteVO lembreteVO = null;
        if (rs.moveToFirst()) {
            lembreteVO = new LembreteVO();
            lembreteVO.setId(rs.getInt(rs.getColumnIndex("id")));
            lembreteVO.setLembrete(rs.getString(rs.getColumnIndex("lembrete")));
            lembreteVO.setDataHora(rs.getString(rs.getColumnIndex("dataHora")));
            lembreteVO.setTipo(rs.getInt(rs.getColumnIndex("tipo")));
            lembreteVO.setTipoPeriodicidade(rs.getInt(rs.getColumnIndex("tipoPeriodicidade")));
            lembreteVO.setPeriodicidade(rs.getInt(rs.getColumnIndex("periodicidade")));
        }
        rs.close();
        db.close();
        return lembreteVO;
    }

    public List<LembreteVO> listar() {
        SQLiteDatabase db = new DB(ctx).getWritableDatabase();
        List<LembreteVO> listaLembreteVO = new ArrayList<LembreteVO>();
        Cursor rs = db.rawQuery("SELECT * FROM " + table_name, null);
        while (rs.moveToNext()) {
            LembreteVO lembreteVO = new LembreteVO();
            lembreteVO.setId(rs.getInt(rs.getColumnIndex("id")));
            lembreteVO.setLembrete(rs.getString(rs.getColumnIndex("lembrete")));
            lembreteVO.setDataHora(rs.getString(rs.getColumnIndex("dataHora")));
            lembreteVO.setTipo(rs.getInt(rs.getColumnIndex("tipo")));
            lembreteVO.setTipoPeriodicidade(rs.getInt(rs.getColumnIndex("tipoPeriodicidade")));
            lembreteVO.setPeriodicidade(rs.getInt(rs.getColumnIndex("periodicidade")));

            listaLembreteVO.add(lembreteVO);
        }
        rs.close();
        db.close();
        return listaLembreteVO;
    }



    public int proximoId() {
        SQLiteDatabase db = new DB(ctx).getWritableDatabase();
        Cursor rs = db.rawQuery("SELECT max(id) FROM " + table_name, null);
        rs.moveToFirst();
        int id = rs.getInt(0);
        id += 1;
        rs.close();
        db.close();
        return id;
    }
}
