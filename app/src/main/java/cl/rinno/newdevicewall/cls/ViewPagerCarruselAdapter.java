package cl.rinno.newdevicewall.cls;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import cl.rinno.newdevicewall.fragments.CarruselFragment;
import cl.rinno.newdevicewall.models.Producto;

/**
 * Created by chinodoge on 22-03-2017.
 */

public class ViewPagerCarruselAdapter extends FragmentPagerAdapter {

    ArrayList<Producto> productoArrayList;
    int type;

    public ViewPagerCarruselAdapter(FragmentManager fm, ArrayList<Producto> productoArrayList, int type) {
        super(fm);
        this.productoArrayList = productoArrayList;
        this.type = type;
    }

    @Override
    public Fragment getItem(int pos) {
        return new CarruselFragment(productoArrayList.get(pos), type);
    }

    @Override
    public int getCount() {
        return productoArrayList.size();
    }
}
