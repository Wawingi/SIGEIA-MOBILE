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

public class CalendarizacaoAdapter extends RecyclerView.Adapter<CalendarizacaoAdapter.ViewHolder> {

    private List<CalendarizacaoClass> listItems;
    private Context context;

    public CalendarizacaoAdapter(List<CalendarizacaoClass> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapterview,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CalendarizacaoClass listItem = listItems.get(position);
        holder.txtevento.setText(listItem.getEvento());
        holder.txtdatainicio.setText(listItem.getDatainicio());
        holder.txtdatafim.setText(listItem.getDatafim());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtevento;
        public TextView txtdatainicio;
        public TextView txtdatafim;

        public ViewHolder(View itemView) {
            super(itemView);

            txtevento = (TextView)itemView.findViewById(R.id.txtevento);
            txtdatainicio = (TextView)itemView.findViewById(R.id.txtdatainicio);
            txtdatafim = (TextView)itemView.findViewById(R.id.txtdatafim);
        }
    }
}
