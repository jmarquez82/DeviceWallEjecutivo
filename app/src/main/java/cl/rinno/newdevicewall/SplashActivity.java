package cl.rinno.newdevicewall;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cl.rinno.newdevicewall.models.DWApi;
import cl.rinno.newdevicewall.models.Global;
import cl.rinno.newdevicewall.models.OutData;
import cl.rinno.newdevicewall.models.Session;
import cz.msebera.android.httpclient.Header;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        Global.makeDirectories();
        Fresco.initialize(this);

        File oldJson = new File(Global.dirJson);
        if(oldJson.exists() && isOnlineNet()){
            Log.d("JSON","EXISTE");
            int length = (int) oldJson.length();
            byte[] bytes = new byte[length];
            try (FileInputStream in = new FileInputStream(oldJson)) {
                in.read(bytes);
                Global.jsonData2 = new String(bytes);
                JSONObject data = new JSONObject(Global.jsonData2);
                serialize(data,1);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if((!oldJson.exists()) || isOnlineNet()){
            Log.d("JSON","NO EXISTE");
            DWApi.get("api/showfruna/1",null, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                serialize(response,1);
                }
            });
        }
    }

    private void serialize(final JSONObject data, final int status) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                GsonBuilder gsonb = new GsonBuilder();
                Gson gson = gsonb.create();
                JSONObject j;
                OutData gig2 = null;
                try {
                    j = data;
                    gig2 = gson.fromJson(j.toString(), OutData.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (gig2 != null) {
                    Session.objData = gig2;
                } else {
                    Log.d("GIG", "null");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if(status == 0){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else if(status == 1){
                    new AsyncTask<Void,Void,Void>(){
                        @Override
                        protected Void doInBackground(Void... params) {
                            for (int i = 0; i < Session.objData.getDevices().size(); i++) {
                                for (int j = 0; j < Session.objData.getDevices().get(i).getDetalles().size(); j++) {
                                    if (Session.objData.getDevices().get(i).getDetalles().get(j).getKey().equalsIgnoreCase("ST")) {
                                        bajar(Session.objData.getDevices().get(i).getDetalles().get(j).getValue(), Global.dirImages, "http://entel.rinno.cl/images/devices/");
                                        bajar(Session.objData.getDevices().get(i).getDetalles().get(0).getValue(), Global.dirImages, "http://entel.rinno.cl/images/devices/");

                                    }
                                }
                                if(Session.objData.getDevices().get(i).getHijos() != null){
                                    for(int k = 0; k < Session.objData.getDevices().get(i).getHijos().size(); k++){
                                        for (int m = 0; m < Session.objData.getDevices().get(i).getHijos().get(k).getDetalles().size(); m++){
                                            if (Session.objData.getDevices().get(i).getHijos().get(k).getDetalles().get(m).getKey().equalsIgnoreCase("ST")) {
                                                bajar(Session.objData.getDevices().get(i).getHijos().get(k).getDetalles().get(0).getValue(), Global.dirImages, "http://entel.rinno.cl/images/devices/");
                                                bajar(Session.objData.getDevices().get(i).getHijos().get(k).getDetalles().get(m).getValue(), Global.dirImages, "http://entel.rinno.cl/images/devices/");
                                            }
                                        }
                                    }
                                }

                            }
                            for (int i = 0; i < Session.objData.getAccessories().size(); i++) {
                                bajar(Session.objData.getAccessories().get(i).getDetalles().get(0).getValue(), Global.dirImages, "http://entel.rinno.cl/images/accessories/");
                            }
                            for (int i = 0; i < Session.objData.getPlanes().size(); i++) {
                                for (int j = 0; j < Session.objData.getPlanes().get(i).getPlans().size(); j++) {
                                    if (i < 2) {
                                        bajar(Session.objData.getPlanes().get(i).getPlans().get(j).getDetalles().get(0).getValue(), Global.dirImages, "http://entel.rinno.cl/images/plans/");
                                        bajar(Session.objData.getPlanes().get(i).getPlans().get(j).getDetalles().get(1).getValue(), Global.dirImages, "http://entel.rinno.cl/images/plans/");
                                    } else if (i == 2) {
                                        bajar(Session.objData.getPlanes().get(i).getPlans().get(j).getDetalles().get(0).getValue(), Global.dirImages, "http://entel.rinno.cl/images/plans/");
                                    }
                                }
                            }
                            return null;
                        }
                    }.execute();
                    crearJson(data);
                }
            }

        }.execute();
    }

    private void crearJson(final JSONObject jsonObject) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                Log.d("JSON", "EMPEZANDO A CREAR");
                File f = new File(Global.dirJson);
                if (f.exists()) {
                    Log.d("JSON", "SI EXISTE");
                    if (f.delete()) {
                        Log.d("JSON", "BORRADO");
                    } else {
                        Log.d("JSON", "NO BORRADO");
                    }

                }
                FileWriter file;
                try {
                    file = new FileWriter(Global.dirJson);
                    file.write(jsonObject.toString());
                    file.flush();
                    file.close();
                    Log.d("JSON", "CREADO");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }.execute();

    }

    private int bajar(final String salida, final String folder, String urlCo) {

        File file = null;
        try {
            double downloadedSize = 0;
            double totalSize;

            URL url = new URL(urlCo + salida);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            totalSize = urlConnection.getContentLength();
            file = new File(folder, salida);
            if (!file.exists()) {

                FileOutputStream fileOutput = new FileOutputStream(file);

                InputStream inputStream = urlConnection.getInputStream();

                byte[] buffer = new byte[1024];
                int bufferLength;

                Log.d("INFOCODE-SERVICIO", "INICIANDO PROCESO DE DESCARGAR DE IMAGEN: " + salida);
                try {
                    while ((bufferLength = inputStream.read(buffer)) > 0) {
                        fileOutput.write(buffer, 0, bufferLength);
                        downloadedSize += bufferLength;
                        int i = (int) ((downloadedSize / totalSize) * 100);
                        if (i <= 100) {
                            switch (i) {
                                case 25:
                                    Log.d("INFOCODE-SERVICIO", "IMAGEN DESCARGADO AL 25%");
                                    break;
                                case 50:
                                    Log.d("INFOCODE-SERVICIO", "IMAGEN DESCARGADO AL 50%");

                                    break;
                                case 75:
                                    Log.d("INFOCODE-SERVICIO", "IMAGEN DESCARGADO AL 75%");

                                    break;
                                case 100:
                                    Log.d("INFOCODE-SERVICIO", "IMAGEN DESCARGADO AL 100%");
                                    break;
                            }

                        }
                    }
                    fileOutput.close();
                } catch (Exception e) {
                    Log.d("INFOCODE-SERVICIO", "INTENTANDO DESCARGAR DE NUEVO");
                    bajar(salida, folder, urlCo);
                }
                if (downloadedSize != urlConnection.getContentLength()) {
                    Log.d("DOWNLOAD SIZE", downloadedSize + "");
                    File videoShow = new File(folder, salida);
                    boolean d = videoShow.delete();
                    if (d) {
                        Log.d("INFOCODE-SERVICIO", "ARCHIVO DAÑADO - ELIMINANDO EL VIDEO");
                    }
                } else {
                    Log.d("INFOCODE-SERVICIO", "VIDEO DESCARGADO EXITOSAMENTE");

                }
                return 1;

            } else {
                return 1;
            }


        } catch (IOException e) {
            e.printStackTrace();
            assert file != null;
            try {
                file.delete();

            } catch (Exception es) {
                return 0;

            }
        }
        return 1;


    }

    public Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.cl");
            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
