package uan.sigeamobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wawingi Sebastiao on 08/01/2019.
 */

public class CRUD extends SQLiteOpenHelper {
    private static final String NOME_BD = "sigea";

    //Tabela PERDIDOS E ACHADOS
    private static final String TABELAPERDIDO = "perdidos_achados";
    private static final String id="id";
    private static final String titulo="titulo";
    private static final String descricao="descricao";

    public CRUD(Context context) {
        super(context, TABELAPERDIDO, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("CREATE TABLE " + TABELAPERDIDO + "(id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, descricao TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int i, int i1) {
        bd.execSQL("DROP TABLE IF EXISTS " + TABELAPERDIDO);
    }

    public Boolean inserir(PerdidoClass p){
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(titulo,p.getNome());
        cv.put(descricao,p.getDescricao());
        long res = bd.insert(TABELAPERDIDO,null,cv);
        if(res==0) {
            return false;
        }else {
            return true;
        }
    }

    public List<PerdidoClass> pegaDados(){
        PerdidoClass p=null;
        SQLiteDatabase bd = this.getWritableDatabase();
        List<PerdidoClass> lista = new ArrayList<>();
        Cursor c = bd.rawQuery("SELECT * FROM " + TABELAPERDIDO ,null);
        c.moveToFirst();
        do{
            p = new PerdidoClass();
            p.setNome(c.getString(1));
            p.setDescricao(c.getString(2));
            lista.add(p);
        }while (c.moveToNext());
        c.close();
        return lista;
    }

    public void eliminar(String tit,String desc){
        SQLiteDatabase bd=this.getWritableDatabase();
        String v[] = new String[2];
        v[0]=tit;
        v[1]=desc;
        bd.delete(TABELAPERDIDO,titulo+"=? AND "+descricao+"=?",v);
    }

}
