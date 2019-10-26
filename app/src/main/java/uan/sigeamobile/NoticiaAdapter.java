package uan.sigeamobile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sigea.wawingisebastiao.jota.R;

import java.util.List;

/**
 * Created by Wawingi Sebastiao on 24/12/2018.
 */

public class NoticiaAdapter extends RecyclerView.Adapter<NoticiaAdapter.ViewHolder> {

    private List<NoticiaClass> listItems;
    private Context context;

    public NoticiaAdapter(List<NoticiaClass> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapterviewnoticia,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        NoticiaClass listItem = listItems.get(position);
        holder.txttitulo.setText(listItem.getTitulo());
        holder.txtdescricao.setText(listItem.getDescricao());
        holder.txtdatapublicacao.setText(listItem.getData());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txttitulo;
        public TextView txtdescricao;
        public TextView txtdatapublicacao;

        public ViewHolder(View itemView) {
            super(itemView);

            txttitulo = (TextView)itemView.findViewById(R.id.txttitulo);
            txtdescricao = (TextView)itemView.findViewById(R.id.txtdescricao);
            txtdatapublicacao = (TextView)itemView.findViewById(R.id.txtdatapublicacao);
        }
    }
}
