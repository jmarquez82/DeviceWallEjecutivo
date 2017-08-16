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
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.ArrayList;

import cl.rinno.newdevicewall.MainActivity;
import cl.rinno.newdevicewall.R;
import cl.rinno.newdevicewall.models.Global;
import cl.rinno.newdevicewall.models.Producto;
import cl.rinno.newdevicewall.models.Session;


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

    }

    @Override
    public PlanesSmartFunSimpleViewHolder onCreateViewHolder( ViewGroup parent, int viewType )
    {
        Point size = new Point();
        Display display = ((WindowManager) activity.getSystemService( Context.WINDOW_SERVICE )).getDefaultDisplay();
        display.getSize( size );
        int width = size.x;
        View view;
        Log.i( TAG, "onCreate: " + width );
        if ( width > 1300 )
        {

            view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_planes_libres_mx, parent, false );
        } else
        {
            view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_planes_libres, parent, false );
        }
        return new PlanesSmartFunSimpleViewHolder( view );
    }

    @Override
    public void onBindViewHolder( PlanesSmartFunSimpleViewHolder holder, int position )
    {
        final Producto plan = planesList.get( position );

        Log.i( TAG, "onBindViewHolder: " + plan.getName() );

        Log.i( "Planes", Session.objData.getGrupos().get( 0 ).getPlanes().get( position ).getBannerPlan() );
        holder.planLibre.setImageURI( Uri.fromFile( new File( Global.dirImages + Session.objData.getGrupos().get( 0 ).getPlanes().get( position ).getBannerPlan() ) ) );

        holder.verEquipos.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                activity.openEDSmartFun( plan.getName(), plan.getCuotaCredito(), 0, plan );
            }
        } );
    }

    @Override
    public int getItemCount()
    {
        return planesList.size();
    }

    class PlanesSmartFunSimpleViewHolder extends RecyclerView.ViewHolder
    {

        ImageView planLibre;
        ImageView verEquipos;

        PlanesSmartFunSimpleViewHolder( View itemView )
        {
            super( itemView );
            planLibre = (SimpleDraweeView) itemView.findViewById( R.id.planLibre );
            verEquipos = (ImageView) itemView.findViewById( R.id.verEquipos );

        }
    }
}
