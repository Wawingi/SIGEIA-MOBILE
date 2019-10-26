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

public class Fau1 extends Fragment {
    View view;
    ListView lv;

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

        curso = new Curso("Biologia","Marginal");
        cursos.add(curso);
        curso = new Curso("Ciências da Computação","Campus / Marginal");
        cursos.add(curso);
        curso = new Curso("Engenharia Geográfica","Campus / Marginal");
        cursos.add(curso);
        curso = new Curso("Física","Campus / Marginal");
        cursos.add(curso);
        curso = new Curso("Geofísica","Campus");
        cursos.add(curso);
        curso = new Curso("Geologia","Marginal");
        cursos.add(curso);
        curso = new Curso("Matemática","Campus / Marginal");
        cursos.add(curso);
        curso = new Curso("Meteorologia","Campus");
        cursos.add(curso);
        curso = new Curso("Química","Campus / Marginal");
        cursos.add(curso);

        return cursos;
    }
}
