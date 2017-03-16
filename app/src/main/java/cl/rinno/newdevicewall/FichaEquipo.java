package cl.rinno.newdevicewall;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cl.rinno.newdevicewall.adapters.AlmacenamientoEquipoAdapter;
import cl.rinno.newdevicewall.models.Global;
import cl.rinno.newdevicewall.models.Producto;

public class FichaEquipo extends AppCompatActivity {

    LinearLayoutManager linearLayoutManagerGB;
    LinearLayoutManager linearLayoutManagerCL;

    ArrayList<Producto> listHijos;

    @BindView(R.id.image_device)
    SimpleDraweeView imageDevice;
    @BindView(R.id.tv_provider_name)
    TextView tvProviderName;
    @BindView(R.id.tv_name_device)
    TextView tvNameDevice;
    @BindView(R.id.tv_screen_size)
    TextView tvScreenSize;
    @BindView(R.id.tv_back_camera)
    TextView tvBackCamera;
    @BindView(R.id.tv_front_camera)
    TextView tvFrontCamera;
    @BindView(R.id.rv_Almacenamiento)
    RecyclerView rvAlmacenamiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ficha_equipo);
        ButterKnife.bind(this);

        listHijos = new ArrayList<>();

        tvProviderName.setText(Global.producto.getProvider_name());
        tvNameDevice.setText(Global.producto.getName());
        imageDevice.setImageURI(Uri.fromFile(new File(Global.dirImages + Global.producto.getDetalles().get(0).getValue())));

        for (int i = 0; i < Global.producto.getDetalles().size(); i++) {
            switch (Global.producto.getDetalles().get(i).getKey()) {
                case "SS":
                    tvScreenSize.setText(Global.producto.getDetalles().get(i).getValue());
                    break;
                case "PC":
                    tvBackCamera.setText(Global.producto.getDetalles().get(i).getValue());
                    break;
                case "SC":
                    tvFrontCamera.setText(Global.producto.getDetalles().get(i).getValue());
                    break;
            }
        }

        linearLayoutManagerCL = new LinearLayoutManager(this);
        linearLayoutManagerCL.setOrientation(LinearLayoutManager.HORIZONTAL);

        linearLayoutManagerGB = new LinearLayoutManager(this);
        linearLayoutManagerGB.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvAlmacenamiento.setHasFixedSize(true);
        rvAlmacenamiento.setLayoutManager(linearLayoutManagerGB);

        if (Global.producto.getHijos() != null) {
            for (int i = 0; i < Global.producto.getHijos().size(); i++) {
                listHijos.add(Global.producto.getHijos().get(i));
            }
            rvAlmacenamiento.setAdapter(new AlmacenamientoEquipoAdapter(this, listHijos));
            rvAlmacenamiento.setVisibility(View.VISIBLE);
        } else {
            rvAlmacenamiento.setVisibility(View.INVISIBLE);
        }

    }
}
