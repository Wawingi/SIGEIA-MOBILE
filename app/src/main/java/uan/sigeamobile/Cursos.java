package uan.sigeamobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.sigea.wawingisebastiao.jota.R;

/**
 * Created by Wawingi Sebastiao on 21/12/2018.
 */

public class Cursos extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cursos);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        //Adicionar a Layout
        tabLayout=(TabLayout)findViewById(R.id.tblayout_id);
        viewPager=(ViewPager)findViewById(R.id.viewpager_id);

        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());
        adapter.AddFragment(new Fau1(),"Faculdade de Ciências");
        adapter.AddFragment(new Fau2(),"Faculdade de Ciências Sociais");
        adapter.AddFragment(new Fau3(),"Faculdade de Direito");
        adapter.AddFragment(new Fau4(),"Faculdade de Economia");
        adapter.AddFragment(new Fau5(),"Faculdade de Engenharia");
        adapter.AddFragment(new Fau6(),"Faculdade de Letras");
        adapter.AddFragment(new Fau8(),"Faculdade de Medicina");
        adapter.AddFragment(new Fau7(),"ISCISA");
        adapter.AddFragment(new Fau9(),"ESHOTUR");



        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
