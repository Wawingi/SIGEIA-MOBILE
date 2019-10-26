package uan.sigeamobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.sigea.wawingisebastiao.jota.R;

/**
 * Created by Wawingi Sebastiao on 03/01/2019.
 */

public class NotaCandidato extends AppCompatActivity {
    TextView txtnome,txtrecibo,txtcurso,txtnota,txtestado;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notacandidato);

        //COnfiguraç]ao do BackButton
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        txtnome = (TextView)findViewById(R.id.txtnome);
        txtrecibo = (TextView)findViewById(R.id.txtrecibo);
        txtcurso = (TextView)findViewById(R.id.txtcurso);
        txtnota = (TextView)findViewById(R.id.txtnota);
        txtestado = (TextView)findViewById(R.id.txtestado);

        String peganome = getIntent().getStringExtra("nome_completo");
        String pegarecibo = getIntent().getStringExtra("recibo");
        String pegacurso = getIntent().getStringExtra("curso");
        String peganota = getIntent().getStringExtra("nota");
        String pegaestado = getIntent().getStringExtra("estado");

        txtnome.setText(peganome);
        txtrecibo.setText(pegarecibo);
        txtcurso.setText(pegacurso);
        txtnota.setText(peganota);
        txtestado.setText(pegaestado);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
