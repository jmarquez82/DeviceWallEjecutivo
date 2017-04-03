package cl.rinno.newdevicewall;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cl.rinno.newdevicewall.adapters.AccesoriosAdapter;
import cl.rinno.newdevicewall.adapters.EquiposCompatiblesAdapter;
import cl.rinno.newdevicewall.adapters.EquiposDestacadosAdapter;
import cl.rinno.newdevicewall.adapters.FiltrosAdapter;
import cl.rinno.newdevicewall.adapters.PlanDeVozAdapter;
import cl.rinno.newdevicewall.adapters.PlanesControlFunSimpleAdapter;
import cl.rinno.newdevicewall.adapters.PlanesSmartFunSimpleAdapter;
import cl.rinno.newdevicewall.cls.TimerDestacado;
import cl.rinno.newdevicewall.fragments.AccentedAccessoryFragment;
import cl.rinno.newdevicewall.gridlibrery.GridBuilder;
import cl.rinno.newdevicewall.gridlibrery.GridItem;
import cl.rinno.newdevicewall.gridlibrery.GridViewHolder;
import cl.rinno.newdevicewall.gridlibrery.calculator.HorizontalPositionCalculator;
import cl.rinno.newdevicewall.gridlibrery.listener.OnViewCreateCallBack;
import cl.rinno.newdevicewall.models.Global;
import cl.rinno.newdevicewall.models.Producto;
import cl.rinno.newdevicewall.models.Session;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    ImageView imageCloseType;
    ImageView imageCloseFilter;

    RelativeLayout rlParent;

    String filtro = "";
    ArrayList<String> filterList;

    TimerDestacado timerDestacado;

    LinearLayoutManager linearLayoutManagerACC;

    ArrayList<Producto> equiposCompatiblesList;
    @BindView(R.id.horizontal_scroll_grid)
    HorizontalScrollView horizontalScrollGrid;
    @BindView(R.id.icn_filtro_multimedia)
    ImageView icnFiltroMultimedia;
    @BindView(R.id.text_filtro_multimedia)
    TextView textFiltroMultimedia;
    @BindView(R.id.button_multimedia_filter)
    LinearLayout btnMultimediaFilter;
    @BindView(R.id.icn_filtro_proteccion)
    ImageView icnFiltroProteccion;
    @BindView(R.id.text_filtro_proteccion)
    TextView textFiltroProteccion;
    @BindView(R.id.button_proteccion_filter)
    LinearLayout btnProteccionFilter;
    @BindView(R.id.icn_filtro_energia)
    ImageView icnFiltroEnergia;
    @BindView(R.id.text_filtro_energia)
    TextView textFiltroEnergia;
    @BindView(R.id.button_energia_filter)
    LinearLayout btnEnergiaFilter;
    @BindView(R.id.icn_filtro_por_equipo)
    ImageView icnFiltroPorEquipo;
    @BindView(R.id.text_filtro_por_equipo)
    TextView textFiltroPorEquipo;
    @BindView(R.id.button_por_equipo_filter)
    LinearLayout btnPorEquipoFilter;
    @BindView(R.id.fragment_accesorio)
    FrameLayout fragmentAccesorio;
    @BindView(R.id.image_banner_plan)
    SimpleDraweeView imgBannerPlan;
    @BindView(R.id.rv_planes_cuenta_controlada)
    RecyclerView rvPlanesCuentaControlada;
    @BindView(R.id.rv_planes_voz_ilimitado)
    RecyclerView rvPlanesVozIlimitado;
    @BindView(R.id.constraint_plan_voz)
    ConstraintLayout constraintPlanVoz;
    @BindView(R.id.constraint_plans)
    LinearLayout constraintPlans;
    @BindView(R.id.icn_plan_smartfun)
    ImageView icnPlanSmartfun;
    @BindView(R.id.text_plan_smartfun)
    TextView textPlanSmartfun;
    @BindView(R.id.button_smartfun)
    LinearLayout btnSmartfun;
    @BindView(R.id.icn_plan_controlfun)
    ImageView icnPlanControlfun;
    @BindView(R.id.text_plan_controlfun)
    TextView textPlanControlfun;
    @BindView(R.id.button_controlfun)
    LinearLayout btnControlfun;
    @BindView(R.id.icn_plan_voz)
    ImageView icnPlanVoz;
    @BindView(R.id.text_plan_voz)
    TextView textPlanVoz;
    @BindView(R.id.button_planvoz)
    LinearLayout btnPlanvoz;
    @BindView(R.id.tv_plan_type)
    TextView tvPlanType;
    @BindView(R.id.linear_plan_elegido)
    LinearLayout linearPlanElegido;
    @BindView(R.id.linear_condiciones_comerciales_solo_voz)
    LinearLayout btnCondicionesComercialesSoloVoz;
    @BindView(R.id.rv_planes_smartfun)
    RecyclerView rvPlanesSmartfun;
    @BindView(R.id.linear_condiciones_comerciales_smart_fun)
    LinearLayout btnCondicionesComercialesSmartFun;
    @BindView(R.id.constraint_plan_smartfun)
    ConstraintLayout constraintPlanSmartfun;
    @BindView(R.id.rv_planes_controlfun)
    RecyclerView rvPlanesControlfun;
    @BindView(R.id.linear_condiciones_comerciales_control_fun)
    LinearLayout btnCondicionesComercialesControlFun;
    @BindView(R.id.constraint_plan_controlfun)
    ConstraintLayout constraintPlanControlfun;
    @BindView(R.id.grid_container)
    GridLayout gridContainer;
    @BindView(R.id.linear_close_nosearch)
    LinearLayout btnCloseNoSearch;
    @BindView(R.id.linear_search_error)
    LinearLayout linearSearchError;
    @BindView(R.id.linear_selecciona_una_marca_equipo_comp)
    LinearLayout linearSeleccionaUnaMarcaEquipoComp;
    @BindView(R.id.pulsator_right)
    PulsatorLayout pulsatorRight;
    @BindView(R.id.pulsator_left)
    PulsatorLayout pulsatorLeft;
    @BindView(R.id.linear_selecciona_filtro_blur)
    LinearLayout linearSeleccionaFiltroBlur;
    @BindView(R.id.image_provider_selected)
    SimpleDraweeView imageProviderSelected;
    @BindView(R.id.rl_parent_image)
    RelativeLayout rlParentImage;
    @BindView(R.id.tv_provider_selected)
    TextView tvProviderSelected;
    @BindView(R.id.rv_equipos_compatibles)
    RecyclerView rvEquiposCompatibles;
    @BindView(R.id.linear_elegir_equipo_compatible)
    LinearLayout linearElegirEquipoCompatible;
    @BindView(R.id.img_device_selected)
    SimpleDraweeView imgDeviceSelected;
    @BindView(R.id.tv_proveedor_equipo_compatible_seleccionado)
    TextView tvProveedorEquipoCompatibleSeleccionado;
    @BindView(R.id.tv_nombre_equipo_compatible_seleccionado)
    TextView tvNombreEquipoCompatibleSeleccionado;
    @BindView(R.id.rv_accesorios)
    RecyclerView rvAccesorios;
    @BindView(R.id.button_arrow_left_filter)
    SimpleDraweeView buttonArrowLeftFilter;
    @BindView(R.id.button_arrow_right_filter)
    SimpleDraweeView buttonArrowRightFilter;
    @BindView(R.id.linear_resultado_equipo_compatible)
    LinearLayout linearResultadoEquipoCompatible;
    @BindView(R.id.image_destacado)
    SimpleDraweeView imgDestacado;
    @BindView(R.id.textView29)
    TextView textView29;
    @BindView(R.id.textView35)
    TextView textView35;
    @BindView(R.id.textView38)
    TextView textView38;
    @BindView(R.id.textView44)
    TextView textView44;
    @BindView(R.id.textView45)
    TextView textView45;
    @BindView(R.id.textView46)
    TextView textView46;
    @BindView(R.id.image_popup)
    SimpleDraweeView imagePopup;
    @BindView(R.id.linear_close_popup)
    LinearLayout linearClosePopup;
    @BindView(R.id.rl_popup)
    RelativeLayout rlPopup;
    @BindView(R.id.linearLayout11)
    LinearLayout linearLayout11;
    @BindView(R.id.textView56)
    TextView textView56;
    @BindView(R.id.tv_precio_equipo_destacado_sf)
    TextView tvPrecioEquipoDestacadoSF;
    @BindView(R.id.textView54)
    TextView textView54;
    @BindView(R.id.textView53)
    TextView textView53;
    @BindView(R.id.tv_gb_plan_sf)
    TextView tvGbPlanSf;
    @BindView(R.id.textView51)
    TextView textView51;
    @BindView(R.id.textView49)
    TextView textView49;
    @BindView(R.id.constraintLayout2)
    ConstraintLayout constraintLayout2;
    @BindView(R.id.rv_equipos_destacados_sf)
    RecyclerView rvEquiposDestacadosSf;
    @BindView(R.id.constraint_equipo_destacado_sf)
    ConstraintLayout constraintEquipoDestacadoSf;
    @BindView(R.id.linear_close_equipo_destacado_sf)
    LinearLayout linearCloseEquipoDestacadoSf;
    @BindView(R.id.rl_equipo_destacado_smart_fun)
    RelativeLayout rlEquipoDestacadoSmartFun;
    private GridLayout mGridLayout;

    Boolean deviceState, accessoryState, planState;
    ArrayList<Producto> productList;
    ArrayList<Producto> filterProductList;

    @BindView(R.id.linear_que_buscas)
    LinearLayout linearQueBuscas;
    @BindView(R.id.tv_filter_product_type)
    TextView tvFilterProductType;
    @BindView(R.id.linear_selecciona_filtro)
    LinearLayout linearSeleccionaFiltro;
    @BindView(R.id.rl_buttons_parent)
    RelativeLayout rlButtonsParent;
    @BindView(R.id.button_devices)
    LinearLayout btnDevices;
    @BindView(R.id.button_accessories)
    LinearLayout btnAccessories;
    @BindView(R.id.button_plans)
    LinearLayout btnPlans;
    @BindView(R.id.relative_content_devices)
    RelativeLayout rlContentDevices;
    @BindView(R.id.relative_content_accessory)
    RelativeLayout rlContentAccessory;
    @BindView(R.id.relative_content_plans)
    RelativeLayout rlContentPlans;
    @BindView(R.id.linear_content_filters)
    RelativeLayout linearContentFilters;
    @BindView(R.id.button_arrow_right)
    SimpleDraweeView btnArrowRight;
    @BindView(R.id.button_arrow_left)
    SimpleDraweeView btnArrowLeft;
    @BindView(R.id.linear_selecciona_plan)
    LinearLayout linearSeleccionaPlan;
    @BindView(R.id.button_marca_filter)
    LinearLayout btnFiltroMarca;
    @BindView(R.id.button_pantalla_filter)
    LinearLayout btnFiltroPantalla;
    @BindView(R.id.button_camara_trasera_filter)
    LinearLayout btnFiltroCamaraTrasera;
    @BindView(R.id.button_camara_frontal_filter)
    LinearLayout btnFiltroCamaraFrontal;
    @BindView(R.id.icn_filtro_marca)
    ImageView icnFiltroMarca;
    @BindView(R.id.text_filtro_marca)
    TextView textFiltroMarca;
    @BindView(R.id.icn_filtro_pantalla)
    ImageView icnFiltroPantalla;
    @BindView(R.id.text_filtro_pantalla)
    TextView textFiltroPantalla;
    @BindView(R.id.icn_filtro_camara_trasera)
    ImageView icnFiltroCamaraTrasera;
    @BindView(R.id.text_filtro_camara_trasera)
    TextView textFiltroCamaraTrasera;
    @BindView(R.id.icn_filtro_camara_frontal)
    ImageView icnFiltroCamaraFrontal;
    @BindView(R.id.text_filtro_camara_frontal)
    TextView textFiltroCamaraFrontal;
    @BindView(R.id.tv_filter_name)
    TextView tvFilterName;
    @BindView(R.id.linear_nombre_filtro)
    LinearLayout linearNombreFiltro;
    @BindView(R.id.tv_filter_type)
    TextView tvFilterType;
    @BindView(R.id.tv_filter_name_device)
    TextView tvFilterNameDevice;
    @BindView(R.id.tv_filter_result_device)
    TextView tvFilterResultDevice;
    @BindView(R.id.linear_filter_results_devices)
    LinearLayout linearFilterResultsDevices;
    @BindView(R.id.blur_content)
    RelativeLayout blurContent;
    @BindView(R.id.linear_close_filters)
    LinearLayout btnCloseFilters;
    @BindView(R.id.rv_filters)
    RecyclerView rvFilters;
    @BindView(R.id.text_selecciona)
    TextView textSelecciona;
    @BindView(R.id.text_filter_name)
    TextView textFilterName;

    private SharedPreferences configuracionFile;

    AccentedAccessoryFragment accentedAccessoryFragment;
    LinearLayoutManager linearLayoutManagerVCC, linearLayoutManagerVI, linearLayoutManagerSF, linearLayoutManagerCF, llmEquiposDestacados;

    PlanDeVozAdapter planCuentraControladaAdapter, planVozIlimitadoAdapter;
    PlanesSmartFunSimpleAdapter planesSmartFunSimpleAdapter;
    PlanesControlFunSimpleAdapter planesControlFunSimpleAdapter;

    ArrayList<Producto> accesoriosList;
    ArrayList<Producto> equiposDestacadosRandom;

    int cont = 0;

    @Override
    protected void onPause() {
        super.onPause();
        timerDestacado.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        timerDestacado.start();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        timerDestacado.cancel();
        timerDestacado.start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        int currentApiVersion = Build.VERSION.SDK_INT;
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {

            getWindow().getDecorView().setSystemUiVisibility(flags);
            final View decorView = getWindow().getDecorView();
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                        decorView.setSystemUiVisibility(flags);
                    }
                }
            });
        }

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mGridLayout = (GridLayout) findViewById(R.id.grid_container);

        imageCloseType = new ImageView(this);
        imageCloseFilter = new ImageView(this);
        rlParent = new RelativeLayout(this);
        deviceState = false;
        accessoryState = false;
        planState = false;

        timerDestacado = new TimerDestacado(120000, 1000, MainActivity.this);

        configuracionFile = this.getSharedPreferences("configuracion", MODE_PRIVATE);

        filterList = new ArrayList<>();
        productList = new ArrayList<>();
        filterProductList = new ArrayList<>();
        equiposCompatiblesList = new ArrayList<>();
        equiposDestacadosRandom = new ArrayList<>();

        linearLayoutManagerVCC = new LinearLayoutManager(this);
        linearLayoutManagerVCC.setOrientation(LinearLayoutManager.VERTICAL);

        linearLayoutManagerVI = new LinearLayoutManager(this);
        linearLayoutManagerVI.setOrientation(LinearLayoutManager.VERTICAL);

        linearLayoutManagerSF = new LinearLayoutManager(this);
        linearLayoutManagerSF.setOrientation(LinearLayoutManager.VERTICAL);

        linearLayoutManagerCF = new LinearLayoutManager(this);
        linearLayoutManagerCF.setOrientation(LinearLayoutManager.VERTICAL);

        llmEquiposDestacados = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);

        rvPlanesVozIlimitado.setLayoutManager(linearLayoutManagerVI);
        rvPlanesCuentaControlada.setLayoutManager(linearLayoutManagerVCC);
        rvPlanesSmartfun.setLayoutManager(linearLayoutManagerSF);
        rvPlanesControlfun.setLayoutManager(linearLayoutManagerCF);
        rvEquiposDestacadosSf.setLayoutManager(llmEquiposDestacados);

        rvPlanesControlfun.setHasFixedSize(true);
        rvPlanesSmartfun.setHasFixedSize(true);
        rvPlanesCuentaControlada.setHasFixedSize(true);
        rvPlanesVozIlimitado.setHasFixedSize(true);
        rvEquiposDestacadosSf.setHasFixedSize(true);

        imageCloseType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlButtonsParent.removeView(imageCloseType);
                closeContents();
                closeTextFilters();
                linearQueBuscas.setVisibility(View.VISIBLE);
                setDefuaultColors();
                rlParent.removeView(imageCloseFilter);
                blurContent.setVisibility(View.GONE);
                filterDevices(Global.allProducts);
                deviceState = false;
                accessoryState = false;
                planState = false;
            }
        });

        imageCloseFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlParent.removeView(imageCloseFilter);
                setDefuaultColors();
                closeTextFilters();
                linearSearchError.setVisibility(View.GONE);
                blurContent.setVisibility(View.GONE);
                if (deviceState) {
                    closePlans();
                    linearSeleccionaFiltro.setVisibility(View.VISIBLE);
                } else if (accessoryState) {
                    closePlans();
                    linearSeleccionaFiltro.setVisibility(View.VISIBLE);
                } else if (planState) {
                    closePlans();
                    filterDevices(Global.allPlans);
                    linearSeleccionaPlan.setVisibility(View.VISIBLE);
                }
            }
        });

        horizontalScrollGrid.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollX = horizontalScrollGrid.getScrollX();
                if (scrollX == 0) {
                    btnArrowLeft.setVisibility(View.GONE);
                    pulsatorLeft.setVisibility(View.GONE);
                } else if (scrollX == horizontalScrollGrid.getChildAt(0).getMeasuredWidth() - getWindowManager().getDefaultDisplay().getWidth()) {
                    btnArrowRight.setVisibility(View.GONE);
                    pulsatorRight.setVisibility(View.GONE);
                } else {
                    btnArrowLeft.setVisibility(View.VISIBLE);
                    btnArrowRight.setVisibility(View.VISIBLE);
                    pulsatorLeft.setVisibility(View.VISIBLE);
                    pulsatorRight.setVisibility(View.VISIBLE);
                }
            }
        });

        rvAccesorios.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.d("DX", "" + dx);
                if (linearLayoutManagerACC.findFirstVisibleItemPosition() == 0) {
                    buttonArrowLeftFilter.setVisibility(View.GONE);
                } else if (linearLayoutManagerACC.findLastCompletelyVisibleItemPosition() == accesoriosList.size() - 1) {
                    buttonArrowRightFilter.setVisibility(View.GONE);
                } else {
                    buttonArrowRightFilter.setVisibility(View.VISIBLE);
                    buttonArrowLeftFilter.setVisibility(View.VISIBLE);
                }
            }
        });

        timerDestacado.start();

        filterDevices(Global.allProducts);
    }


    private void setCloseTypeImage(LinearLayout linear) {
        Drawable myDrawable = getResources().getDrawable(R.drawable.close);
        rlButtonsParent.removeView(imageCloseType);
        imageCloseType.setLayoutParams(new ViewGroup.LayoutParams(66, 66));
        imageCloseType.setImageDrawable(myDrawable);
        imageCloseType.setX(linear.getX() + 250);
        imageCloseType.setY(10);
        rlButtonsParent.addView(imageCloseType);
    }

    private void setCloseFilterImage(LinearLayout linear) {
        Drawable myDrawable = getResources().getDrawable(R.drawable.close);
        rlParent.removeView(imageCloseFilter);
        imageCloseFilter.setLayoutParams(new ViewGroup.LayoutParams(50, 50));
        imageCloseFilter.setImageDrawable(myDrawable);
        imageCloseFilter.setX(linear.getX() + 182);
        imageCloseFilter.setY(linear.getY() - 20);
        rlParent.addView(imageCloseFilter);
    }

    private void closeTextFilters() {
        linearQueBuscas.setVisibility(View.GONE);
        linearSeleccionaFiltro.setVisibility(View.GONE);
        linearSeleccionaPlan.setVisibility(View.GONE);
        linearNombreFiltro.setVisibility(View.GONE);
        linearFilterResultsDevices.setVisibility(View.GONE);
    }

    private void closeContents() {
        rlContentDevices.setVisibility(View.GONE);
        rlContentAccessory.setVisibility(View.GONE);
        rlContentPlans.setVisibility(View.GONE);
        closePlans();
        linearSearchError.setVisibility(View.GONE);
        closeFilterAccessory();
    }

    private void closePlans() {
        constraintPlans.setVisibility(View.GONE);
        constraintPlanVoz.setVisibility(View.GONE);
        constraintPlanControlfun.setVisibility(View.GONE);
        constraintPlanSmartfun.setVisibility(View.GONE);
    }

    private void closeFilterAccessory() {
        linearFilterResultsDevices.setVisibility(View.GONE);
        linearResultadoEquipoCompatible.setVisibility(View.GONE);
        linearSeleccionaUnaMarcaEquipoComp.setVisibility(View.GONE);
        linearSeleccionaFiltro.setVisibility(View.GONE);
        blurContent.setVisibility(View.GONE);
    }

    private void setDefuaultColors() {
        btnSmartfun.setBackground(getResources().getDrawable(R.drawable.bg_filter_plans));
        btnPlanvoz.setBackground(getResources().getDrawable(R.drawable.bg_filter_plans));
        btnControlfun.setBackground(getResources().getDrawable(R.drawable.bg_filter_plans));
        btnFiltroMarca.setBackground(getResources().getDrawable(R.drawable.bg_filter_devices));
        btnFiltroPantalla.setBackground(getResources().getDrawable(R.drawable.bg_filter_devices));
        btnFiltroCamaraTrasera.setBackground(getResources().getDrawable(R.drawable.bg_filter_devices));
        btnFiltroCamaraFrontal.setBackground(getResources().getDrawable(R.drawable.bg_filter_devices));
        btnProteccionFilter.setBackground(getResources().getDrawable(R.drawable.bg_filter_accessory));
        btnMultimediaFilter.setBackground(getResources().getDrawable(R.drawable.bg_filter_accessory));
        btnPorEquipoFilter.setBackground(getResources().getDrawable(R.drawable.bg_filter_accessory));
        btnEnergiaFilter.setBackground(getResources().getDrawable(R.drawable.bg_filter_accessory));


        icnFiltroPantalla.setImageResource(R.drawable.filtro_pantalla);
        icnFiltroMarca.setImageResource(R.drawable.filtro_marca);
        icnFiltroCamaraFrontal.setImageResource(R.drawable.filtro_camara_frontal);
        icnFiltroCamaraTrasera.setImageResource(R.drawable.filtro_camara_trasera);
        icnFiltroMultimedia.setImageResource(R.drawable.icn_multimedia);
        icnFiltroProteccion.setImageResource(R.drawable.icn_proteccion);
        icnFiltroEnergia.setImageResource(R.drawable.icn_energia);
        icnFiltroPorEquipo.setImageResource(R.drawable.icn_por_equipo);
        icnPlanSmartfun.setImageResource(R.drawable.icn_smartfun);
        icnPlanControlfun.setImageResource(R.drawable.icn_controlfun);
        icnPlanVoz.setImageResource(R.drawable.icn_planvoz);


        textFiltroPorEquipo.setTextColor(getResources().getColor(R.color.white));
        textFiltroProteccion.setTextColor(getResources().getColor(R.color.white));
        textFiltroMultimedia.setTextColor(getResources().getColor(R.color.white));
        textFiltroEnergia.setTextColor(getResources().getColor(R.color.white));
        textFiltroCamaraFrontal.setTextColor(getResources().getColor(R.color.white));
        textFiltroMarca.setTextColor(getResources().getColor(R.color.white));
        textFiltroPantalla.setTextColor(getResources().getColor(R.color.white));
        textFiltroCamaraTrasera.setTextColor(getResources().getColor(R.color.white));
        textPlanControlfun.setTextColor(getResources().getColor(R.color.white));
        textPlanSmartfun.setTextColor(getResources().getColor(R.color.white));
        textPlanVoz.setTextColor(getResources().getColor(R.color.white));
    }

    private void openBlur(String filter) {
        blurContent.setVisibility(View.VISIBLE);
        linearElegirEquipoCompatible.setVisibility(View.GONE);
        linearSeleccionaFiltroBlur.setVisibility(View.GONE);
        switch (filter) {
            case "Marca":
                linearSeleccionaFiltroBlur.setVisibility(View.VISIBLE);
                textSelecciona.setText("Selecciona la");
                textFilterName.setText("Marca_");
                seleccionarMarca();
                break;
            case "Pantalla":
                linearSeleccionaFiltroBlur.setVisibility(View.VISIBLE);
                textSelecciona.setText("Selecciona el");
                textFilterName.setText("tamaño de la pantalla_");
                seleccionarPantalla();
                break;
            case "Cámara Trasera":
                linearSeleccionaFiltroBlur.setVisibility(View.VISIBLE);
                textSelecciona.setText("Selecciona la");
                textFilterName.setText("Cámara trasera_");
                seleccionarCamaraTrasera();
                break;
            case "Cámara Frontal":
                linearSeleccionaFiltroBlur.setVisibility(View.VISIBLE);
                textSelecciona.setText("Selecciona la");
                textFilterName.setText("Cámara frontal_");
                seleccionarCamaraFrontal();
                break;
            case "EquiposCompatibles":
                textSelecciona.setText("Selecciona la");
                textFilterName.setText("Marca_");
                linearSeleccionaFiltroBlur.setVisibility(View.VISIBLE);
                linearSeleccionaUnaMarcaEquipoComp.setVisibility(View.VISIBLE);
                seleccionarEquipoCompatible();
                break;
        }
    }

    public void moveGrid(int position) {
        rlPopup.setVisibility(View.GONE);
        imgDestacado.setVisibility(View.GONE);
        rlButtonsParent.removeView(imageCloseType);
        closeContents();
        fragmentAccesorio.setVisibility(View.GONE);
        closeTextFilters();
        linearQueBuscas.setVisibility(View.VISIBLE);
        setDefuaultColors();
        rlParent.removeView(imageCloseFilter);
        blurContent.setVisibility(View.GONE);
        filterDevices(Global.allProducts);
        deviceState = false;
        accessoryState = false;
        planState = false;
        Log.d("posicion", "" + position);
        horizontalScrollGrid.fullScroll(View.FOCUS_LEFT);
        horizontalScrollGrid.smoothScrollBy(horizontalScrollGrid.getLeft() + position, horizontalScrollGrid.getTop());

    }


    @OnClick({R.id.linear_condiciones_comerciales_control_fun, R.id.linear_close_nosearch, R.id.linear_condiciones_comerciales_solo_voz, R.id.linear_condiciones_comerciales_smart_fun, R.id.constraint_plans, R.id.button_smartfun, R.id.button_controlfun, R.id.button_planvoz, R.id.button_devices, R.id.linear_close_filters, R.id.button_accessories, R.id.button_plans, R.id.button_arrow_right, R.id.button_arrow_left, R.id.button_marca_filter, R.id.button_pantalla_filter, R.id.button_camara_trasera_filter, R.id.button_camara_frontal_filter, R.id.button_multimedia_filter, R.id.button_proteccion_filter, R.id.button_energia_filter, R.id.button_por_equipo_filter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.constraint_plans:
                break;
            case R.id.linear_close_filters:
                blurContent.setVisibility(View.GONE);
                setDefuaultColors();
                rlParent.removeView(imageCloseFilter);
                closeTextFilters();
                linearSeleccionaFiltro.setVisibility(View.VISIBLE);
                break;
            case R.id.button_devices:
                closeContents();
                closeTextFilters();
                setDefuaultColors();
                accessoryState = false;
                planState = false;
                if (!deviceState) {
                    blurContent.setVisibility(View.GONE);
                    setCloseTypeImage(btnDevices);
                    rlParent.removeView(imageCloseFilter);
                    rlContentDevices.setVisibility(View.VISIBLE);
                    linearSeleccionaFiltro.setVisibility(View.VISIBLE);
                    tvFilterProductType.setText("equipos_");
                    filterDevices(Global.allDevices);
                    deviceState = true;
                } else {
                    closeTextFilters();
                    linearQueBuscas.setVisibility(View.VISIBLE);
                    rlButtonsParent.removeView(imageCloseType);
                    rlParent.removeView(imageCloseFilter);
                    blurContent.setVisibility(View.GONE);
                    filterDevices(Global.allProducts);
                    deviceState = false;
                }
                break;
            case R.id.button_accessories:
                closeContents();
                closeTextFilters();
                setDefuaultColors();
                deviceState = false;
                planState = false;
                if (!accessoryState) {
                    blurContent.setVisibility(View.GONE);
                    setCloseTypeImage(btnAccessories);
                    rlParent.removeView(imageCloseFilter);
                    rlContentAccessory.setVisibility(View.VISIBLE);
                    linearSeleccionaFiltro.setVisibility(View.VISIBLE);
                    tvFilterProductType.setText("accesorios_");
                    accessoryState = true;
                    filterDevices(Global.allAccessories);
                } else {
                    linearQueBuscas.setVisibility(View.VISIBLE);
                    rlButtonsParent.removeView(imageCloseType);
                    rlParent.removeView(imageCloseFilter);
                    blurContent.setVisibility(View.GONE);
                    filterDevices(Global.allProducts);
                    accessoryState = false;
                }

                break;
            case R.id.button_plans:
                closeContents();
                closeTextFilters();
                setDefuaultColors();
                deviceState = false;
                accessoryState = false;
                if (!planState) {
                    blurContent.setVisibility(View.GONE);
                    setCloseTypeImage(btnPlans);
                    rlParent.removeView(imageCloseFilter);
                    rlContentPlans.setVisibility(View.VISIBLE);
                    linearSeleccionaPlan.setVisibility(View.VISIBLE);
                    filterDevices(Global.allPlans);
                    planState = true;
                } else {
                    linearQueBuscas.setVisibility(View.VISIBLE);
                    rlButtonsParent.removeView(imageCloseType);
                    rlParent.removeView(imageCloseFilter);
                    blurContent.setVisibility(View.GONE);
                    filterDevices(Global.allProducts);
                    planState = false;
                }
                break;
            case R.id.button_arrow_right:
                horizontalScrollGrid.smoothScrollBy(horizontalScrollGrid.getLeft() + 960, horizontalScrollGrid.getTop());
                break;
            case R.id.button_arrow_left:
                horizontalScrollGrid.smoothScrollBy(horizontalScrollGrid.getLeft() - 960, horizontalScrollGrid.getTop());
                break;
            case R.id.button_marca_filter:
                linearSearchError.setVisibility(View.GONE);
                setDefuaultColors();
                closeTextFilters();
                rlParent = rlContentDevices;
                btnFiltroMarca.setBackground(getResources().getDrawable(R.drawable.bg_filter_selected));
                icnFiltroMarca.setImageResource(R.drawable.icn_marca_selected);
                textFiltroMarca.setTextColor(getResources().getColor(R.color.blue));
                filtro = "Marca";
                tvFilterName.setText(filtro);
                linearNombreFiltro.setVisibility(View.VISIBLE);
                openBlur(filtro);
                break;
            case R.id.button_pantalla_filter:
                linearSearchError.setVisibility(View.GONE);
                setDefuaultColors();
                closeTextFilters();
                rlParent = rlContentDevices;
                btnFiltroPantalla.setBackground(getResources().getDrawable(R.drawable.bg_filter_selected));
                textFiltroPantalla.setTextColor(getResources().getColor(R.color.blue));
                icnFiltroPantalla.setImageResource(R.drawable.icn_pantalla_selected);
                filtro = "Pantalla";
                tvFilterName.setText(filtro);
                linearNombreFiltro.setVisibility(View.VISIBLE);
                openBlur(filtro);
                break;
            case R.id.button_camara_trasera_filter:
                linearSearchError.setVisibility(View.GONE);
                setDefuaultColors();
                closeTextFilters();
                rlParent = rlContentDevices;
                btnFiltroCamaraTrasera.setBackground(getResources().getDrawable(R.drawable.bg_filter_selected));
                textFiltroCamaraTrasera.setTextColor(getResources().getColor(R.color.blue));
                icnFiltroCamaraTrasera.setImageResource(R.drawable.icn_camara_trasera_selected);
                filtro = "Cámara Trasera";
                tvFilterName.setText(filtro);
                linearNombreFiltro.setVisibility(View.VISIBLE);
                openBlur(filtro);
                break;
            case R.id.button_camara_frontal_filter:
                linearSearchError.setVisibility(View.GONE);
                setDefuaultColors();
                closeTextFilters();
                rlParent = rlContentDevices;
                btnFiltroCamaraFrontal.setBackground(getResources().getDrawable(R.drawable.bg_filter_selected));
                textFiltroCamaraFrontal.setTextColor(getResources().getColor(R.color.blue));
                icnFiltroCamaraFrontal.setImageResource(R.drawable.icn_camara_frontal_selected);
                filtro = "Cámara Frontal";
                tvFilterName.setText(filtro);
                linearNombreFiltro.setVisibility(View.VISIBLE);
                openBlur(filtro);
                break;
            case R.id.button_multimedia_filter:
                linearSearchError.setVisibility(View.GONE);
                filterProductList.clear();
                setDefuaultColors();
                closeTextFilters();
                closeFilterAccessory();
                rlParent = rlContentAccessory;
                setCloseFilterImage(btnMultimediaFilter);
                btnMultimediaFilter.setBackground(getResources().getDrawable(R.drawable.bg_filter_selected));
                textFiltroMultimedia.setTextColor(getResources().getColor(R.color.greenMint));
                icnFiltroMultimedia.setImageResource(R.drawable.icn_multimedia_selected);
                filtro = "Multimedia";
                tvFilterName.setText(filtro);
                linearNombreFiltro.setVisibility(View.VISIBLE);
                for (int i = 0; i < Session.objData.getAccessories().size(); i++) {
                    for (int j = 0; j < Session.objData.getAccessories().get(i).getDetalles().size(); j++) {
                        if (Session.objData.getAccessories().get(i).getDetalles().get(j).getValue().equalsIgnoreCase("1")) {
                            filterProductList.add(Session.objData.getAccessories().get(i));
                        }
                    }
                }
                Collections.sort(filterProductList, new Comparator<Producto>() {
                    @Override
                    public int compare(Producto o1, Producto o2) {
                        return o1.getProvider_name().compareTo(o2.getProvider_name());
                    }
                });
                filterDevices(filterProductList);
                break;
            case R.id.button_proteccion_filter:
                linearSearchError.setVisibility(View.GONE);
                filterProductList.clear();
                setDefuaultColors();
                closeTextFilters();
                closeFilterAccessory();
                rlParent = rlContentAccessory;
                setCloseFilterImage(btnProteccionFilter);
                btnProteccionFilter.setBackground(getResources().getDrawable(R.drawable.bg_filter_selected));
                textFiltroProteccion.setTextColor(getResources().getColor(R.color.greenMint));
                icnFiltroProteccion.setImageResource(R.drawable.icn_proteccion_selected);
                filtro = "Protección";
                tvFilterName.setText(filtro);
                linearNombreFiltro.setVisibility(View.VISIBLE);
                for (int i = 0; i < Session.objData.getAccessories().size(); i++) {
                    for (int j = 0; j < Session.objData.getAccessories().get(i).getDetalles().size(); j++) {
                        if (Session.objData.getAccessories().get(i).getDetalles().get(j).getValue().equalsIgnoreCase("3")) {
                            filterProductList.add(Session.objData.getAccessories().get(i));
                        }
                    }
                }
                Collections.sort(filterProductList, new Comparator<Producto>() {
                    @Override
                    public int compare(Producto o1, Producto o2) {
                        return o1.getProvider_name().compareTo(o2.getProvider_name());
                    }
                });
                filterDevices(filterProductList);
                break;
            case R.id.button_energia_filter:
                linearSearchError.setVisibility(View.GONE);
                filterProductList.clear();
                setDefuaultColors();
                closeTextFilters();
                closeFilterAccessory();
                rlParent = rlContentAccessory;
                setCloseFilterImage(btnEnergiaFilter);
                btnEnergiaFilter.setBackground(getResources().getDrawable(R.drawable.bg_filter_selected));
                textFiltroEnergia.setTextColor(getResources().getColor(R.color.greenMint));
                icnFiltroEnergia.setImageResource(R.drawable.icn_energia_selected);
                filtro = "Energía";
                tvFilterName.setText(filtro);
                linearNombreFiltro.setVisibility(View.VISIBLE);
                for (int i = 0; i < Session.objData.getAccessories().size(); i++) {
                    for (int j = 0; j < Session.objData.getAccessories().get(i).getDetalles().size(); j++) {
                        if (Session.objData.getAccessories().get(i).getDetalles().get(j).getValue().equalsIgnoreCase("2")) {
                            filterProductList.add(Session.objData.getAccessories().get(i));
                        }
                    }
                }
                Collections.sort(filterProductList, new Comparator<Producto>() {
                    @Override
                    public int compare(Producto o1, Producto o2) {
                        return o1.getProvider_name().compareTo(o2.getProvider_name());
                    }
                });
                filterDevices(filterProductList);
                break;
            case R.id.button_por_equipo_filter:
                setDefuaultColors();
                linearSearchError.setVisibility(View.GONE);
                linearResultadoEquipoCompatible.setVisibility(View.GONE);
                closeTextFilters();
                rlParent = rlContentAccessory;
                setCloseFilterImage(btnPorEquipoFilter);
                btnPorEquipoFilter.setBackground(getResources().getDrawable(R.drawable.bg_filter_selected));
                textFiltroPorEquipo.setTextColor(getResources().getColor(R.color.greenMint));
                icnFiltroPorEquipo.setImageResource(R.drawable.icn_por_equipo_selected);
                filtro = "EquiposCompatibles";
                openBlur(filtro);
                break;
            case R.id.button_smartfun:
                selectPlanSmartFun();
                break;
            case R.id.button_controlfun:
                selectPlanControlFun();
                break;
            case R.id.button_planvoz:
                selectPlanVoz();
                break;

            case R.id.linear_condiciones_comerciales_solo_voz:
                rlPopup.setVisibility(View.VISIBLE);
                imagePopup.setImageURI(Uri.fromFile(new File(Global.dirImages + Session.objData.getPlanes().get(2).getCondicionImage())));
                break;
            case R.id.linear_condiciones_comerciales_smart_fun:
                rlPopup.setVisibility(View.VISIBLE);
                imagePopup.setImageURI(Uri.fromFile(new File(Global.dirImages + Session.objData.getPlanes().get(0).getCondicionImage())));
                break;
            case R.id.linear_condiciones_comerciales_control_fun:
                rlPopup.setVisibility(View.VISIBLE);
                imagePopup.setImageURI(Uri.fromFile(new File(Global.dirImages + Session.objData.getPlanes().get(0).getCondicionImage())));
                break;
            case R.id.linear_close_nosearch:
                linearSearchError.setVisibility(View.GONE);
                closeTextFilters();
                setDefuaultColors();
                rlParent.removeView(imageCloseFilter);
                if (deviceState) {
                    linearSeleccionaFiltro.setVisibility(View.VISIBLE);
                    filterDevices(Global.allDevices);
                } else if (accessoryState) {
                    linearSeleccionaFiltro.setVisibility(View.VISIBLE);
                    filterDevices(Global.allAccessories);
                } else {
                    linearQueBuscas.setVisibility(View.VISIBLE);
                }

                break;
        }

    }

    private void selectPlanControlFun() {
        linearSearchError.setVisibility(View.GONE);
        setDefuaultColors();
        closeTextFilters();
        closePlans();
        rlParent = rlContentPlans;
        setCloseFilterImage(btnControlfun);
        btnControlfun.setBackground(getResources().getDrawable(R.drawable.bg_filter_selected));
        icnPlanControlfun.setImageResource(R.drawable.icn_controlfun_selected);
        textPlanControlfun.setTextColor(getResources().getColor(R.color.yellow));
        linearPlanElegido.setVisibility(View.VISIBLE);
        tvPlanType.setText("Control Fun_");
        constraintPlans.setVisibility(View.VISIBLE);
        constraintPlanControlfun.setVisibility(View.VISIBLE);
        planesControlFunSimpleAdapter = new PlanesControlFunSimpleAdapter(Global.planesControlFunSimple, MainActivity.this);
        rvPlanesControlfun.setAdapter(planesControlFunSimpleAdapter);
        constraintPlans.setVisibility(View.VISIBLE);
        constraintPlanControlfun.setVisibility(View.VISIBLE);
        imgBannerPlan.setImageURI(Uri.fromFile(new File(Global.dirImages + Session.objData.getPlanes().get(1).getBannerImage())));
    }

    private void selectPlanVoz() {
        linearSearchError.setVisibility(View.GONE);
        setDefuaultColors();
        closeTextFilters();
        closePlans();
        btnPlanvoz.setBackground(getResources().getDrawable(R.drawable.bg_filter_selected));
        icnPlanVoz.setImageResource(R.drawable.icn_planvoz_selected);
        textPlanVoz.setTextColor(getResources().getColor(R.color.yellow));
        rlParent = rlContentPlans;
        setCloseFilterImage(btnPlanvoz);
        constraintPlans.setVisibility(View.VISIBLE);
        constraintPlanVoz.setVisibility(View.VISIBLE);
        planVozIlimitadoAdapter = new PlanDeVozAdapter(Global.planesDeVozIlimitado, 2);
        planCuentraControladaAdapter = new PlanDeVozAdapter(Global.planesDeVozCCList, 1);
        rvPlanesCuentaControlada.setAdapter(planCuentraControladaAdapter);
        rvPlanesVozIlimitado.setAdapter(planVozIlimitadoAdapter);
        linearPlanElegido.setVisibility(View.VISIBLE);
        tvPlanType.setText("Sólo Voz_ ");
        imgBannerPlan.setImageURI(Uri.fromFile(new File(Global.dirImages + Session.objData.getPlanes().get(2).getBannerImage())));
    }

    private void selectPlanSmartFun() {
        linearSearchError.setVisibility(View.GONE);
        setDefuaultColors();
        closeTextFilters();
        closePlans();
        rlParent = rlContentPlans;
        setCloseFilterImage(btnSmartfun);
        btnSmartfun.setBackground(getResources().getDrawable(R.drawable.bg_filter_selected));
        icnPlanSmartfun.setImageResource(R.drawable.icn_smartfun_selected);
        textPlanSmartfun.setTextColor(getResources().getColor(R.color.yellow));
        linearPlanElegido.setVisibility(View.VISIBLE);
        tvPlanType.setText("Smart Fun_");
        planesSmartFunSimpleAdapter = new PlanesSmartFunSimpleAdapter(Global.planesSmartFunSimple, MainActivity.this);
        rvPlanesSmartfun.setAdapter(planesSmartFunSimpleAdapter);
        constraintPlans.setVisibility(View.VISIBLE);
        constraintPlanSmartfun.setVisibility(View.VISIBLE);
        imgBannerPlan.setImageURI(Uri.fromFile(new File(Global.dirImages + Session.objData.getPlanes().get(0).getBannerImage())));
    }

    private void seleccionarPantalla() {
        filterList.clear();
        rvFilters.setHasFixedSize(true);
        filterList.add("3\"");
        filterList.add("4\"");
        filterList.add("4.5\"");
        filterList.add("5\"");
        filterList.add("5.5\"");
        filterList.add("6\"+");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        FiltrosAdapter filtrosAdapter = new FiltrosAdapter(filterList, 1, MainActivity.this);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFilters.setLayoutManager(gridLayoutManager);
        rvFilters.setAdapter(filtrosAdapter);
    }

    private void seleccionarCamaraFrontal() {
        filterList.clear();
        filtro = "Cámara Frontal";
        rvFilters.setHasFixedSize(true);
        filterList.add("1.3MP");
        filterList.add("3MP");
        filterList.add("5MP");
        filterList.add("8MP");
        filterList.add("13MP");
        filterList.add("20MP+");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        FiltrosAdapter filtrosAdapter = new FiltrosAdapter(filterList, 3, MainActivity.this);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFilters.setLayoutManager(gridLayoutManager);
        rvFilters.setAdapter(filtrosAdapter);
    }

    private void seleccionarCamaraTrasera() {
        filterList.clear();
        filtro = "Cámara Trasera";
        rvFilters.setHasFixedSize(true);
        filterList.add("1.3MP");
        filterList.add("3MP");
        filterList.add("5MP");
        filterList.add("8MP");
        filterList.add("13MP");
        filterList.add("20MP+");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        FiltrosAdapter filtrosAdapter = new FiltrosAdapter(filterList, 2, MainActivity.this);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFilters.setLayoutManager(gridLayoutManager);
        rvFilters.setAdapter(filtrosAdapter);
    }

    private void seleccionarMarca() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        FiltrosAdapter filtrosAdapter = new FiltrosAdapter(Global.providersDevices, 0, MainActivity.this, "Nada relevantexdxd");
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFilters.setLayoutManager(gridLayoutManager);
        rvFilters.setAdapter(filtrosAdapter);
    }

    private void seleccionarEquipoCompatible() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        FiltrosAdapter filtrosAdapter = new FiltrosAdapter(Global.providersDevices, 4, MainActivity.this, "Nada relevantexdxd");
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFilters.setLayoutManager(gridLayoutManager);
        rvFilters.setAdapter(filtrosAdapter);
    }

    public void setFilter(String value, String valueTwo) {
        double valor1;
        double valor2;
        double va;
        switch (filtro) {
            case "Marca":
                filterProductList.clear();
                closeTextFilters();
                linearFilterResultsDevices.setVisibility(View.VISIBLE);
                tvFilterType.setText(filtro);
                tvFilterNameDevice.setText(value);
                setDefuaultColors();
                setCloseFilterImage(btnFiltroMarca);
                blurContent.setVisibility(View.GONE);
                btnFiltroMarca.setBackground(getResources().getDrawable(R.drawable.bg_filter_selected));
                icnFiltroMarca.setImageResource(R.drawable.icn_marca_selected);
                textFiltroMarca.setTextColor(getResources().getColor(R.color.blue));
                for (int i = 0; i < Session.objData.getDevices().size(); i++) {
                    if (Session.objData.getDevices().get(i).getProvider_id().equalsIgnoreCase(valueTwo)) {
                        filterProductList.add(Session.objData.getDevices().get(i));
                    }
                }
                filterDevices(filterProductList);
                break;
            case "Pantalla":
                filterProductList.clear();
                closeTextFilters();
                linearFilterResultsDevices.setVisibility(View.VISIBLE);
                tvFilterType.setText(filtro);
                tvFilterNameDevice.setText(value);
                setDefuaultColors();
                setCloseFilterImage(btnFiltroPantalla);
                blurContent.setVisibility(View.GONE);
                btnFiltroPantalla.setBackground(getResources().getDrawable(R.drawable.bg_filter_selected));
                textFiltroPantalla.setTextColor(getResources().getColor(R.color.blue));
                icnFiltroPantalla.setImageResource(R.drawable.icn_pantalla_selected);
                valor1 = Double.parseDouble(value.split("\"")[0]);
                valor2 = Double.parseDouble(valueTwo.split("\"")[0]);
                for (int i = 0; i < Session.objData.getDevices().size(); i++) {
                    va = Double.parseDouble(Session.objData.getDevices().get(i).getDetalles().get(1).getValue().split("\"")[0]);
                    if (va <= valor1 && va > valor2) {
                        filterProductList.add(Session.objData.getDevices().get(i));
                    }
                }
                Collections.sort(filterProductList, new Comparator<Producto>() {
                    @Override
                    public int compare(Producto o1, Producto o2) {
                        return o1.getProvider_name().compareTo(o2.getProvider_name());
                    }
                });
                filterDevices(filterProductList);
                break;
            case "Cámara Trasera":
                filterProductList.clear();
                closeTextFilters();
                linearFilterResultsDevices.setVisibility(View.VISIBLE);
                tvFilterType.setText(filtro);
                tvFilterNameDevice.setText(value);
                setDefuaultColors();
                setCloseFilterImage(btnFiltroCamaraTrasera);
                blurContent.setVisibility(View.GONE);
                btnFiltroCamaraTrasera.setBackground(getResources().getDrawable(R.drawable.bg_filter_selected));
                textFiltroCamaraTrasera.setTextColor(getResources().getColor(R.color.blue));
                icnFiltroCamaraTrasera.setImageResource(R.drawable.icn_camara_trasera_selected);
                filterProductList.clear();
                valor1 = Double.parseDouble(value.split("M")[0]);
                valor2 = Double.parseDouble(valueTwo.split("M")[0]);
                for (int i = 0; i < Session.objData.getDevices().size(); i++) {
                    try {
                        String valor = Session.objData.getDevices().get(i).getDetalles().get(3).getValue().split(" ")[0];
                        Log.d("VALOR " + i, valor);
                        if (valor.equals("Dual")) {
                            va = Double.parseDouble(Session.objData.getDevices().get(i).getDetalles().get(3).getValue().split(" ")[1]);
                            Log.d("VALOR " + i, "si " + va);
                        } else {
                            va = Double.parseDouble(Session.objData.getDevices().get(i).getDetalles().get(3).getValue().split(" ")[0]);
                            Log.d("VALOR " + i, "no");
                        }
                        if (va <= valor1 && va > valor2) {
                            filterProductList.add(Session.objData.getDevices().get(i));
                        }
                        if (value.equals("20 MP+") && va >= valor1) {
                            filterProductList.add(Session.objData.getDevices().get(i));
                        }
                    } catch (NumberFormatException ex) {
                        Log.d("NUMBER (ERROR)" + i, Session.objData.getDevices().get(i).getDetalles().get(3).getValue().split(" ")[0]);
                    }
                }
                Collections.sort(filterProductList, new Comparator<Producto>() {
                    @Override
                    public int compare(Producto o1, Producto o2) {
                        return o1.getProvider_name().compareTo(o2.getProvider_name());
                    }
                });
                filterDevices(filterProductList);
                break;
            case "Cámara Frontal":
                filterProductList.clear();
                closeTextFilters();
                linearFilterResultsDevices.setVisibility(View.VISIBLE);
                tvFilterType.setText(filtro);
                tvFilterNameDevice.setText(value);
                setDefuaultColors();
                setCloseFilterImage(btnFiltroCamaraFrontal);
                blurContent.setVisibility(View.GONE);
                btnFiltroCamaraFrontal.setBackground(getResources().getDrawable(R.drawable.bg_filter_selected));
                textFiltroCamaraFrontal.setTextColor(getResources().getColor(R.color.blue));
                icnFiltroCamaraFrontal.setImageResource(R.drawable.icn_camara_frontal_selected);
                valor1 = Double.parseDouble(value.split("M")[0]);
                valor2 = Double.parseDouble(valueTwo.split("M")[0]);
                for (int i = 0; i < Session.objData.getDevices().size(); i++) {
                    va = Double.parseDouble(Session.objData.getDevices().get(i).getDetalles().get(4).getValue().split(" ")[0]);
                    if (va <= valor1 && va > valor2) {
                        filterProductList.add(Session.objData.getDevices().get(i));
                    }
                }

                Collections.sort(filterProductList, new Comparator<Producto>() {
                    @Override
                    public int compare(Producto o1, Producto o2) {
                        return o1.getProvider_name().compareTo(o2.getProvider_name());
                    }
                });
                filterDevices(filterProductList);
                break;

            case "EquiposCompatibles":
                equiposCompatiblesList.clear();
                closeTextFilters();
                setDefuaultColors();
                linearFilterResultsDevices.setVisibility(View.VISIBLE);
                tvFilterType.setText("Marca");
                tvFilterNameDevice.setText(value);
                btnPorEquipoFilter.setBackground(getResources().getDrawable(R.drawable.bg_filter_selected));
                textFiltroPorEquipo.setTextColor(getResources().getColor(R.color.greenMint));
                icnFiltroPorEquipo.setImageResource(R.drawable.icn_por_equipo_selected);
                for (int i = 0; i < Global.providersDevices.size(); i++) {
                    if (Global.providersDevices.get(i).getId().equalsIgnoreCase(valueTwo)) {
                        if (valueTwo.equalsIgnoreCase("7") || valueTwo.equalsIgnoreCase("9") || valueTwo.equalsIgnoreCase("15")) {
                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(105, 105);
                            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                            rlParentImage.setLayoutParams(layoutParams);
                        } else {
                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(70, 70);
                            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                            rlParentImage.setLayoutParams(layoutParams);
                        }
                        imageProviderSelected.setImageURI(Uri.fromFile(new File(Global.dirImages + Global.providersDevices.get(i).getProvider_image())));
                        break;
                    }
                }
                for (int i = 0; i < Global.allDevices.size(); i++) {
                    if (Global.allDevices.get(i).getProvider_id().equalsIgnoreCase(valueTwo)) {
                        equiposCompatiblesList.add(Global.allDevices.get(i));
                    }
                }
                linearElegirEquipoCompatible.setVisibility(View.VISIBLE);
                linearSeleccionaFiltroBlur.setVisibility(View.GONE);
                linearResultadoEquipoCompatible.setVisibility(View.GONE);
                tvProviderSelected.setText(value);

                EquiposCompatiblesAdapter equiposCompatiblesAdapter = new EquiposCompatiblesAdapter(equiposCompatiblesList, MainActivity.this);
                rvEquiposCompatibles.setHasFixedSize(true);
                rvEquiposCompatibles.setAdapter(equiposCompatiblesAdapter);

                if (equiposCompatiblesList.size() > 10) {
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
                    gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    rvEquiposCompatibles.setLayoutManager(gridLayoutManager);
                } else {
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rvEquiposCompatibles.setLayoutManager(gridLayoutManager);
                }

                tvFilterResultDevice.setText(equiposCompatiblesList.size() + " equipos_");
                break;
        }
    }

    public void setAccesoriosCompatibles(Producto device) {
        linearResultadoEquipoCompatible.setVisibility(View.VISIBLE);
        linearElegirEquipoCompatible.setVisibility(View.GONE);
        accesoriosList = new ArrayList<>();
        tvNombreEquipoCompatibleSeleccionado.setText(device.getName());
        tvProveedorEquipoCompatibleSeleccionado.setText(device.getProvider_name());
        imgDeviceSelected.setImageURI(Uri.fromFile(new File(Global.dirImages + device.getDetalles().get(0).getValue())));
        linearLayoutManagerACC = new LinearLayoutManager(this);
        linearLayoutManagerACC.setOrientation(LinearLayoutManager.HORIZONTAL);

        rvAccesorios.setHasFixedSize(true);
        rvAccesorios.setLayoutManager(linearLayoutManagerACC);

        for (int i = 0; i < device.getAccesorios().size(); i++) {
            for (int k = 0; k < Session.objData.getAccessories().size(); k++) {
                if (Session.objData.getAccessories().get(k).getId().equals(device.getAccesorios().get(i).getId())) {
                    device.getAccesorios().get(i).setDetalles(Session.objData.getAccessories().get(k).getDetalles());
                    device.getAccesorios().get(i).setProvider_name(Session.objData.getAccessories().get(k).getProvider_name());
                    device.getAccesorios().get(i).setSizes(Session.objData.getAccessories().get(k).getSizes());
                    break;
                }
            }
            accesoriosList.add(device.getAccesorios().get(i));
        }
        Collections.shuffle(accesoriosList);
        AccesoriosAdapter accesoriosAdapter = new AccesoriosAdapter(accesoriosList, this);
        rvAccesorios.setAdapter(accesoriosAdapter);
        tvFilterResultDevice.setText(accesoriosList.size() + " accesorios_");

    }

    private void filterDevices(final ArrayList<Producto> filterProductList) {
        horizontalScrollGrid.fullScroll(View.FOCUS_LEFT);
        mGridLayout.removeAllViews();
        btnArrowLeft.setVisibility(View.GONE);
        pulsatorLeft.setVisibility(View.GONE);

        if (filterProductList.isEmpty()) {
            linearSearchError.setVisibility(View.VISIBLE);
        }

        if (filterProductList.size() <= 9) {
            btnArrowRight.setVisibility(View.GONE);
        } else {
            btnArrowRight.setVisibility(View.VISIBLE);
        }

        tvFilterResultDevice.setText(String.valueOf(filterProductList.size()));

        final List<GridItem> gridItemList = new ArrayList<>();

        for (int i = 0; i < filterProductList.size(); i++) {
            GridItem gridItem = new GridItem() {
            };

            switch (filterProductList.get(i).getProduct_type_id()) {
                case "1":
                    gridItem.setRowSpec(1);
                    gridItem.setColumnSpec(1);
                    gridItem.setProducto(filterProductList.get(i));
                    gridItemList.add(gridItem);
                    break;
                case "2":
                    switch (filterProductList.get(i).getSizes()) {
                        case "1":
                        case "2":
                            gridItem.setRowSpec(1);
                            gridItem.setColumnSpec(1);
                            gridItem.setProducto(filterProductList.get(i));
                            gridItemList.add(gridItem);
                            break;
                        case "3":
                            gridItem.setRowSpec(2);
                            gridItem.setColumnSpec(1);
                            gridItem.setProducto(filterProductList.get(i));
                            gridItemList.add(gridItem);
                            break;
                        case "4":
                            gridItem.setRowSpec(1);
                            gridItem.setColumnSpec(2);
                            gridItem.setProducto(filterProductList.get(i));
                            gridItemList.add(gridItem);
                            break;
                    }
                    break;
                case "3":
                    gridItem.setRowSpec(1);
                    gridItem.setColumnSpec(2);
                    gridItem.setProducto(filterProductList.get(i));
                    gridItemList.add(gridItem);
                    break;

                case "4":
                    switch (filterProductList.get(i).getSizes()) {
                        case "1":
                        case "2":
                            gridItem.setRowSpec(1);
                            gridItem.setColumnSpec(1);
                            gridItem.setProducto(filterProductList.get(i));
                            gridItemList.add(gridItem);
                            break;
                        case "3":
                            gridItem.setRowSpec(2);
                            gridItem.setColumnSpec(1);
                            gridItem.setProducto(filterProductList.get(i));
                            gridItemList.add(gridItem);
                            break;
                        case "4":
                            gridItem.setRowSpec(1);
                            gridItem.setColumnSpec(2);
                            gridItem.setProducto(filterProductList.get(i));
                            gridItemList.add(gridItem);
                            break;
                    }
                    break;
            }
        }

        GridViewHolder holder = new GridViewHolder(mGridLayout);
        GridBuilder.newInstance(this, mGridLayout)
                .setScaleAnimationDuration(0)
                .setPositionCalculator(new HorizontalPositionCalculator(3))
                .setBaseSize(300, 410)
                .setMargin(20)
                .setOutMargin(0, 0, 20, 20)
                .setGridItemList(gridItemList)
                .setViewHolder(holder)
                .setOnCreateViewCallBack(new OnViewCreateCallBack() {
                    @Override
                    public View onViewCreate(LayoutInflater inflater, View convertView, final GridItem gridItem) {
                        View view = inflater.inflate(R.layout.masonry_item, null);

                        RelativeLayout itemParent = (RelativeLayout) view.findViewById(R.id.item_parent);
                        LinearLayout itemContainer = (LinearLayout) view.findViewById(R.id.item_container);
                        final SimpleDraweeView imageAccessory = (SimpleDraweeView) view.findViewById(R.id.image_product);
                        SimpleDraweeView imgComplete = (SimpleDraweeView) view.findViewById(R.id.image_complete_product);
                        TextView providerName = (TextView) view.findViewById(R.id.provider_name);
                        final TextView accessoryName = (TextView) view.findViewById(R.id.product_name);

                        Random rand = new Random();
                        int numberColorRandom = rand.nextInt(6);

                        String Colors[][] = Global.getBackgroundColorsCard();

                        switch (gridItem.getProducto().getProduct_type_id()) {
                            case "1":
                                itemContainer.setVisibility(View.VISIBLE);
                                imgComplete.setVisibility(View.GONE);
                                imageAccessory.setImageURI(Uri.fromFile(new File(Global.dirImages + gridItem.getProducto().getDetalles().get(0).getValue())));
                                providerName.setText(gridItem.getProducto().getProvider_name());
                                if (gridItem.getProducto().getName().length() > 30) {
                                    accessoryName.setText(gridItem.getProducto().getName().substring(0, 27) + "...");
                                } else {
                                    accessoryName.setText(gridItem.getProducto().getName());
                                }
                                break;
                            case "2":
                                switch (gridItem.getProducto().getSizes()) {
                                    case "1":
                                        imageAccessory.setImageURI(Uri.fromFile(new File(Global.dirImages + gridItem.getProducto().getDetalles().get(0).getValue())));
                                        itemContainer.setVisibility(View.VISIBLE);
                                        imgComplete.setVisibility(View.GONE);
                                        providerName.setText(gridItem.getProducto().getProvider_name());
                                        if (gridItem.getProducto().getName().length() > 30) {
                                            accessoryName.setText(gridItem.getProducto().getName().substring(0, 27) + "...");
                                        } else {
                                            accessoryName.setText(gridItem.getProducto().getName());
                                        }
                                        break;
                                    case "2":
                                        if (gridItem.getProducto().getImageHigh().equalsIgnoreCase("1") || gridItem.getProducto().getImageHigh().equalsIgnoreCase("0")) {
                                            imageAccessory.setImageURI(Uri.fromFile(new File(Global.dirImages + gridItem.getProducto().getDetalles().get(0).getValue())));
                                            itemContainer.setVisibility(View.VISIBLE);
                                            imgComplete.setVisibility(View.GONE);
                                            providerName.setText(gridItem.getProducto().getProvider_name());
                                            accessoryName.setTextColor(getResources().getColor(R.color.white));
                                            providerName.setTextColor(getResources().getColor(R.color.white));
                                            if (gridItem.getProducto().getName().length() > 30) {
                                                accessoryName.setText(gridItem.getProducto().getName().substring(0, 27) + "...");
                                            } else {
                                                accessoryName.setText(gridItem.getProducto().getName());
                                            }
                                            itemContainer.setBackgroundColor(Color.parseColor(Colors[numberColorRandom][0]));
                                        } else {
                                            itemContainer.setVisibility(View.GONE);
                                            imgComplete.setVisibility(View.VISIBLE);
                                            imgComplete.setImageURI(Uri.fromFile(new File(Global.dirImages + gridItem.getProducto().getImageHigh())));
                                        }
                                        break;
                                    case "3":
                                        itemContainer.setVisibility(View.GONE);
                                        imgComplete.setVisibility(View.VISIBLE);
                                        imgComplete.setImageURI(Uri.fromFile(new File(Global.dirImages + gridItem.getProducto().getImageHigh())));
                                        break;
                                    case "4":
                                        itemContainer.setVisibility(View.GONE);
                                        imgComplete.setVisibility(View.VISIBLE);
                                        imgComplete.setImageURI(Uri.fromFile(new File(Global.dirImages + gridItem.getProducto().getImageHigh())));
                                        break;
                                }
                                break;
                            case "3":
                                itemContainer.setVisibility(View.GONE);
                                imgComplete.setVisibility(View.VISIBLE);
                                imgComplete.setImageURI(Uri.fromFile(new File(Global.dirImages + gridItem.getProducto().getPrimaryImage())));
                                break;

                            case "4":
                                switch (gridItem.getProducto().getSizes()) {
                                    case "2":
                                    case "3":
                                        itemContainer.setVisibility(View.GONE);
                                        imgComplete.setImageURI(Uri.fromFile(new File(Global.dirImages + gridItem.getProducto().getBannerImage())));
                                        imgComplete.setVisibility(View.VISIBLE);
                                        break;
                                    case "4":
                                        break;
                                }
                        }

                        itemParent.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Global.producto = gridItem.getProducto();
                                Log.d("ROWs", gridItem.getRowSpec() + " xd");
                                Log.d("IDK", gridItem.getProducto().toString());
                                switch (gridItem.getProducto().getProduct_type_id()) {
                                    case "1":
                                        startActivity(new Intent(MainActivity.this, FichaEquipo.class));
                                        break;
                                    case "2":
                                        accentedAccessoryFragment = new AccentedAccessoryFragment(MainActivity.this, Global.producto);
                                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                        transaction.add(R.id.fragment_accesorio, accentedAccessoryFragment);
                                        transaction.commit();
                                        fragmentAccesorio.setVisibility(View.VISIBLE);
                                        break;
                                    case "3":
                                        blurContent.setVisibility(View.GONE);
                                        setCloseTypeImage(btnPlans);
                                        rlParent.removeView(imageCloseFilter);
                                        rlContentPlans.setVisibility(View.VISIBLE);
                                        linearSeleccionaPlan.setVisibility(View.VISIBLE);
                                        planState = true;
                                        if (gridItem.getProducto().getName().startsWith("Smart")) {
                                            selectPlanSmartFun();
                                        } else if (gridItem.getProducto().getName().startsWith("Planes")) {
                                            selectPlanVoz();
                                        } else if (gridItem.getProducto().getName().startsWith("Control")) {
                                            selectPlanControlFun();
                                        }
                                        break;
                                    case "4":
                                        closeContents();
                                        closePlans();
                                        rlPopup.setVisibility(View.VISIBLE);
                                        imagePopup.setImageURI(Uri.fromFile(new File(Global.dirImages + gridItem.getProducto().getPrimaryImage())));
                                        break;
                                }
                            }
                        });


                        return view;
                    }
                })
                .build();
    }

    public void closeBlurAccessories() {
        fragmentAccesorio.setVisibility(View.GONE);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase, R.attr.fontPath));
    }

    @Override
    protected void onDestroy() {
        timerDestacado.cancel();
        super.onDestroy();

    }

    @Override
    protected void onStop() {
        timerDestacado.cancel();
        super.onStop();
    }

    public void openEDSmartFun(String gbPlan, String precioPlan, int type) {
        equiposDestacadosRandom.clear();
        tvGbPlanSf.setText(gbPlan);
        tvPrecioEquipoDestacadoSF.setText(precioPlan);
        Random rdm = new Random();
        equiposDestacadosRandom.add(Session.objData.getDevices().get(rdm.nextInt(Session.objData.getDevices().size())));
        equiposDestacadosRandom.add(Session.objData.getDevices().get(rdm.nextInt(Session.objData.getDevices().size())));
        equiposDestacadosRandom.add(Session.objData.getDevices().get(rdm.nextInt(Session.objData.getDevices().size())));
        EquiposDestacadosAdapter equiposDestacadosAdapter = new EquiposDestacadosAdapter(equiposDestacadosRandom, this);
        rvEquiposDestacadosSf.setAdapter(equiposDestacadosAdapter);
        if(type == 0){
            constraintLayout2.setBackgroundColor(getResources().getColor(R.color.blue));
            textView51.setText("Smart Fun SIMple ");
        }
        else{
            textView51.setText("Control Fun SIMple ");
            constraintLayout2.setBackgroundColor(getResources().getColor(R.color.orange));
        }


        rlEquipoDestacadoSmartFun.setVisibility(View.VISIBLE);

    }

    @OnClick({R.id.constraintLayout2, R.id.rv_equipos_destacados_sf, R.id.constraint_equipo_destacado_sf, R.id.linear_close_equipo_destacado_sf, R.id.rl_equipo_destacado_smart_fun, R.id.button_arrow_left_filter, R.id.button_arrow_right_filter, R.id.linear_que_buscas, R.id.image_destacado, R.id.image_popup, R.id.linear_close_popup, R.id.rl_popup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_arrow_left_filter:
                if (linearLayoutManagerACC.findFirstVisibleItemPosition() > 0) {
                    rvAccesorios.smoothScrollToPosition(linearLayoutManagerACC.findFirstVisibleItemPosition() - 1);
                }
                break;
            case R.id.button_arrow_right_filter:
                rvAccesorios.smoothScrollToPosition(linearLayoutManagerACC.findLastCompletelyVisibleItemPosition() + 1);
                break;
            case R.id.linear_que_buscas:
                cont++;
                if (cont == 20) {
                    finish();
                    startActivity(new Intent(this, SettingsActivity.class));
                }
                break;
            case R.id.image_destacado:
                switch (configuracionFile.getString("pantalla_id_index", "5")) {
                    case "1":
                        for (int i = 0; i < Global.allDevices.size(); i++) {
                            if (Session.objData.getCatalog().getScreenOneId().equalsIgnoreCase(Global.allDevices.get(i).getId())) {
                                Global.producto = Global.allDevices.get(i);
                                startActivity(new Intent(this, FichaEquipo.class));
                                break;
                            }
                        }
                        break;
                    case "2":
                        break;
                    case "3":
                        for (int i = 0; i < Global.allDevices.size(); i++) {
                            if (Session.objData.getCatalog().getScreenThreeId().equalsIgnoreCase(Global.allDevices.get(i).getId())) {
                                Global.producto = Global.allDevices.get(i);
                                startActivity(new Intent(this, FichaEquipo.class));
                                break;
                            }
                        }
                        break;
                    case "4":
                        break;
                    case "5":
                        startActivity(new Intent(this, SettingsActivity.class));
                        finish();
                        break;
                }

                imgDestacado.setVisibility(View.GONE);

                break;

            case R.id.image_popup:
                break;
            case R.id.linear_close_popup:
                rlPopup.setVisibility(View.GONE);
                break;
            case R.id.rl_popup:
                rlPopup.setVisibility(View.GONE);
                break;

            case R.id.constraintLayout2:
                break;
            case R.id.rv_equipos_destacados_sf:
                break;
            case R.id.constraint_equipo_destacado_sf:
                break;
            case R.id.linear_close_equipo_destacado_sf:
                rlEquipoDestacadoSmartFun.setVisibility(View.GONE);
                break;
            case R.id.rl_equipo_destacado_smart_fun:
                rlEquipoDestacadoSmartFun.setVisibility(View.GONE);
                break;
        }
    }

    public void openImageHigh() {
        closeContents();
        fragmentAccesorio.setVisibility(View.GONE);
        switch (configuracionFile.getString("pantalla_id_index", "5")) {
            case "1":
                imgDestacado.setImageURI(Uri.fromFile(new File(Global.dirImages + Session.objData.getCatalog().getScreenOneImage())));
                imgDestacado.setVisibility(View.VISIBLE);
                break;
            case "2":
                Uri uri = Uri.fromFile(new File(Global.dirImages + Session.objData.getCatalog().getScreenTwoImage()));
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setAutoPlayAnimations(true)
                        .build();
                imgDestacado.setController(controller);
                imgDestacado.setVisibility(View.VISIBLE);
                break;
            case "3":
                imgDestacado.setImageURI(Uri.fromFile(new File(Global.dirImages + Session.objData.getCatalog().getScreenThreeImage())));
                imgDestacado.setVisibility(View.VISIBLE);
                break;
            case "4":
                imgDestacado.setImageURI(Uri.fromFile(new File(Global.dirImages + Session.objData.getCatalog().getScreenFourImage())));
                imgDestacado.setVisibility(View.VISIBLE);
                break;
            case "5":
                startActivity(new Intent(this, SettingsActivity.class));
                finish();
                break;
        }
    }
}
