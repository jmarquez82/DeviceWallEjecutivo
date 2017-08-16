package cl.rinno.newdevicewall.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import cl.rinno.newdevicewall.R;
import cl.rinno.newdevicewall.models.Producto;



public class PlanDeVozAdapter extends RecyclerView.Adapter<PlanDeVozAdapter.PlanDeVozViewHolder>{

    private ArrayList<Producto> planesList;
    private int type;

    public PlanDeVozAdapter(ArrayList<Producto> planesList, int type) {
        this.planesList = planesList;
        this.type = type;
    }

    @Override
    public PlanDeVozViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan_de_voz,parent,false);
        return new PlanDeVozViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlanDeVozViewHolder holder, int position) {
        if(type == 1){
            holder.imgPlan.setImageURI(Uri.parse("res:/"+R.drawable.icn_cuentacontrolada));
        }else if(type == 2){
            holder.imgPlan.setImageURI(Uri.parse("res:/"+R.drawable.icn_vozlimitado));
        }
        holder.txtNombrePlan.setText(planesList.get(position).getName());
       // holder.txtSMS.setText(planesList.get(position).getDetalles().get(3).getValue());
      //  holder.txtMin.setText(planesList.get(position).getDetalles().get(1).getValue());
      //  holder.txtCargoFijo.setText(planesList.get(position).getDetalles().get(4).getValue());
    }

    @Override
    public int getItemCount() {
        return planesList.size();
    }

    class PlanDeVozViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView imgPlan;
        TextView txtCargoFijo;
        TextView txtNombrePlan;
        TextView txtMin;
        TextView txtSMS;

        public PlanDeVozViewHolder(View itemView) {
            super(itemView);
            imgPlan = (SimpleDraweeView) itemView.findViewById(R.id.image_plan);
            txtCargoFijo = (TextView) itemView.findViewById(R.id.tv_cargo_fijo_voz);
            txtNombrePlan = (TextView) itemView.findViewById(R.id.tv_plan_name);
            txtMin = (TextView) itemView.findViewById(R.id.tv_min_voz);
            txtSMS = (TextView) itemView.findViewById(R.id.tv_sms_voz);
        }
    }
}
