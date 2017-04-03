package cl.rinno.newdevicewall.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cl.rinno.newdevicewall.EquipoConPlanActivity;
import cl.rinno.newdevicewall.MainActivity;
import cl.rinno.newdevicewall.R;
import cl.rinno.newdevicewall.models.Producto;

/**
 * Created by chinodoge on 21-03-2017.
 */

public class PlanesSmartFunSimpleAdapter extends RecyclerView.Adapter<PlanesSmartFunSimpleAdapter.PlanesSmartFunSimpleViewHolder>{

    ArrayList<Producto> planesList;
    MainActivity activity;

    public PlanesSmartFunSimpleAdapter(ArrayList<Producto> planesList, MainActivity activity) {
        this.planesList = planesList;
        this.activity = activity;
    }

    @Override
    public PlanesSmartFunSimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan_smartfun_simple,parent,false);
        return new PlanesSmartFunSimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlanesSmartFunSimpleViewHolder holder, int position) {
        final Producto plan = planesList.get(position);
        holder.txtMinutos.setText(plan.getDetalles().get(2).getValue());
        holder.txtGBNombre.setText(activity.getString(R.string.cuota_datos,plan.getDetalles().get(4).getValue()));
        holder.txtCuotaInicial.setText(plan.getDetalles().get(9).getValue());
        holder.txtCuotaDatos.setText(activity.getString(R.string.cuota_datos,plan.getDetalles().get(4).getValue()));
        double gbPlan = Double.parseDouble(plan.getDetalles().get(4).getValue());
        gbPlan = (gbPlan * 2);
        int cuotaMusica = (int) gbPlan;
        holder.txtCuotaPromo.setText(activity.getString(R.string.cuota_datos,""+cuotaMusica));
        holder.btnEquiposDestacados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openEDSmartFun(plan.getDetalles().get(4).getValue(), plan.getDetalles().get(9).getValue(),0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return planesList.size();
    }

    class PlanesSmartFunSimpleViewHolder extends RecyclerView.ViewHolder {
        TextView txtGBNombre;
        TextView txtCuotaDatos;
        TextView txtCuotaPromo;
        TextView txtCuotaInicial;
        LinearLayout btnEquiposDestacados;
        TextView txtMinutos;

        PlanesSmartFunSimpleViewHolder(View itemView) {
            super(itemView);
            txtGBNombre = (TextView) itemView.findViewById(R.id.tv_gb_nombre);
            txtCuotaDatos = (TextView) itemView.findViewById(R.id.tv_cuota_datos);
            txtCuotaPromo = (TextView) itemView.findViewById(R.id.tv_cuota_promo);
            txtCuotaInicial = (TextView) itemView.findViewById(R.id.tv_valor_plan);
            btnEquiposDestacados = (LinearLayout) itemView.findViewById(R.id.linear_equipos_destacados);
            txtMinutos = (TextView) itemView.findViewById(R.id.tv_minutos);
        }
    }
}
