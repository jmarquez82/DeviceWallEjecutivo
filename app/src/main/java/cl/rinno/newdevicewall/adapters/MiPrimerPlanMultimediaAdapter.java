package cl.rinno.newdevicewall.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cl.rinno.newdevicewall.MainActivity;
import cl.rinno.newdevicewall.R;
import cl.rinno.newdevicewall.models.Producto;

/**
 * Created by chinodoge on 06-04-2017.
 */

public class MiPrimerPlanMultimediaAdapter extends RecyclerView.Adapter<MiPrimerPlanMultimediaAdapter.MiPrimerPlanMultimediaViewHolder>{

    ArrayList<Producto> plansList;
    MainActivity mainActivity;

    public MiPrimerPlanMultimediaAdapter(ArrayList<Producto> plansList, MainActivity mainActivity) {
        this.plansList = plansList;
        this.mainActivity = mainActivity;
    }

    @Override
    public MiPrimerPlanMultimediaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mi_primer_plan_multimedia,parent,false);
        return new MiPrimerPlanMultimediaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MiPrimerPlanMultimediaViewHolder holder, final int position) {
        holder.txtMinutos.setText(mainActivity.getString(R.string.minutos_plan, plansList.get(position).getDetalles().get(1).getValue()));
        holder.txtValorMensual.setText(plansList.get(position).getDetalles().get(9).getValue());
        holder.txtNavegaHasta.setText(plansList.get(position).getDetalles().get(4).getValue() + " GB");
        holder.txtPrecioNombre.setText(plansList.get(position).getDetalles().get(9).getValue());
        holder.txtCuotaDatos.setText(plansList.get(position).getDetalles().get(4).getValue() + " GB");
    }

    @Override
    public int getItemCount() {
        return plansList.size();
    }

    class MiPrimerPlanMultimediaViewHolder extends RecyclerView.ViewHolder {

        TextView txtPrecioNombre;
        TextView txtCuotaDatos;
        LinearLayout btnEquiposDestacados;
        TextView txtValorMensual;
        TextView txtMinutos;
        TextView txtNavegaHasta;

        public MiPrimerPlanMultimediaViewHolder(View itemView) {
            super(itemView);
            txtPrecioNombre = (TextView) itemView.findViewById(R.id.tv_precio_nombre);
            txtCuotaDatos = (TextView) itemView.findViewById(R.id.tv_cuota_datos_mpm);
            txtMinutos = (TextView) itemView.findViewById(R.id.tv_minutos_mpm);
            txtValorMensual = (TextView) itemView.findViewById(R.id.tv_valor_plan);
            txtNavegaHasta = (TextView) itemView.findViewById(R.id.tv_navega_hasta_mpm);
            btnEquiposDestacados = (LinearLayout) itemView.findViewById(R.id.linear_equipos_destacados);
        }
    }
}
