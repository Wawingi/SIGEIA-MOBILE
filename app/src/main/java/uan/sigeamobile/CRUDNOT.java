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

public class CRUDNOT extends SQLiteOpenHelper {
    private static final String NOME_BD = "sigea";

    //Tabela NOTICIA
    private static final String TABELANOTICIA = "noticia";
    private static final String idnot="idnot";
    private static final String titulo="titulo";
    private static final String descricao="descricao";
    private static final String data_publicacao="data_publicacao";


    public CRUDNOT(Context context) {
        super(context, TABELANOTICIA, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("CREATE TABLE " + TABELANOTICIA + "("
                + idnot+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + titulo+" TEXT, "
                + descricao+" TEXT, "
                + data_publicacao+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int i, int i1) {
        bd.execSQL("DROP TABLE IF EXISTS " + TABELANOTICIA);
    }

    public Boolean inserir(NoticiaClass not){
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(titulo,not.getTitulo());
        cv.put(descricao,not.getDescricao());
        cv.put(data_publicacao,not.getData());
        long res = bd.insert(TABELANOTICIA,null,cv);
        if(res==0) {
            return false;
        }else {
            return true;
        }
    }

    public List<NoticiaClass> pegaDados(){
        NoticiaClass not=null;
        SQLiteDatabase bd = this.getWritableDatabase();
        List<NoticiaClass> lista = new ArrayList<>();
        Cursor c = bd.rawQuery("SELECT * FROM " + TABELANOTICIA ,null);
        c.moveToFirst();
        do{
            not = new NoticiaClass();

            not.setTitulo(c.getString(1));
            not.setDescricao(c.getString(2));
            not.setData(c.getString(3));

            lista.add(not);
        }while (c.moveToNext());
        c.close();
        return lista;
    }


    public void eliminar(String tit,String desc, String data_pub){
        SQLiteDatabase bd=this.getWritableDatabase();
        String v[] = new String[3];
        v[0]=tit;
        v[1]=desc;
        v[2]=data_pub;
        bd.delete(TABELANOTICIA,titulo+"=? AND "+descricao+"=? AND "+data_publicacao+"=?",v);
    }

}
