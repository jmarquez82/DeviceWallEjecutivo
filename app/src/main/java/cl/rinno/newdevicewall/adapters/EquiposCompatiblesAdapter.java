package cl.rinno.newdevicewall.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.ArrayList;

import cl.rinno.newdevicewall.MainActivity;
import cl.rinno.newdevicewall.R;
import cl.rinno.newdevicewall.models.Global;
import cl.rinno.newdevicewall.models.Producto;


public class EquiposCompatiblesAdapter extends RecyclerView.Adapter<EquiposCompatiblesAdapter.EquiposCompatiblesViewHolder>{

    ArrayList<Producto> equiposList;
    MainActivity mainActivity;

    public EquiposCompatiblesAdapter(ArrayList<Producto> equiposList, MainActivity mainActivity) {
        this.equiposList = equiposList;
        this.mainActivity = mainActivity;
    }

    @Override
    public EquiposCompatiblesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_equipo_compatible, parent, false);
        return new EquiposCompatiblesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EquiposCompatiblesViewHolder holder, final int position) {
        holder.imgEquipoCompatible.setImageURI( Uri.fromFile( new File( Global.dirImages +equiposList.get( position ).getImagenPrimaria() )) );


        holder.txtNombreEquipoCompatible.setText(equiposList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setAccesoriosCompatibles(equiposList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return equiposList.size();
    }

    public class EquiposCompatiblesViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView imgEquipoCompatible;
        TextView txtNombreEquipoCompatible;

        public EquiposCompatiblesViewHolder(View itemView) {
            super(itemView);
            imgEquipoCompatible = (SimpleDraweeView) itemView.findViewById(R.id.image_equipo_compatible);
            txtNombreEquipoCompatible = (TextView) itemView.findViewById(R.id.tv_nombre_equipo_compatible);
        }
    }
}
