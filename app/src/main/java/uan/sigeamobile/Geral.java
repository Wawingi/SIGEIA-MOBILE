package uan.sigeamobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sigea.wawingisebastiao.jota.R;

/**
 * Created by Wawingi Sebastiao on 17/12/2018.
 */

public class Geral extends Fragment {
    View view;

    LinearLayout btnCursos,btnDocumetos,btnContacto,btnConheca,btnPerdido,btnCalendarizacao;

    public Geral() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.geral,container,false);


        btnCursos = (LinearLayout)view.findViewById(R.id.btnCursos);
        btnDocumetos = (LinearLayout)view.findViewById(R.id.btnDocumentos);
        btnCalendarizacao = (LinearLayout)view.findViewById(R.id.btnCalendarizacao);
        btnPerdido = (LinearLayout)view.findViewById(R.id.btnPerdido);
        btnContacto = (LinearLayout)view.findViewById(R.id.btnContacto);
        btnConheca = (LinearLayout)view.findViewById(R.id.btnConheca);

        btnCursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Cursos.class);
                startActivity(intent);
            }
        });

        btnDocumetos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Documentos.class);
                startActivity(intent);
            }
        });

        btnCalendarizacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Calendarizacao.class);
                startActivity(intent);
            }
        });

        btnPerdido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),PerdidoAchado.class);
                startActivity(intent);
            }
        });

        btnContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Contacto.class);
                startActivity(intent);
            }
        });

        btnConheca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Conhecauan.class);
                startActivity(intent);
            }
        });


        return view;
    }
}
