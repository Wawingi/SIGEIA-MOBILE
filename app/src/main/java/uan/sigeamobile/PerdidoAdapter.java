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

public class PerdidoAdapter extends RecyclerView.Adapter<PerdidoAdapter.ViewHolder> {

    private List<PerdidoClass> listItems;
    private Context context;

    public PerdidoAdapter(List<PerdidoClass> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapterviewperdido,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        PerdidoClass listItem = listItems.get(position);
        holder.txtnome.setText(listItem.getNome());
        holder.txtdescricao.setText(listItem.getDescricao());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtnome;
        public TextView txtdescricao;

        public ViewHolder(View itemView) {
            super(itemView);

            txtnome = (TextView)itemView.findViewById(R.id.txtnome);
            txtdescricao = (TextView)itemView.findViewById(R.id.txtdescricao);
        }
    }
}
