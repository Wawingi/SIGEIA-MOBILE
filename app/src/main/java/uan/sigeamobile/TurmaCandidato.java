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

public class TurmaCandidato extends AppCompatActivity {
    TextView txtnome,txtrecibo,txtcurso,txtturma,txtdata,txtedificio,txtsala,txthorainicio,txthorafim;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.turmacandidato);

        //COnfiguraç]ao do BackButton
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        txtnome = (TextView)findViewById(R.id.txtnome);
        txtrecibo = (TextView)findViewById(R.id.txtrecibo);
        txtcurso = (TextView)findViewById(R.id.txtcurso);
        txtturma = (TextView)findViewById(R.id.txtturma);
        txtdata = (TextView)findViewById(R.id.txtdata);
        txtedificio = (TextView)findViewById(R.id.txtedificio);
        txtsala = (TextView)findViewById(R.id.txtsala);
        txthorainicio = (TextView)findViewById(R.id.txthorainicio);
        txthorafim = (TextView)findViewById(R.id.txthorafim);

        String peganome = getIntent().getStringExtra("nome_completo");
        String pegarecibo = getIntent().getStringExtra("recibo");
        String pegacurso = getIntent().getStringExtra("curso");
        String pegaturma = getIntent().getStringExtra("turma");
        String pegadata = getIntent().getStringExtra("data_exame");
        String pegaedificio = getIntent().getStringExtra("edificio");
        String pegasala = getIntent().getStringExtra("sala");
        String pegahora_inicio = getIntent().getStringExtra("hora_inicio");
        String pegahora_fim = getIntent().getStringExtra("hora_fim");

        txtnome.setText(peganome);
        txtrecibo.setText(pegarecibo);
        txtcurso.setText(pegacurso);
        txtturma.setText(pegaturma);
        txtdata.setText(pegadata);
        txtedificio.setText(pegaedificio);
        txtsala.setText(pegasala);
        txthorainicio.setText(pegahora_inicio);
        txthorafim.setText(pegahora_fim);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
