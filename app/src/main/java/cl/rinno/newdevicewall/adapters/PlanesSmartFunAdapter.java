package cl.rinno.newdevicewall.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cl.rinno.newdevicewall.EquipoConPlanActivity;
import cl.rinno.newdevicewall.R;
import cl.rinno.newdevicewall.models.Producto;

/**
 * Created by chinodoge on 21-03-2017.
 */

public class PlanesSmartFunAdapter extends RecyclerView.Adapter<PlanesSmartFunAdapter.PlanesSmartFunViewHolder>{

    ArrayList<Producto> planesList;
    EquipoConPlanActivity activity;

    public PlanesSmartFunAdapter(ArrayList<Producto> planesList, EquipoConPlanActivity activity) {
        this.planesList = planesList;
        this.activity = activity;
        Collections.sort(this.planesList, new Comparator<Producto>() {
            @Override
            public int compare(Producto o1, Producto o2) {
                int valueOne;
                int valueTwo;
                double doubleOne, doubleTwo;
                doubleOne = Double.parseDouble(o1.getDetalles().get(4).getValue());
                doubleTwo = Double.parseDouble(o2.getDetalles().get(4).getValue());
                valueOne = (int) doubleOne;
                valueTwo = (int) doubleTwo;
                return valueOne - valueTwo;
            }
        });
    }

    @Override
    public PlanesSmartFunViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan_smartfun,parent,false);
        return new PlanesSmartFunViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlanesSmartFunViewHolder holder, int position) {
        final Producto plan = planesList.get(position);
        holder.txtMinutos.setText(plan.getDetalles().get(2).getValue());
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
                activity.verPromo(plan.getDetalles().get(plan.getDetalles().size()-1).getValue());
            }
        });
    }

    @Override
    public int getItemCount() {
        return planesList.size();
    }

    class PlanesSmartFunViewHolder extends RecyclerView.ViewHolder {
        TextView txtGBNombre;
        TextView txtCargoFijo;
        TextView txtCuotaDatos;
        TextView txtCuotaPromo;
        TextView txtCuotaInicial;
        LinearLayout btnVerPromo;
        TextView txtMinutos;

        PlanesSmartFunViewHolder(View itemView) {
            super(itemView);
            txtGBNombre = (TextView) itemView.findViewById(R.id.tv_gb_nombre);
            txtCargoFijo = (TextView) itemView.findViewById(R.id.tv_cargo_fijo);
            txtCuotaDatos = (TextView) itemView.findViewById(R.id.tv_cuota_datos);
            txtCuotaPromo = (TextView) itemView.findViewById(R.id.tv_cuota_promo);
            txtCuotaInicial = (TextView) itemView.findViewById(R.id.tv_valor_plan);
            btnVerPromo = (LinearLayout) itemView.findViewById(R.id.linear_equipos_destacados);
            txtMinutos = (TextView) itemView.findViewById(R.id.tv_minutos_mpm);
        }
    }
}
