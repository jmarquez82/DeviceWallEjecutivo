package cl.rinno.newdevicewall.gridlibrery.listener;

import android.view.View;

import cl.rinno.newdevicewall.gridlibrery.GridItem;


/**
 * Created by EasonX on 15/5/20.
 */
public interface OnItemSelectedListener {

    void onItemSelected(GridItem gridItem, View view, boolean hasFocus);

}
