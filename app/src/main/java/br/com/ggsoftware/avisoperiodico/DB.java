package br.com.ggsoftware.avisoperiodico;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper{
    private static String dbName="lembrete.db";
    private static String tableName= "lembrete";

    private static String sql = "CREATE TABLE IF NOT EXISTS ["+ tableName +"] ([id] INTEGER PRIMARY KEY AUTOINCREMENT,[lembrete] TEXT,[dataHora] TEXT,[tipo] INTEGER, [tipoPeriodicidade] INTEGER, [periodicidade] INTEGER);";

    private static String sql2 = "DROP TABLE " + tableName;
    StringBuilder strb = new StringBuilder();

    private static int version = 1;
    public DB(Context context) {

        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
