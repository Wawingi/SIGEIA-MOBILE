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

public class Fau2 extends Fragment {
    View view;
    ListView lv;

    public Fau2() {

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

        curso = new Curso("Antropologia","Avenida Ho Chi Minh");
        cursos.add(curso);
        curso = new Curso("Ciência Política","Avenida Ho Chi Minh");
        cursos.add(curso);
        curso = new Curso("Comunicação Social","Avenida Ho Chi Minh");
        cursos.add(curso);
        curso = new Curso("Geodemografia","Avenida Ho Chi Minh");
        cursos.add(curso);
        curso = new Curso("Gestão e Administração Pública","Avenida Ho Chi Minh");
        cursos.add(curso);
        curso = new Curso("História","Avenida Ho Chi Minh");
        cursos.add(curso);
        curso = new Curso("Psicologia (de Trabalho, Criminal e Social)","Avenida Ho Chi Minh");
        cursos.add(curso);
        curso = new Curso("Sociologia","Avenida Ho Chi Minh");
        cursos.add(curso);

        return cursos;
    }
}
