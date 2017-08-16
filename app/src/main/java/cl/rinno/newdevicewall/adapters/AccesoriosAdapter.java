package cl.rinno.newdevicewall.adapters;

import android.content.Context;
import android.graphics.Color;
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
import java.util.Random;

import cl.rinno.newdevicewall.R;
import cl.rinno.newdevicewall.models.Global;
import cl.rinno.newdevicewall.models.Producto;
 

public class AccesoriosAdapter extends RecyclerView.Adapter<AccesoriosAdapter.AccesoriosViewHolder>{
    ArrayList<Producto> accesoriosList;
    Context context;

    private static final String TAG = "AccesoriosAdapter";

    public AccesoriosAdapter(ArrayList<Producto> accesoriosList, Context context) {
        this.accesoriosList = accesoriosList;
        this.context = context;
    }

    @Override
    public AccesoriosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accesorio_galeria,parent,false);
        return new AccesoriosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AccesoriosViewHolder holder, int position) {
        //TODO CAMBIAR PROVEEDOR ID
        holder.txtProviderName.setText(accesoriosList.get(position).getProvider_name());
        holder.txtName.setText(accesoriosList.get(position).getName());
        holder.imgAccesorio.setImageURI(         Uri.fromFile( new File( Global.dirImages +accesoriosList.get(position).getImagenPrimaria() ) )
        );


        Random rand = new Random();
        int numberColorRandom = rand.nextInt(6);
        String Colors[][] = Global.getBackgroundColorsCard();

        Log.i( TAG, accesoriosList.get(position).getTam() );
        if(accesoriosList.get(position).getTam().equalsIgnoreCase("1")){
            holder.txtProviderName.setTextColor(context.getResources().getColor(R.color.dimGray));
            holder.txtName.setTextColor(context.getResources().getColor(R.color.orange));
            holder.itemContainer.setBackgroundColor(context.getResources().getColor(R.color.white));
        }else{
            holder.txtProviderName.setTextColor(context.getResources().getColor(R.color.white));
            holder.txtName.setTextColor(context.getResources().getColor(R.color.white));
            holder.itemContainer.setBackgroundColor(Color.parseColor(Colors[numberColorRandom][0]));
        }
    }

    @Override
    public int getItemCount() {
        return accesoriosList.size();
    }

    class AccesoriosViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView imgAccesorio;
        TextView txtProviderName;
        TextView txtName;
        LinearLayout itemContainer;

        public AccesoriosViewHolder(View itemView) {
            super(itemView);
            imgAccesorio = (SimpleDraweeView) itemView.findViewById(R.id.image_product);
            txtName = (TextView) itemView.findViewById(R.id.product_name);
            txtProviderName = (TextView) itemView.findViewById(R.id.provider_name);
            itemContainer = (LinearLayout) itemView.findViewById(R.id.item_container);
        }
    }
}
