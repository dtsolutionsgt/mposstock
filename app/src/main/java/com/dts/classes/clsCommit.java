package com.dts.classes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.dts.base.BaseDatos;

public class clsCommit {

    private Context cont;

    private SQLiteDatabase db;
    private BaseDatos Con;

    private String errcode,errstr,sql;

    public clsCommit(Context context) {
        cont=context;
    }

    public String commit(String cmd) {
        errcode="000";errstr="";

        if (openDatabase()) {
            if (parseCommands(cmd)) executeCommit();
            closeDatabase();
        }

        return errcode+"\n"+errstr;
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
            errcode="002";errstr=e.getMessage();
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
            errcode="001";errstr="No se puede conectar a la base datos";return false;
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
