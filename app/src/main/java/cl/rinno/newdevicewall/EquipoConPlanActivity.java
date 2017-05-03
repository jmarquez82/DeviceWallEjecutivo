package cl.rinno.newdevicewall;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cl.rinno.newdevicewall.adapters.PlanesControlFunAdapter;
import cl.rinno.newdevicewall.adapters.PlanesSmartFunAdapter;
import cl.rinno.newdevicewall.cls.TimerInactivity;
import cl.rinno.newdevicewall.models.Global;
import cl.rinno.newdevicewall.models.Producto;
import cl.rinno.newdevicewall.models.Session;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EquipoConPlanActivity extends AppCompatActivity {

    @BindView(R.id.tv_provider_name)
    TextView tvProviderName;
    @BindView(R.id.img_device)
    SimpleDraweeView imgDevice;
    @BindView(R.id.tv_device_name)
    TextView tvDeviceName;
    @BindView(R.id.tv_tab_smartfun)
    TextView tvTabSmartfun;
    @BindView(R.id.linear_tab_smartfun)
    LinearLayout btnTabSmartfun;
    @BindView(R.id.tv_tab_controlfun)
    TextView tvTabControlfun;
    @BindView(R.id.linear_tab_controlfun)
    LinearLayout btnTabControlfun;
    @BindView(R.id.rv_planes)
    RecyclerView rvPlanes;
    @BindView(R.id.linear_volver_al_equipo)
    LinearLayout btnVolverAlEquipo;
    @BindView(R.id.linear_volver_catalogo)
    LinearLayout btnVolverCatalogo;
    @BindView(R.id.linear_condiciones_comerciales)
    LinearLayout btnCondicionesComerciales;

    ArrayList<Producto> smartFunList;
    ArrayList<Producto> controlFunList;

    LinearLayoutManager smartFunLLM;
    LinearLayoutManager controlFunLLM;

    PlanesSmartFunAdapter smartFunAdapter;
    PlanesControlFunAdapter controlFunAdapter;

    TimerInactivity timerInactivity;

    Boolean smartFunState, controlFunState;
    @BindView(R.id.image_promo)
    SimpleDraweeView imgPromo;
    @BindView(R.id.linear_close_promo)
    LinearLayout btnClosePromo;
    @BindView(R.id.linear_content_promo)
    RelativeLayout contentPromo;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.image_popup)
    SimpleDraweeView imagePopup;
    @BindView(R.id.linear_close_popup)
    LinearLayout linearClosePopup;
    @BindView(R.id.rl_popup)
    RelativeLayout rlPopup;

    Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_equipo_con_plan);
        ButterKnife.bind(this);
        imgDevice.setImageURI(Uri.fromFile(new File(Global.dirImages + Global.producto.getDetalles().get(0).getValue())));
        tvDeviceName.setText(Global.producto.getName());
        tvProviderName.setText(Global.producto.getProvider_name());
        controlFunList = new ArrayList<>();
        smartFunList = new ArrayList<>();
        smartFunLLM = new LinearLayoutManager(this);
        smartFunLLM.setOrientation(LinearLayoutManager.VERTICAL);
        controlFunLLM = new LinearLayoutManager(this);
        controlFunLLM.setOrientation(LinearLayoutManager.VERTICAL);
        rvPlanes.setLayoutManager(smartFunLLM);
        rvPlanes.setHasFixedSize(true);
        smartFunState = true;
        controlFunState = false;
        timerInactivity = new TimerInactivity(180000, 1000, this);
        timerInactivity.start();

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Equipo")
                .setAction(Global.producto.getProvider_name() +" - "+Global.producto.getName())
                .setLabel("Plan: Smart Fun (Default)")
                .build());
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                controlFunList.clear();
                smartFunList.clear();
                btnCondicionesComerciales.setVisibility(View.GONE);
            }

            @Override
            protected Void doInBackground(Void... params) {
                for (int i = 0; i < Global.producto.getPlanes().size(); i++) {
                    if (Global.producto.getPlanes().get(i).getName().startsWith("Control")) {
                        controlFunList.add(Global.producto.getPlanes().get(i));
                    } else if (Global.producto.getPlanes().get(i).getName().startsWith("Smart")) {
                        smartFunList.add(Global.producto.getPlanes().get(i));
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                smartFunAdapter = new PlanesSmartFunAdapter(smartFunList, EquipoConPlanActivity.this);
                controlFunAdapter = new PlanesControlFunAdapter(controlFunList, EquipoConPlanActivity.this);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rvPlanes.setAdapter(smartFunAdapter);
                        setAnimationRecycler(rvPlanes);
                        btnCondicionesComerciales.setVisibility(View.VISIBLE);
                    }
                }, 200);
            }
        }.execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timerInactivity.cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        timerInactivity.cancel();
    }

    @Override
    protected void onStart() {
        super.onStart();
        timerInactivity.start();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        timerInactivity.cancel();
        timerInactivity.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerInactivity.cancel();
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
                            .setDuration(350)
                            .setStartDelay(i * 50)
                            .scaleX(1.0f)
                            .scaleY(1.0f)
                            .start();
                }
                return true;
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase, R.attr.fontPath));
    }

    @OnClick({R.id.linear_tab_smartfun, R.id.linear_tab_controlfun, R.id.linear_volver_al_equipo, R.id.linear_volver_catalogo, R.id.linear_condiciones_comerciales, R.id.image_promo, R.id.linear_close_promo, R.id.linear_content_promo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_tab_smartfun:
                if (!smartFunState) {
                    defaultColor();
                    btnTabSmartfun.setBackground(getResources().getDrawable(R.drawable.bg_tab_smartfun));
                    tvTabSmartfun.setTextColor(getResources().getColor(R.color.white));
                    rvPlanes.setAdapter(smartFunAdapter);
                    setAnimationRecycler(rvPlanes);
                    controlFunState = false;
                    smartFunState = true;
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Equipo")
                            .setAction(Global.producto.getProvider_name() +" - "+Global.producto.getName())
                            .setLabel("Plan: Smart Fun")
                            .build());
                }

                break;
            case R.id.linear_tab_controlfun:
                if (!controlFunState) {
                    defaultColor();
                    btnTabControlfun.setBackground(getResources().getDrawable(R.drawable.bg_tab_controlfun));
                    tvTabControlfun.setTextColor(getResources().getColor(R.color.white));
                    rvPlanes.setAdapter(controlFunAdapter);
                    setAnimationRecycler(rvPlanes);
                    smartFunState = false;
                    controlFunState = true;
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Equipo")
                            .setAction(Global.producto.getProvider_name() +" - "+Global.producto.getName())
                            .setLabel("Plan: Control Fun")
                            .build());
                }
                break;
            case R.id.linear_volver_al_equipo:
                finish();
                break;
            case R.id.linear_volver_catalogo:
                Intent i = new Intent(EquipoConPlanActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;
            case R.id.linear_condiciones_comerciales:
                rlPopup.setVisibility(View.VISIBLE);
                if (smartFunState) {
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Ver Equipo Com Plan")
                            .setAction(Global.producto.getProvider_name() +" - "+Global.producto.getName())
                            .setLabel("Condiciones Comerciales - Smart Fun")
                            .build());
                    imagePopup.setImageURI(Uri.fromFile(new File(Global.dirImages + Session.objData.getPlanes().get(0).getCondicionImage())));
                } else if (controlFunState) {
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Ver Equipo Com Plan")
                            .setAction(Global.producto.getProvider_name() +" - "+Global.producto.getName())
                            .setLabel("Condiciones Comerciales - Control Fun")
                            .build());
                    imagePopup.setImageURI(Uri.fromFile(new File(Global.dirImages + Session.objData.getPlanes().get(1).getCondicionImage())));
                }
                break;
            case R.id.image_promo:
                break;
            case R.id.linear_close_promo:
                contentPromo.setVisibility(View.GONE);
                break;
            case R.id.linear_content_promo:
                contentPromo.setVisibility(View.GONE);
                break;
        }
    }

    private void defaultColor() {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            btnTabControlfun.setBackground(getResources().getDrawable(R.drawable.bg_tab_land));
            btnTabSmartfun.setBackground(getResources().getDrawable(R.drawable.bg_tab_land));
            tvTabControlfun.setTextColor(getResources().getColor(R.color.white));
            tvTabSmartfun.setTextColor(getResources().getColor(R.color.white));
        }else{
            btnTabControlfun.setBackground(getResources().getDrawable(R.drawable.bg_tab));
            btnTabSmartfun.setBackground(getResources().getDrawable(R.drawable.bg_tab));
            tvTabControlfun.setTextColor(getResources().getColor(R.color.dimGray));
            tvTabSmartfun.setTextColor(getResources().getColor(R.color.dimGray));
        }
    }

    public void verPromo(String img) {
        imgPromo.setImageURI(Uri.fromFile(new File(Global.dirImages + img)));
        contentPromo.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.image_popup, R.id.linear_close_popup, R.id.rl_popup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_popup:
                break;
            case R.id.linear_close_popup:
                rlPopup.setVisibility(View.GONE);
                break;
            case R.id.rl_popup:
                rlPopup.setVisibility(View.GONE);
                break;
        }
    }
}
