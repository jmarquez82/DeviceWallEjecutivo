package cl.rinno.newdevicewall.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cl.rinno.newdevicewall.MainActivity;
import cl.rinno.newdevicewall.R;
import cl.rinno.newdevicewall.models.Producto;

/**
 * Created by chinodoge on 06-04-2017.
 */

public class MiPrimerPlanVozAdapter extends RecyclerView.Adapter<MiPrimerPlanVozAdapter.MiPrimerPlanVozViewHolder> {

    ArrayList<Producto> plansList;

    public MiPrimerPlanVozAdapter(ArrayList<Producto> plansList) {
        this.plansList = plansList;
    }

    @Override
    public MiPrimerPlanVozViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mi_primer_plan_voz, parent, false);
        return new MiPrimerPlanVozViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MiPrimerPlanVozViewHolder holder, int position) {
      //  holder.txtPrecioNombre.setText(plansList.get(position).getDetalles().get(9).getValue());
    //    holder.txtValorPlan.setText(plansList.get(position).getDetalles().get(9).getValue());
     //   holder.txtSMS.setText(plansList.get(position).getDetalles().get(5).getValue());
     //   holder.txtMin.setText(plansList.get(position).getDetalles().get(1).getValue()+" Min.");
    }

    @Override
    public int getItemCount() {
        return plansList.size();
    }

    class MiPrimerPlanVozViewHolder extends RecyclerView.ViewHolder {
        TextView txtPrecioNombre;
        TextView txtMin;
        TextView txtSMS;
        TextView txtValorPlan;

        public MiPrimerPlanVozViewHolder(View itemView) {
            super(itemView);
            txtMin = (TextView) itemView.findViewById(R.id.tv_habla_hasta_mpv);
            txtPrecioNombre = (TextView) itemView.findViewById(R.id.tv_precio_nombre_mpv);
            txtSMS = (TextView) itemView.findViewById(R.id.tv_sms_mpv);
            txtValorPlan = (TextView) itemView.findViewById(R.id.tv_precio_mi_primer_plan_mpv);
        }
    }
}


