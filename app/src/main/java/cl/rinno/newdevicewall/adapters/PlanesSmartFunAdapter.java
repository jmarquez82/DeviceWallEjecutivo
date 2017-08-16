package cl.rinno.newdevicewall.adapters;

import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.ArrayList;

import cl.rinno.newdevicewall.EquipoConPlanActivity;
import cl.rinno.newdevicewall.R;
import cl.rinno.newdevicewall.models.Global;
import cl.rinno.newdevicewall.models.Producto;


public class PlanesSmartFunAdapter extends RecyclerView.Adapter<PlanesSmartFunAdapter.PlanesSmartFunViewHolder>
{

    private ArrayList<Producto> planesList;
    private EquipoConPlanActivity activity;
    Producto producto;

    private static final String TAG = "PlanesSmartFunAdapter";

    public PlanesSmartFunAdapter( ArrayList<Producto> planesList, EquipoConPlanActivity activity, Producto producto )
    {
        this.planesList = planesList;
        this.activity = activity;
        this.producto = producto;

    }

    @Override
    public PlanesSmartFunViewHolder onCreateViewHolder( ViewGroup parent, int viewType )
    {

        Point size = new Point();
        Display display = ((WindowManager) activity.getSystemService( Context.WINDOW_SERVICE )).getDefaultDisplay();
        display.getSize( size );
        int width = size.x;
        View view;
        Log.i( TAG, "onCreate: " + width );
        if ( width > 1300 )
        {

          view =  LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_plan_smartfun_mx, parent, false );
        } else
        {
            view =  LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_plan_smartfun, parent, false );

         }        return new PlanesSmartFunViewHolder( view );
    }

    @Override
    public void onBindViewHolder( PlanesSmartFunViewHolder holder, int position )
    {
        final Producto plan = planesList.get( position );

        holder.imagenBasePlan.setImageURI( Uri.fromFile( new File( Global.dirImages + plan.getImagenBase() ) ) );
        for ( int i = 0; i < producto.getPlanes().size(); i++ )
        {


            if ( producto.getPlanes().get( i ).getId().equals( plan.getId() ) )
            {

                holder.pagoInicial.setText( "$" + producto.getPlanes().get( i ).getPM() );
                holder.pagoCuota.setText( "$" + producto.getPlanes().get( i ).getVC() );
                break;
            }
        }

    }

    @Override
    public int getItemCount()
    {
        return planesList.size();
    }

    class PlanesSmartFunViewHolder extends RecyclerView.ViewHolder
    {

        SimpleDraweeView imagenBasePlan;
        TextView pagoInicial, pagoCuota;

        PlanesSmartFunViewHolder( View itemView )
        {
            super( itemView );
            imagenBasePlan = (SimpleDraweeView) itemView.findViewById( R.id.imagenBasePlan );
            pagoInicial = (TextView) itemView.findViewById( R.id.pagoInicialLibre );
            pagoCuota = (TextView) itemView.findViewById( R.id.pagoCuotaLibre );
        }
    }
}

