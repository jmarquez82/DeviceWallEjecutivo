package cl.rinno.newdevicewall.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;

import cl.rinno.newdevicewall.AnalyticsApplication;
import cl.rinno.newdevicewall.FichaEquipo;
import cl.rinno.newdevicewall.R;
import cl.rinno.newdevicewall.models.Global;
import cl.rinno.newdevicewall.models.Producto;

/**
 * Created by ChinoDev on 21-12-2016.
 */

public class AlmacenamientoEquipoAdapter extends RecyclerView.Adapter<AlmacenamientoEquipoAdapter.AlmacenamientoEquipoViewHolder> {

    FichaEquipo context;
    ArrayList<Producto> listaHIjos;

    Tracker mTracker;

    public AlmacenamientoEquipoAdapter(FichaEquipo context, ArrayList<Producto> listaHIjos) {
        this.context = context;
        this.listaHIjos = listaHIjos;
        AnalyticsApplication application = (AnalyticsApplication) this.context.getApplication();
        mTracker = application.getDefaultTracker();
    }

    @Override
    public AlmacenamientoEquipoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_almacenamiento, parent, false);
        return new AlmacenamientoEquipoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AlmacenamientoEquipoViewHolder holder, final int position) {
        holder.txtGB.setText(listaHIjos.get(position).getDetalles().get(5).getValue());
        if (listaHIjos.get(position).getDetalles().get(5).getValue().equals(Global.producto.getDetalles().get(5).getValue())) {
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                holder.itemView.setBackgroundResource(R.drawable.bg_gb_selected_land);
                holder.txtGB.setTextColor(context.getResources().getColor(R.color.white));
            }else{
                holder.itemView.setBackgroundResource(R.drawable.bg_gb_selected);
                holder.txtGB.setTextColor(context.getResources().getColor(R.color.yellow));
            }
            holder.txtGB.setTypeface(null,Typeface.BOLD_ITALIC);

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = context;
                if (!Global.producto.getDetalles().get(5).getValue().equals(listaHIjos.get(position).getDetalles().get(5).getValue())) {
                    listaHIjos.get(position).getDetalles().set(0, Global.producto.getDetalles().get(0));
                    Global.producto.setDetalles(listaHIjos.get(position).getDetalles());
                    Global.producto.setPlanes(listaHIjos.get(position).getPlanes());
                    Global.producto.setPrecios(listaHIjos.get(position).getPrecios());
                    Global.producto.setCae(listaHIjos.get(position).getCae());
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Equipo")
                            .setAction(Global.producto.getProvider_name() + " - " + Global.producto.getName())
                            .setLabel("Almacenamiento: "+Global.producto.getDetalles().get(5).getValue())
                            .build());
                    activity.startActivity(new Intent(activity.getApplicationContext(), FichaEquipo.class));
                    activity.overridePendingTransition(0,0);
                    activity.finish();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaHIjos.size();
    }

    public static class AlmacenamientoEquipoViewHolder extends RecyclerView.ViewHolder {
        TextView txtGB;

        public AlmacenamientoEquipoViewHolder(View itemView) {
            super(itemView);
            txtGB = (TextView) itemView.findViewById(R.id.txtAlmacenamiendo);
        }
    }
}
