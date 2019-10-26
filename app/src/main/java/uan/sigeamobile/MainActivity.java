package uan.sigeamobile;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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

//import uan.sigea.wawingisebastiao.jota.R;
import com.sigea.wawingisebastiao.jota.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    TextView topicos;
    Dialog epicDialog;
    ImageView btnfechar;
    Button t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Adicionar a Layout
        tabLayout=(TabLayout)findViewById(R.id.tblayout_id);
        //appBarLayout=(AppBarLayout)findViewById(R.id.appbar_id);
        viewPager=(ViewPager)findViewById(R.id.viewpager_id);

        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());
        adapter.AddFragment(new FragmentNoticia(),"Notícias");
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

        if(id == R.id.pesquisaturma){
            Intent intent = new Intent(MainActivity.this,Pesquisa.class);
            startActivity(intent);
            finish();
        }

        if (id == R.id.menutopicos) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.acessouan.com/"));
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
