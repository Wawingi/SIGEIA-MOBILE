package uan.sigeamobile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sigea.wawingisebastiao.jota.R;

import java.util.ArrayList;

/**
 * Created by Wawingi Sebastiao on 24/12/2018.
 */

public class CursoAdapter extends ArrayAdapter<Curso> {

    private final Context context;
    private final ArrayList<Curso> cursos;


    public CursoAdapter(Context context, ArrayList<Curso> cursos) {
        super(context, R.layout.itemview,cursos);
        this.context=context;
        this.cursos=cursos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.itemview,parent,false);

        TextView nome = (TextView) rowView.findViewById(R.id.txtcurso);
        TextView endereco = (TextView) rowView.findViewById(R.id.txtendereco);

        nome.setText(cursos.get(position).getNome());
        endereco.setText(cursos.get(position).getEndereco());

        return rowView;
    }
}
