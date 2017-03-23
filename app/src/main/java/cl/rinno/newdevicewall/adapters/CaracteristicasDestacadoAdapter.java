package cl.rinno.newdevicewall.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cl.rinno.newdevicewall.R;

/**
 * Created by chinodoge on 17-03-2017.
 */

public class CaracteristicasDestacadoAdapter extends RecyclerView.Adapter<CaracteristicasDestacadoAdapter.CaracteristicasViewHolder>{

    ArrayList<String> caracteristicasList;

    public CaracteristicasDestacadoAdapter(ArrayList<String> caracteristicasList) {
        this.caracteristicasList = caracteristicasList;
    }

    @Override
    public CaracteristicasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_caracteristicas_destacado,parent,false);
        return new CaracteristicasViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CaracteristicasViewHolder holder, int position) {
        holder.txtCaracteristica.setText(caracteristicasList.get(position));
    }

    @Override
    public int getItemCount() {
        return caracteristicasList.size();
    }

    public static class CaracteristicasViewHolder extends RecyclerView.ViewHolder {
        TextView txtCaracteristica;
        public CaracteristicasViewHolder(View itemView) {
            super(itemView);
            txtCaracteristica = (TextView) itemView.findViewById(R.id.tv_caracteristicas_destacado);
        }
    }
}
