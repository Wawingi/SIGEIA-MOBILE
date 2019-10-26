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
 * Created by Wawingi Sebastiao on 20/12/2018.
 */

public class PerdidoAchado extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    public List<PerdidoClass> listItems;
    public List<PerdidoClass> listItemsoffline;
    //private static final String URL = "http://192.168.1.100/sigeia/Controller/ListarPerdidosAchados.php";
    private static final String URL = "https://sigeamobile.acessouan.com/Controller/ListarPerdidosAchados.php";

    CRUD bd;
    int cont=0;
    int cont1=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perdidoachado);

        //Back Button config
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        bd = new CRUD(this);
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
                Toast.makeText(PerdidoAchado.this,"Nenhum dado encontrado, Verifique a sua conexão com a Internet", Toast.LENGTH_SHORT).show();
            }
            if(listItemsoffline!=null){
                adapter = new PerdidoAdapter(listItemsoffline, getApplicationContext());
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
                            JSONArray array = jsonObject.getJSONArray("perdido");

                            for(int k=0;k<listItemsoffline.size();k++){
                                String titulo="";
                                String descricao="";
                                for(int i=0; i<array.length(); i++){
                                    JSONObject o = array.getJSONObject(i);

                                    titulo = o.getString("titulo");
                                    descricao = o.getString("descricao");

                                    if(titulo.equals(listItemsoffline.get(k).getNome()) && descricao.equals(listItemsoffline.get(k).getDescricao())){
                                        cont1 ++;
                                        break;
                                    }
                                }
                                if (cont1==0){
                                    bd.eliminar(listItemsoffline.get(k).getNome(),listItemsoffline.get(k).getDescricao());
                                }
                                cont1=0;
                            }

                            for(int i=0; i<array.length(); i++){
                                JSONObject o = array.getJSONObject(i);
                                PerdidoClass item = new PerdidoClass();
                                item.setNome(o.getString("titulo"));
                                item.setDescricao(o.getString("descricao"));

                                String nome = o.getString("titulo");
                                String descricao = o.getString("descricao");

                                for(int j=0;j<listItemsoffline.size();j++){
                                    if(nome.equals(listItemsoffline.get(j).getNome())&&descricao.equals(listItemsoffline.get(j).getDescricao())){
                                        cont ++;
                                    }
                                }

                                if(cont>0) {
                                    cont=0;
                                } else {
                                    Boolean retorno = bd.inserir(item);
                                    cont=0;
                                    if (retorno == true) {
                                        Toast.makeText(PerdidoAchado.this, "Novos dados actualizados", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(PerdidoAchado.this, "Erro ao importar", Toast.LENGTH_SHORT).show();
                                    }
                                    listItemsoffline = bd.pegaDados();
                                }
                            }

                            adapter = new PerdidoAdapter(listItemsoffline, getApplicationContext());
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

    public void obterDadosPrimeiraVez() {
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
                            JSONArray array = jsonObject.getJSONArray("perdido");
                            for(int i=0; i<array.length(); i++){
                                JSONObject o = array.getJSONObject(i);
                                PerdidoClass item = new PerdidoClass();
                                item.setNome(o.getString("titulo"));
                                item.setDescricao(o.getString("descricao"));

                                Boolean retorno = bd.inserir(item);

                                if (retorno == true) {
                                        Toast.makeText(PerdidoAchado.this, "Importado com sucesso", Toast.LENGTH_SHORT).show();
                                } else {
                                        Toast.makeText(PerdidoAchado.this, "Erro ao importar", Toast.LENGTH_SHORT).show();
                                }
                            }

                            listItemsoffline = bd.pegaDados();
                            adapter = new PerdidoAdapter(listItemsoffline, getApplicationContext());
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
