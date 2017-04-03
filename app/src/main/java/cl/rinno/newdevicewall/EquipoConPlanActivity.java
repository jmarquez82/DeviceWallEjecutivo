package cl.rinno.newdevicewall;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cl.rinno.newdevicewall.adapters.PlanesControlFunAdapter;
import cl.rinno.newdevicewall.adapters.PlanesSmartFunAdapter;
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
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                controlFunList.clear();
                smartFunList.clear();
                rvPlanes.setVisibility(View.GONE);
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
                rvPlanes.setAdapter(smartFunAdapter);
                rvPlanes.setVisibility(View.VISIBLE);
                btnCondicionesComerciales.setVisibility(View.VISIBLE);
            }
        }.execute();


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
                    controlFunState = false;
                    smartFunState = true;
                }

                break;
            case R.id.linear_tab_controlfun:
                if (!controlFunState) {
                    defaultColor();
                    btnTabControlfun.setBackground(getResources().getDrawable(R.drawable.bg_tab_controlfun));
                    tvTabControlfun.setTextColor(getResources().getColor(R.color.white));
                    rvPlanes.setAdapter(controlFunAdapter);
                    smartFunState = false;
                    controlFunState = true;
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
                if(smartFunState){
                    imagePopup.setImageURI(Uri.fromFile(new File(Global.dirImages+ Session.objData.getPlanes().get(0).getCondicionImage())));
                }else if (controlFunState){
                    imagePopup.setImageURI(Uri.fromFile(new File(Global.dirImages+ Session.objData.getPlanes().get(1).getCondicionImage())));
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
        btnTabControlfun.setBackground(getResources().getDrawable(R.drawable.bg_tab));
        btnTabSmartfun.setBackground(getResources().getDrawable(R.drawable.bg_tab));
        tvTabControlfun.setTextColor(getResources().getColor(R.color.dimGray));
        tvTabSmartfun.setTextColor(getResources().getColor(R.color.dimGray));
    }

    public void verPromo(int typePlan) {
        contentPromo.setVisibility(View.VISIBLE);
        if (typePlan == 1) {
            imgPromo.setImageURI(Uri.parse("res:/" + R.drawable.promo_smartfun));
        } else {
            imgPromo.setImageURI(Uri.parse("res:/" + R.drawable.promo_controlfun));
        }
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
