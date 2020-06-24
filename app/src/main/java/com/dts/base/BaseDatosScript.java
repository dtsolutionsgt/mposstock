package com.dts.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.dts.mpossocket.R;

public class BaseDatosScript {
	
	private Context vcontext;
	
	public BaseDatosScript(Context context) {
		vcontext=context;
	}
	
	public int scriptDatabase(SQLiteDatabase database) {
		try {
			if (scriptTablas(database)==0) return 0; else return 1;
		} catch (SQLiteException e) {
			msgbox(e.getMessage());
			return 0;
		}
	}

	private int scriptTablas(SQLiteDatabase db) {
		String sql;

		try {
            sql="CREATE TABLE [Test] ("+
                    "CODIGO TEXT NOT NULL,"+
                    "CANT INTEGER NOT NULL,"+
                    "PRIMARY KEY ([CODIGO])"+
                    ");";
            db.execSQL(sql);


            sql="CREATE TABLE [P_stock] ("+
                    "CODIGO_STOCK INTEGER NOT NULL,"+
                    "EMPRESA INTEGER NOT NULL,"+
                    "SUCURSAL INTEGER NOT NULL,"+
                    "CODIGO_PRODUCTO INTEGER NOT NULL,"+
                    "CANT REAL NOT NULL,"+
                    "CANTM REAL NOT NULL,"+
                    "PESO REAL NOT NULL,"+
                    "PESOM REAL NOT NULL,"+
                    "LOTE TEXT NOT NULL,"+
                    "FECHA_VENCE INTEGER NOT NULL,"+
                    "UNIDADMEDIDA TEXT NOT NULL,"+
                    "ANULADO INTEGER NOT NULL,"+
                    "ENVIADO INTEGER NOT NULL,"+
                    "CODIGOLIQUIDACION INTEGER NOT NULL,"+
                    "FECHA_SISTEMA INTEGER NOT NULL,"+
                    "DOCUMENTO TEXT NOT NULL,"+
                    "PRIMARY KEY ([CODIGO_STOCK])"+
                    ");";
            db.execSQL(sql);

            sql="CREATE INDEX P_stock_idx1 ON P_stock(CODIGO_PRODUCTO)";db.execSQL(sql);

			return 1;

		} catch (SQLiteException e) {
			msgbox(e.getMessage());
			return 0;
		}
	}

	public int scriptData(SQLiteDatabase db) {

		try {

            return 1;
		} catch (SQLiteException e) {
			msgbox(e.getMessage());
			return 0;
		}

	}
	
	private void msgbox(String msg) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(vcontext);
    	
		dialog.setTitle(R.string.app_name);
		dialog.setMessage(msg);

		dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int which) {}
    	});
		dialog.show();
	
	}   	
	
}