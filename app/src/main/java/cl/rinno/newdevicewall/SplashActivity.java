package cl.rinno.newdevicewall;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import cl.rinno.newdevicewall.models.DWApi;
import cl.rinno.newdevicewall.models.Global;
import cl.rinno.newdevicewall.models.OutData;
import cl.rinno.newdevicewall.models.Session;
import cz.msebera.android.httpclient.Header;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Global.makeDirectories();

        File oldJson = new File(Global.dirJson);
        if(oldJson.exists()){
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

        }else{
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
                crearJson(data);
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
                Fresco.initialize(getApplicationContext());
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }.execute();

    }
}
