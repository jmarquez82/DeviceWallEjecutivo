package cl.rinno.newdevicewall.gridlibrery.listener;

import android.view.LayoutInflater;
import android.view.View;

import cl.rinno.newdevicewall.gridlibrery.GridItem;


public interface OnViewCreateCallBack {

    View onViewCreate(LayoutInflater inflater, View convertView, GridItem gridItem);

}
