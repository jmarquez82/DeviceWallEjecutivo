package cl.rinno.newdevicewall;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

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
import cl.rinno.newdevicewall.adapters.MiPrimerPlanMultimediaAdapter;
import cl.rinno.newdevicewall.adapters.MiPrimerPlanVozAdapter;
import cl.rinno.newdevicewall.adapters.PlanDeVozAdapter;
import cl.rinno.newdevicewall.adapters.PlanesControlFunSimpleAdapter;
import cl.rinno.newdevicewall.adapters.PlanesSmartFunSimpleAdapter;
import cl.rinno.newdevicewall.cls.TimerDestacado;
import cl.rinno.newdevicewall.gridlibrery.GridBuilder;
import cl.rinno.newdevicewall.gridlibrery.GridItem;
import cl.rinno.newdevicewall.gridlibrery.GridViewHolder;
import cl.rinno.newdevicewall.gridlibrery.calculator.HorizontalPositionCalculator;
import cl.rinno.newdevicewall.gridlibrery.calculator.VerticalBasePositionCalculator;
import cl.rinno.newdevicewall.gridlibrery.listener.OnViewCreateCallBack;
import cl.rinno.newdevicewall.models.Global;
import cl.rinno.newdevicewall.models.Producto;
import cl.rinno.newdevicewall.models.Session;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    ImageView imageCloseType;
    ImageView imageCloseFilter;

    int contMoveGrid;

    RelativeLayout rlParent;

    String filtro = "";
    ArrayList<String> filterList;

    LinearLayoutManager linearLayoutManagerACC;

    ArrayList<String> caracteristicasList;

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
    ImageView imgDestacado;
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
    @BindView(R.id.icn_primer_plan)
    ImageView icnPrimerPlan;
    @BindView(R.id.text_primer_plan)
    TextView textPrimerPlan;
    @BindView(R.id.button_miprimerplan)
    LinearLayout btnMiprimerplan;
    @BindView(R.id.textView299)
    TextView textView299;
    @BindView(R.id.textView355)
    TextView textView355;
    @BindView(R.id.rv_mi_primer_plan_multimedia)
    RecyclerView rvMiPrimerPlanMultimedia;
    @BindView(R.id.textView444)
    TextView textView444;
    @BindView(R.id.textView455)
    TextView textView455;
    @BindView(R.id.rv_mi_primer_plan_voz)
    RecyclerView rvMiPrimerPlanVoz;
    @BindView(R.id.linear_condiciones_comerciales_primerplan)
    LinearLayout linearCondicionesComercialesPrimerplan;
    @BindView(R.id.constraint_mi_primer_plan)
    ConstraintLayout constraintMiPrimerPlan;
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

    LinearLayoutManager linearLayoutManagerVCC, linearLayoutManagerVI, linearLayoutManagerSF, linearLayoutManagerCF, llmEquiposDestacados, linearLayoutManagerMPM, linearLayoutManagerMPV;

    PlanDeVozAdapter planCuentraControladaAdapter, planVozIlimitadoAdapter;
    PlanesSmartFunSimpleAdapter planesSmartFunSimpleAdapter;
    PlanesControlFunSimpleAdapter planesControlFunSimpleAdapter;
    MiPrimerPlanMultimediaAdapter miPrimerPlanMultimediaAdapter;
    MiPrimerPlanVozAdapter miPrimerPlanVozAdapter;

    ArrayList<Producto> accesoriosList;
    ArrayList<Producto> equiposDestacadosRandom;

    int cont = 0;

    Boolean gridMoveMax;


    TimerDestacado timerDestacado;

    Tracker mTracker;

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

    int contArrow = 0;

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

        contMoveGrid = 0;

        gridMoveMax = false;

        imageCloseType = new ImageView(this);
        imageCloseFilter = new ImageView(this);
        rlParent = new RelativeLayout(this);
        deviceState = false;
        accessoryState = false;
        planState = false;

        timerDestacado = new TimerDestacado(180000, 1000, MainActivity.this);

        configuracionFile = this.getSharedPreferences("configuracion", MODE_PRIVATE);

        caracteristicasList = new ArrayList<>();

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
        linearLayoutManagerMPM = new LinearLayoutManager(this);
        linearLayoutManagerMPM.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManagerMPV = new LinearLayoutManager(this);
        linearLayoutManagerMPV.setOrientation(LinearLayoutManager.VERTICAL);

        llmEquiposDestacados = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);

        rvPlanesVozIlimitado.setLayoutManager(linearLayoutManagerVI);
        rvPlanesCuentaControlada.setLayoutManager(linearLayoutManagerVCC);
        rvPlanesSmartfun.setLayoutManager(linearLayoutManagerSF);
        rvPlanesControlfun.setLayoutManager(linearLayoutManagerCF);
        rvEquiposDestacadosSf.setLayoutManager(llmEquiposDestacados);
        rvMiPrimerPlanVoz.setLayoutManager(linearLayoutManagerMPV);
        rvMiPrimerPlanMultimedia.setLayoutManager(linearLayoutManagerMPM);

        rvPlanesControlfun.setHasFixedSize(true);
        rvPlanesSmartfun.setHasFixedSize(true);
        rvPlanesCuentaControlada.setHasFixedSize(true);
        rvPlanesVozIlimitado.setHasFixedSize(true);
        rvEquiposDestacadosSf.setHasFixedSize(true);
        rvMiPrimerPlanMultimedia.setHasFixedSize(true);
        rvMiPrimerPlanVoz.setHasFixedSize(true);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

        /*
        type = 0: sin filtros.
        type = 1: Click tab equipos.
        type = 2: click tab accesorios.
        type = 3: click tab planes.
        type = 4: filtro equipo.
        type = 5: filtro accesorio.
        */


        imageCloseType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageCloseType.animate().scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setDuration(300).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        closeContents();
                        closeTextFilters();
                        linearQueBuscas.setVisibility(View.VISIBLE);
                        setDefuaultColors();
                        rlParent.removeView(imageCloseFilter);
                        blurContent.setVisibility(View.GONE);
                        filterDevices(Global.allProducts, 0);
                        deviceState = false;
                        accessoryState = false;
                        planState = false;
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        rlButtonsParent.removeView(imageCloseType);
                    }
                });

            }
        });

        imageCloseFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageCloseFilter.animate().scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setDuration(300).setListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        setDefuaultColors();
                        closeTextFilters();
                        linearSearchError.setVisibility(View.GONE);
                        blurContent.setVisibility(View.GONE);
                        if (deviceState) {
                            closePlans();
                            linearSeleccionaFiltro.setVisibility(View.VISIBLE);
                            filterDevices(Global.allDevices, 1);
                        } else if (accessoryState) {
                            closePlans();
                            linearSeleccionaFiltro.setVisibility(View.VISIBLE);
                            filterDevices(Global.allAccessories, 2);
                        } else if (planState) {
                            closePlans();
                            filterDevices(Global.allPlans, 3);
                            linearSeleccionaPlan.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        rlParent.removeView(imageCloseFilter);
                    }
                });

            }
        });

        horizontalScrollGrid.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollX = horizontalScrollGrid.getScrollX();
                if (scrollX == 0) {
                    gridMoveMax = false;
                    btnArrowLeft.animate().alpha(0.0f).scaleX(0.0f).scaleY(0.0f)
                            .setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    btnArrowLeft.setVisibility(View.GONE);

                                }
                            });
                    contArrow = 0;
                } else if (scrollX == horizontalScrollGrid.getChildAt(0).getMeasuredWidth() - getWindowManager().getDefaultDisplay().getWidth()) {
                    gridMoveMax = true;
                    btnArrowRight.animate().scaleX(0.0f).scaleY(0.0f)
                            .alpha(0.0f)
                            .setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    btnArrowRight.setVisibility(View.GONE);

                                }
                            });
                    contArrow = 0;
                } else {
                    gridMoveMax = false;
                    if (contArrow == 0) {
                        if (btnArrowRight.getVisibility() == View.GONE) {
                            btnArrowRight.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f)
                                    .setDuration(300)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationStart(Animator animation) {
                                            super.onAnimationStart(animation);
                                            btnArrowRight.setVisibility(View.VISIBLE);
                                        }
                                    });
                        } else if (btnArrowLeft.getVisibility() == View.GONE) {
                            btnArrowLeft.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f)
                                    .setDuration(300)
                                    .setListener(new AnimatorListenerAdapter() {

                                        @Override
                                        public void onAnimationStart(Animator animation) {
                                            super.onAnimationStart(animation);
                                            btnArrowLeft.setVisibility(View.VISIBLE);
                                        }
                                    });
                        }
                        contArrow++;
                    }
                    /*pulsatorLeft.setVisibility(View.VISIBLE);
                    pulsatorRight.setVisibility(View.VISIBLE);*/
                }
            }
        });

        rvAccesorios.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (linearLayoutManagerACC.findFirstCompletelyVisibleItemPosition() == 0) {
                    buttonArrowLeftFilter.animate().alpha(0.0f).scaleY(0.0f).scaleX(0.0f).setDuration(300).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            buttonArrowLeftFilter.setVisibility(View.GONE);
                        }
                    });
                } else if (linearLayoutManagerACC.findLastCompletelyVisibleItemPosition() == accesoriosList.size() - 1) {
                    buttonArrowRightFilter.animate().alpha(0.0f).scaleY(0.0f).scaleX(0.0f).setDuration(300).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            buttonArrowRightFilter.setVisibility(View.GONE);
                        }
                    });
                } else {
                    buttonArrowRightFilter.animate().alpha(1.0f).scaleY(1.0f).scaleX(1.0f).setDuration(300).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            buttonArrowRightFilter.setVisibility(View.VISIBLE);
                        }
                    });
                    buttonArrowLeftFilter.animate().alpha(1.0f).scaleY(1.0f).scaleX(1.0f).setDuration(300).setListener(new AnimatorListenerAdapter() {

                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            buttonArrowLeftFilter.setVisibility(View.VISIBLE);
                        }
                    });

                }
            }
        });

        timerDestacado.start();

        filterDevices(Global.allProducts, 0);
    }


    private void setCloseTypeImage(LinearLayout linear) {
        Drawable myDrawable = getResources().getDrawable(R.drawable.close);
        rlButtonsParent.removeView(imageCloseType);
        imageCloseType.setLayoutParams(new ViewGroup.LayoutParams(66, 66));
        imageCloseType.setImageDrawable(myDrawable);
        imageCloseType.setX(linear.getX() + 250);
        imageCloseType.setY(10);
        imageCloseType.animate().alpha(1.0f).setDuration(300).scaleY(1.0f).scaleX(1.0f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                rlButtonsParent.removeView(imageCloseType);
                rlButtonsParent.addView(imageCloseType);

            }
        });
    }

    private void setCloseFilterImage(LinearLayout linear) {
        Drawable myDrawable = getResources().getDrawable(R.drawable.close);
        rlParent.removeView(imageCloseFilter);
        imageCloseFilter.setLayoutParams(new ViewGroup.LayoutParams(50, 50));
        imageCloseFilter.setImageDrawable(myDrawable);
        imageCloseFilter.setAlpha(0.0f);
        imageCloseFilter.setScaleX(0.0f);
        imageCloseFilter.setScaleY(0.0f);
        imageCloseFilter.setX(linear.getX() + 182);
        imageCloseFilter.setY(linear.getY() - 20);
        imageCloseFilter.animate().alpha(1.0f).setDuration(300).scaleY(1.0f).scaleX(1.0f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                rlParent.removeView(imageCloseFilter);
                rlParent.addView(imageCloseFilter);
            }
        });
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
        rlContentDevices.setAlpha(0.0f);
        rlContentAccessory.setVisibility(View.GONE);
        rlContentAccessory.setAlpha(0.0f);
        rlContentPlans.setVisibility(View.GONE);
        rlContentPlans.setAlpha(0.0f);
        closePlans();
        linearSearchError.setVisibility(View.GONE);
        closeFilterAccessory();

        /* Botones Filtros Equipos */
        btnFiltroMarca.setAlpha(0.0f);
        btnFiltroMarca.setScaleX(0.0f);
        btnFiltroMarca.setScaleY(0.0f);
        btnFiltroPantalla.setAlpha(0.0f);
        btnFiltroPantalla.setScaleX(0.0f);
        btnFiltroPantalla.setScaleY(0.0f);
        btnFiltroCamaraFrontal.setAlpha(0.0f);
        btnFiltroCamaraFrontal.setScaleX(0.0f);
        btnFiltroCamaraFrontal.setScaleY(0.0f);
        btnFiltroCamaraTrasera.setAlpha(0.0f);
        btnFiltroCamaraTrasera.setScaleX(0.0f);
        btnFiltroCamaraTrasera.setScaleY(0.0f);
        /* Botones Filtros Accesorios */
        btnMultimediaFilter.setAlpha(0.0f);
        btnMultimediaFilter.setScaleX(0.0f);
        btnMultimediaFilter.setScaleY(0.0f);
        btnProteccionFilter.setAlpha(0.0f);
        btnProteccionFilter.setScaleX(0.0f);
        btnProteccionFilter.setScaleY(0.0f);
        btnEnergiaFilter.setAlpha(0.0f);
        btnEnergiaFilter.setScaleX(0.0f);
        btnEnergiaFilter.setScaleY(0.0f);
        btnPorEquipoFilter.setAlpha(0.0f);
        btnPorEquipoFilter.setScaleX(0.0f);
        btnPorEquipoFilter.setScaleY(0.0f);
        /* Botones Planes */
        btnSmartfun.setAlpha(0.0f);
        btnSmartfun.setScaleX(0.0f);
        btnSmartfun.setScaleY(0.0f);
        btnControlfun.setAlpha(0.0f);
        btnControlfun.setScaleX(0.0f);
        btnControlfun.setScaleY(0.0f);
        btnPlanvoz.setAlpha(0.0f);
        btnPlanvoz.setScaleX(0.0f);
        btnPlanvoz.setScaleY(0.0f);
        btnMiprimerplan.setAlpha(0.0f);
        btnMiprimerplan.setScaleX(0.0f);
        btnMiprimerplan.setScaleY(0.0f);
    }

    private void closePlans() {
        constraintPlans.setVisibility(View.GONE);
        constraintPlanVoz.setVisibility(View.GONE);
        constraintPlanControlfun.setVisibility(View.GONE);
        constraintPlanSmartfun.setVisibility(View.GONE);
        constraintMiPrimerPlan.setVisibility(View.GONE);
    }

    private void closeFilterAccessory() {
        linearFilterResultsDevices.setVisibility(View.GONE);
        linearResultadoEquipoCompatible.setVisibility(View.GONE);
        linearSeleccionaUnaMarcaEquipoComp.setVisibility(View.GONE);
        linearSeleccionaFiltro.setVisibility(View.GONE);
        blurContent.setVisibility(View.GONE);
    }

    private void setDefuaultColors() {
        btnMiprimerplan.setBackground(getResources().getDrawable(R.drawable.bg_filter_plans));
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
        icnPrimerPlan.setImageResource(R.drawable.icn_mi_primer_plan);


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
        textPrimerPlan.setTextColor(getResources().getColor(R.color.white));
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

    public void moveGrid() {
        contMoveGrid++;
        rlPopup.setVisibility(View.GONE);
        imgDestacado.setVisibility(View.GONE);
        closeContents();
        fragmentAccesorio.setVisibility(View.GONE);
        closeTextFilters();
        linearQueBuscas.setVisibility(View.VISIBLE);
        setDefuaultColors();
        rlEquipoDestacadoSmartFun.setVisibility(View.GONE);
        blurContent.setVisibility(View.GONE);
        Random random = new Random();
        if (deviceState || accessoryState || planState) {
            deviceState = false;
            accessoryState = false;
            planState = false;
            rlParent.removeView(imageCloseFilter);
            rlButtonsParent.removeView(imageCloseType);
            filterDevices(Global.allProducts, 0);
        } else {
            if (!gridMoveMax) {
                horizontalScrollGrid.smoothScrollBy(horizontalScrollGrid.getLeft() + 960, horizontalScrollGrid.getTop());
            } else {
                int move = (-960 * random.nextInt(9));
                horizontalScrollGrid.smoothScrollBy(horizontalScrollGrid.getLeft() + move, horizontalScrollGrid.getTop());
            }
        }
        switch (configuracionFile.getString("pantalla_id_index", "5")) {
            case "1":
                if (contMoveGrid == 1) {
                    openImageHigh(configuracionFile.getString("pantalla_id_index", "1"));
                }
                break;
            case "2":
                if (contMoveGrid == 2) {
                    openImageHigh(configuracionFile.getString("pantalla_id_index", "2"));
                }
                break;
            case "3":
                if (contMoveGrid == 3) {
                    openImageHigh(configuracionFile.getString("pantalla_id_index", "3"));
                }
                break;
            case "4":
                if (contMoveGrid == 4) {
                    openImageHigh(configuracionFile.getString("pantalla_id_index", "4"));
                    contMoveGrid = 0;
                }
                break;
        }
    }


    @OnClick({R.id.blur_content, R.id.button_miprimerplan, R.id.linear_condiciones_comerciales_control_fun, R.id.linear_close_nosearch, R.id.linear_condiciones_comerciales_solo_voz, R.id.linear_condiciones_comerciales_smart_fun, R.id.constraint_plans, R.id.button_smartfun, R.id.button_controlfun, R.id.button_planvoz, R.id.button_devices, R.id.linear_close_filters, R.id.button_accessories, R.id.button_plans, R.id.button_arrow_right, R.id.button_arrow_left, R.id.button_marca_filter, R.id.button_pantalla_filter, R.id.button_camara_trasera_filter, R.id.button_camara_frontal_filter, R.id.button_multimedia_filter, R.id.button_proteccion_filter, R.id.button_energia_filter, R.id.button_por_equipo_filter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.constraint_plans:
                break;
            case R.id.blur_content:
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
                    imageCloseFilter.animate().scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setDuration(300).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            rlParent.removeView(imageCloseFilter);
                        }
                    });
                    rlContentDevices.animate().setDuration(300).alpha(1.0f).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            rlContentDevices.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            btnFiltroMarca.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    super.onAnimationStart(animation);
                                    btnFiltroMarca.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    btnFiltroPantalla.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationStart(Animator animation) {
                                            super.onAnimationStart(animation);
                                            btnFiltroPantalla.setVisibility(View.VISIBLE);
                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                            btnFiltroCamaraTrasera.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationStart(Animator animation) {
                                                    super.onAnimationStart(animation);
                                                    btnFiltroCamaraTrasera.setVisibility(View.VISIBLE);
                                                }

                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    super.onAnimationEnd(animation);
                                                    btnFiltroCamaraFrontal.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                                                        @Override
                                                        public void onAnimationStart(Animator animation) {
                                                            super.onAnimationStart(animation);
                                                            btnFiltroCamaraFrontal.setVisibility(View.VISIBLE);
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });
                    linearSeleccionaFiltro.setVisibility(View.VISIBLE);
                    tvFilterProductType.setText("equipos_");
                    filterDevices(Global.allDevices, 1);
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Catálogo")
                            .setAction("Filtros")
                            .setLabel("Equipos")
                            .build());
                    deviceState = true;
                } else {
                    closeTextFilters();
                    linearQueBuscas.setVisibility(View.VISIBLE);
                    imageCloseType.animate().scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setDuration(300).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            rlButtonsParent.removeView(imageCloseType);
                        }
                    });
                    rlParent.removeView(imageCloseFilter);
                    blurContent.setVisibility(View.GONE);
                    filterDevices(Global.allProducts, 0);
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
                    imageCloseFilter.animate().scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setDuration(300).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            rlParent.removeView(imageCloseFilter);
                        }
                    });
                    rlContentAccessory.animate().setDuration(300).alpha(1.0f).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            rlContentAccessory.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            btnMultimediaFilter.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    super.onAnimationStart(animation);
                                    btnMultimediaFilter.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    btnProteccionFilter.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationStart(Animator animation) {
                                            super.onAnimationStart(animation);
                                            btnProteccionFilter.setVisibility(View.VISIBLE);
                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                            btnEnergiaFilter.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationStart(Animator animation) {
                                                    super.onAnimationStart(animation);
                                                    btnEnergiaFilter.setVisibility(View.VISIBLE);
                                                }

                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    super.onAnimationEnd(animation);
                                                    btnPorEquipoFilter.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                                                        @Override
                                                        public void onAnimationStart(Animator animation) {
                                                            super.onAnimationStart(animation);
                                                            btnPorEquipoFilter.setVisibility(View.VISIBLE);
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });
                    linearSeleccionaFiltro.setVisibility(View.VISIBLE);
                    tvFilterProductType.setText("accesorios_");
                    accessoryState = true;
                    filterDevices(Global.allAccessories, 2);
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Catálogo")
                            .setAction("Filtros")
                            .setLabel("Accesorios")
                            .build());
                } else {
                    linearQueBuscas.setVisibility(View.VISIBLE);
                    imageCloseType.animate().scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setDuration(300).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            rlButtonsParent.removeView(imageCloseType);
                        }
                    });
                    rlParent.removeView(imageCloseFilter);
                    blurContent.setVisibility(View.GONE);
                    filterDevices(Global.allProducts, 0);
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
                    rlContentPlans.animate().setDuration(300).alpha(1.0f).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            rlContentPlans.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            btnSmartfun.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    super.onAnimationStart(animation);
                                    btnSmartfun.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    btnControlfun.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationStart(Animator animation) {
                                            super.onAnimationStart(animation);
                                            btnControlfun.setVisibility(View.VISIBLE);
                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                            btnPlanvoz.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationStart(Animator animation) {
                                                    super.onAnimationStart(animation);
                                                    btnPlanvoz.setVisibility(View.VISIBLE);
                                                }

                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    super.onAnimationEnd(animation);
                                                    btnMiprimerplan.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                                                        @Override
                                                        public void onAnimationStart(Animator animation) {
                                                            super.onAnimationStart(animation);
                                                            btnMiprimerplan.setVisibility(View.VISIBLE);
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });
                    linearSeleccionaPlan.setVisibility(View.VISIBLE);
                    filterDevices(Global.allPlans, 3);
                    planState = true;
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Catálogo")
                            .setAction("Filtros")
                            .setLabel("Planes")
                            .build());
                } else {
                    linearQueBuscas.setVisibility(View.VISIBLE);
                    imageCloseType.animate().scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setDuration(300).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            rlButtonsParent.removeView(imageCloseType);
                        }
                    });
                    blurContent.setVisibility(View.GONE);
                    filterDevices(Global.allProducts, 0);
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
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Equipo")
                        .setAction("Filtros")
                        .setLabel(filtro)
                        .build());
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
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Equipo")
                        .setAction("Filtros")
                        .setLabel(filtro)
                        .build());
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
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Equipo")
                        .setAction("Filtros")
                        .setLabel(filtro)
                        .build());
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
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Equipo")
                        .setAction("Filtros")
                        .setLabel(filtro)
                        .build());
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
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Accesorio")
                        .setAction("Filtros")
                        .setLabel(filtro)
                        .build());
                filterDevices(filterProductList, 5);
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
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Accesorio")
                        .setAction("Filtros")
                        .setLabel(filtro)
                        .build());
                filterDevices(filterProductList, 5);
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
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Accesorio")
                        .setAction("Filtros")
                        .setLabel(filtro)
                        .build());
                filterDevices(filterProductList, 5);
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
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Accesorio")
                        .setAction("Filtros")
                        .setLabel(filtro)
                        .build());
                openBlur(filtro);
                break;
            case R.id.button_smartfun:
                selectPlanSmartFun();
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Planes")
                        .setAction("Filtros")
                        .setLabel("Smart Fun")
                        .build());
                break;
            case R.id.button_controlfun:
                selectPlanControlFun();
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Planes")
                        .setAction("Filtros")
                        .setLabel("Control Fun")
                        .build());
                break;
            case R.id.button_planvoz:
                selectPlanVoz();
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Planes")
                        .setAction("Filtros")
                        .setLabel("Plan de Voz")
                        .build());
                break;
            case R.id.button_miprimerplan:
                selectMiPrimerPlan();
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Planes")
                        .setAction("Filtros")
                        .setLabel("Mi Primer Plan")
                        .build());
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
                imagePopup.setImageURI(Uri.fromFile(new File(Global.dirImages + Session.objData.getPlanes().get(1).getCondicionImage())));
                break;
            case R.id.linear_close_nosearch:
                linearSearchError.setVisibility(View.GONE);
                closeTextFilters();
                setDefuaultColors();
                rlParent.removeView(imageCloseFilter);
                if (deviceState) {
                    linearSeleccionaFiltro.setVisibility(View.VISIBLE);
                    filterDevices(Global.allDevices, 1);
                } else if (accessoryState) {
                    linearSeleccionaFiltro.setVisibility(View.VISIBLE);
                    filterDevices(Global.allAccessories, 2);
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
        if (!planState) {
            planState = true;
            rlContentPlans.animate().setDuration(300).alpha(1.0f).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    rlContentPlans.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    btnSmartfun.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            btnSmartfun.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                            btnControlfun.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    super.onAnimationStart(animation);
                                    btnControlfun.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    setCloseFilterImage(btnControlfun);
                                    btnPlanvoz.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationStart(Animator animation) {
                                            super.onAnimationStart(animation);
                                            btnPlanvoz.setVisibility(View.VISIBLE);
                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                            btnMiprimerplan.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationStart(Animator animation) {
                                                    super.onAnimationStart(animation);
                                                    btnMiprimerplan.setVisibility(View.VISIBLE);
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            });
        } else {
            setCloseFilterImage(btnControlfun);
        }
        btnControlfun.setBackground(getResources().getDrawable(R.drawable.bg_filter_selected));
        icnPlanControlfun.setImageResource(R.drawable.icn_controlfun_selected);
        textPlanControlfun.setTextColor(getResources().getColor(R.color.yellow));
        linearPlanElegido.setVisibility(View.VISIBLE);
        tvPlanType.setText("Control Fun_");
        constraintPlans.setVisibility(View.VISIBLE);
        constraintPlanControlfun.setVisibility(View.VISIBLE);
        planesControlFunSimpleAdapter = new PlanesControlFunSimpleAdapter(Global.planesControlFunSimple, MainActivity.this);
        rvPlanesControlfun.setAdapter(planesControlFunSimpleAdapter);
        setAnimationRecycler(rvPlanesControlfun);
        imgBannerPlan.setImageURI(Uri.fromFile(new File(Global.dirImages + Session.objData.getPlanes().get(1).getBannerImage())));
    }

    private void selectPlanVoz() {
        linearSearchError.setVisibility(View.GONE);
        setDefuaultColors();
        closeTextFilters();
        closePlans();
        rlParent = rlContentPlans;
        if (!planState) {
            planState = true;
            rlContentPlans.animate().setDuration(300).alpha(1.0f).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    rlContentPlans.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    btnSmartfun.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            btnSmartfun.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            btnControlfun.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    super.onAnimationStart(animation);
                                    btnControlfun.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    btnPlanvoz.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationStart(Animator animation) {
                                            super.onAnimationStart(animation);
                                            btnPlanvoz.setVisibility(View.VISIBLE);
                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                            setCloseFilterImage(btnPlanvoz);
                                            btnMiprimerplan.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationStart(Animator animation) {
                                                    super.onAnimationStart(animation);
                                                    btnMiprimerplan.setVisibility(View.VISIBLE);
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            });
        } else {
            setCloseFilterImage(btnPlanvoz);
        }

        btnPlanvoz.setBackground(getResources().getDrawable(R.drawable.bg_filter_selected));
        icnPlanVoz.setImageResource(R.drawable.icn_planvoz_selected);
        textPlanVoz.setTextColor(getResources().getColor(R.color.yellow));
        constraintPlans.setVisibility(View.VISIBLE);
        constraintPlanVoz.setVisibility(View.VISIBLE);
        planVozIlimitadoAdapter = new PlanDeVozAdapter(Global.planesDeVozIlimitado, 2);
        planCuentraControladaAdapter = new PlanDeVozAdapter(Global.planesDeVozCCList, 1);
        rvPlanesCuentaControlada.setAdapter(planCuentraControladaAdapter);
        rvPlanesVozIlimitado.setAdapter(planVozIlimitadoAdapter);
        setAnimationRecycler(rvPlanesVozIlimitado);
        setAnimationRecycler(rvPlanesCuentaControlada);
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
        if (!planState) {
            planState = true;
            rlContentPlans.animate().setDuration(300).alpha(1.0f).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    rlContentPlans.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    btnSmartfun.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            btnSmartfun.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            setCloseFilterImage(btnSmartfun);
                            btnControlfun.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    super.onAnimationStart(animation);
                                    btnControlfun.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    btnPlanvoz.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationStart(Animator animation) {
                                            super.onAnimationStart(animation);
                                            btnPlanvoz.setVisibility(View.VISIBLE);
                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                            btnMiprimerplan.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationStart(Animator animation) {
                                                    super.onAnimationStart(animation);
                                                    btnMiprimerplan.setVisibility(View.VISIBLE);
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            });
        } else {
            setCloseFilterImage(btnSmartfun);
        }
        btnSmartfun.setBackground(getResources().getDrawable(R.drawable.bg_filter_selected));
        icnPlanSmartfun.setImageResource(R.drawable.icn_smartfun_selected);
        textPlanSmartfun.setTextColor(getResources().getColor(R.color.yellow));
        linearPlanElegido.setVisibility(View.VISIBLE);
        tvPlanType.setText("Smart Fun_");
        constraintPlans.setVisibility(View.VISIBLE);
        constraintPlanSmartfun.setVisibility(View.VISIBLE);
        planesSmartFunSimpleAdapter = new PlanesSmartFunSimpleAdapter(Global.planesSmartFunSimple, MainActivity.this);
        rvPlanesSmartfun.setAdapter(planesSmartFunSimpleAdapter);
        setAnimationRecycler(rvPlanesSmartfun);
        imgBannerPlan.setImageURI(Uri.fromFile(new File(Global.dirImages + Session.objData.getPlanes().get(0).getBannerImage())));
    }

    private void selectMiPrimerPlan() {
        linearSearchError.setVisibility(View.GONE);
        setDefuaultColors();
        closeTextFilters();
        closePlans();
        rlParent = rlContentPlans;
        if (!planState) {
            planState = true;
            rlContentPlans.animate().setDuration(300).alpha(1.0f).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    rlContentPlans.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    btnSmartfun.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            btnSmartfun.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            btnControlfun.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    super.onAnimationStart(animation);
                                    btnControlfun.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    btnPlanvoz.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationStart(Animator animation) {
                                            super.onAnimationStart(animation);
                                            btnPlanvoz.setVisibility(View.VISIBLE);
                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                            btnMiprimerplan.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationStart(Animator animation) {
                                                    super.onAnimationStart(animation);
                                                    btnMiprimerplan.setVisibility(View.VISIBLE);
                                                }

                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    super.onAnimationEnd(animation);
                                                    setCloseFilterImage(btnMiprimerplan);
                                                }
                                            });

                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            });
        } else {
            setCloseFilterImage(btnMiprimerplan);
        }
        btnMiprimerplan.setBackground(getResources().getDrawable(R.drawable.bg_filter_selected));
        icnPrimerPlan.setImageResource(R.drawable.icn_mi_primer_plan_selected);
        textPrimerPlan.setTextColor(getResources().getColor(R.color.yellow));
        linearPlanElegido.setVisibility(View.VISIBLE);
        tvPlanType.setText("Mi Primer Plan_");
        constraintPlans.setVisibility(View.VISIBLE);
        constraintMiPrimerPlan.setVisibility(View.VISIBLE);
        miPrimerPlanMultimediaAdapter = new MiPrimerPlanMultimediaAdapter(Global.miPrimerPlanMultimedia, MainActivity.this);
        rvMiPrimerPlanMultimedia.setAdapter(miPrimerPlanMultimediaAdapter);
        miPrimerPlanVozAdapter = new MiPrimerPlanVozAdapter(Global.miPrimerPlanVoz);
        rvMiPrimerPlanVoz.setAdapter(miPrimerPlanVozAdapter);
        setAnimationRecycler(rvMiPrimerPlanMultimedia);
        setAnimationRecycler(rvMiPrimerPlanVoz);
        imgBannerPlan.setImageURI(Uri.fromFile(new File(Global.dirImages + Session.objData.getPlanes().get(3).getBannerImage())));
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
        setAnimationRecycler(rvFilters);
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
        setAnimationRecycler(rvFilters);
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
        setAnimationRecycler(rvFilters);
    }

    private void seleccionarMarca() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        FiltrosAdapter filtrosAdapter = new FiltrosAdapter(Global.providersDevices, 0, MainActivity.this, "Nada relevantexdxd");
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFilters.setLayoutManager(gridLayoutManager);
        rvFilters.setAdapter(filtrosAdapter);
        setAnimationRecycler(rvFilters);
    }

    private void setAnimationRecycler(final RecyclerView recyclerView) {
        recyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                recyclerView.getViewTreeObserver().removeOnPreDrawListener(this);

                for (int i = 0; i < recyclerView.getChildCount(); i++) {
                    View v = recyclerView.getChildAt(i);
                    v.setScaleY(0);
                    v.setAlpha(0);
                    v.setScaleX(0);
                    v.animate().alpha(1.0f)
                            .setDuration(250)
                            .setStartDelay(i * 50)
                            .scaleX(1.0f)
                            .scaleY(1.0f)
                            .start();
                }
                return true;
            }
        });
    }

    private void seleccionarEquipoCompatible() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        FiltrosAdapter filtrosAdapter = new FiltrosAdapter(Global.providersDevices, 4, MainActivity.this, "Nada relevantexdxd");
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFilters.setLayoutManager(gridLayoutManager);
        rvFilters.setAdapter(filtrosAdapter);
        setAnimationRecycler(rvFilters);
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
                Collections.sort(filterProductList, new Comparator<Producto>() {
                    @Override
                    public int compare(Producto o1, Producto o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
                filterDevices(filterProductList, 4);
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
                filterDevices(filterProductList, 4);
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
                filterDevices(filterProductList, 4);
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
                filterDevices(filterProductList, 4);
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
                rvEquiposCompatibles.setAdapter(equiposCompatiblesAdapter);
                setAnimationRecycler(rvEquiposCompatibles);
                break;
        }
    }

    public void setAccesoriosCompatibles(Producto device) {
        linearResultadoEquipoCompatible.setVisibility(View.VISIBLE);
        linearElegirEquipoCompatible.setVisibility(View.GONE);
        accesoriosList = new ArrayList<>();
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Accesorio")
                .setAction("Filtro Equipo Compatible - Marca: "+device.getProvider_name())
                .setLabel(device.getName())
                .build());
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
        setAnimationRecycler(rvAccesorios);
        tvFilterResultDevice.setText(accesoriosList.size() + " accesorios_");
    }

    private void filterDevices(ArrayList<Producto> filterProductList, final int type) {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearCaches();
        try {
            Boolean sizeState = false;
            horizontalScrollGrid.fullScroll(View.FOCUS_LEFT);
            mGridLayout.removeAllViews();
            btnArrowLeft.setVisibility(View.GONE);
            btnArrowRight.setVisibility(View.VISIBLE);

            if (filterProductList.isEmpty()) {
                linearSearchError.setVisibility(View.VISIBLE);
            } else if (filterProductList.size() <= 8) {
                sizeState = !filterProductList.get(0).getProduct_type_id().equalsIgnoreCase("3");
            }

            if (filterProductList.size() <= 9) {
                btnArrowRight.setVisibility(View.GONE);
            } else {
                btnArrowRight.setVisibility(View.VISIBLE);
                btnArrowRight.setAlpha(1.0f);
                btnArrowRight.setScaleY(1.0f);
                btnArrowRight.setScaleX(1.0f);
            }

            tvFilterResultDevice.setText(String.valueOf(filterProductList.size()));

            final List<GridItem> gridItemList = new ArrayList<>();

            for (int i = 0; i < filterProductList.size(); i++) {
                GridItem gridItem = new GridItem() {
                };

                switch (filterProductList.get(i).getProduct_type_id()) {
                    case "1":
                        if (sizeState) {
                            gridItem.setRowSpec(1);
                            gridItem.setColumnSpec(1);
                            gridItem.setProducto(filterProductList.get(i));
                            gridItemList.add(gridItem);
                        } else {
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
                        }
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

            if (sizeState) {
                GridViewHolder holder = new GridViewHolder(mGridLayout);
                GridBuilder.newInstance(this, mGridLayout)
                        .setScaleAnimationDuration(0)
                        .setPositionCalculator(new VerticalBasePositionCalculator(3))
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
                                final ImageView imageAccessory = (ImageView) view.findViewById(R.id.image_product);
                                ImageView imgComplete = (ImageView) view.findViewById(R.id.image_complete_product);
                                TextView providerName = (TextView) view.findViewById(R.id.provider_name);
                                final TextView accessoryName = (TextView) view.findViewById(R.id.product_name);

                                Random rand = new Random();
                                int numberColorRandom = rand.nextInt(6);

                                String Colors[][] = Global.getBackgroundColorsCard();

                                switch (gridItem.getProducto().getProduct_type_id()) {
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
                                                itemContainer.setVisibility(View.GONE);
                                                imgComplete.setImageURI(Uri.fromFile(new File(Global.dirImages + gridItem.getProducto().getBannerImage())));
                                                imgComplete.setVisibility(View.VISIBLE);
                                                break;
                                        }
                                }

                                itemParent.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Global.producto = gridItem.getProducto();
                                        switch (gridItem.getProducto().getProduct_type_id()) {
                                            case "1":
                                                mTracker.send(new HitBuilders.EventBuilder()
                                                        .setCategory("Catálogo")
                                                        .setAction("Equipo")
                                                        .setLabel(Global.producto.getProvider_name() + " - " + Global.producto.getName())
                                                        .build());
                                                if (type == 0) {

                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Equipo")
                                                            .setAction(Global.producto.getProvider_name() + " - " + Global.producto.getName())
                                                            .setLabel("Catálogo")
                                                            .build());
                                                } else if (type == 1) {
                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Equipo")
                                                            .setAction(Global.producto.getProvider_name() + " - " + Global.producto.getName())
                                                            .setLabel("Filtro 'Equipos'")
                                                            .build());
                                                } else if (type == 4) {
                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Equipo")
                                                            .setAction(Global.producto.getProvider_name() + " - " + Global.producto.getName())
                                                            .setLabel("Filtro: " + filtro)
                                                            .build());
                                                }
                                                startActivity(new Intent(MainActivity.this, FichaEquipo.class));
                                                break;
                                            case "2":
                                                Global.accesorio = gridItem.getProducto();
                                                mTracker.send(new HitBuilders.EventBuilder()
                                                        .setCategory("Catálogo")
                                                        .setAction("Accesorios")
                                                        .setLabel(Global.accesorio.getProvider_name() + " - " + Global.accesorio.getName())
                                                        .build());
                                                if (type == 0) {
                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Accesorio")
                                                            .setAction(Global.accesorio.getProvider_name() + " - " + Global.accesorio.getName())
                                                            .setLabel("Catálogo Principal")
                                                            .build());
                                                } else if (type == 2) {
                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Accesorio")
                                                            .setAction(Global.accesorio.getProvider_name() + " - " + Global.accesorio.getName())
                                                            .setLabel("Filtro 'Accesorios'")
                                                            .build());
                                                } else if (type == 5) {
                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Accesorio")
                                                            .setAction(Global.accesorio.getProvider_name() +" - "+Global.accesorio.getName())
                                                            .setLabel("Filtro: "+filtro)
                                                            .build());

                                                }
                                                startActivity(new Intent(MainActivity.this, AccesoriosActivity.class));
                                                overridePendingTransition(0, 0);
                                                break;
                                            case "3":
                                                blurContent.setVisibility(View.GONE);
                                                setCloseTypeImage(btnPlans);
                                                rlParent.removeView(imageCloseFilter);
                                                rlContentPlans.setVisibility(View.VISIBLE);
                                                linearSeleccionaPlan.setVisibility(View.VISIBLE);
                                                if (gridItem.getProducto().getName().startsWith("Smart")) {
                                                    selectPlanSmartFun();
                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Catálogo")
                                                            .setAction("Planes")
                                                            .setLabel("Smart Fun")
                                                            .build());
                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Planes")
                                                            .setAction("Catálogo Principal")
                                                            .setLabel("Smart Fun")
                                                            .build());
                                                } else if (gridItem.getProducto().getName().startsWith("Planes")) {
                                                    selectPlanVoz();
                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Catálogo")
                                                            .setAction("Planes")
                                                            .setLabel("Plan de Voz")
                                                            .build());
                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Planes")
                                                            .setAction("Catálogo Principal")
                                                            .setLabel("Plan de Voz")
                                                            .build());
                                                } else if (gridItem.getProducto().getName().startsWith("Control")) {
                                                    selectPlanControlFun();
                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Catálogo")
                                                            .setAction("Planes")
                                                            .setLabel("Control Fun")
                                                            .build());
                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Planes")
                                                            .setAction("Catálogo Principal")
                                                            .setLabel("Control Fun")
                                                            .build());
                                                } else if (gridItem.getProducto().getName().startsWith("Mi")) {
                                                    selectMiPrimerPlan();
                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Catálogo")
                                                            .setAction("Planes")
                                                            .setLabel("Mi Primer Plan")
                                                            .build());
                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Planes")
                                                            .setAction("Catálogo Principal")
                                                            .setLabel("Mi Primer Plan")
                                                            .build());
                                                }
                                                break;
                                            case "4":
                                                rlPopup.setVisibility(View.VISIBLE);
                                                imagePopup.setImageURI(Uri.fromFile(new File(Global.dirImages + gridItem.getProducto().getPrimaryImage())));
                                                mTracker.send(new HitBuilders.EventBuilder()
                                                        .setCategory("Catálogo")
                                                        .setAction("Ofertas")
                                                        .setLabel("http://entel.rinno.cl/images/details/high/" + gridItem.getProducto().getPrimaryImage())
                                                        .build());
                                                mTracker.send(new HitBuilders.EventBuilder()
                                                        .setCategory("Ofertas")
                                                        .setAction("Catálogo Principal")
                                                        .setLabel("http://entel.rinno.cl/images/details/high/" + gridItem.getProducto().getPrimaryImage())
                                                        .build());
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                });


                                return view;
                            }
                        })
                        .build();
            } else {
                final GridViewHolder holder = new GridViewHolder(mGridLayout);
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
                                final LinearLayout itemContainer = (LinearLayout) view.findViewById(R.id.item_container);
                                final SimpleDraweeView imageAccessory = (SimpleDraweeView) view.findViewById(R.id.image_product);
                                SimpleDraweeView imgComplete = (SimpleDraweeView) view.findViewById(R.id.image_complete_product);
                                TextView providerName = (TextView) view.findViewById(R.id.provider_name);
                                final TextView accessoryName = (TextView) view.findViewById(R.id.product_name);

                                Random rand = new Random();
                                int numberColorRandom = rand.nextInt(6);

                                String Colors[][] = Global.getBackgroundColorsCard();

                                switch (gridItem.getProducto().getProduct_type_id()) {
                                    case "1":
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
                                                itemContainer.setVisibility(View.GONE);
                                                imgComplete.setImageURI(Uri.fromFile(new File(Global.dirImages + gridItem.getProducto().getBannerImage())));
                                                imgComplete.setVisibility(View.VISIBLE);
                                                break;
                                        }
                                }

                                itemParent.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Global.producto = gridItem.getProducto();
                                        switch (gridItem.getProducto().getProduct_type_id()) {
                                            case "1":
                                                mTracker.send(new HitBuilders.EventBuilder()
                                                        .setCategory("Catálogo")
                                                        .setAction("Equipo")
                                                        .setLabel(Global.producto.getProvider_name() + " - " + Global.producto.getName())
                                                        .build());
                                                if (type == 0) {

                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Equipo")
                                                            .setAction(Global.producto.getProvider_name() + " - " + Global.producto.getName())
                                                            .setLabel("Catálogo")
                                                            .build());
                                                } else if (type == 1) {
                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Equipo")
                                                            .setAction(Global.producto.getProvider_name() + " - " + Global.producto.getName())
                                                            .setLabel("Filtro 'Equipos'")
                                                            .build());
                                                } else if (type == 4) {
                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Equipo")
                                                            .setAction(Global.producto.getProvider_name() + " - " + Global.producto.getName())
                                                            .setLabel("Filtro: " + filtro)
                                                            .build());
                                                }
                                                startActivity(new Intent(MainActivity.this, FichaEquipo.class));
                                                break;
                                            case "2":
                                                Global.accesorio = gridItem.getProducto();
                                                mTracker.send(new HitBuilders.EventBuilder()
                                                        .setCategory("Catálogo")
                                                        .setAction("Accesorios")
                                                        .setLabel(Global.accesorio.getProvider_name() + " - " + Global.accesorio.getName())
                                                        .build());
                                                if (type == 0) {
                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Accesorio")
                                                            .setAction(Global.accesorio.getProvider_name() + " - " + Global.accesorio.getName())
                                                            .setLabel("Catálogo Principal")
                                                            .build());
                                                } else if (type == 2) {
                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Accesorio")
                                                            .setAction(Global.accesorio.getProvider_name() + " - " + Global.accesorio.getName())
                                                            .setLabel("Filtro 'Accesorios'")
                                                            .build());
                                                } else if (type == 5) {
                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Accesorio")
                                                            .setAction(Global.accesorio.getProvider_name() +" - "+Global.accesorio.getName())
                                                            .setLabel("Filtro: "+filtro)
                                                            .build());

                                                }
                                                startActivity(new Intent(MainActivity.this, AccesoriosActivity.class));
                                                overridePendingTransition(0, 0);
                                                break;
                                            case "3":
                                                blurContent.setVisibility(View.GONE);
                                                setCloseTypeImage(btnPlans);
                                                rlParent.removeView(imageCloseFilter);
                                                rlContentPlans.setVisibility(View.VISIBLE);
                                                linearSeleccionaPlan.setVisibility(View.VISIBLE);
                                                if (gridItem.getProducto().getName().startsWith("Smart")) {
                                                    selectPlanSmartFun();
                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Catálogo")
                                                            .setAction("Planes")
                                                            .setLabel("Smart Fun")
                                                            .build());
                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Planes")
                                                            .setAction("Catálogo Principal")
                                                            .setLabel("Smart Fun")
                                                            .build());
                                                } else if (gridItem.getProducto().getName().startsWith("Planes")) {
                                                    selectPlanVoz();
                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Catálogo")
                                                            .setAction("Planes")
                                                            .setLabel("Plan de Voz")
                                                            .build());
                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Planes")
                                                            .setAction("Catálogo Principal")
                                                            .setLabel("Plan de Voz")
                                                            .build());
                                                } else if (gridItem.getProducto().getName().startsWith("Control")) {
                                                    selectPlanControlFun();
                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Catálogo")
                                                            .setAction("Planes")
                                                            .setLabel("Control Fun")
                                                            .build());
                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Planes")
                                                            .setAction("Catálogo Principal")
                                                            .setLabel("Control Fun")
                                                            .build());
                                                } else if (gridItem.getProducto().getName().startsWith("Mi")) {
                                                    selectMiPrimerPlan();
                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Catálogo")
                                                            .setAction("Planes")
                                                            .setLabel("Mi Primer Plan")
                                                            .build());
                                                    mTracker.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Planes")
                                                            .setAction("Catálogo Principal")
                                                            .setLabel("Mi Primer Plan")
                                                            .build());
                                                }
                                                break;
                                            case "4":
                                                rlPopup.setVisibility(View.VISIBLE);
                                                imagePopup.setImageURI(Uri.fromFile(new File(Global.dirImages + gridItem.getProducto().getPrimaryImage())));
                                                mTracker.send(new HitBuilders.EventBuilder()
                                                        .setCategory("Catálogo")
                                                        .setAction("Ofertas")
                                                        .setLabel("http://entel.rinno.cl/images/details/high/" + gridItem.getProducto().getPrimaryImage())
                                                        .build());
                                                mTracker.send(new HitBuilders.EventBuilder()
                                                        .setCategory("Ofertas")
                                                        .setAction("Catálogo Principal")
                                                        .setLabel("http://entel.rinno.cl/images/details/high/" + gridItem.getProducto().getPrimaryImage())
                                                        .build());
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                });


                                return view;
                            }
                        })
                        .build();
            }
        } catch (IllegalArgumentException ex) {
            Log.e("ERROR", ex.toString());
            Intent i = new Intent(MainActivity.this, MainActivity.class);
            overridePendingTransition(0, 0);
            Collections.shuffle(Global.allAccessories);
            Collections.shuffle(Global.allProducts);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase, R.attr.fontPath));
    }

    @Override
    protected void onDestroy() {
        timerDestacado.cancel();
        cont = 0;
        super.onDestroy();

    }

    @Override
    protected void onStop() {
        timerDestacado.cancel();
        super.onStop();
        cont = 0;
    }

    public void openEDSmartFun(String gbPlan, String precioPlan, int type, Producto producto) {
        equiposDestacadosRandom.clear();
        tvGbPlanSf.setText(gbPlan);
        tvGbPlanSf.setVisibility(View.VISIBLE);
        tvPrecioEquipoDestacadoSF.setText(precioPlan);
        textView53.setVisibility(View.VISIBLE);
        textView49.setVisibility(View.VISIBLE);
        for (int i = 0; i < Session.objData.getDevices().size(); i++) {
            if (Session.objData.getDevices().get(i).getId().equalsIgnoreCase(producto.getEquno()) || Session.objData.getDevices().get(i).getId().equalsIgnoreCase(producto.getEqdos())) {
                if (Session.objData.getDevices().get(i).getId().equalsIgnoreCase("44")) {
                    Session.objData.getDevices().get(i).setCae(Session.objData.getDevices().get(i).getHijos().get(3).getCae());
                    Session.objData.getDevices().get(i).setPrecios(Session.objData.getDevices().get(i).getHijos().get(3).getPrecios());
                }
                equiposDestacadosRandom.add(Session.objData.getDevices().get(i));
            }
        }
        EquiposDestacadosAdapter equiposDestacadosAdapter = new EquiposDestacadosAdapter(equiposDestacadosRandom, this);
        rvEquiposDestacadosSf.setAdapter(equiposDestacadosAdapter);
        if (type == 0) {
            constraintLayout2.setBackgroundColor(getResources().getColor(R.color.blue));
            textView51.setText("Smart Fun SIMple ");
        } else if (type == 1) {
            textView51.setText("Control Fun SIMple ");
            constraintLayout2.setBackgroundColor(getResources().getColor(R.color.orange));
        } else if (type == 2) {
            textView51.setText("Mi Primer Plan Multimedia ");
            constraintLayout2.setBackgroundColor(getResources().getColor(R.color.greenMint));
            textView49.setVisibility(View.GONE);
            tvGbPlanSf.setText(precioPlan);
            textView53.setVisibility(View.GONE);
        }
        rlEquipoDestacadoSmartFun.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.linear_condiciones_comerciales_primerplan, R.id.constraint_mi_primer_plan, R.id.constraintLayout2, R.id.rv_equipos_destacados_sf, R.id.constraint_equipo_destacado_sf, R.id.linear_close_equipo_destacado_sf, R.id.rl_equipo_destacado_smart_fun, R.id.button_arrow_left_filter, R.id.button_arrow_right_filter, R.id.linear_que_buscas, R.id.image_destacado, R.id.image_popup, R.id.linear_close_popup, R.id.rl_popup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linear_que_buscas:
                cont++;
                if (cont == 20) {
                    startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                    this.finish();
                }
                break;
            case R.id.image_destacado:
                switch (configuracionFile.getString("pantalla_id_index", "5")) {
                    case "1":
                        Log.d("ID ONE", Session.objData.getCatalog().getScreenThreeId());
                        for (int i = 0; i < Global.allDevices.size(); i++) {
                            if (Session.objData.getCatalog().getScreenOneId().equalsIgnoreCase(Global.allDevices.get(i).getId())) {
                                Global.producto = Global.allDevices.get(i);
                                startActivity(new Intent(this, FichaEquipo.class));
                                break;
                            }
                        }
                        break;
                    case "2":
                        for (int i = 0; i < Global.allAccessories.size(); i++) {
                            if (Session.objData.getCatalog().getScreenTwoId().equalsIgnoreCase(Global.allAccessories.get(i).getId())) {
                                Global.accesorio = Global.allAccessories.get(i);
                                startActivity(new Intent(MainActivity.this, AccesoriosActivity.class));
                                overridePendingTransition(0, 0);
                                break;
                            }
                        }
                        break;
                    case "3":
                        Log.d("ID THREE", Session.objData.getCatalog().getScreenThreeId());
                        for (int i = 0; i < Global.allDevices.size(); i++) {
                            Log.d("ID", Global.allDevices.get(i).getId());
                            if (Session.objData.getCatalog().getScreenThreeId().equalsIgnoreCase(Global.allDevices.get(i).getId())) {
                                Global.producto = Global.allDevices.get(i);
                                Global.producto.setCae(Global.allDevices.get(i).getHijos().get(3).getCae());
                                Global.producto.setDetalles(Global.allDevices.get(i).getHijos().get(3).getDetalles());
                                Global.producto.setPrecios(Global.allDevices.get(i).getHijos().get(3).getPrecios());
                                Global.producto.setPlanes(Global.allDevices.get(i).getHijos().get(3).getPlanes());
                                startActivity(new Intent(this, FichaEquipo.class));
                                break;
                            }
                        }
                        break;
                    case "4":
                        selectPlanSmartFun();
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
            case R.id.linear_condiciones_comerciales_primerplan:
                rlPopup.setVisibility(View.VISIBLE);
                imagePopup.setImageURI(Uri.fromFile(new File(Global.dirImages + Session.objData.getPlanes().get(3).getCondicionImage())));
                break;
            case R.id.constraint_mi_primer_plan:
                break;
        }
    }

    public void openImageHigh(String id) {
        closeContents();
        fragmentAccesorio.setVisibility(View.GONE);
        switch (id) {
            case "1":
                Uri f = Uri.fromFile(new File(Global.dirImages + Session.objData.getCatalog().getScreenOneImage()));
                Log.d("IMAGE", f.toString());
                imgDestacado.setImageURI(f);
                break;
            case "2":
                Uri uri = Uri.fromFile(new File(Global.dirImages + Session.objData.getCatalog().getScreenTwoImage()));
                imgDestacado.setImageURI(uri);
                break;
            case "3":
                imgDestacado.setImageURI(Uri.fromFile(new File(Global.dirImages + Session.objData.getCatalog().getScreenThreeImage())));
                break;
            case "4":
                imgDestacado.setImageURI(Uri.fromFile(new File(Global.dirImages + Session.objData.getCatalog().getScreenFourImage())));

                break;

        }
        imgDestacado.setVisibility(View.VISIBLE);
    }
}
