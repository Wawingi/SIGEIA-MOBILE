package uan.sigeamobile;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import uan.sigea.wawingisebastiao.jota.R;
import com.sigea.wawingisebastiao.jota.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    TextView txtnome;
    Dialog epicDialog;
    ImageView btnfechar;
    Button t;
    //private static final String URLTURMA = "http://192.168.1.100/sigeia/Controller/TurmaCandidato.php";
    //private static final String URLNOTA = "http://192.168.1.100/sigeia/Controller/PautaCandidato.php";
    private static final String URLTURMA = "https://sigeamobile.acessouan.com/Controller/TurmaCandidato.php";
    private static final String URLNOTA = "https://sigeamobile.acessouan.com/Controller/PautaCandidato.php";


    StringRequest stringRequest;
    RequestQueue requestQueue;
    String pegarecibo,peganome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        requestQueue = Volley.newRequestQueue(this);

        pegarecibo = getIntent().getStringExtra("recibo");
        peganome = getIntent().getStringExtra("nome_completo");

        //Adicionar a Layout
        tabLayout=(TabLayout)findViewById(R.id.tblayout_id);
        //appBarLayout=(AppBarLayout)findViewById(R.id.appbar_id);
        viewPager=(ViewPager)findViewById(R.id.viewpager_id);

        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());
        adapter.AddFragment(new FragmentNoticia(),"Noticia");
        adapter.AddFragment(new Geral(),"Geral");
        //adapter.AddFragment(new Mapa(),"Mapa");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Apresentar o nome do candidato no Navigatr Drawer
        View headview = navigationView.getHeaderView(0);
        txtnome = headview.findViewById(R.id.txtnomecandidato);
        txtnome.setText(peganome);

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
                                Intent intent = new Intent(MainActivity2.this,TurmaCandidato.class);
                                intent.putExtra("nome_completo",jsonObject.getString("nome_completo"));
                                intent.putExtra("recibo",jsonObject.getString("recibo"));
                                intent.putExtra("curso",jsonObject.getString("curso"));
                                intent.putExtra("turma",jsonObject.getString("turma"));
                                intent.putExtra("data_exame",jsonObject.getString("data_exame"));
                                intent.putExtra("edificio",jsonObject.getString("edificio"));
                                intent.putExtra("sala",jsonObject.getString("sala"));
                                intent.putExtra("hora_inicio",jsonObject.getString("hora_inicio"));
                                intent.putExtra("hora_fim",jsonObject.getString("hora_fim"));


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
                params.put("recibo",pegarecibo);
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
                                Toast.makeText(getApplicationContext(),"Ainda não realizou a prova.",Toast.LENGTH_LONG).show();
                            }else{
                                Intent intent = new Intent(MainActivity2.this,NotaCandidato.class);
                                intent.putExtra("nome_completo",jsonObject.getString("nome_completo"));
                                intent.putExtra("recibo",jsonObject.getString("recibo"));
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
                params.put("recibo",pegarecibo);
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
                                Intent intent = new Intent(MainActivity2.this,Senhacandidato.class);
                                intent.putExtra("recibo",jsonObject.getString("recibo"));
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
                params.put("recibo",pegarecibo);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.sair) {
        //  return true;
        //}
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.verturma){
            pegaTurma();
        }

        if(id == R.id.vernota){
            pegaNota();
        }

        if(id == R.id.trocarsenha){
            pegaSenha();
        }

        if (id == R.id.menutopicos) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.acessouan.com/topicos"));
            startActivity(intent);
        }

        if (id == R.id.menuuan) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.uan.ao"));
            startActivity(intent);
        }
        if(id == R.id.sobre){
            alertaDialogo();
        }
        if(id == R.id.partilhar){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=uan.sigea.wawingisebastiao.ao.uan.sigeamobile");
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent,"Partilhar com..."));
        }

        if(id == R.id.logout){
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle("ALERTA");
            builder.setMessage("Tens a certeza que desejas voltar ao menu principal?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(MainActivity2.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            builder.create().show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void alertaDialogo(){
        epicDialog = new Dialog(this);
        epicDialog.setContentView(R.layout.caixadialogo);
        btnfechar = (ImageView)epicDialog.findViewById(R.id.btnfechar);
        btnfechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                epicDialog.dismiss();
            }
        });
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }



}
