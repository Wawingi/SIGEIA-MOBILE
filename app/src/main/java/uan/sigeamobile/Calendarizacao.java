package uan.sigeamobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sigea.wawingisebastiao.jota.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wawingi Sebastiao on 30/12/2018.
 */

public class Calendarizacao extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<CalendarizacaoClass> listItems;
    public List<CalendarizacaoClass> listItemsoffline;
    //private static final String URL = "http://192.168.1.100/sigeia/Controller/ListarCalendarizacao.php";
    private static final String URL = "https://sigeamobile.acessouan.com/Controller/ListarCalendarizacao.php";

    CRUDCAL bd;
    int cont=0;
    int cont1=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Back Button config
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        setContentView(R.layout.calendarizacao);
        bd = new CRUDCAL(this);
        listItemsoffline = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<>();

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if(netInfo != null && netInfo.isConnectedOrConnecting()){
            //Listar os dados vindo do SQLite, quando tiver a conexão
            try{
                listItemsoffline = bd.pegaDados();
                obterDados();
            } catch (Exception e){
                if(listItemsoffline.size()==0){
                    obterDados();
                }
            }
        }else{
            //Listar os dados vindo do SQLite, quando não tiver a conexão
            try{
                listItemsoffline = bd.pegaDados();
            }catch (Exception e){
                Toast.makeText(Calendarizacao.this,"Nenhum dado encontrado, Verifique a sua conexão com a Internet", Toast.LENGTH_SHORT).show();
            }
            if(listItemsoffline!=null){
                adapter = new CalendarizacaoAdapter(listItemsoffline, getApplicationContext());
                recyclerView.setAdapter(adapter);
            }
        }
    }

    public void obterDados() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("calendarizacao");

                            for(int k=0;k<listItemsoffline.size();k++){
                                String evento="";
                                String data_inicio="";
                                String data_fim="";
                                for(int i=0; i<array.length(); i++){
                                    JSONObject o = array.getJSONObject(i);
                                    CalendarizacaoClass item = new CalendarizacaoClass();

                                    evento = o.getString("nome_evento");
                                    data_inicio = o.getString("data_inicio");
                                    data_fim = o.getString("data_fim");

                                    if(evento.equals(listItemsoffline.get(k).getEvento()) && data_inicio.equals(listItemsoffline.get(k).getDatainicio()) && data_fim.equals(listItemsoffline.get(k).getDatafim())){
                                        cont1 ++;
                                        break;
                                    }
                                }
                                if (cont1==0){
                                    bd.eliminar(listItemsoffline.get(k).getEvento(),listItemsoffline.get(k).getDatainicio(),listItemsoffline.get(k).getDatafim());
                                }
                                cont1=0;
                            }

                            for(int i=0; i<array.length(); i++){
                                JSONObject o = array.getJSONObject(i);
                                CalendarizacaoClass item = new CalendarizacaoClass();
                                item.setEvento(o.getString("nome_evento"));
                                item.setDatainicio(o.getString("data_inicio"));
                                item.setDatafim(o.getString("data_fim"));

                                String evento = o.getString("nome_evento");
                                String data_inicio = o.getString("data_inicio");
                                String data_fim = o.getString("data_fim");

                                for(int j=0;j<listItemsoffline.size();j++){
                                    if(evento.equals(listItemsoffline.get(j).getEvento())&&data_inicio.equals(listItemsoffline.get(j).getDatainicio())&&data_fim.equals(listItemsoffline.get(j).getDatafim())){
                                        cont ++;
                                    }
                                }

                                if(cont>0) {
                                    cont=0;
                                } else {
                                    Boolean retorno = bd.inserir(item);

                                    cont=0;
                                    if (retorno == true) {
                                        Toast.makeText(Calendarizacao.this, "Novos dados actualizados", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Calendarizacao.this, "Erro ao actualizar", Toast.LENGTH_SHORT).show();
                                    }
                                    listItemsoffline = bd.pegaDados();
                                }
                            }

                            adapter = new CalendarizacaoAdapter(listItemsoffline, getApplicationContext());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
