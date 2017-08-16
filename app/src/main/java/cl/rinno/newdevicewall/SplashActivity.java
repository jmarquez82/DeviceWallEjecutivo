package cl.rinno.newdevicewall;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import butterknife.ButterKnife;
import cl.rinno.newdevicewall.models.DWAMpi;
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

        if ( oldJson.exists() )
        {
            Log.i( TAG, "Archivos existe & serializando modo online " );
            //<editor-fold desc="Archivo existe serializando">
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
            //</editor-fold>

        } else if ( (!oldJson.exists()) && isOnlineNet() )
        {
            Log.i( TAG, "Obteniendo contenido por primera vez" );
            //<editor-fold desc="Ingreso por primera vez">
            DWAMpi.get( "bodega/imagenes/document.json", null, new JsonHttpResponseHandler()
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
            //</editor-fold>

        } else
        {
            Toast.makeText( this, "Contactar desarrollador, Gracias", Toast.LENGTH_SHORT ).show();
        }
    }

    ////////////////////////////
    private void serialize( final JSONObject data, final int status )
    {

        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected Void doInBackground( Void... params )
            {
                //<editor-fold desc="Codigo serializacion">
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
                }
                //</editor-fold>
                return null;
            }

            @Override
            protected void onPostExecute( Void aVoid )
            {
                super.onPostExecute( aVoid );
                if ( status == 0 )
                {
                    cargaLista();

                    Log.i( TAG, "Cargando servicio" );
                    startService( new Intent( SplashActivity.this, DownloadService.class ) );

                    startActivity( new Intent( getApplicationContext(), MainActivity.class ) );

                    finish();
                } else if ( status == 1 )
                {
                    new AsyncTask<Void,Void,Void>()
                    {
                        @Override
                        protected Void doInBackground( Void... params )
                        {

                            // for ( String imagen : Session.objData.getImagenes() )
                            // {
                            //   Log.i( TAG, "doInBackground: " + imagen );
                            // downloadFile( imagen );
                            //}
                            return null;
                        }

                        @Override
                        protected void onPostExecute( Void aVoid )
                        {
                            super.onPostExecute( aVoid );
                            entregarDatos();
                            crearJson( data );

                        }
                    }.execute();
                }
            }
        }.execute();
    }

    public void entregarDatos()
    {
        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected Void doInBackground( Void... params )
            {
                cargaLista();
                return null;
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


        Global.allDevices = (ArrayList<Producto>) Session.objData.getProductos();
        Global.allAccessories = (ArrayList<Producto>) Session.objData.getAccesorios();


        ArrayList<String> proveedores = new ArrayList<>();
        ArrayList<Provider> listaProvedores = new ArrayList<>();

        ArrayList<String> idHijos = new ArrayList<>();


        for ( Producto producto : Session.objData.getAccesorios() )
        {

            for ( Provider proveedor : Session.objData.getProveedores() )
            {
                if ( proveedor.getId().equals( producto.getProvider_id() ) )
                {
                    Log.i( TAG, proveedor.getName() );
                    producto.setProvider_name( proveedor.getName() );
                    break;
                }
            }

            Log.i( TAG, producto.getImagenPrimaria() );
            Global.allProducts.add( producto );
        }

        for ( Producto producto : Session.objData.getProductos() )
        {
            for ( Provider proveedor : Session.objData.getProveedores() )
            {
                if ( proveedor.getId().equals( producto.getProvider_id() ) )
                {
                    Log.i( TAG, proveedor.getName() );
                    producto.setProvider_name( proveedor.getName() );
                    break;
                }
            }


            for ( Producto hijos : producto.getHijos() )
            {
                if ( !hijos.getId().equals( producto.getId() ) )
                {
                    idHijos.add( hijos.getId() );
                }
            }

            proveedores.add( producto.getProvider_id() );
            Global.allProducts.add( producto );
        }

        for ( Producto producto : Global.allProducts )
        {
            for ( String id : idHijos )
            {
                if ( id.equals( producto.getId() ) )
                {
                    producto.setEstado( 0 );
                }
            }
        }

        HashSet<String> hs = new HashSet<>();
        hs.addAll( proveedores );
        proveedores.clear();
        proveedores.addAll( hs );

        for ( String id : proveedores )
        {
            for ( Provider proveedor : Session.objData.getProveedores() )
            {
                if ( proveedor.getId().equals( id ) )
                {
                    listaProvedores.add( proveedor );
                    break;
                }
            }
        }


        Global.providersDevices = listaProvedores;


        for ( int i = 0; i < Session.objData.getGrupos().size(); i++ )
        {
            switch ( i )
            {
                case 0:
                    for ( Producto producto : Session.objData.getGrupos().get( i ).getPlanes() )
                    {

                        Global.planesSmartFunSimple.add( producto );
                    }

                    break;

                case 1:
                    for ( Producto producto : Session.objData.getGrupos().get( i ).getPlanes() )
                    {
                        Global.planesControlFunSimple.add( producto );

                    }

                    break;
            }

            Session.objData.getGrupos().get( i ).setProduct_type_id( "3" );
            Global.allProducts.add( Session.objData.getGrupos().get( i ) );
            Global.allProducts.add( Session.objData.getGrupos().get( i ) );
            Global.allProducts.add( Session.objData.getGrupos().get( i ) );

        }


    }

    private void crearJson( final JSONObject jsonObject )
    {
        Log.i( TAG, "Creando Json" );
        new AsyncTask<Void,Void,Void>()
        {

            @Override
            protected Void doInBackground( Void... params )
            {
                Log.i( TAG, "Iniciando proceso" );
                File f = new File( Global.dirJson );
                if ( f.exists() )
                {
                    Log.i( TAG, "Archivo existe" );
                    if ( f.delete() )
                    {
                        Log.i( TAG, "Archivo borrado" );
                    } else
                    {
                        Log.i( TAG, "El Archivo no fue borrado" );
                    }

                }
                FileWriter file;
                try
                {
                    file = new FileWriter( Global.dirJson );
                    file.write( jsonObject.toString() );
                    file.flush();
                    file.close();
                    Log.i( TAG, "El Archivo creado" );
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
            e.printStackTrace();
        }
        return false;
    }
}
