package cl.rinno.newdevicewall;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cl.rinno.newdevicewall.adapters.FiltrosAdapter;
import cl.rinno.newdevicewall.gridlibrery.GridBuilder;
import cl.rinno.newdevicewall.gridlibrery.GridItem;
import cl.rinno.newdevicewall.gridlibrery.GridViewHolder;
import cl.rinno.newdevicewall.gridlibrery.calculator.HorizontalPositionCalculator;
import cl.rinno.newdevicewall.gridlibrery.listener.OnViewCreateCallBack;
import cl.rinno.newdevicewall.models.Global;
import cl.rinno.newdevicewall.models.Producto;
import cl.rinno.newdevicewall.models.Session;

public class MainActivity extends AppCompatActivity {

    ImageView imageCloseType;
    ImageView imageCloseFilter;

    RelativeLayout rlParent;

    String filtro = "";
    ArrayList<String> filterList;
    ArrayList<String> providers_ids;
    @BindView(R.id.horizontal_scroll_grid)
    HorizontalScrollView horizontalScrollGrid;
    private GridLayout mGridLayout;

    Boolean deviceState, accessoryState, planState;
    ArrayList<Producto> productList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        imageCloseType = new ImageView(this);
        imageCloseFilter = new ImageView(this);
        rlParent = new RelativeLayout(this);
        deviceState = false;
        accessoryState = false;
        planState = false;

        filterList = new ArrayList<>();
        providers_ids = new ArrayList<>();
        productList = new ArrayList<>();
        mGridLayout = (GridLayout) findViewById(R.id.grid_container);

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
                allProducts();
                deviceState = false;
            }
        });

        imageCloseFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlParent.removeView(imageCloseFilter);
                setDefuaultColors();
                closeTextFilters();
                linearSeleccionaFiltro.setVisibility(View.VISIBLE);
            }
        });

        allProducts();
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
    }

    private void setDefuaultColors() {
        btnFiltroMarca.setBackground(getResources().getDrawable(R.drawable.bg_filter_devices));
        btnFiltroPantalla.setBackground(getResources().getDrawable(R.drawable.bg_filter_devices));
        btnFiltroCamaraTrasera.setBackground(getResources().getDrawable(R.drawable.bg_filter_devices));
        btnFiltroCamaraFrontal.setBackground(getResources().getDrawable(R.drawable.bg_filter_devices));
        icnFiltroPantalla.setImageResource(R.drawable.filtro_pantalla);
        icnFiltroMarca.setImageResource(R.drawable.filtro_marca);
        icnFiltroCamaraFrontal.setImageResource(R.drawable.filtro_camara_frontal);
        icnFiltroCamaraTrasera.setImageResource(R.drawable.filtro_camara_trasera);
        textFiltroCamaraFrontal.setTextColor(getResources().getColor(R.color.white));
        textFiltroMarca.setTextColor(getResources().getColor(R.color.white));
        textFiltroPantalla.setTextColor(getResources().getColor(R.color.white));
        textFiltroCamaraTrasera.setTextColor(getResources().getColor(R.color.white));
    }

    private void openBlur(String filter) {
        blurContent.setVisibility(View.VISIBLE);
        switch (filter) {
            case "Marca":
                textSelecciona.setText("Selecciona la");
                textFilterName.setText("Marca_");
                seleccionarMarca();
                break;
            case "Pantalla":
                textSelecciona.setText("Selecciona el");
                textFilterName.setText("tamaño de la pantalla_");
                seleccionarPantalla();
                break;
            case "Cámara Trasera":
                textSelecciona.setText("Selecciona la");
                textFilterName.setText("Cámara trasera_");
                seleccionarCamaraTrasera();
                break;
            case "Cámara Frontal":
                textSelecciona.setText("Selecciona la");
                textFilterName.setText("Cámara frontal_");
                seleccionarCamaraFrontal();
                break;
        }
    }


    @OnClick({R.id.button_devices, R.id.linear_close_filters, R.id.button_accessories, R.id.button_plans, R.id.button_arrow_right, R.id.button_arrow_left, R.id.button_marca_filter, R.id.button_pantalla_filter, R.id.button_camara_trasera_filter, R.id.button_camara_frontal_filter})
    public void onClick(View view) {
        switch (view.getId()) {
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
                if (!deviceState) {
                    setCloseTypeImage(btnDevices);
                    rlParent.removeView(imageCloseFilter);
                    rlContentDevices.setVisibility(View.VISIBLE);
                    linearSeleccionaFiltro.setVisibility(View.VISIBLE);
                    tvFilterProductType.setText("equipos_");
                    allDevices();
                    deviceState = true;
                } else {
                    closeTextFilters();
                    linearQueBuscas.setVisibility(View.VISIBLE);
                    rlButtonsParent.removeView(imageCloseType);
                    rlParent.removeView(imageCloseFilter);
                    blurContent.setVisibility(View.GONE);
                    allProducts();
                    deviceState = false;
                }
                break;
            case R.id.button_accessories:
                setCloseTypeImage(btnAccessories);
                closeContents();
                closeTextFilters();
                setDefuaultColors();
                rlContentAccessory.setVisibility(View.VISIBLE);
                linearSeleccionaFiltro.setVisibility(View.VISIBLE);
                tvFilterProductType.setText("accesorios_");
                deviceState = false;

                break;
            case R.id.button_plans:
                setCloseTypeImage(btnPlans);
                closeContents();
                closeTextFilters();
                setDefuaultColors();
                rlContentPlans.setVisibility(View.VISIBLE);
                linearSeleccionaPlan.setVisibility(View.VISIBLE);
                deviceState = false;
                break;
            case R.id.button_arrow_right:
                Toast.makeText(this, "WORK RIGHT", Toast.LENGTH_SHORT).show();
                horizontalScrollGrid.smoothScrollBy(horizontalScrollGrid.getLeft() + 960, horizontalScrollGrid.getTop());
                break;
            case R.id.button_arrow_left:
                horizontalScrollGrid.smoothScrollBy(horizontalScrollGrid.getLeft() - 960, horizontalScrollGrid.getTop());
                Toast.makeText(this, "WORK LEFT", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_marca_filter:
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
        }

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
        FiltrosAdapter filtrosAdapter = new FiltrosAdapter(filterList, 1, MainActivity.this);
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
        FiltrosAdapter filtrosAdapter = new FiltrosAdapter(filterList, 1, MainActivity.this);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFilters.setLayoutManager(gridLayoutManager);
        rvFilters.setAdapter(filtrosAdapter);
    }

    private void seleccionarMarca() {
        filterList.clear();
        for (int i = 0; i < Session.objData.getProviders().size(); i++) {
            filterList.add(Session.objData.getProviders().get(i).getProvider_image());
            providers_ids.add(Session.objData.getProviders().get(i).getId());
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        FiltrosAdapter filtrosAdapter = new FiltrosAdapter(filterList, 1, MainActivity.this, providers_ids);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFilters.setLayoutManager(gridLayoutManager);
        rvFilters.setAdapter(filtrosAdapter);
    }


    public void setFilter(String value, String valueTwo) {
        switch (filtro) {
            case "Marca":
                closeTextFilters();
                linearFilterResultsDevices.setVisibility(View.VISIBLE);
                tvFilterType.setText(filtro);
                tvFilterNameDevice.setText(value);
                tvFilterResultDevice.setText("" + 0);
                setDefuaultColors();
                setCloseFilterImage(btnFiltroMarca);
                blurContent.setVisibility(View.GONE);
                btnFiltroMarca.setBackground(getResources().getDrawable(R.drawable.bg_filter_selected));
                icnFiltroMarca.setImageResource(R.drawable.icn_marca_selected);
                textFiltroMarca.setTextColor(getResources().getColor(R.color.blue));
                break;
            case "Pantalla":
                closeTextFilters();
                linearFilterResultsDevices.setVisibility(View.VISIBLE);
                tvFilterType.setText(filtro);
                tvFilterNameDevice.setText(value);
                tvFilterResultDevice.setText("" + 0);
                setDefuaultColors();
                setCloseFilterImage(btnFiltroPantalla);
                blurContent.setVisibility(View.GONE);
                btnFiltroPantalla.setBackground(getResources().getDrawable(R.drawable.bg_filter_selected));
                textFiltroPantalla.setTextColor(getResources().getColor(R.color.blue));
                icnFiltroPantalla.setImageResource(R.drawable.icn_pantalla_selected);
                break;
            case "Cámara Trasera":
                closeTextFilters();
                linearFilterResultsDevices.setVisibility(View.VISIBLE);
                tvFilterType.setText(filtro);
                tvFilterNameDevice.setText(value);
                tvFilterResultDevice.setText("" + 0);
                setDefuaultColors();
                setCloseFilterImage(btnFiltroCamaraTrasera);
                blurContent.setVisibility(View.GONE);
                btnFiltroCamaraTrasera.setBackground(getResources().getDrawable(R.drawable.bg_filter_selected));
                textFiltroCamaraTrasera.setTextColor(getResources().getColor(R.color.blue));
                icnFiltroCamaraTrasera.setImageResource(R.drawable.icn_camara_trasera_selected);
                break;
            case "Cámara Frontal":
                closeTextFilters();
                linearFilterResultsDevices.setVisibility(View.VISIBLE);
                tvFilterType.setText(filtro);
                tvFilterNameDevice.setText(value);
                tvFilterResultDevice.setText("" + 0);
                setDefuaultColors();
                setCloseFilterImage(btnFiltroCamaraFrontal);
                blurContent.setVisibility(View.GONE);
                btnFiltroCamaraFrontal.setBackground(getResources().getDrawable(R.drawable.bg_filter_selected));
                textFiltroCamaraFrontal.setTextColor(getResources().getColor(R.color.blue));
                icnFiltroCamaraFrontal.setImageResource(R.drawable.icn_camara_frontal_selected);
                break;
        }
    }

    private void allProducts() {
        mGridLayout.removeAllViews();
        productList.clear();
        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(200);
        set.addAnimation(animation);

        animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f
        );
        animation.setDuration(200);
        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);

        mGridLayout.setLayoutAnimation(controller);

        List<GridItem> gridItemList = new ArrayList<>();


        for (int i = 0; i < Session.objData.getDevices().size(); i++) {
            productList.add(Session.objData.getDevices().get(i));
        }
        for (int i = 0; i < Session.objData.getAccessories().size(); i++){
            productList.add(Session.objData.getAccessories().get(i));
        }

        Collections.sort(productList, new Comparator<Producto>() {
            @Override
            public int compare(Producto o1, Producto o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        for (int i = 0; i < productList.size(); i++) {
            GridItem gridItem = new GridItem() {
            };

            gridItem.setRowSpec(1);
            gridItem.setColumnSpec(1);
            gridItem.setProducto(productList.get(i));
            gridItemList.add(gridItem);
        }


        GridViewHolder holder = new GridViewHolder(mGridLayout);

        GridBuilder.newInstance(this, mGridLayout)
                .setScaleAnimationDuration(500)
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

                        LinearLayout itemContainer = (LinearLayout) view.findViewById(R.id.item_container);
                        final SimpleDraweeView imageAccessory = (SimpleDraweeView) view.findViewById(R.id.image_product);

                        TextView providerName = (TextView) view.findViewById(R.id.provider_name);
                        TextView accessoryName = (TextView) view.findViewById(R.id.product_name);

                        Random rand = new Random();
                        int numberColorRandom = rand.nextInt(7);


                        String Colors[][] = Global.getBackgroundColorsCard();
                        itemContainer.setBackgroundColor(Color.parseColor(Colors[numberColorRandom][0]));
                        if(numberColorRandom != 6){
                            providerName.setTextColor(getResources().getColor(R.color.white));
                            accessoryName.setTextColor(getResources().getColor(R.color.white));
                        }
                        itemContainer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, gridItem.getProducto().getName(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        Uri uri = Uri.fromFile(new File(Global.dirImages + gridItem.getProducto().getDetalles().get(0).getValue()));
                        imageAccessory.setImageURI(uri);
                        providerName.setText(gridItem.getProducto().getProvider_name());
                        if (gridItem.getProducto().getName().length() > 30) {
                            accessoryName.setText(gridItem.getProducto().getName().substring(0, 27) + "...");
                        } else {
                            accessoryName.setText(gridItem.getProducto().getName());
                        }
                        return view;
                    }
                })
                .build();
    }

    private void allDevices() {
        mGridLayout.removeAllViews();
        productList.clear();
        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(200);
        set.addAnimation(animation);

        animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f
        );
        animation.setDuration(200);
        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);

        mGridLayout.setLayoutAnimation(controller);

        List<GridItem> gridItemList = new ArrayList<>();


        for (int i = 0; i < Session.objData.getDevices().size(); i++) {
            productList.add(Session.objData.getDevices().get(i));
        }

        Collections.sort(productList, new Comparator<Producto>() {
            @Override
            public int compare(Producto o1, Producto o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        for (int i = 0; i < productList.size(); i++) {
            GridItem gridItem = new GridItem() {
            };
            gridItem.setRowSpec(1);
            gridItem.setColumnSpec(1);
            gridItem.setProducto(productList.get(i));
            gridItemList.add(gridItem);
        }

        GridViewHolder holder = new GridViewHolder(mGridLayout);
        GridBuilder.newInstance(this, mGridLayout)
                .setScaleAnimationDuration(500)
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

                        LinearLayout itemContainer = (LinearLayout) view.findViewById(R.id.item_container);
                        final SimpleDraweeView imageAccessory = (SimpleDraweeView) view.findViewById(R.id.image_product);

                        TextView providerName = (TextView) view.findViewById(R.id.provider_name);
                        TextView accessoryName = (TextView) view.findViewById(R.id.product_name);

                        Random rand = new Random();
                        int numberColorRandom = rand.nextInt(7);

                        String Colors[][] = Global.getBackgroundColorsCard();


                        itemContainer.setBackgroundColor(Color.parseColor(Colors[numberColorRandom][0]));

                        if(numberColorRandom != 6){
                            providerName.setTextColor(getResources().getColor(R.color.white));
                            accessoryName.setTextColor(getResources().getColor(R.color.white));
                        }

                        itemContainer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, gridItem.getProducto().getName(), Toast.LENGTH_SHORT).show();
                            }
                        });



                        Uri uri = Uri.fromFile(new File(Global.dirImages + gridItem.getProducto().getDetalles().get(0).getValue()));
                        imageAccessory.setImageURI(uri);
                        providerName.setText(gridItem.getProducto().getProvider_name());
                        if (gridItem.getProducto().getName().length() > 30) {
                            accessoryName.setText(gridItem.getProducto().getName().substring(0, 27) + "...");
                        } else {
                            accessoryName.setText(gridItem.getProducto().getName());
                        }
                        return view;
                    }
                })
                .build();

    }

    private void allAccessories() {
        mGridLayout.removeAllViews();
        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(200);
        set.addAnimation(animation);

        animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f
        );
        animation.setDuration(200);
        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);

        mGridLayout.setLayoutAnimation(controller);

        List<GridItem> gridItemList = new ArrayList<>();

        for (int i = 0; i < Session.objData.getAccessories().size(); i++){
            productList.add(Session.objData.getAccessories().get(i));
        }

        Collections.sort(productList, new Comparator<Producto>() {
            @Override
            public int compare(Producto o1, Producto o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        for (int i = 0; i < productList.size(); i++) {
            GridItem gridItem = new GridItem() {
            };

            gridItem.setRowSpec(1);
            gridItem.setColumnSpec(1);
            gridItem.setProducto(productList.get(i));
            gridItemList.add(gridItem);
        }

        GridViewHolder holder = new GridViewHolder(mGridLayout);

        GridBuilder.newInstance(this, mGridLayout)
                .setScaleAnimationDuration(500)
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

                        LinearLayout itemContainer = (LinearLayout) view.findViewById(R.id.item_container);
                        final SimpleDraweeView imageAccessory = (SimpleDraweeView) view.findViewById(R.id.image_product);

                        TextView providerName = (TextView) view.findViewById(R.id.provider_name);
                        TextView accessoryName = (TextView) view.findViewById(R.id.product_name);

                        Random rand = new Random();
                        int numberColorRandom = rand.nextInt(6);


                        String Colors[][] = Global.getBackgroundColorsCard();
                        itemContainer.setBackgroundColor(Color.parseColor(Colors[numberColorRandom][0]));

                        itemContainer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, gridItem.getProducto().getName(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        Uri uri = Uri.fromFile(new File(Global.dirImages + gridItem.getProducto().getDetalles().get(0).getValue()));
                        imageAccessory.setImageURI(uri);
                        providerName.setText(gridItem.getProducto().getProvider_name());
                        if (gridItem.getProducto().getName().length() > 30) {
                            accessoryName.setText(gridItem.getProducto().getName().substring(0, 27) + "...");
                        } else {
                            accessoryName.setText(gridItem.getProducto().getName());
                        }
                        return view;
                    }
                })
                .build();

    }

}
