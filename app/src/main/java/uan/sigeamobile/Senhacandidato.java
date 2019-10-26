package uan.sigeamobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
 * Created by Wawingi Sebastiao on 06/01/2019.
 */

public class Senhacandidato extends AppCompatActivity {
    EditText txtsenhaantiga,txtsenhanova,txtsenhanovarepetida;
    Button btnalterarsenha;
    //private static final String URLSENHA = "http://192.168.1.100/sigeia/Controller/AlterarSenha.php";
    private static final String URLSENHA = "https://sigeamobile.acessouan.com/Controller/AlterarSenha.php";

    StringRequest stringRequest;
    RequestQueue requestQueue;
    String pegarecibo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.senhacandidato);

        //COnfiguraç]ao do BackButton
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        txtsenhaantiga = (EditText)findViewById(R.id.editsenhaantiga);
        txtsenhanova = (EditText)findViewById(R.id.editsenhanova);
        txtsenhanovarepetida = (EditText)findViewById(R.id.editsenhanovarepete);

        btnalterarsenha = (Button)findViewById(R.id.btnalterarsenha);
        requestQueue = Volley.newRequestQueue(this);

        pegarecibo = getIntent().getStringExtra("recibo");

        btnalterarsenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean validar = true;
                if(txtsenhaantiga.getText().length()==0){
                    txtsenhaantiga.setError("Senha antiga obrigatório");
                    txtsenhaantiga.requestFocus();
                    validar = false;
                }

                if(txtsenhanova.getText().length()==0){
                    txtsenhanova.setError("Senha nova obrigatório");
                    txtsenhanova.requestFocus();
                    validar = false;
                }
                if (txtsenhanovarepetida.getText().length()==0){
                    txtsenhanovarepetida.setError("Repita a nova senha, Por favor!");
                    txtsenhanovarepetida.requestFocus();
                    validar = false;
                }

                if(validar){
                    if(txtsenhanova.getText().toString().equals(txtsenhanovarepetida.getText().toString())){
                        alterarSenha();
                    }else{
                        Toast.makeText(getApplicationContext(),"Senha repetida diferente da anterior!",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    public void alterarSenha(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando...");
        progressDialog.show();
        stringRequest = new StringRequest(Request.Method.POST,
                URLSENHA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.e("Log",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            boolean isErro = jsonObject.getBoolean("erro");

                            if(isErro){
                                Toast.makeText(getApplicationContext(),"A senha fornecida está incorrecta!",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(),"Senha Alterada com sucesso",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Senhacandidato.this,Pesquisa.class);
                                startActivity(intent);
                                finish();
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
                params.put("recibo",pegarecibo);
                params.put("senha_antiga",txtsenhaantiga.getText().toString());
                params.put("senha_nova",txtsenhanova.getText().toString());
                return params;
            }
        };
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