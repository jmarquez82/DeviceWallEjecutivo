package cl.rinno.newdevicewall.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import cl.rinno.newdevicewall.R;
import cl.rinno.newdevicewall.models.Global;
import cl.rinno.newdevicewall.models.Producto;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarruselFragment extends Fragment {

    Producto producto;
    int type;
    @BindView(R.id.image_product)
    SimpleDraweeView imgProduct;
    @BindView(R.id.tv_product_name)
    TextView txtProductName;
    @BindView(R.id.tv_provider_name)
    TextView txtProviderName;
    @BindView(R.id.fragment_carrusel)
    ConstraintLayout fragmentCarrusel;

    public CarruselFragment(Producto producto, int type) {
        this.producto = producto;
        this.type = type;
    }

    public CarruselFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carrusel, container, false);
        ButterKnife.bind(this, view);
        //Si es 0 es un accesorio y se le pone un background
        //Si es 1 es un celular, se le quita el fondo y el color del nombre es naranjo.
        if (type == 0) {
            fragmentCarrusel.setBackgroundResource(R.drawable.bg_carrusel_fragment);
            txtProductName.setTextColor(getResources().getColor(R.color.dimGray));
        }else if(type == 1){
            fragmentCarrusel.setBackgroundColor(getResources().getColor(R.color.white));
            txtProductName.setTextColor(getResources().getColor(R.color.orange));
        }
        imgProduct.setImageURI(Uri.fromFile(new File(Global.dirImages + producto.getDetalles().get(0).getValue())));
        txtProductName.setText(producto.getName());
        txtProviderName.setText(producto.getProvider_name());
        return view;
    }

}
