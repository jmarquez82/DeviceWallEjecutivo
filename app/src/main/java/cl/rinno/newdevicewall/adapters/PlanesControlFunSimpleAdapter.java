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
import cl.rinno.newdevicewall.models.Session;


public class PlanesControlFunSimpleAdapter extends RecyclerView.Adapter<PlanesControlFunSimpleAdapter.PlanesControlFunViewHolder>{

    ArrayList<Producto> planesList;
    MainActivity activity;
    private static final String TAG = "PlanesControlFunSimpleA";
    public PlanesControlFunSimpleAdapter(ArrayList<Producto> planesList, MainActivity activity) {
        this.planesList = planesList;
        this.activity = activity;

    }

    @Override
    public PlanesControlFunViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Point size = new Point();
        Display display = ((WindowManager) activity.getSystemService( Context.WINDOW_SERVICE )).getDefaultDisplay();
        display.getSize( size );
        int width = size.x;
        View view;
        Log.i( TAG, "onCreate: " + width );
        if ( width > 1300 )
        {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_planes_control_mx,parent,false);
        } else
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_planes_control,parent,false);
        }
        return new PlanesControlFunViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlanesControlFunViewHolder holder, final int position) {
        final Producto plan = planesList.get(position);
     //   Log.i( "TAG", "onBindViewHolder: " + Global.dirImages + plan.getDetalles().get( 0 ).getValue() );

        Log.i( "Planes", Session.objData.getGrupos().get( 1 ).getPlanes().get( position ).getBannerPlan() );
        File f = new File( Global.dirImages + Session.objData.getGrupos().get( 1 ).getPlanes().get( position ).getBannerPlan() ) ;
        if(f.exists())
        {
            Log.i( TAG, "Imagen Existe" );
        }
        holder.planControl.setImageURI( Uri.fromFile( new File( Global.dirImages + Session.objData.getGrupos().get( 1 ).getPlanes().get( position ).getBannerPlan() ) ) );
            holder.verEquiposControlados.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    activity.openEDSmartFun(plan.getName(), plan.getCuotaCredito(),0, plan);

                }
            } );
    }

    @Override
    public int getItemCount() {
        return planesList.size();
    }

    class PlanesControlFunViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView planControl;
        LinearLayout verEquiposControlados;

        PlanesControlFunViewHolder(View itemView) {
            super(itemView);
            planControl = (SimpleDraweeView) itemView.findViewById( R.id.planControl );
            verEquiposControlados= (LinearLayout) itemView.findViewById( R.id.verEquiposControlados );

        }
    }
}
