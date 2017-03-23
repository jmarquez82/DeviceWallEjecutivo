package cl.rinno.newdevicewall.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cl.rinno.newdevicewall.EquipoConPlanActivity;
import cl.rinno.newdevicewall.R;
import cl.rinno.newdevicewall.models.Producto;

/**
 * Created by chinodoge on 21-03-2017.
 */

public class PlanesControlFunAdapter extends RecyclerView.Adapter<PlanesControlFunAdapter.PlanesControlFunViewHolder>{

    ArrayList<Producto> planesList;
    EquipoConPlanActivity activity;

    public PlanesControlFunAdapter(ArrayList<Producto> planesList, EquipoConPlanActivity activity) {
        this.planesList = planesList;
        this.activity = activity;
    }

    @Override
    public PlanesControlFunViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan_controlfun,parent,false);
        return new PlanesControlFunViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlanesControlFunViewHolder holder, int position) {
        Producto plan = planesList.get(position);
        holder.txtMinutos.setText(activity.getString(R.string.minutos_plan, plan.getDetalles().get(2).getValue()));
        holder.txtGBNombre.setText(activity.getString(R.string.cuota_datos,plan.getDetalles().get(4).getValue()));
        holder.txtCargoFijo.setText(activity.getString(R.string.precio_venta,plan.getDetalles().get(9).getValue()));
        holder.txtCuotaInicial.setText(plan.getRelation().getValue());
        holder.txtCuotaDatos.setText(activity.getString(R.string.cuota_datos,plan.getDetalles().get(4).getValue()));
        double gbPlan = Double.parseDouble(plan.getDetalles().get(4).getValue());
        gbPlan = (gbPlan * 2);
        int cuotaMusica = (int) gbPlan;
        holder.txtCuotaPromo.setText(activity.getString(R.string.cuota_datos,""+cuotaMusica));
        holder.btnVerPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.verPromo(2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return planesList.size();
    }

    class PlanesControlFunViewHolder extends RecyclerView.ViewHolder {
        TextView txtGBNombre;
        TextView txtCargoFijo;
        TextView txtCuotaDatos;
        TextView txtCuotaPromo;
        TextView txtCuotaInicial;
        LinearLayout btnVerPromo;
        TextView txtMinutos;
        PlanesControlFunViewHolder(View itemView) {
            super(itemView);
            txtGBNombre = (TextView) itemView.findViewById(R.id.tv_gb_nombre);
            txtCargoFijo = (TextView) itemView.findViewById(R.id.tv_cargo_fijo);
            txtCuotaDatos = (TextView) itemView.findViewById(R.id.tv_cuota_datos);
            txtCuotaPromo = (TextView) itemView.findViewById(R.id.tv_cuota_promo);
            txtCuotaInicial = (TextView) itemView.findViewById(R.id.tv_cuota_inicial);
            btnVerPromo = (LinearLayout) itemView.findViewById(R.id.linear_ver_promo);
            txtMinutos = (TextView) itemView.findViewById(R.id.tv_minutos);
        }
    }
}
