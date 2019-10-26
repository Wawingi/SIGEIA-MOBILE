package uan.sigeamobile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
 * Created by Wawingi Sebastiao on 06/01/2019.
 */

public class Menucandidato extends AppCompatActivity {
    Button btnturma,btnnota,btnsenha;
    private static final String URLTURMA = "http://192.168.1.100/sigeia/Controller/TurmaCandidato.php";
    private static final String URLNOTA = "http://192.168.1.100/sigeia/Controller/PautaCandidato.php";
    StringRequest stringRequest;
    RequestQueue requestQueue;
    String pegarecibo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menucandidato);
        btnturma = (Button)findViewById(R.id.btnturma);
        btnnota = (Button)findViewById(R.id.btnnota);
        btnsenha = (Button)findViewById(R.id.btnsenha);

        requestQueue = Volley.newRequestQueue(this);

        pegarecibo = getIntent().getStringExtra("numero_recibo");

        btnturma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pegaTurma();
            }
        });

        btnnota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pegaNota();
            }
        });

        btnsenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pegaSenha();
            }
        });
    }

    public void pegaTurma(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando...");
        progressDialog.show();
        stringRequest = new StringRequest(Request.Method.POST,
                URLTURMA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.e("Log",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            boolean isErro = jsonObject.getBoolean("erro");

                            if(isErro){
                                Toast.makeText(getApplicationContext(),"Dados inválidos",Toast.LENGTH_LONG).show();
                            }else{
                                Intent intent = new Intent(Menucandidato.this,TurmaCandidato.class);
                                intent.putExtra("nome",jsonObject.getString("nome"));
                                intent.putExtra("numero_recibo",jsonObject.getString("numero_recibo"));
                                intent.putExtra("turma",jsonObject.getString("turma"));
                                intent.putExtra("data",jsonObject.getString("data"));
                                intent.putExtra("hora",jsonObject.getString("hora"));
                                intent.putExtra("curso",jsonObject.getString("curso"));
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
                params.put("numero_recibo",pegarecibo);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void pegaNota(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando...");
        progressDialog.show();
        stringRequest = new StringRequest(Request.Method.POST,
                URLNOTA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.e("Log",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            boolean isErro = jsonObject.getBoolean("erro");

                            if(isErro){
                                Toast.makeText(getApplicationContext(),"Dados inválidos",Toast.LENGTH_LONG).show();
                            }else{
                                Intent intent = new Intent(Menucandidato.this,NotaCandidato.class);
                                intent.putExtra("nome",jsonObject.getString("nome"));
                                intent.putExtra("numero_recibo",jsonObject.getString("numero_recibo"));
                                intent.putExtra("curso",jsonObject.getString("curso"));
                                intent.putExtra("nota",jsonObject.getString("nota"));
                                intent.putExtra("estado",jsonObject.getString("estado"));
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
                params.put("numero_recibo",pegarecibo);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void pegaSenha(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando...");
        progressDialog.show();
        stringRequest = new StringRequest(Request.Method.POST,
                URLTURMA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.e("Log",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            boolean isErro = jsonObject.getBoolean("erro");

                            if(isErro){
                                Toast.makeText(getApplicationContext(),"Dados inválidos",Toast.LENGTH_LONG).show();
                            }else{
                                Intent intent = new Intent(Menucandidato.this,Senhacandidato.class);
                                intent.putExtra("numero_recibo",jsonObject.getString("numero_recibo"));
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
                params.put("numero_recibo",pegarecibo);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("ALERTA");
        builder.setMessage("Tens a certeza que desejas voltar ao menu principal?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Menucandidato.this,MainActivity.class);
                startActivity(intent);
            }
        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //finish();
                    }
                });
        builder.create().show();
    }

    public void Dialogo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("ALERTA");
        builder.setMessage("Tens a certeza que desejas voltar ao menu principal?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Menucandidato.this,MainActivity.class);
                startActivity(intent);
            }
        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismissDialog(i);//finish();
                    }
                });
        builder.create().show();
    }
}
