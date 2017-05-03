package cl.rinno.newdevicewall;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cl.rinno.newdevicewall.models.DWApi;
import cl.rinno.newdevicewall.models.Global;
import cz.msebera.android.httpclient.Header;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.version_app)
    TextView versionApp;

    @BindView(R.id.android_version)
    TextView androidVersion;

    @BindView(R.id.serial_number_device)
    TextView serialNumberDevice;

    @BindView(R.id.btnCerrar)
    LinearLayout btnCerrar;

    @BindView(R.id.fabric_model)
    TextView fabricModel;

    @BindView(R.id.btnVolver)
    LinearLayout btnVolver;

    @BindView(R.id.status)
    LinearLayout status;

    @BindView(R.id.btnActualizar)
    Button btnActualizar;

    @BindView(R.id.spinnerCatalog)
    Spinner spinnerCatalog;

    @BindView(R.id.spinnerPantallas)
    Spinner spinnerPantallas;

    @BindView(R.id.guardar_configuracion)
    Button guardarConfiguracion;

    private SharedPreferences configuracionFile;
    private SharedPreferences.Editor editorConfiguracionFile;

    private int indexCatalog;
    private int indexPantalla;
    final List<String> list = new ArrayList<String>();
    JSONArray dataCatalog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        configuracionFile = this.getSharedPreferences("configuracion", MODE_PRIVATE);
        editorConfiguracionFile = configuracionFile.edit();

        Field[] fields = Build.VERSION_CODES.class.getFields();
        String osName = fields[Build.VERSION.SDK_INT].getName();

        versionApp.setText(BuildConfig.VERSION_NAME);
        androidVersion.setText(Build.VERSION.RELEASE + " - " + osName + " - " + Build.VERSION.SDK_INT);
        serialNumberDevice.setText(Build.SERIAL);
        fabricModel.setText(Build.MODEL + " - " + Build.MANUFACTURER);

        DWApi.get("/api/catalog/list", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("SET", response.toString());
                dataCatalog = response;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        Log.d("Log", object.get("name").toString());

                        list.add(object.get("name").toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                populateSpinner(list);
            }
        });

        if (!configuracionFile.getBoolean("hasSettings", false)) {
            btnVolver.setVisibility(View.GONE);
            editorConfiguracionFile.putString("pantalla_id_index", "1");
        }else{
            switch (configuracionFile.getString("pantalla_id_index", "1")){
                case "1":
                    spinnerPantallas.setSelection(0);
                    break;
                case "2":
                    spinnerPantallas.setSelection(1);
                    break;
                case "3":
                    spinnerPantallas.setSelection(2);
                    break;
                case "4":
                    spinnerPantallas.setSelection(3);
                    break;
            }
        }

        if (!Global.isOnlineNet()) {
            status.setBackground(getResources().getDrawable(R.drawable.circle_status_network_red));
        } else {
            status.setBackground(getResources().getDrawable(R.drawable.circle_status_network_green));
        }


        spinnerCatalog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                indexCatalog = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerPantallas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                indexPantalla = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void populateSpinner(List list) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCatalog.setAdapter(dataAdapter);
    }


    @OnClick({R.id.btnCerrar, R.id.btnVolver, R.id.btnActualizar, R.id.guardar_configuracion})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCerrar:
                finish();
                break;
            case R.id.btnActualizar:
                if (!Global.isOnlineNet()) {
                    status.setBackground(getResources().getDrawable(R.drawable.circle_status_network_red));
                } else {
                    status.setBackground(getResources().getDrawable(R.drawable.circle_status_network_green));
                }
                break;

            case R.id.guardar_configuracion:
                try {
                    Log.d("IDCATALOG", dataCatalog.getJSONObject(indexCatalog).getString("id"));
                    Log.d("IDPANTALLA", indexPantalla + 1 + "");
                    editorConfiguracionFile.putString("catalog_id", dataCatalog.getJSONObject(indexCatalog).getString("id"));
                    editorConfiguracionFile.putString("pantalla_id_index", indexPantalla + 1 + "");
                    editorConfiguracionFile.putBoolean("hasSettings",true);
                    editorConfiguracionFile.apply();
                    Intent i = new Intent(this, SplashActivity.class);
                    i.putExtra("settings","si");
                    startActivity(i);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.btnVolver:
                Intent i = new Intent(this, SplashActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }


}
