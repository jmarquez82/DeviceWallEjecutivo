package cl.rinno.newdevicewall.adapters;

import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.ArrayList;

import cl.rinno.newdevicewall.MainActivity;
import cl.rinno.newdevicewall.R;
import cl.rinno.newdevicewall.models.Global;
import cl.rinno.newdevicewall.models.Producto;
import cl.rinno.newdevicewall.models.Provider;
import cl.rinno.newdevicewall.models.Session;

/**
 * Created by chinodoge on 13-03-2017.
 */

public class FiltrosAdapter extends RecyclerView.Adapter<FiltrosAdapter.FiltrosViewHolder>{

    private ArrayList<String> filterList;
    private int type;
    private MainActivity mainActivity;
    private ArrayList<Provider> providersArrayList;

    public FiltrosAdapter(ArrayList<Provider> providersArrayList, int type, MainActivity mainActivity, String xd) {
        this.providersArrayList = providersArrayList;
        this.type = type;
        this.mainActivity = mainActivity;
    }

    public FiltrosAdapter(ArrayList<String> filterList, int type, MainActivity mainActivity) {
        this.filterList = filterList;
        this.type = type;
        this.mainActivity = mainActivity;
    }

    @Override
    public FiltrosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(type == 4){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_accessory,parent,false);
            return new FiltrosViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_device,parent,false);
            return new FiltrosViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(final FiltrosViewHolder holder, final int position) {
        if(type == 0){
            holder.imgProvider.setImageURI(Uri.fromFile(new File(Global.dirImages + Global.providersDevices.get(position).getProvider_image())));
            holder.txtFilter.setVisibility(View.GONE);
            holder.imgProvider.setVisibility(View.VISIBLE);
            if(Global.providersDevices.get(position).getId().equalsIgnoreCase("7") || Global.providersDevices.get(position).getId().equalsIgnoreCase("15") || Global.providersDevices.get(position).getId().equalsIgnoreCase("9")){
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(105,105);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                holder.rlParentImage.setLayoutParams(layoutParams);
            }
        }else if(type == 1 || type == 2 || type == 3){
            holder.imgProvider.setVisibility(View.GONE);
            holder.txtFilter.setVisibility(View.VISIBLE);
            holder.txtFilter.setText(filterList.get(position));
        } else if(type == 4){
            holder.imgProvider.setImageURI(Uri.fromFile(new File(Global.dirImages + Global.providersDevices.get(position).getProvider_image())));
            holder.txtFilter.setVisibility(View.GONE);
            holder.imgProvider.setVisibility(View.VISIBLE);
            if(Global.providersDevices.get(position).getId().equalsIgnoreCase("7") || Global.providersDevices.get(position).getId().equalsIgnoreCase("15") || Global.providersDevices.get(position).getId().equalsIgnoreCase("9")){
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(105,105);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                holder.rlParentImage.setLayoutParams(layoutParams);
            }
        }

        //setAnimation(holder.itemView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = holder.txtFilter.getText().toString();
                String valueTwo = "";
                switch(type){
                    case 0:
                    case 4:
                        value = Global.providersDevices.get(position).getName();
                        valueTwo = Global.providersDevices.get(position).getId();
                        break;
                    case 1:
                        switch (value) {
                            case "3\"":
                                valueTwo = "0\"";
                                break;
                            case "4\"":
                                valueTwo = "3\"";
                                break;
                            case "4.5\"":
                                valueTwo = "4\"";
                                break;
                            case "5\"":
                                valueTwo = "4.5\"";
                                break;
                            case "5.5\"":
                                valueTwo = "5\"";
                                break;
                            case "6\"+":
                                valueTwo = "5.5\"";
                                break;
                        }
                        break;
                    case 2:
                        switch (value) {
                            case "1.3MP":
                                valueTwo = "0M";
                                break;
                            case "3MP":
                                valueTwo = "1.3MP";
                                break;
                            case "5MP":
                                valueTwo = "3MP";
                                break;
                            case "8MP":
                                valueTwo = "5MP";
                                break;
                            case "13MP":
                                valueTwo = "8MP";
                                break;
                            case "20MP+":
                                valueTwo = "13MP";
                                break;
                        }
                        break;
                    case 3:
                        switch (value) {
                            case "1.3MP":
                                valueTwo = "0 ";
                                break;
                            case "3MP":
                                valueTwo = "1.3MP";
                                break;
                            case "5MP":
                                valueTwo = "3MP";
                                break;
                            case "8MP":
                                valueTwo = "5MP";
                                break;
                            case "13MP":
                                valueTwo = "8MP";
                                break;
                            case "20MP+":
                                valueTwo = "13MP";
                                break;
                        }
                        break;
                }
                final String finalValue = value;
                final String finalValueTwo = valueTwo;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mainActivity.setFilter(finalValue, finalValueTwo);
                    }
                },0);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(filterList == null){
            return providersArrayList.size();
        }else{
            return filterList.size();
        }
    }

    private void setAnimation(View viewToAnimate) {
        viewToAnimate.animate().setDuration(300).alpha(1.0f).scaleX(1.0f).scaleY(1.0f).start();
    }

    public class FiltrosViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView imgProvider;
        TextView txtFilter;
        RelativeLayout rlParentImage;

        public FiltrosViewHolder(View itemView) {
            super(itemView);
            rlParentImage = (RelativeLayout) itemView.findViewById(R.id.rl_parent_image);
            imgProvider = (SimpleDraweeView) itemView.findViewById(R.id.image_provider_filter);
            txtFilter = (TextView) itemView.findViewById(R.id.text_nombre_filtro);
        }
    }
}
