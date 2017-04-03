package cl.rinno.newdevicewall.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.ArrayList;

import cl.rinno.newdevicewall.R;
import cl.rinno.newdevicewall.models.Global;
import cl.rinno.newdevicewall.models.Producto;

/**
 * Created by chinodoge on 31-03-2017.
 */

public class EquiposDestacadosAdapter extends RecyclerView.Adapter<EquiposDestacadosAdapter.EquiposDestacadosViewHolder>{

    ArrayList<Producto> productoArrayList;
    Context context;

    public EquiposDestacadosAdapter(ArrayList<Producto> productoArrayList, Context context) {
        this.productoArrayList = productoArrayList;
        this.context = context;
    }

    @Override
    public EquiposDestacadosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_equipo_destacado,parent,false);
        return new EquiposDestacadosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EquiposDestacadosViewHolder holder, int position) {
        holder.txtNombre.setText(productoArrayList.get(position).getName());
        holder.imgEquipo.setImageURI(Uri.fromFile(new File(Global.dirImages+productoArrayList.get(position).getDetalles().get(0).getValue())));
        holder.txtProvider.setText(productoArrayList.get(position).getProvider_name());
        holder.txtPrecioVenta.setText(productoArrayList.get(position).getPrecios().get(0).getValue());
        holder.txtCuotaMensual.setText(context.getString(R.string.precio_venta, productoArrayList.get(position).getCae().get(1).getValue()));
        holder.txtCae.setText(context.getString(R.string.precio_cae, productoArrayList.get(position).getCae().get(0).getValue()));
        holder.txtTotalAPagar.setText(context.getString(R.string.precio_venta, productoArrayList.get(position).getCae().get(2).getValue()));
    }

    @Override
    public int getItemCount() {
        return productoArrayList.size();
    }

    class EquiposDestacadosViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView imgEquipo;
        TextView txtNombre;
        TextView txtProvider;
        TextView txtPrecioVenta;
        TextView txtCuotaMensual;
        TextView txtCae;
        TextView txtTotalAPagar;

        public EquiposDestacadosViewHolder(View itemView) {
            super(itemView);

            imgEquipo = (SimpleDraweeView) itemView.findViewById(R.id.image_equipo_destacado);
            txtNombre = (TextView) itemView.findViewById(R.id.tv_nombre_equipo_destacado);
            txtProvider = (TextView) itemView.findViewById(R.id.tv_provider_equipo_destacado);
            txtPrecioVenta = (TextView) itemView.findViewById(R.id.tv_precio_venta);
            txtCuotaMensual = (TextView) itemView.findViewById(R.id.tv_cuota_mensual);
            txtCae = (TextView) itemView.findViewById(R.id.tv_cae_equipo_destacado);
            txtTotalAPagar = (TextView) itemView.findViewById(R.id.tv_total_a_pagar_equipo_destacado);
        }
    }
}
