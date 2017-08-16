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

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

import cl.rinno.newdevicewall.EquipoConPlanActivity;
import cl.rinno.newdevicewall.R;
import cl.rinno.newdevicewall.models.Global;
import cl.rinno.newdevicewall.models.Producto;


public class PlanesControlFunAdapter extends RecyclerView.Adapter<PlanesControlFunAdapter.PlanesControlFunViewHolder>
{

    ArrayList<Producto> planesList;
    EquipoConPlanActivity activity;
    Producto producto;

    private static final String TAG = "PlanesControlFunAdapter";

    public PlanesControlFunAdapter( ArrayList<Producto> planesList, EquipoConPlanActivity activity, Producto producto)
    {
        this.planesList = planesList;
        this.activity = activity;
        this.producto = producto;

    }

    @Override
    public PlanesControlFunViewHolder onCreateViewHolder( ViewGroup parent, int viewType )
    {
         Point size = new Point();
        Display display = ((WindowManager) activity.getSystemService( Context.WINDOW_SERVICE )).getDefaultDisplay();
        display.getSize( size );
        int width = size.x;
        View view;
        Log.i( TAG, "onCreate: " + width );
        if ( width > 1300 )
        {

            view =  LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_plan_controlfun_mx, parent, false );
        } else
        {
            view =  LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_plan_controlfun, parent, false );

        }

        return new PlanesControlFunViewHolder( view );
    }

    @Override
    public void onBindViewHolder( PlanesControlFunViewHolder holder, int position )
    {
        final Producto plan = planesList.get( position );





        holder.imagenBasePlan.setImageURI( Uri.fromFile( new File( Global.dirImages + plan.getImagenBase() ) ) );


        for ( int i = 0; i < producto.getPlanes().size(); i++ )
        {
            Log.i( TAG, Global.producto.getPlanes().get( i ).getVC() );
            Log.i( TAG, Global.producto.getPlanes().get( i ).getPM() );

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

    class PlanesControlFunViewHolder extends RecyclerView.ViewHolder
    {

        SimpleDraweeView imagenBasePlan;
        TextView pagoInicial, pagoCuota;



        PlanesControlFunViewHolder( View itemView )
        {
            super( itemView );
            imagenBasePlan = (SimpleDraweeView) itemView.findViewById( R.id.imagenBasePlanControl );
            pagoInicial = (TextView) itemView.findViewById( R.id.pagoInicial );
            pagoCuota = (TextView) itemView.findViewById( R.id.pagoCuota );
        }
    }
}
