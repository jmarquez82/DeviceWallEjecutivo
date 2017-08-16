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
import java.math.BigDecimal;
import java.util.ArrayList;

import cl.rinno.newdevicewall.R;
import cl.rinno.newdevicewall.models.Global;
import cl.rinno.newdevicewall.models.Producto;


public class EquiposDestacadosAdapter extends RecyclerView.Adapter<EquiposDestacadosAdapter.EquiposDestacadosViewHolder>
{

    ArrayList<Producto> productoArrayList;
    Context context;
    Producto plan;
    private static final String TAG = "EquiposDestacadosAdapte";

    public EquiposDestacadosAdapter( ArrayList<Producto> productoArrayList, Context context, Producto plan )
    {
        this.productoArrayList = productoArrayList;
        this.context = context;
        this.plan = plan;
    }

    @Override
    public EquiposDestacadosViewHolder onCreateViewHolder( ViewGroup parent, int viewType )
    {
        Point size = new Point();
        Display display = ((WindowManager) context.getSystemService( Context.WINDOW_SERVICE )).getDefaultDisplay();
        display.getSize( size );
        int width = size.x;
        View view;
        if ( width > 1300 )
        {

            view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_equipo_destacado_mx, parent, false );
        } else
        {
            view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_equipo_destacado, parent, false );
        }
        return new EquiposDestacadosViewHolder( view );
    }

    @Override
    public void onBindViewHolder( EquiposDestacadosViewHolder holder, int position )
    {
        holder.txtNombre.setText( productoArrayList.get( position ).getName() );
        holder.imgEquipo.setImageURI( Uri.fromFile( new File( Global.dirImages + productoArrayList.get( position ).getImagenPrimaria() ) ) );

        holder.txtProvider.setText( productoArrayList.get( position ).getProvider_name() );
        holder.txtPrecioVenta.setText( productoArrayList.get( position ).getPrecioVenta() );

        for ( int i = 0; i < productoArrayList.get( position ).getPlanes().size(); i++ )
        {
            if ( productoArrayList.get( position ).getPlanes().get( i ).getId().equals( plan.getId() ) )
            {
                holder.pagoMinimoDestacado.setText( "$" + productoArrayList.get( position ).getPlanes().get( i ).getPM() );
                holder.pagoCuotaDestacado.setText( "$" + productoArrayList.get( position ).getPlanes().get( i ).getVC() );

                Log.i( TAG, "Valor 1" + productoArrayList.get( position ).getPlanes().get( i ).getVC() );
                Log.i( TAG, "Valor 2" + plan.getCuotaCredito() );

                BigDecimal valorTelefono = new BigDecimal( productoArrayList.get( position ).getPlanes().get( i ).getVC() );
                BigDecimal valorPlan = new BigDecimal( plan.getCuotaCredito() );
                BigDecimal totalValor = valorPlan.add( valorTelefono );

                Log.i( TAG, "Monto Final " + totalValor );
                holder.valorTotal.setText( "$" + totalValor );

            }
        }
    }

    @Override
    public int getItemCount()
    {
        return productoArrayList.size();
    }

    class EquiposDestacadosViewHolder extends RecyclerView.ViewHolder
    {

        SimpleDraweeView imgEquipo;
        TextView txtNombre;
        TextView txtProvider;
        TextView txtPrecioVenta;
        TextView txtCuotaMensual;
        TextView txtCae;
        TextView txtTotalAPagar;
        TextView pagoMinimoDestacado;
        TextView pagoCuotaDestacado;
        TextView valorTotal;

        public EquiposDestacadosViewHolder( View itemView )
        {
            super( itemView );

            imgEquipo = (SimpleDraweeView) itemView.findViewById( R.id.image_equipo_destacado );
            txtNombre = (TextView) itemView.findViewById( R.id.tv_nombre_equipo_destacado );
            txtProvider = (TextView) itemView.findViewById( R.id.tv_provider_equipo_destacado );
            txtPrecioVenta = (TextView) itemView.findViewById( R.id.tv_precio_venta );
            txtCuotaMensual = (TextView) itemView.findViewById( R.id.tv_cuota_mensual );
            txtCae = (TextView) itemView.findViewById( R.id.tv_cae_equipo_destacado );
            txtTotalAPagar = (TextView) itemView.findViewById( R.id.tv_total_a_pagar_equipo_destacado );
            pagoMinimoDestacado = (TextView) itemView.findViewById( R.id.pagoMinimoDestacado );
            pagoCuotaDestacado = (TextView) itemView.findViewById( R.id.pagoCuotaDestacado );
            valorTotal = (TextView) itemView.findViewById( R.id.valorTotal );
        }
    }
}
