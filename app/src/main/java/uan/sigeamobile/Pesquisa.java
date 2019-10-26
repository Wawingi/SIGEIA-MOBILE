package uan.sigeamobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sigea.wawingisebastiao.jota.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wawingi Sebastiao on 03/01/2019.
 */

public class Pesquisa extends AppCompatActivity {
    EditText numero_recibo,numero_bi_senha;
    Button btnlogar;
    //private static final String URL = "http://192.168.1.100/sigeia/Controller/autenticacao.php";
    private static final String URL = "https://sigeamobile.acessouan.com/Controller/Autenticacao.php";

    StringRequest stringRequest;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pesquisa);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        requestQueue = Volley.newRequestQueue(this);

        numero_recibo = (EditText)findViewById(R.id.editnumrecibo);
        numero_bi_senha = (EditText)findViewById(R.id.editnumbi);
        btnlogar = (Button)findViewById(R.id.btnlogar);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if(netInfo != null && netInfo.isConnectedOrConnecting()){
            btnlogar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean validar = true;

                    if(numero_recibo.getText().length()==0){
                        numero_recibo.setError("Numero do recibo obrigatório");
                        numero_recibo.requestFocus();
                        validar = false;
                    }

                    if(numero_bi_senha.getText().length()==0){
                        numero_bi_senha.setError("Numero do recibo obrigatório");
                        numero_bi_senha.requestFocus();
                        validar = false;
                    }

                    if(validar){
                        validarlogin();
                    }

                }
            });

        }else{
            Toast.makeText(Pesquisa.this,"Verifique a sua conexão com a Internet", Toast.LENGTH_SHORT).show();
        }

    }

    public void validarlogin(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando...");
        progressDialog.show();
        stringRequest = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.e("Log",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            boolean isErro = jsonObject.getBoolean("erro");

                            if(isErro){
                                Toast.makeText(getApplicationContext(),"Desculpa! as suas credenciais estão incorrectas, digite novamente.",Toast.LENGTH_LONG).show();
                            }else{
                                Intent intent = new Intent(Pesquisa.this,MainActivity2.class);
                                    intent.putExtra("recibo",jsonObject.getString("recibo"));
                                    intent.putExtra("nome_completo",jsonObject.getString("nome_completo"));
                                    startActivity(intent);
                                    //finish();
                            }
                        }catch (Exception e){
                            Log.v("Log",e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Log",error.getMessage());
                    }
                }){
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("recibo",numero_recibo.getText().toString());
                params.put("senha",numero_bi_senha.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
