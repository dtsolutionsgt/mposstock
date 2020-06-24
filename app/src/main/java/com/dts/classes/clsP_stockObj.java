package com.dts.classes;

import java.util.ArrayList;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.dts.base.BaseDatos;
import com.dts.base.clsClasses;

public class clsP_stockObj {

    public int count;

    private Context cont;
    private BaseDatos Con;
    private SQLiteDatabase db;
    public BaseDatos.Insert ins;
    public BaseDatos.Update upd;
    private clsClasses clsCls = new clsClasses();

    private String sel="SELECT * FROM P_stock";
    private String sql;
    public ArrayList<clsClasses.clsP_stock> items= new ArrayList<clsClasses.clsP_stock>();

    public clsP_stockObj(Context context, BaseDatos dbconnection, SQLiteDatabase dbase) {
        cont=context;
        Con=dbconnection;
        ins=Con.Ins;upd=Con.Upd;
        db = dbase;
        count = 0;
    }

    public void reconnect(BaseDatos dbconnection, SQLiteDatabase dbase) {
        Con=dbconnection;
        ins=Con.Ins;upd=Con.Upd;
        db = dbase;
    }

    public void add(clsClasses.clsP_stock item) {
        addItem(item);
    }

    public void update(clsClasses.clsP_stock item) {
        updateItem(item);
    }

    public void delete(clsClasses.clsP_stock item) {
        deleteItem(item);
    }

    public void delete(int id) {
        deleteItem(id);
    }

    public void fill() {
        fillItems(sel);
    }

    public void fill(String specstr) {
        fillItems(sel+ " "+specstr);
    }

    public void fillSelect(String sq) {
        fillItems(sq);
    }

    public clsClasses.clsP_stock first() {
        return items.get(0);
    }


    // Private

    private void addItem(clsClasses.clsP_stock item) {

        ins.init("P_stock");

        ins.add("CODIGO_STOCK",item.codigo_stock);
        ins.add("EMPRESA",item.empresa);
        ins.add("SUCURSAL",item.sucursal);
        ins.add("CODIGO_PRODUCTO",item.codigo_producto);
        ins.add("CANT",item.cant);
        ins.add("CANTM",item.cantm);
        ins.add("PESO",item.peso);
        ins.add("PESOM",item.pesom);
        ins.add("LOTE",item.lote);
        ins.add("FECHA_VENCE",item.fecha_vence);
        ins.add("UNIDADMEDIDA",item.unidadmedida);
        ins.add("ANULADO",item.anulado);
        ins.add("ENVIADO",item.enviado);
        ins.add("CODIGOLIQUIDACION",item.codigoliquidacion);
        ins.add("FECHA_SISTEMA",item.fecha_sistema);
        ins.add("DOCUMENTO",item.documento);

        db.execSQL(ins.sql());

    }

    private void updateItem(clsClasses.clsP_stock item) {

        upd.init("P_stock");

        upd.add("EMPRESA",item.empresa);
        upd.add("SUCURSAL",item.sucursal);
        upd.add("CODIGO_PRODUCTO",item.codigo_producto);
        upd.add("CANT",item.cant);
        upd.add("CANTM",item.cantm);
        upd.add("PESO",item.peso);
        upd.add("PESOM",item.pesom);
        upd.add("LOTE",item.lote);
        upd.add("FECHA_VENCE",item.fecha_vence);
        upd.add("UNIDADMEDIDA",item.unidadmedida);
        upd.add("ANULADO",item.anulado);
        upd.add("ENVIADO",item.enviado);
        upd.add("CODIGOLIQUIDACION",item.codigoliquidacion);
        upd.add("FECHA_SISTEMA",item.fecha_sistema);
        upd.add("DOCUMENTO",item.documento);

        upd.Where("(CODIGO_STOCK="+item.codigo_stock+")");

        db.execSQL(upd.sql());

        //Toast toast= Toast.makeText(cont,upd.sql(), Toast.LENGTH_LONG);toast.show();

    }

    private void deleteItem(clsClasses.clsP_stock item) {
        sql="DELETE FROM P_stock WHERE (CODIGO_STOCK="+item.codigo_stock+")";
        db.execSQL(sql);
    }

    private void deleteItem(int id) {
        sql="DELETE FROM P_stock WHERE id=" + id;
        db.execSQL(sql);
    }

    private void fillItems(String sq) {
        Cursor dt;
        clsClasses.clsP_stock item;

        items.clear();

        dt=Con.OpenDT(sq);
        count =dt.getCount();
        if (dt.getCount()>0) dt.moveToFirst();

        while (!dt.isAfterLast()) {

            item = clsCls.new clsP_stock();

            item.codigo_stock=dt.getInt(0);
            item.empresa=dt.getInt(1);
            item.sucursal=dt.getInt(2);
            item.codigo_producto=dt.getInt(3);
            item.cant=dt.getDouble(4);
            item.cantm=dt.getDouble(5);
            item.peso=dt.getDouble(6);
            item.pesom=dt.getDouble(7);
            item.lote=dt.getString(8);
            item.fecha_vence=dt.getInt(9);
            item.unidadmedida=dt.getString(10);
            item.anulado=dt.getInt(11);
            item.enviado=dt.getInt(12);
            item.codigoliquidacion=dt.getInt(13);
            item.fecha_sistema=dt.getInt(14);
            item.documento=dt.getString(15);

            items.add(item);

            dt.moveToNext();
        }

        if (dt!=null) dt.close();

    }

    public int newID(String idsql) {
        Cursor dt=null;
        int nid;

        try {
            dt=Con.OpenDT(idsql);
            dt.moveToFirst();
            nid=dt.getInt(0)+1;
        } catch (Exception e) {
            nid=1;
        }

        if (dt!=null) dt.close();

        return nid;
    }

    public String addItemSql(clsClasses.clsP_stock item) {

        ins.init("P_stock");

        ins.add("CODIGO_STOCK",item.codigo_stock);
        ins.add("EMPRESA",item.empresa);
        ins.add("SUCURSAL",item.sucursal);
        ins.add("CODIGO_PRODUCTO",item.codigo_producto);
        ins.add("CANT",item.cant);
        ins.add("CANTM",item.cantm);
        ins.add("PESO",item.peso);
        ins.add("PESOM",item.pesom);
        ins.add("LOTE",item.lote);
        ins.add("FECHA_VENCE",item.fecha_vence);
        ins.add("UNIDADMEDIDA",item.unidadmedida);
        ins.add("ANULADO",item.anulado);
        ins.add("ENVIADO",item.enviado);
        ins.add("CODIGOLIQUIDACION",item.codigoliquidacion);
        ins.add("FECHA_SISTEMA",item.fecha_sistema);
        ins.add("DOCUMENTO",item.documento);

        return ins.sql();

    }

    public String updateItemSql(clsClasses.clsP_stock item) {

        upd.init("P_stock");

        upd.add("EMPRESA",item.empresa);
        upd.add("SUCURSAL",item.sucursal);
        upd.add("CODIGO_PRODUCTO",item.codigo_producto);
        upd.add("CANT",item.cant);
        upd.add("CANTM",item.cantm);
        upd.add("PESO",item.peso);
        upd.add("PESOM",item.pesom);
        upd.add("LOTE",item.lote);
        upd.add("FECHA_VENCE",item.fecha_vence);
        upd.add("UNIDADMEDIDA",item.unidadmedida);
        upd.add("ANULADO",item.anulado);
        upd.add("ENVIADO",item.enviado);
        upd.add("CODIGOLIQUIDACION",item.codigoliquidacion);
        upd.add("FECHA_SISTEMA",item.fecha_sistema);
        upd.add("DOCUMENTO",item.documento);

        upd.Where("(CODIGO_STOCK="+item.codigo_stock+")");

        return upd.sql();

        //Toast toast= Toast.makeText(cont,upd.sql(), Toast.LENGTH_LONG);toast.show();

    }

}

