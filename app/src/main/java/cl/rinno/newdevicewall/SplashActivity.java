package cl.rinno.newdevicewall;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;

import butterknife.ButterKnife;
import cl.rinno.newdevicewall.models.DWAMpi;
import cl.rinno.newdevicewall.models.DWApi;
import cl.rinno.newdevicewall.models.Global;
import cl.rinno.newdevicewall.models.OutData;
import cl.rinno.newdevicewall.models.Producto;
import cl.rinno.newdevicewall.models.Provider;
import cl.rinno.newdevicewall.models.Session;
import cz.msebera.android.httpclient.Header;

public class SplashActivity extends AppCompatActivity
{

    private static final String TAG = "SplashActivity";
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        //<editor-fold desc="CODIGO DE PANTALLA">
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        this.getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

        int currentApiVersion = Build.VERSION.SDK_INT;
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        if ( currentApiVersion >= Build.VERSION_CODES.KITKAT )
        {
            getWindow().getDecorView().setSystemUiVisibility( flags );
            final View decorView = getWindow().getDecorView();
            decorView.setOnSystemUiVisibilityChangeListener( new View.OnSystemUiVisibilityChangeListener()
            {
                @Override
                public void onSystemUiVisibilityChange( int visibility )
                {
                    if ( (visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0 )
                    {
                        decorView.setSystemUiVisibility( flags );
                    }
                }
            } );
        }
        //</editor-fold>

        setContentView( R.layout.activity_splash );
        ButterKnife.bind( this );
        Global.makeDirectories();

        //REVISAR PORQUE FRESCO PRESENTA PROBLEMAS

        //<editor-fold desc="INICIALIZACION DE FRESCO">
        if ( Global.mBandera == 0 )
        {
            Fresco.initialize( this );
            Global.mBandera++;
            Log.d( "FRESCO", "INICIALIZADO" );
        } else
        {
            Log.d( "FRESCO", "NO INICIALIZADO" );
        }
        //</editor-fold>


        File oldJson = new File( Global.dirJson );

        //EXISTE EL ARCHIVO, SE BUSCARA UNA ACTUALIZACION
        if ( oldJson.exists() && isOnlineNet() )
        {
            int length = (int) oldJson.length();
            byte[] bytes = new byte[ length ];
            try (FileInputStream in = new FileInputStream( oldJson ))
            {
                in.read( bytes );
                Global.jsonData2 = new String( bytes );
                JSONObject data = new JSONObject( Global.jsonData2 );
                serialize( data, 2 );
            } catch ( Exception e )
            {
                e.printStackTrace();
            }



        }
        //SE ENCONTRO EL ARCHIVO - EJECUCION LOCAL
        else if ( oldJson.exists() )
        {
            int length = (int) oldJson.length();
            byte[] bytes = new byte[ length ];
            try (FileInputStream in = new FileInputStream( oldJson ))
            {
                in.read( bytes );
                Global.jsonData2 = new String( bytes );
                JSONObject data = new JSONObject( Global.jsonData2 );
                serialize( data, 0 );
            } catch ( Exception e )
            {
                e.printStackTrace();
            }
        }
        //NO EXISTE EL ARCHIVO, SE DESCARGARA
        else if ( (!oldJson.exists()) && isOnlineNet() )
        {
            DWAMpi.get( "labheru/data.json", null, new JsonHttpResponseHandler()
            {
                @Override
                public void onSuccess( int statusCode, Header[] headers, JSONObject response )
                {
                    serialize( response, 1 );
                }

                @Override
                public void onFailure( int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse )
                {
                    super.onFailure( statusCode, headers, throwable, errorResponse );
                }
            } );
        } else
        {
            Log.d( "TAG", "EXPLO" );
        }
    }

    private void serialize( final JSONObject data, final int status )
    {

        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected Void doInBackground( Void... params )
            {
                GsonBuilder gsonb = new GsonBuilder();
                Gson gson = gsonb.create();
                JSONObject j;
                OutData gig2 = null;
                try
                {
                    j = data;
                    gig2 = gson.fromJson( j.toString(), OutData.class );
                } catch ( Exception e )
                {
                    e.printStackTrace();
                }

                if ( gig2 != null )
                {
                    Session.objData = gig2;
                } else
                {
                    Log.d( "GIG", "null" );
                }
                return null;
            }

            @Override
            protected void onPostExecute( Void aVoid )
            {
                super.onPostExecute( aVoid );
                if ( status == 0 )
                {
                    cargaLista();
                    startActivity( new Intent( getApplicationContext(), MainActivity.class ) );
                    finish();
                } else if ( status == 1 )
                {
                    new AsyncTask<Void,Void,Void>()
                    {
                        @Override
                        protected Void doInBackground( Void... params )
                        {
                            cargaLista();

                            for ( int i = 0; i < Session.objData.getDevices().size(); i++ )
                            {
                                for ( int j = 0; j < Session.objData.getDevices().get( i ).getDetalles().size(); j++ )
                                {
                                    if ( Session.objData.getDevices().get( i ).getDetalles().get( j ).getKey().equalsIgnoreCase( "ST" ) )
                                    {
                                        bajar( Session.objData.getDevices().get( i ).getDetalles().get( j ).getValue(), Global.dirImages, "http://entel.rinno.cl/images/devices/" );
                                        bajar( Session.objData.getDevices().get( i ).getDetalles().get( 0 ).getValue(), Global.dirImages, "http://entel.rinno.cl/images/devices/" );
                                        bajar( Session.objData.getDevices().get( i ).getImageHigh(), Global.dirImages, "http://entel.rinno.cl/images/details/high/" );

                                    }
                                }
                                if ( Session.objData.getDevices().get( i ).getHijos() != null )
                                {
                                    for ( int k = 0; k < Session.objData.getDevices().get( i ).getHijos().size(); k++ )
                                    {
                                        for ( int m = 0; m < Session.objData.getDevices().get( i ).getHijos().get( k ).getDetalles().size(); m++ )
                                        {
                                            if ( Session.objData.getDevices().get( i ).getHijos().get( k ).getDetalles().get( m ).getKey().equalsIgnoreCase( "ST" ) )
                                            {
                                                bajar( Session.objData.getDevices().get( i ).getHijos().get( k ).getDetalles().get( 0 ).getValue(), Global.dirImages, "http://entel.rinno.cl/images/devices/" );
                                                bajar( Session.objData.getDevices().get( i ).getHijos().get( k ).getDetalles().get( m ).getValue(), Global.dirImages, "http://entel.rinno.cl/images/devices/" );
                                            }
                                        }
                                    }
                                }

                            }

                            bajar( Session.objData.getCatalog().getScreenTwoImage(), Global.dirImages, "http://entel.rinno.cl/images/details/high/" );
                            bajar( Session.objData.getCatalog().getScreenOneImage(), Global.dirImages, "http://entel.rinno.cl/images/details/high/" );
                            bajar( Session.objData.getCatalog().getScreenThreeImage(), Global.dirImages, "http://entel.rinno.cl/images/details/high/" );
                            bajar( Session.objData.getCatalog().getScreenFourImage(), Global.dirImages, "http://entel.rinno.cl/images/details/high/" );
                            bajar( Session.objData.getCatalog().getScreenTwoImage_h(), Global.dirImages, "http://entel.rinno.cl/images/details/high/" );
                            bajar( Session.objData.getCatalog().getScreenOneImage_h(), Global.dirImages, "http://entel.rinno.cl/images/details/high/" );
                            bajar( Session.objData.getCatalog().getScreenThreeImage_h(), Global.dirImages, "http://entel.rinno.cl/images/details/high/" );
                            bajar( Session.objData.getCatalog().getScreenFourImage_h(), Global.dirImages, "http://entel.rinno.cl/images/details/high/" );

                            for ( int i = 0; i < Session.objData.getCatalog().getOfertas().size(); i++ )
                            {
                                bajar( Session.objData.getCatalog().getOfertas().get( i ).getBannerImage(), Global.dirImages, "http://entel.rinno.cl/images/details/high/" );
                                bajar( Session.objData.getCatalog().getOfertas().get( i ).getPrimaryImage(), Global.dirImages, "http://entel.rinno.cl/images/details/high/" );
                                bajar( Session.objData.getCatalog().getOfertas().get( i ).getPrimaryImage_h(), Global.dirImages, "http://entel.rinno.cl/images/details/high/" );
                            }


                            for ( int i = 0; i < Session.objData.getAccessories().size(); i++ )
                            {
                                bajar( Session.objData.getAccessories().get( i ).getDetalles().get( 0 ).getValue(), Global.dirImages, "http://entel.rinno.cl/images/accessories/" );
                                if ( !Session.objData.getAccessories().get( i ).getImageHigh().equalsIgnoreCase( "1" ) )
                                {
                                    bajar( Session.objData.getAccessories().get( i ).getImageHigh(), Global.dirImages, "http://entel.rinno.cl/images/details/high/" );
                                }
                            }

                            for ( int i = 0; i < Session.objData.getPlanes().size(); i++ )
                            {
                                Log.i( TAG, "doInBackground: "+Session.objData.getPlanes().get( i ).getPlans().size() );
                                for ( int j = 0; j < Session.objData.getPlanes().get( i ).getPlans().size(); j++ )
                                {
                                    if ( i < 2 )
                                    {
                                        bajar( Session.objData.getPlanes().get( i ).getPlans().get( j ).getDetalles().get( 0 ).getValue(), Global.dirImages, "http://entel.rinno.cl/images/plans/" );
//                                        bajar( Session.objData.getPlanes().get( i ).getPlans().get( j ).getDetalles().get( 1 ).getValue(), Global.dirImages, "http://entel.rinno.cl/images/plans/" );
                                    } else if ( i == 2 )
                                    {
                                        bajar( Session.objData.getPlanes().get( i ).getPlans().get( j ).getDetalles().get( 0 ).getValue(), Global.dirImages, "http://entel.rinno.cl/images/plans/" );
                                    }
                                    bajar( Session.objData.getPlanes().get( i ).getPlans().get( j ).getImagen_oferta(), Global.dirImages, "http://entel.rinno.cl/images/plans/" );

                                }
                                bajar( Session.objData.getPlanes().get( i ).getCondicionImage(), Global.dirImages, "http://entel.rinno.cl/images/groups/" );
                                bajar( Session.objData.getPlanes().get( i ).getCondicionImageHorizontal(), Global.dirImages, "http://entel.rinno.cl/images/groups/" );
                                bajar( Session.objData.getPlanes().get( i ).getPrimaryImage(), Global.dirImages, "http://entel.rinno.cl/images/groups/" );
                                bajar( Session.objData.getPlanes().get( i ).getBannerImage(), Global.dirImages, "http://entel.rinno.cl/images/groups/" );
                            }

                            for ( int i = 0; i < Session.objData.getProviders().size(); i++ )
                            {
                                bajar( Session.objData.getProviders().get( i ).getProvider_image(), Global.dirImages, "http://entel.rinno.cl/images/details/providers/" );
                            }
                            return null;
                        }
                    }.execute();
                    crearJson( data );
                } else if ( status == 2 )
                {
                    Handler mainHandler = new Handler( getApplicationContext().getMainLooper() );
                    Runnable runnable = new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            DWApi.get( "apii/status/1/edeviceswall", null, new AsyncHttpResponseHandler()
                            {
                                @Override
                                public void onSuccess( int statusCode, Header[] headers, byte[] responseBody )
                                {
                                    String status = new String( responseBody );
                                    Log.d( "STATUS CATALOG", status );
                                    Log.d( "OWN STATUS", Session.objData.getCatalog().getStatus() );
                                    if ( !Session.objData.getCatalog().getStatus().equalsIgnoreCase( status ) )
                                    {
                                        Log.d( "SERVICIO", "SE INICIO" );
                                        startService( new Intent( SplashActivity.this, DownloadService.class ) );
                                    } else
                                    {
                                        Log.d( "SERVICIO", "NO SE INICIO" );
                                    }
                                    cargaLista();
                                    startActivity( new Intent( getApplicationContext(), MainActivity.class ) );
                                    finish();
                                }

                                @Override
                                public void onFailure( int statusCode, Header[] headers, byte[] responseBody, Throwable error )
                                {

                                }
                            } );
                        }
                    };
                    mainHandler.post( runnable );
                }
            }

        }.execute();
    }

    private void cargaLista()
    {
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

        for ( int i = 0; i < Session.objData.getDevices().size(); i++ )
        {
            Global.allProducts.add( Session.objData.getDevices().get( i ) );
            Global.allDevices.add( Session.objData.getDevices().get( i ) );
        }
        for ( int i = 0; i < Session.objData.getAccessories().size(); i++ )
        {
            Global.allProducts.add( Session.objData.getAccessories().get( i ) );
            Global.allAccessories.add( Session.objData.getAccessories().get( i ) );
        }
        for ( int i = 0; i < Session.objData.getCatalog().getOfertas().size(); i++ )
        {
            Global.allProducts.add( Session.objData.getCatalog().getOfertas().get( i ) );
        }

        for ( int i = 0; i < Session.objData.getPlanes().size(); i++ )
        {
            Global.allPlans.add( i, Session.objData.getPlanes().get( i ) );
        }

        Collections.shuffle( Global.allProducts );
        Collections.shuffle( Global.allAccessories );
        Collections.swap( Global.allPlans, 1, 2 );

        int j = 1;
        while ( j < 11 )
        {
            for ( int i = 0; i < Session.objData.getPlanes().size(); i++ )
            {
                switch ( j )
                {
                    case 1:
                        Global.allProducts.add( 1, Session.objData.getPlanes().get( i ) );
                        break;
                    case 2:
                        Global.allProducts.add( 11, Session.objData.getPlanes().get( i ) );
                        break;
                    case 3:
                        Global.allProducts.add( 21, Session.objData.getPlanes().get( i ) );
                        break;
                    case 4:
                        Global.allProducts.add( 28, Session.objData.getPlanes().get( i ) );
                        break;
                    case 5:
                        Global.allProducts.add( 35, Session.objData.getPlanes().get( i ) );
                        break;
                    case 6:
                        Global.allProducts.add( 42, Session.objData.getPlanes().get( i ) );
                        break;
                    case 7:
                        Global.allProducts.add( 50, Session.objData.getPlanes().get( i ) );
                        break;
                    case 8:
                        Global.allProducts.add( 56, Session.objData.getPlanes().get( i ) );
                        break;
                    case 9:
                        Global.allProducts.add( 64, Session.objData.getPlanes().get( i ) );
                        break;
                    case 10:
                        Global.allProducts.add( 71, Session.objData.getPlanes().get( i ) );
                        break;
                }
                j++;
            }
        }
        for ( int i = 0; i < Session.objData.getPlanes().get( 2 ).getPlans().size(); i++ )
        {
            Log.d( "VOZ", Session.objData.getPlanes().get( 2 ).getPlans().get( i ).getName() );

            if ( i < 2 )
            {
                Global.planesDeVozCCList.add( Session.objData.getPlanes().get( 2 ).getPlans().get( i ) );
            } else
            {
                Global.planesDeVozIlimitado.add( Session.objData.getPlanes().get( 2 ).getPlans().get( i ) );
            }
        }
        for ( int i = 0; i < Session.objData.getPlanes().get( 0 ).getPlans().size(); i++ )
        {
            Log.i( "TAG", Session.objData.getPlanes().get( 0 ).getPlans().get( i ).getName() );
            if ( (Session.objData.getPlanes().get( 0 ).getPlans().get( i ).getName().toLowerCase().contains( "libre" )) )
            {
                Global.planesSmartFunSimple.add( Session.objData.getPlanes().get( 0 ).getPlans().get( i ) );

            }
        }

        for ( int i = 0; i < Session.objData.getPlanes().get( 1 ).getPlans().size(); i++ )
        {
            if ( (Session.objData.getPlanes().get( 1 ).getPlans().get( i ).getName().toLowerCase().contains( "control" )) )
            {
                Global.planesControlFunSimple.add( Session.objData.getPlanes().get( 1 ).getPlans().get( i ) );
            }
        }
        for ( int i = 0; i < Session.objData.getProviders().size(); i++ )
        {
            if ( (Session.objData.getProviders().get( i ).getProduct_type().equalsIgnoreCase( "2" ) || Session.objData.getProviders().get( i ).getProduct_type().equalsIgnoreCase( "3" )) && (Session.objData.getProviders().get( i ).getStatus().equalsIgnoreCase( "1" )) )
            {
                Global.providersDevices.add( Session.objData.getProviders().get( i ) );
            }
        }

        Collections.sort( Global.providersDevices, new Comparator<Provider>()
        {
            @Override
            public int compare( Provider o1, Provider o2 )
            {
                return o1.getName().compareTo( o2.getName() );
            }
        } );
        Collections.sort( Global.allDevices, new Comparator<Producto>()
        {
            @Override
            public int compare( Producto o1, Producto o2 )
            {
                return o1.getProvider_name().compareTo( o2.getProvider_name() );
            }
        } );
    }

    private void crearJson( final JSONObject jsonObject )
    {
        Log.d( "JSON", "EMPEZANDO A CREAR" );
        new AsyncTask<Void,Void,Void>()
        {

            @Override
            protected Void doInBackground( Void... params )
            {
                Log.d( "JSON", "EMPEZANDO A CREAR" );
                File f = new File( Global.dirJson );
                if ( f.exists() )
                {
                    Log.d( "JSON", "SI EXISTE" );
                    if ( f.delete() )
                    {
                        Log.d( "JSON", "BORRADO" );
                    } else
                    {
                        Log.d( "JSON", "NO BORRADO" );
                    }

                }
                FileWriter file;
                try
                {
                    file = new FileWriter( Global.dirJson );
                    file.write( jsonObject.toString() );
                    file.flush();
                    file.close();
                    Log.d( "JSON", "CREADO" );
                } catch ( IOException e )
                {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute( Void aVoid )
            {
                super.onPostExecute( aVoid );
                startActivity( new Intent( getApplicationContext(), MainActivity.class ) );
                finish();
            }
        }.execute();

    }

    private int bajar( final String salida, final String folder, String urlCo )
    {

        /*Handler handler = new Handler(getApplicationContext().getMainLooper());
            Runnable mRun = new Runnable() {
                @Override
                public void run() {
                    tvDetalle.setText(salida);
                }
            };
        handler.post(mRun);*/
        File file = null;
        try
        {
            double downloadedSize = 0;
            double totalSize;
            URL url = new URL( urlCo + salida );
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod( "GET" );
            urlConnection.connect();
            totalSize = urlConnection.getContentLength();
            file = new File( folder, salida );
            if ( !file.exists() )
            {

                FileOutputStream fileOutput = new FileOutputStream( file );

                InputStream inputStream = urlConnection.getInputStream();

                byte[] buffer = new byte[ 1024 ];
                int bufferLength;

                Log.d( "INFOCODE-SERVICIO", "INICIANDO PROCESO DE DESCARGAR DE IMAGEN: " + salida );
                try
                {
                    while ( (bufferLength = inputStream.read( buffer )) > 0 )
                    {
                        fileOutput.write( buffer, 0, bufferLength );
                        downloadedSize += bufferLength;
                        int i = (int) ((downloadedSize / totalSize) * 100);
                        if ( i <= 100 )
                        {
                            switch ( i )
                            {
                                case 25:
                                    Log.d( "INFOCODE-SERVICIO", "IMAGEN DESCARGADO AL 25%" );
                                    break;
                                case 50:
                                    Log.d( "INFOCODE-SERVICIO", "IMAGEN DESCARGADO AL 50%" );

                                    break;
                                case 75:
                                    Log.d( "INFOCODE-SERVICIO", "IMAGEN DESCARGADO AL 75%" );

                                    break;
                                case 100:
                                    Log.d( "INFOCODE-SERVICIO", "IMAGEN DESCARGADO AL 100%" );
                                    break;
                            }

                        }
                    }
                    fileOutput.close();
                } catch ( Exception e )
                {
                    Log.d( "INFOCODE-SERVICIO", "INTENTANDO DESCARGAR DE NUEVO" );
                    bajar( salida, folder, urlCo );
                }
                if ( downloadedSize != urlConnection.getContentLength() )
                {
                    Log.d( "DOWNLOAD SIZE", downloadedSize + "" );
                    File videoShow = new File( folder, salida );
                    boolean d = videoShow.delete();
                    if ( d )
                    {
                        Log.d( "INFOCODE-SERVICIO", "ARCHIVO DAÑADO - ELIMINANDO EL VIDEO" );
                    }
                } else
                {
                    Log.d( "INFOCODE-SERVICIO", "VIDEO DESCARGADO EXITOSAMENTE" );

                }
                return 1;

            } else
            {
                return 1;
            }


        } catch ( IOException e )
        {
            e.printStackTrace();
            assert file != null;
            try
            {
                file.delete();

            } catch ( Exception es )
            {
                return 0;

            }
        }
        return 1;


    }

    public Boolean isOnlineNet()
    {

        try
        {
            Process p = Runtime.getRuntime().exec( "ping -c 1 www.google.cl" );
            int val = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
