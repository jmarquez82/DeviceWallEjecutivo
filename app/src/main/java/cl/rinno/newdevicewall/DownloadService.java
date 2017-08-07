package cl.rinno.newdevicewall;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;

import cl.rinno.newdevicewall.models.DWApi;
import cl.rinno.newdevicewall.models.Global;
import cl.rinno.newdevicewall.models.OutData;
import cl.rinno.newdevicewall.models.Producto;
import cl.rinno.newdevicewall.models.Provider;
import cl.rinno.newdevicewall.models.Session;
import cz.msebera.android.httpclient.Header;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */


public class DownloadService extends IntentService {

    public String TAG = "DOWNLOAD-SERVICE";

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Empezó");

        Handler mainHandler = new Handler(getApplicationContext().getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                DWApi.get("api/showfruna/1", null, new JsonHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        Log.d("RESPONSE", "Start");
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        serialize(response, 1);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.e("ERROR", errorResponse.toString());
                    }
                });
            }
        };
        mainHandler.post(runnable);
    }

    private void bajar(final String salida, final String folder, final String urlCo) {
        File file = null;
        if (salida.length() > 3) {
            try {
                double downloadedSize = 0;
                double totalSize;
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
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

                }


            } catch (IOException e) {
                e.printStackTrace();
                assert file != null;
                try {
                    file.delete();

                } catch (Exception ex) {

                }
            }
        }
    }

    private void crearJson(final JSONObject jsonObject) {
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


    }

    private void cargaLista() {
        Global.allProducts.clear();
        Global.allAccessories.clear();
        Global.allDevices.clear();
        Global.planesControlFunSimple.clear();
        Global.planesSmartFunSimple.clear();
        Global.planesDeVozCCList.clear();
        Global.planesDeVozIlimitado.clear();
        Global.providersDevices.clear();
        Global.allPlans.clear();
        Global.miPrimerPlanVoz.clear();
        Global.miPrimerPlanMultimedia.clear();

        for (int i = 0; i < Session.objData.getDevices().size(); i++) {
            Global.allProducts.add(Session.objData.getDevices().get(i));
            Global.allDevices.add(Session.objData.getDevices().get(i));
        }
        for (int i = 0; i < Session.objData.getAccessories().size(); i++) {
            Global.allProducts.add(Session.objData.getAccessories().get(i));
            Global.allAccessories.add(Session.objData.getAccessories().get(i));
        }
        for (int i = 0; i < Session.objData.getCatalog().getOfertas().size(); i++) {
            Global.allProducts.add(Session.objData.getCatalog().getOfertas().get(i));
        }

        for (int i = 0; i < Session.objData.getPlanes().size(); i++) {
            Global.allPlans.add(i, Session.objData.getPlanes().get(i));
        }

        Collections.shuffle(Global.allProducts);
        Collections.shuffle(Global.allAccessories);
        Collections.swap(Global.allPlans, 1, 2);

        int j = 1;
        while (j < 11) {
            for (int i = 0; i < Session.objData.getPlanes().size(); i++) {
                switch (j) {
                    case 1:
                        Global.allProducts.add(1, Session.objData.getPlanes().get(i));
                        break;
                    case 2:
                        Global.allProducts.add(11, Session.objData.getPlanes().get(i));
                        break;
                    case 3:
                        Global.allProducts.add(21, Session.objData.getPlanes().get(i));
                        break;
                    case 4:
                        Global.allProducts.add(28, Session.objData.getPlanes().get(i));
                        break;
                    case 5:
                        Global.allProducts.add(35, Session.objData.getPlanes().get(i));
                        break;
                    case 6:
                        Global.allProducts.add(42, Session.objData.getPlanes().get(i));
                        break;
                    case 7:
                        Global.allProducts.add(50, Session.objData.getPlanes().get(i));
                        break;
                    case 8:
                        Global.allProducts.add(56, Session.objData.getPlanes().get(i));
                        break;
                    case 9:
                        Global.allProducts.add(64, Session.objData.getPlanes().get(i));
                        break;
                    case 10:
                        Global.allProducts.add(71, Session.objData.getPlanes().get(i));
                        break;
                }
                j++;
            }
        }
        for (int i = 0; i < Session.objData.getPlanes().get(2).getPlans().size(); i++) {
            Log.d("VOZ",Session.objData.getPlanes().get(2).getPlans().get(i).getName());

            if (i < 2) {
                Global.planesDeVozCCList.add(Session.objData.getPlanes().get(2).getPlans().get(i));
            } else {
                Global.planesDeVozIlimitado.add(Session.objData.getPlanes().get(2).getPlans().get(i));
            }
        }
        for (int i = 0; i < Session.objData.getPlanes().get(1).getPlans().size(); i++) {
            if ((Session.objData.getPlanes().get(1).getPlans().get(i).getName().toLowerCase().contains("simple")) ) {
            Global.planesSmartFunSimple.add(Session.objData.getPlanes().get(1).getPlans().get(i));
                Log.d("CONTROL",Session.objData.getPlanes().get(1).getPlans().get(i).getName());

            }
        }

        for (int i = 0; i < Session.objData.getPlanes().get(0).getPlans().size(); i++) {
            if ((Session.objData.getPlanes().get(0).getPlans().get(i).getName().toLowerCase().contains("simple"))) {
                Global.planesControlFunSimple.add(Session.objData.getPlanes().get(0).getPlans().get(i));
            }
        }
        for (int i = 0; i < Session.objData.getProviders().size(); i++) {
            if ((Session.objData.getProviders().get(i).getProduct_type().equalsIgnoreCase("2") || Session.objData.getProviders().get(i).getProduct_type().equalsIgnoreCase("3")) && (Session.objData.getProviders().get(i).getStatus().equalsIgnoreCase("1"))) {
                Global.providersDevices.add(Session.objData.getProviders().get(i));
            }
        }
        Collections.sort(Global.providersDevices, new Comparator<Provider>() {
            @Override
            public int compare(Provider o1, Provider o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        Collections.sort(Global.allDevices, new Comparator<Producto>() {
            @Override
            public int compare(Producto o1, Producto o2) {
                return o1.getProvider_name().compareTo(o2.getProvider_name());
            }
        });
    }


    private void serialize(final JSONObject data, final int status) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                GsonBuilder gsonb = new GsonBuilder();
                Gson gson = gsonb.create();
                JSONObject jObject;
                OutData gig2 = null;
                try {
                    jObject = data;
                    gig2 = gson.fromJson(jObject.toString(), OutData.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (gig2 != null) {
                    Session.objData = gig2;
                } else {
                    Log.d("GIG", "null");
                }
                if (status == 1) {
                    cargaLista();
                }
                for (int i = 0; i < Session.objData.getDevices().size(); i++) {
                    for (int j = 0; j < Session.objData.getDevices().get(i).getDetalles().size(); j++) {
                        if (Session.objData.getDevices().get(i).getDetalles().get(j).getKey().equalsIgnoreCase("ST")) {
                            bajar(Session.objData.getDevices().get(i).getDetalles().get(j).getValue(), Global.dirImages, "http://entel.rinno.cl/images/devices/");
                            bajar(Session.objData.getDevices().get(i).getDetalles().get(0).getValue(), Global.dirImages, "http://entel.rinno.cl/images/devices/");
                            bajar(Session.objData.getDevices().get(i).getImageHigh(), Global.dirImages, "http://entel.rinno.cl/images/details/high/");
                        }
                    }
                    if (Session.objData.getDevices().get(i).getHijos() != null) {
                        for (int k = 0; k < Session.objData.getDevices().get(i).getHijos().size(); k++) {
                            for (int m = 0; m < Session.objData.getDevices().get(i).getHijos().get(k).getDetalles().size(); m++) {
                                if (Session.objData.getDevices().get(i).getHijos().get(k).getDetalles().get(m).getKey().equalsIgnoreCase("ST")) {
                                    bajar(Session.objData.getDevices().get(i).getHijos().get(k).getDetalles().get(0).getValue(), Global.dirImages, "http://entel.rinno.cl/images/devices/");
                                    bajar(Session.objData.getDevices().get(i).getHijos().get(k).getDetalles().get(m).getValue(), Global.dirImages, "http://entel.rinno.cl/images/devices/");
                                }
                            }
                        }
                    }

                }
                bajar(Session.objData.getCatalog().getScreenTwoImage(), Global.dirImages, "http://entel.rinno.cl/images/details/high/");
                bajar(Session.objData.getCatalog().getScreenOneImage(), Global.dirImages, "http://entel.rinno.cl/images/details/high/");
                bajar(Session.objData.getCatalog().getScreenThreeImage(), Global.dirImages, "http://entel.rinno.cl/images/details/high/");
                bajar(Session.objData.getCatalog().getScreenFourImage(), Global.dirImages, "http://entel.rinno.cl/images/details/high/");
                bajar(Session.objData.getCatalog().getScreenTwoImage_h(), Global.dirImages, "http://entel.rinno.cl/images/details/high/");
                bajar(Session.objData.getCatalog().getScreenOneImage_h(), Global.dirImages, "http://entel.rinno.cl/images/details/high/");
                bajar(Session.objData.getCatalog().getScreenThreeImage_h(), Global.dirImages, "http://entel.rinno.cl/images/details/high/");
                bajar(Session.objData.getCatalog().getScreenFourImage_h(), Global.dirImages, "http://entel.rinno.cl/images/details/high/");

                for (int i = 0; i < Session.objData.getCatalog().getOfertas().size(); i++) {
                    bajar(Session.objData.getCatalog().getOfertas().get(i).getBannerImage(), Global.dirImages, "http://entel.rinno.cl/images/details/high/");
                    bajar(Session.objData.getCatalog().getOfertas().get(i).getPrimaryImage(), Global.dirImages, "http://entel.rinno.cl/images/details/high/");
                    bajar(Session.objData.getCatalog().getOfertas().get(i).getPrimaryImage_h(), Global.dirImages, "http://entel.rinno.cl/images/details/high/");
                }
                for (int i = 0; i < Session.objData.getAccessories().size(); i++) {
                    bajar(Session.objData.getAccessories().get(i).getDetalles().get(0).getValue(), Global.dirImages, "http://entel.rinno.cl/images/accessories/");
                    if (!Session.objData.getAccessories().get(i).getImageHigh().equalsIgnoreCase("1")) {
                        bajar(Session.objData.getAccessories().get(i).getImageHigh(), Global.dirImages, "http://entel.rinno.cl/images/details/high/");
                    }
                }
                for (int i = 0; i < Session.objData.getPlanes().size(); i++) {
                    for (int j = 0; j < Session.objData.getPlanes().get(i).getPlans().size(); j++) {
                        if (i < 2) {
                            bajar(Session.objData.getPlanes().get(i).getPlans().get(j).getDetalles().get(0).getValue(), Global.dirImages, "http://entel.rinno.cl/images/plans/");
//                            bajar(Session.objData.getPlanes().get(i).getPlans().get(j).getDetalles().get(1).getValue(), Global.dirImages, "http://entel.rinno.cl/images/plans/");
                        } else if (i == 2) {
                            bajar(Session.objData.getPlanes().get(i).getPlans().get(j).getDetalles().get(0).getValue(), Global.dirImages, "http://entel.rinno.cl/images/plans/");
                        }
                        bajar(Session.objData.getPlanes().get(i).getPlans().get(j).getImagen_oferta(), Global.dirImages, "http://entel.rinno.cl/images/plans/");

                    }
                    bajar(Session.objData.getPlanes().get(i).getCondicionImage(), Global.dirImages, "http://entel.rinno.cl/images/groups/");
                    bajar(Session.objData.getPlanes().get(i).getCondicionImageHorizontal(), Global.dirImages, "http://entel.rinno.cl/images/groups/");
                    bajar(Session.objData.getPlanes().get(i).getPrimaryImage(), Global.dirImages, "http://entel.rinno.cl/images/groups/");
                    bajar(Session.objData.getPlanes().get(i).getBannerImage(), Global.dirImages, "http://entel.rinno.cl/images/groups/");
                }
                for (int i = 0; i < Session.objData.getProviders().size(); i++) {
                    bajar(Session.objData.getProviders().get(i).getProvider_image(), Global.dirImages, "http://entel.rinno.cl/images/details/providers/");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                crearJson(data);
                Intent bcIntent = new Intent();
                bcIntent.setAction("ok");
                sendBroadcast(bcIntent);
            }
        }.execute();
    }
}
