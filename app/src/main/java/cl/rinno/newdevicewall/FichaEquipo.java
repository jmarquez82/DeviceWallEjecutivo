package cl.rinno.newdevicewall;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cl.rinno.newdevicewall.adapters.AlmacenamientoEquipoAdapter;
import cl.rinno.newdevicewall.adapters.ColorAdapter;
import cl.rinno.newdevicewall.cls.ViewPagerCarruselAdapter;
import cl.rinno.newdevicewall.fragments.AccentedAccessoryFragment;
import cl.rinno.newdevicewall.models.Global;
import cl.rinno.newdevicewall.models.Producto;
import cl.rinno.newdevicewall.models.Session;
import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.PageItemClickListener;
import me.crosswall.lib.coverflow.core.PagerContainer;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

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
    @BindView(R.id.rvColores)
    RecyclerView rvColor;
    @BindView(R.id.llColores)
    LinearLayout llColores;
    @BindView(R.id.tv_precio_venta)
    TextView tvPrecioVenta;
    @BindView(R.id.button_volver_catalogo)
    LinearLayout btnVolverCatalogo;
    @BindView(R.id.button_llevatelo_con_un_plan)
    LinearLayout btnLlevateloConUnPlan;
    @BindView(R.id.image_sticker)
    SimpleDraweeView imgSticker;
    @BindView(R.id.vp_carrusel)
    ViewPager vpCarrusel;
    @BindView(R.id.pager_container)
    PagerContainer pagerContainer;

    ViewPagerCarruselAdapter viewPagerCarruselAdapter;
    ArrayList<Producto> accesoriosCompatibles;
    @BindView(R.id.lineat_next_accesory)
    LinearLayout btnNextAccessory;
    @BindView(R.id.lineat_back_accesory)
    LinearLayout btnBackAccessory;

    int cont;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @BindView(R.id.linearLayout3)
    LinearLayout linearLayout3;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.textView8)
    LinearLayout textView8;
    @BindView(R.id.textView10)
    LinearLayout textView10;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.textView14)
    LinearLayout textView14;
    @BindView(R.id.tv_cuota_mensual)
    TextView tvCuotaMensual;
    @BindView(R.id.linearLayout5)
    LinearLayout linearLayout5;
    @BindView(R.id.textView15)
    TextView textView15;
    @BindView(R.id.textView16)
    TextView textView16;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.textView12)
    TextView tvCae;
    @BindView(R.id.textView17)
    LinearLayout textView17;
    @BindView(R.id.textView22)
    TextView tvTotalAPagar;
    @BindView(R.id.fragment_accesorio_relacionado)
    FrameLayout fragmentAccesorioRelacionado;

    AccentedAccessoryFragment accentedAccessoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ficha_equipo);
        ButterKnife.bind(this);

        listHijos = new ArrayList<>();
        accesoriosCompatibles = new ArrayList<>();

        tvProviderName.setText(Global.producto.getProvider_name());
        tvNameDevice.setText(Global.producto.getName());
        imageDevice.setImageURI(Uri.fromFile(new File(Global.dirImages + Global.producto.getDetalles().get(0).getValue())));
        tvPrecioVenta.setText(getString(R.string.precio_venta, Global.producto.getPrecios().get(0).getValue()));
        tvCuotaMensual.setText(getString(R.string.precio_venta, Global.producto.getCae().get(1).getValue()));
        tvCae.setText(getString(R.string.precio_cae, Global.producto.getCae().get(0).getValue()));
        tvTotalAPagar.setText(getString(R.string.precio_venta, Global.producto.getCae().get(2).getValue()));

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
                case "CL":
                    if (Global.producto.getDetalles().get(i).getValue().startsWith("#")) {
                        ArrayList<String> listColores = new ArrayList<>();
                        String[] items = Global.producto.getDetalles().get(i).getValue().split(",");
                        Collections.addAll(listColores, items);
                        ColorAdapter colorAdapter = new ColorAdapter(listColores, FichaEquipo.this);
                        rvColor.setAdapter(colorAdapter);
                        Log.d("Colores", Global.producto.getDetalles().get(i).getValue());
                        rvColor.setVisibility(View.VISIBLE);
                        llColores.setVisibility(View.VISIBLE);
                    } else {
                        rvColor.setVisibility(View.INVISIBLE);
                        llColores.setVisibility(View.INVISIBLE);
                        Log.d("Colores", Global.producto.getDetalles().get(i).getValue());
                    }
                    break;
                case "ST":
                    imgSticker.setImageURI(Uri.fromFile(new File(Global.dirImages + Global.producto.getDetalles().get(i).getValue())));
                    break;
            }
        }

        linearLayoutManagerCL = new LinearLayoutManager(this);
        linearLayoutManagerCL.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvColor.setHasFixedSize(true);
        rvColor.setLayoutManager(linearLayoutManagerCL);

        linearLayoutManagerGB = new LinearLayoutManager(this);
        linearLayoutManagerGB.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvAlmacenamiento.setHasFixedSize(true);
        rvAlmacenamiento.setLayoutManager(linearLayoutManagerGB);

        if (Global.producto.getHijos() != null) {
            for (int i = 0; i < Global.producto.getHijos().size(); i++) {
                listHijos.add(Global.producto.getHijos().get(i));
            }
            Collections.sort(listHijos, new Comparator<Producto>() {
                @Override
                public int compare(Producto o1, Producto o2) {
                    int val = Integer.parseInt(o1.getDetalles().get(5).getValue().split(" ")[0]);
                    int val2 = Integer.parseInt(o2.getDetalles().get(5).getValue().split(" ")[0]);
                    return val - val2;
                }
            });
            rvAlmacenamiento.setAdapter(new AlmacenamientoEquipoAdapter(this, listHijos));
            rvAlmacenamiento.setVisibility(View.VISIBLE);
        } else {
            rvAlmacenamiento.setVisibility(View.INVISIBLE);
        }

        for (int r = 0; r < Global.producto.getAccesorios().size(); r++) {
            for (int k = 0; k < Session.objData.getAccessories().size(); k++) {
                if (Session.objData.getAccessories().get(k).getId().equals(Global.producto.getAccesorios().get(r).getId())) {
                    Global.producto.getAccesorios().get(r).setDetalles(Session.objData.getAccessories().get(k).getDetalles());
                    Global.producto.getAccesorios().get(r).setProvider_name(Session.objData.getAccessories().get(k).getProvider_name());
                    Global.producto.getAccesorios().get(r).setPrecios(Session.objData.getAccessories().get(k).getPrecios());
                    Global.producto.getAccesorios().get(r).setCae(Session.objData.getAccessories().get(k).getCae());
                    break;
                }
            }
            accesoriosCompatibles.add(Global.producto.getAccesorios().get(r));
        }

        viewPagerCarruselAdapter = new ViewPagerCarruselAdapter(getSupportFragmentManager(), accesoriosCompatibles, 0);

        vpCarrusel.setAdapter(viewPagerCarruselAdapter);


        new CoverFlow.Builder()
                .with(vpCarrusel)
                .pagerMargin(-20f)
                .scale(0.4f)
                .spaceSize(0f)
                .rotationY(0f)
                .build();

        pagerContainer.setOverlapEnabled(true);

        pagerContainer.setPageItemClickListener(new PageItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                Log.d("QUE SUCEDE REALMENTE", "Viejo " + i);
            }
        });

        btnBackAccessory.setBackground(getResources().getDrawable(R.drawable.bg_disabled));

        vpCarrusel.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    btnBackAccessory.setBackground(getResources().getDrawable(R.drawable.bg_disabled));
                } else {
                    btnBackAccessory.setBackground(getResources().getDrawable(R.drawable.bg_type_filters_device));
                }
                if (position == (accesoriosCompatibles.size() - 1)) {
                    btnNextAccessory.setBackground(getResources().getDrawable(R.drawable.bg_disabled));
                } else {
                    btnNextAccessory.setBackground(getResources().getDrawable(R.drawable.bg_type_filters_device));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        cont = 0;

        vpCarrusel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                vpCarrusel.setCurrentItem(vpCarrusel.getCurrentItem());
                if (cont == 0) {
                    Log.d("POSITION", vpCarrusel.getCurrentItem() + "");
                    accentedAccessoryFragment = new AccentedAccessoryFragment(FichaEquipo.this, accesoriosCompatibles.get(vpCarrusel.getCurrentItem()));
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.fragment_accesorio_relacionado, accentedAccessoryFragment);
                    transaction.commit();
                    fragmentAccesorioRelacionado.setVisibility(View.VISIBLE);
                    cont++;
                }
                return true;
            }
        });
    }

    @OnClick({R.id.button_volver_catalogo, R.id.button_llevatelo_con_un_plan, R.id.lineat_back_accesory, R.id.lineat_next_accesory})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_volver_catalogo:
                finish();
                break;
            case R.id.button_llevatelo_con_un_plan:
                startActivity(new Intent(FichaEquipo.this, EquipoConPlanActivity.class));
                break;
            case R.id.lineat_back_accesory:
                if (vpCarrusel.getCurrentItem() > 0) {
                    vpCarrusel.setCurrentItem(vpCarrusel.getCurrentItem() - 1);
                }
                break;
            case R.id.lineat_next_accesory:
                vpCarrusel.setCurrentItem(vpCarrusel.getCurrentItem() + 1);
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase, R.attr.fontPath));
    }

    public void closeBlurAccessories() {
        fragmentAccesorioRelacionado.setVisibility(View.GONE);
        cont = 0;
    }
}
