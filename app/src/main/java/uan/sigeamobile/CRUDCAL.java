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

public class CRUDCAL extends SQLiteOpenHelper {
    private static final String NOME_BD = "sigea";

    //Tabela Calendarizção
    private static final String TABELACALENDARIZACAO = "calendarizacao";
    private static final String idcal="idcal";
    private static final String nome_evento="nome_evento";
    private static final String data_inicio="data_inicio";
    private static final String data_fim="data_fim";


    public CRUDCAL(Context context) {
        super(context, TABELACALENDARIZACAO, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("CREATE TABLE " + TABELACALENDARIZACAO + "(idcal INTEGER PRIMARY KEY AUTOINCREMENT, nome_evento TEXT, data_inicio TEXT, data_fim TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int i, int i1) {
        bd.execSQL("DROP TABLE IF EXISTS " + TABELACALENDARIZACAO);
    }

    public Boolean inserir(CalendarizacaoClass cal){
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(nome_evento,cal.getEvento());
        cv.put(data_inicio,cal.getDatainicio());
        cv.put(data_fim,cal.getDatafim());
        long res = bd.insert(TABELACALENDARIZACAO,null,cv);
        if(res==0) {
            return false;
        }else {
            return true;
        }
    }

    public List<CalendarizacaoClass> pegaDados(){
        CalendarizacaoClass cal=null;
        SQLiteDatabase bd = this.getWritableDatabase();
        List<CalendarizacaoClass> lista = new ArrayList<>();
        Cursor c = bd.rawQuery("SELECT * FROM " + TABELACALENDARIZACAO ,null);
        c.moveToFirst();
        do{
            cal = new CalendarizacaoClass();

            cal.setEvento(c.getString(1));
            cal.setDatainicio(c.getString(2));
            cal.setDatafim(c.getString(3));

            lista.add(cal);
        }while (c.moveToNext());
        c.close();
        return lista;
    }

    public void eliminar(String ev,String data_i, String data_f){
        SQLiteDatabase bd=this.getWritableDatabase();
        String v[] = new String[3];
        v[0]=ev;
        v[1]=data_i;
        v[2]=data_f;
        bd.delete(TABELACALENDARIZACAO,nome_evento+"=? AND "+data_inicio+"=? AND "+data_fim+"=?",v);
    }

}
