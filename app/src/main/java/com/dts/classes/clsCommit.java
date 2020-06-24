package com.dts.classes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.dts.base.BaseDatos;

public class clsCommit {

    private Context cont;

    private SQLiteDatabase db;
    private BaseDatos Con;

    private String resultcode, resultstr,sql;

    public clsCommit(Context context) {
        cont=context;
    }

    public String commit(String cmd) {
        resultcode ="000";
        resultstr ="";

        if (openDatabase()) {
            if (parseCommands(cmd)) executeCommit();
            closeDatabase();
        }

        return "C\n"+resultcode +"\n"+ resultstr;
    }

    public void dispose() {

    }

    //region Events


    //endregion

    //region Main

    private void executeCommit() {
        try {
            db.execSQL(sql);
        } catch (Exception e) {
            resultcode ="100";
            resultstr =e.getMessage();
        }
    }

    private boolean parseCommands(String cmd) {
        sql=cmd;
        return true;
    }

    //endregion

    //region Database

    private boolean openDatabase() {
        try {
            Con = new BaseDatos(cont);
            db = Con.getWritableDatabase();
            Con.vDatabase =db;

            return true;
        } catch (Exception e) {
            resultcode ="010"; resultstr =e.getMessage();return false;
        }
    }

    private void closeDatabase() {
        try {
            Con.close();   }
        catch (Exception e) { }
    }

    //endregion

    //region Aux


    //endregion

}
