package uan.sigeamobile;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sigea.wawingisebastiao.jota.R;

import java.util.ArrayList;

/**
 * Created by Wawingi Sebastiao on 21/12/2018.
 */

public class Fau4 extends Fragment {
    View view;
    ListView lv;

    public Fau4() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fau1,container,false);

        lv=(ListView)view.findViewById(R.id.lstview);
        ArrayAdapter adapter = new CursoAdapter(getActivity(),adicionarCursos());
        lv.setAdapter(adapter);

        return view;
    }

    private ArrayList<Curso> adicionarCursos(){
        ArrayList<Curso> cursos = new ArrayList<Curso>();
        Curso curso;

        curso = new Curso("Contabilidade e Administração","Marginal / Kikuxi");
        cursos.add(curso);
        curso = new Curso("Contabilidade e Auditoria","Marginal / Kikuxi");
        cursos.add(curso);
        curso = new Curso("Economia","Marginal / Kikuxi");
        cursos.add(curso);
        curso = new Curso("Gestão de Empresas","Marginal / Kikuxi");
        cursos.add(curso);
        curso = new Curso("Gestão Financeira","Marginal / Kikuxi");
        cursos.add(curso);

        return cursos;
    }
}
