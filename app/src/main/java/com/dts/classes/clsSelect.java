package com.dts.classes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dts.base.BaseDatos;

public class clsSelect {

    private Context cont;

    private SQLiteDatabase db;
    private BaseDatos Con;

    private String resultcode,resultstr;

    public clsSelect(Context context) {
        cont=context;
    }

    public String select(String cmd) {
        resultcode ="000";resultstr="";

        if (openDatabase()) {
            executeSelect(cmd);
            closeDatabase();
        }

        return "S\n"+resultcode +"\n"+resultstr;
    }

    public void dispose() {

    }

    //region Events

    //endregion

    //region Main

    private void executeSelect(String sql) {
        Cursor dt;
        int rc,cc;
        String ss;

        try {
            dt=Con.OpenDT(sql);
            if (dt.getCount()==0) return;
            rc=dt.getCount();
            cc=dt.getColumnCount();
        } catch (Exception e) {
            resultcode ="200";resultstr=e.getMessage();return;
        }

        try {
            resultstr=rc+"\n"+cc+"\n";

            dt.moveToFirst();
            while (!dt.isAfterLast()) {
                for (int i = 0; i <cc; i++) {
                    try {
                        ss=dt.getString(i);
                    } catch (Exception e) {
                        ss="";
                    }
                    resultstr+=ss+"\n";
                }
                dt.moveToNext();
            }
        } catch (Exception e) {
            resultcode ="201";resultstr=e.getMessage();return;
        }
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
