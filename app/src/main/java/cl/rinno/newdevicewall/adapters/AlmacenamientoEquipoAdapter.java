package cl.rinno.newdevicewall.adapters;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cl.rinno.newdevicewall.FichaEquipo;
import cl.rinno.newdevicewall.R;
import cl.rinno.newdevicewall.models.Global;
import cl.rinno.newdevicewall.models.Producto;

public class AlmacenamientoEquipoAdapter extends RecyclerView.Adapter<AlmacenamientoEquipoAdapter.AlmacenamientoEquipoViewHolder>
{

    FichaEquipo context;
    ArrayList<Producto> listaHIjos;

    private static final String TAG = "AlmacenamientoEquipoAda";

    public AlmacenamientoEquipoAdapter( FichaEquipo context, ArrayList<Producto> listaHIjos )
    {
        this.context = context;
        this.listaHIjos = listaHIjos;
    }

    @Override
    public AlmacenamientoEquipoViewHolder onCreateViewHolder( ViewGroup parent, int viewType )
    {
        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_almacenamiento, parent, false );
        return new AlmacenamientoEquipoViewHolder( v );
    }

    @Override
    public void onBindViewHolder( AlmacenamientoEquipoViewHolder holder, final int position )
    {
        Producto hijo = new Producto();

        for ( Producto product : Global.allProducts )
        {
            if ( listaHIjos.get( position ).getId().equals( product.getId() ) )
            {
                hijo = product;
            }
        }


        Log.i( TAG, hijo.getMemoriaInterna() );
        holder.txtGB.setText( hijo.getMemoriaInterna() );
        if ( listaHIjos.get( position ).getMemoriaInterna().equals( Global.producto.getMemoriaInterna() ) )
        {
            if ( context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE )
            {
                holder.itemView.setBackgroundResource( R.drawable.bg_gb_selected_land );
                holder.txtGB.setTextColor( context.getResources().getColor( R.color.white ) );
            } else
            {
                holder.itemView.setBackgroundResource( R.drawable.bg_gb_selected );
                holder.txtGB.setTextColor( context.getResources().getColor( R.color.yellow ) );
            }
            holder.txtGB.setTypeface( null, Typeface.BOLD_ITALIC );

        }
        final Producto finalHijo = hijo;
        holder.itemView.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                Activity activity = context;
                Log.i( TAG, finalHijo.getId() + " - " + Global.producto.getId() );

                if ( !Global.producto.getMemoriaInterna().equals( finalHijo.getId() ) )
                {
                    ArrayList<Producto> hijos = Global.producto.getHijos();
                    ArrayList<Producto> accesorios = Global.producto.getAccesorios();

                    Global.producto = finalHijo;
                    Global.producto.setAccesorios( accesorios );
                    Global.producto.setHijos( hijos );

                    activity.startActivity( new Intent( activity.getApplicationContext(), FichaEquipo.class ) );
                    activity.overridePendingTransition( 0, 0 );
                    activity.finish();
                }
            }
        } );

    }

    @Override
    public int getItemCount()
    {
        return listaHIjos.size();
    }

    public static class AlmacenamientoEquipoViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtGB;

        public AlmacenamientoEquipoViewHolder( View itemView )
        {
            super( itemView );
            txtGB = (TextView) itemView.findViewById( R.id.txtAlmacenamiendo );
        }
    }
}
