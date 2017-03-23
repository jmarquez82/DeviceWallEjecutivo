package cl.rinno.newdevicewall.fragments;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.github.mmin18.widget.RealtimeBlurView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cl.rinno.newdevicewall.MainActivity;
import cl.rinno.newdevicewall.R;
import cl.rinno.newdevicewall.adapters.CaracteristicasDestacadoAdapter;
import cl.rinno.newdevicewall.models.Global;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccentedAccessoryFragment extends Fragment {

    MainActivity mainActivity;
    ArrayList<String> caracteristicasList;
    LinearLayoutManager linearLayoutManagerCaract;

    @BindView(R.id.blur_content)
    RealtimeBlurView blurContent;
    @BindView(R.id.tv_provider_name)
    TextView tvProviderName;
    @BindView(R.id.tv_name_accessory)
    TextView tvNameAccessory;
    @BindView(R.id.image_accented_accessory)
    SimpleDraweeView imageAccentedAccessory;
    @BindView(R.id.rv_caracteristicas)
    RecyclerView rvCaracteristicas;
    @BindView(R.id.button_close_fragment)
    LinearLayout buttonCloseFragment;
    @BindView(R.id.tv_total_a_pagar)
    TextView tvTotalAPagar;
    @BindView(R.id.tv_cae)
    TextView tvCae;
    @BindView(R.id.tv_cuota_mensual)
    TextView tvCuotaMensual;
    @BindView(R.id.tv_precio_venta)
    TextView tvPrecioVenta;
    @BindView(R.id.content_precios)
    ConstraintLayout contentPrecios;
    @BindView(R.id.content_descuento)
    ConstraintLayout contentDescuento;
    @BindView(R.id.content_main)
    ConstraintLayout contentMain;

    public AccentedAccessoryFragment() {
        // Required empty public constructor
    }

    public AccentedAccessoryFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accented_accessory, container, false);
        ButterKnife.bind(this, view);
        caracteristicasList = new ArrayList<>();
        linearLayoutManagerCaract = new LinearLayoutManager(getContext());
        linearLayoutManagerCaract.setOrientation(LinearLayoutManager.VERTICAL);
        tvNameAccessory.setText(Global.producto.getName());
        tvProviderName.setText(Global.producto.getProvider_name());
        imageAccentedAccessory.setImageURI(Uri.fromFile(new File(Global.dirImages + Global.producto.getDetalles().get(0).getValue())));
        tvPrecioVenta.setText(getString(R.string.precio_venta, Global.producto.getPrecios().get(0).getValue()));
        rvCaracteristicas.setHasFixedSize(true);
        rvCaracteristicas.setLayoutManager(linearLayoutManagerCaract);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                for (int i = 0; i < Global.producto.getDetalles().size(); i++) {
                    switch (Global.producto.getDetalles().get(i).getKey()) {
                        case "ATONE":
                        case "ATTWO":
                        case "ATTHREE":
                        case "ATFOUR":
                        case "ATFIVE":
                            caracteristicasList.add(Global.producto.getDetalles().get(i).getValue());
                            break;
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                CaracteristicasDestacadoAdapter caracteristicasAdapter = new CaracteristicasDestacadoAdapter(caracteristicasList);
                rvCaracteristicas.setAdapter(caracteristicasAdapter);
            }
        }.execute();
        return view;
    }

    @OnClick({R.id.blur_content, R.id.button_close_fragment,R.id.content_precios, R.id.content_descuento, R.id.content_main})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.blur_content:
                mainActivity.closeBlurAccessories();
                break;
            case R.id.button_close_fragment:
                mainActivity.closeBlurAccessories();
                break;
            case R.id.content_precios:
                break;
            case R.id.content_descuento:
                break;
            case R.id.content_main:
                break;
        }
    }
}
