package cl.rinno.newdevicewall.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cl.rinno.newdevicewall.MainActivity;
import cl.rinno.newdevicewall.R;
import cl.rinno.newdevicewall.models.Global;
import cl.rinno.newdevicewall.models.Producto;


public class PlanesControlFunSimpleAdapter extends RecyclerView.Adapter<PlanesControlFunSimpleAdapter.PlanesControlFunViewHolder>{

    ArrayList<Producto> planesList;
    MainActivity activity;

    public PlanesControlFunSimpleAdapter(ArrayList<Producto> planesList, MainActivity activity) {
        this.planesList = planesList;
        this.activity = activity;

    }

    @Override
    public PlanesControlFunViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_planes_control,parent,false);
        return new PlanesControlFunViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlanesControlFunViewHolder holder, final int position) {
        final Producto plan = planesList.get(position);
        Log.i( "TAG", "onBindViewHolder: " + Global.dirImages + plan.getDetalles().get( 0 ).getValue() );

        holder.planControl.setImageURI( Uri.fromFile( new File( Global.dirImages + plan.getDetalles().get( 0 ).getValue() ) ) );

    }

    @Override
    public int getItemCount() {
        return planesList.size();
    }

    class PlanesControlFunViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView planControl;

        PlanesControlFunViewHolder(View itemView) {
            super(itemView);
            planControl = (SimpleDraweeView) itemView.findViewById( R.id.planControl );

        }
    }
}
