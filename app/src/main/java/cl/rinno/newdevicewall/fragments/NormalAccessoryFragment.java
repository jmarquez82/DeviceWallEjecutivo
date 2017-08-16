package cl.rinno.newdevicewall.fragments;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.github.mmin18.widget.RealtimeBlurView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cl.rinno.newdevicewall.MainActivity;
import cl.rinno.newdevicewall.R;
import cl.rinno.newdevicewall.adapters.CaracteristicasAdapter;
import cl.rinno.newdevicewall.cls.NonSwipeableViewPager;
import cl.rinno.newdevicewall.cls.ViewPagerCarruselAdapter;
import cl.rinno.newdevicewall.models.Global;
import cl.rinno.newdevicewall.models.Producto;
import cl.rinno.newdevicewall.models.Session;
import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.PageItemClickListener;
import me.crosswall.lib.coverflow.core.PagerContainer;

/**
 * A simple {@link Fragment} subclass.
 */
public class NormalAccessoryFragment extends Fragment {

    LinearLayoutManager linearLayoutManagerCaract;
    ArrayList<String> caracteristicasList;

    MainActivity mainActivity;
    Producto producto;

    @BindView(R.id.image_accented_accessory)
    SimpleDraweeView imageNormalAccessory;
    @BindView(R.id.tv_provider_normal_accessory)
    TextView tvProviderNormalAccessory;
    @BindView(R.id.tv_name_normal_accessory)
    TextView tvNameNormalAccessory;
    @BindView(R.id.rv_caracteristicas)
    RecyclerView rvCaracteristicas;
    @BindView(R.id.tv_precio_venta_accessory)
    TextView tvPrecioVentaAccessory;
    @BindView(R.id.button_close_fragment)
    LinearLayout btnCloseFragment;
    @BindView(R.id.blur_content)
    RealtimeBlurView blurContent;
    @BindView(R.id.vp_carrusel)
    NonSwipeableViewPager vpCarrusel;
    @BindView(R.id.pager_container)
    PagerContainer pagerContainer;

    ViewPagerCarruselAdapter viewPagerCarruselAdapter;
    ArrayList<Producto> equiposCompatibles;
    @BindView(R.id.lineat_back_accesory)
    LinearLayout btnBackAccesory;
    @BindView(R.id.lineat_next_accesory)
    LinearLayout lineatNextAccesory;

    public NormalAccessoryFragment() {
        // Required empty public constructor
    }

    public NormalAccessoryFragment(MainActivity mainActivity, Producto producto) {
        this.mainActivity = mainActivity;
        this.producto = producto;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_normal_accessory, container, false);
        ButterKnife.bind(this, view);
        caracteristicasList = new ArrayList<>();
        equiposCompatibles = new ArrayList<>();
        linearLayoutManagerCaract = new LinearLayoutManager(getContext());
        linearLayoutManagerCaract.setOrientation(LinearLayoutManager.VERTICAL);
        tvNameNormalAccessory.setText(Global.producto.getName());
        tvProviderNormalAccessory.setText(Global.producto.getProvider_name());


        imageNormalAccessory.setImageURI( Uri.fromFile( new File( Global.dirImages + producto.getImagenPrimaria() )));
        tvPrecioVentaAccessory.setText(getString(R.string.precio_venta, producto.getPrecioVenta()));
        rvCaracteristicas.setHasFixedSize(true);
        rvCaracteristicas.setLayoutManager(linearLayoutManagerCaract);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                caracteristicasList.add( producto.getAtributoUno() );
                caracteristicasList.add( producto.getAtributoDos() );
                caracteristicasList.add( producto.getAtributoTres() );


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                CaracteristicasAdapter caracteristicasAdapter = new CaracteristicasAdapter(caracteristicasList);
                rvCaracteristicas.setAdapter(caracteristicasAdapter);

            }
        }.execute();


        if(equiposCompatibles.size() > 2){
            btnBackAccesory.setVisibility(View.VISIBLE);
            btnBackAccesory.setBackground(getResources().getDrawable(R.drawable.bg_disabled));
            lineatNextAccesory.setVisibility(View.VISIBLE);
        }else{
            btnBackAccesory.setVisibility(View.GONE);
            lineatNextAccesory.setVisibility(View.GONE);
        }

        viewPagerCarruselAdapter = new ViewPagerCarruselAdapter(getChildFragmentManager(), equiposCompatibles, 1);
        vpCarrusel.setAdapter(viewPagerCarruselAdapter);

        vpCarrusel.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    btnBackAccesory.setBackground(getResources().getDrawable(R.drawable.bg_disabled));
                } else {
                    btnBackAccesory.setBackground(getResources().getDrawable(R.drawable.bg_type_filters_device));
                }
                if(position == (equiposCompatibles.size()-1)){
                    lineatNextAccesory.setBackground(getResources().getDrawable(R.drawable.bg_disabled));
                }else{
                    lineatNextAccesory.setBackground(getResources().getDrawable(R.drawable.bg_type_filters_device));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        new CoverFlow.Builder()
                .with(vpCarrusel)
                .pagerMargin(0f)
                .scale(0.4f)
                .spaceSize(0f)
                .rotationY(0f)
                .build();

        pagerContainer.setOverlapEnabled(true);

        return view;
    }

    @OnClick({R.id.blur_content, R.id.button_close_fragment, R.id.content_modal, R.id.lineat_back_accesory, R.id.lineat_next_accesory})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.blur_content:
                break;
            case R.id.button_close_fragment:
                break;
            case R.id.content_modal:
                break;
            case R.id.lineat_back_accesory:
                if(vpCarrusel.getCurrentItem()>0){
                    vpCarrusel.setCurrentItem(vpCarrusel.getCurrentItem()-1);
                }
                break;
            case R.id.lineat_next_accesory:
                vpCarrusel.setCurrentItem(vpCarrusel.getCurrentItem()+1);
                break;
        }
    }
}
