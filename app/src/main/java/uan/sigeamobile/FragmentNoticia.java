package uan.sigeamobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by Wawingi Sebastiao on 17/12/2018.
 */

public class FragmentNoticia extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<NoticiaClass> listItems;
    public List<NoticiaClass> listItemsoffline;
    //private static final String URL = "http://192.168.1.100/sigeia/Controller/ListarNoticias.php";
    private static final String URL = "https://sigeamobile.acessouan.com/Controller/ListarNoticias.php";

    CRUDNOT bd;
    int cont=0;
    int cont1=0;

    View view;

    public FragmentNoticia() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.noticias,container,false);
        bd = new CRUDNOT(getContext());
        listItemsoffline = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listItems = new ArrayList<>();

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));

        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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
                Toast.makeText(getContext(),"Nenhum dado encontrado, Verifique a sua conexão com a Internet", Toast.LENGTH_SHORT).show();
            }
            if(listItemsoffline!=null){
                adapter = new NoticiaAdapter(listItemsoffline, getContext());
                recyclerView.setAdapter(adapter);
            }
        }
        
        return view;
    }

    public void obterDados() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
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
                            JSONArray array = jsonObject.getJSONArray("noticia");

                            for(int k=0;k<listItemsoffline.size();k++){
                                String titulo="";
                                String descricao="";
                                String data_publicacao="";
                                for(int i=0; i<array.length(); i++){
                                    JSONObject o = array.getJSONObject(i);
                                    NoticiaClass item = new NoticiaClass();

                                    titulo = o.getString("titulo");
                                    descricao = o.getString("descricao");
                                    data_publicacao = o.getString("data_publicacao");

                                    if(titulo.equals(listItemsoffline.get(k).getTitulo()) && descricao.equals(listItemsoffline.get(k).getDescricao()) && data_publicacao.equals(listItemsoffline.get(k).getData())){
                                        cont1 ++;
                                        break;
                                    }
                                }
                                if (cont1==0){
                                    bd.eliminar(listItemsoffline.get(k).getTitulo(),listItemsoffline.get(k).getDescricao(),listItemsoffline.get(k).getData());
                                }
                                cont1=0;
                            }

                            for(int i=0; i<array.length(); i++){
                                JSONObject o = array.getJSONObject(i);
                                NoticiaClass item = new NoticiaClass();
                                item.setTitulo(o.getString("titulo"));
                                item.setDescricao(o.getString("descricao"));
                                item.setData(o.getString("data_publicacao"));

                                String titulo = o.getString("titulo");
                                String descricao = o.getString("descricao");
                                String data_publicacao = o.getString("data_publicacao");

                                if(titulo.equals("SIGEA COMUNICAÇÃO #S001")){
                                    continue;
                                }

                                for(int j=0;j<listItemsoffline.size();j++){
                                    if(titulo.equals(listItemsoffline.get(j).getTitulo()) && descricao.equals(listItemsoffline.get(j).getDescricao()) && data_publicacao.equals(listItemsoffline.get(j).getData())){
                                        cont ++;
                                    }
                                }

                                if(cont>0) {
                                    cont=0;
                                } else {
                                    Boolean retorno = bd.inserir(item);

                                    cont=0;
                                    if (retorno == true) {
                                        Toast.makeText(getActivity(), "Novos dados actualizados", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), "Erro ao actualizar", Toast.LENGTH_SHORT).show();
                                    }
                                    listItemsoffline = bd.pegaDados();
                                }

                            }

                            adapter = new NoticiaAdapter(listItemsoffline, getContext());
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}
