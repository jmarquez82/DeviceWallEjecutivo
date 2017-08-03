package cl.rinno.newdevicewall.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.ArrayList;

import cl.rinno.newdevicewall.MainActivity;
import cl.rinno.newdevicewall.R;
import cl.rinno.newdevicewall.models.Global;
import cl.rinno.newdevicewall.models.Producto;


/**
 * Created by chinodoge on 21-03-2017.
 */

public class PlanesSmartFunSimpleAdapter extends RecyclerView.Adapter<PlanesSmartFunSimpleAdapter.PlanesSmartFunSimpleViewHolder>
{

    private static final String TAG = "PlanesSmartFunSimpleAda";
    ArrayList<Producto> planesList;
    MainActivity activity;

    public PlanesSmartFunSimpleAdapter( ArrayList<Producto> planesList, MainActivity activity )
    {
        this.planesList = planesList;
        this.activity = activity;

        Log.i( TAG, "AAAAAAAAAAAAAAAAA!" );
    }

    @Override
    public PlanesSmartFunSimpleViewHolder onCreateViewHolder( ViewGroup parent, int viewType )
    {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_planes_libres, parent, false );
        return new PlanesSmartFunSimpleViewHolder( view );
    }

    @Override
    public void onBindViewHolder( PlanesSmartFunSimpleViewHolder holder, int position )
    {
        final Producto plan = planesList.get( position );

        Log.i( TAG, "onBindViewHolder: " + plan.getName() );
        holder.planLibre.setImageURI( Uri.fromFile( new File( Global.dirImages + plan.getDetalles().get( 0 ).getValue() ) ) );

    }

    @Override
    public int getItemCount()
    {
        return planesList.size();
    }

    class PlanesSmartFunSimpleViewHolder extends RecyclerView.ViewHolder
    {

        SimpleDraweeView planLibre;

        PlanesSmartFunSimpleViewHolder( View itemView )
        {
            super( itemView );
            planLibre = (SimpleDraweeView) itemView.findViewById( R.id.planLibre );

        }
    }
}
