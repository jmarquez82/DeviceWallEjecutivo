package cl.rinno.newdevicewall;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
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
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;

import cl.rinno.newdevicewall.models.DWAMpi;
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


public class DownloadService extends IntentService
{

    public String TAG = "DOWNLOAD-SERVICE";
    public int totalDescargado = 0;

    public DownloadService()
    {
        super( "DownloadService" );
    }

    @Override
    protected void onHandleIntent( Intent intent )
    {
        Log.d( TAG, "Empezó" );

        Handler mainHandler = new Handler( getApplicationContext().getMainLooper() );
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
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
            }
        };
        mainHandler.post( runnable );
    }

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
                    Intent i = new Intent( getApplicationContext(), MainActivity.class );
                    i.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                    startActivity( i );


                } else if ( status == 1 )
                {
                    new AsyncTask<Void,Void,Void>()
                    {
                        @Override
                        protected Void doInBackground( Void... params )
                        {

                            for ( String imagen : Session.objData.getImagenes() )
                            {
                                Log.i( TAG, "doInBackground: " + imagen );
                                try
                                {
                                    downloadFile( imagen );
                                } catch ( IOException e )
                                {
                                    e.printStackTrace();
                                }
                            }
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

    public void downloadFile( String uRl ) throws IOException
    {

        File direct = new File( Global.dirImages );

        if ( !direct.exists() )
        {
            direct.mkdirs();
        }

        File imagen = new File( Global.dirImages + uRl );
        if ( !imagen.exists() && isOnlineNet() )
        {
            Log.i( TAG, "downloadFile: descargado" );
            totalDescargado++;

            try
            {
                URL url = new URL( "http://rinno.cl/bodega/imagenes/" + uRl );


                        /* checks the file and if it already exist delete */
                String fname = uRl;
                File file = new File( Global.dirImages, fname );
                if ( file.exists() )
                {
                    file.delete();

                }

                             /* Open a connection */
                URLConnection ucon = url.openConnection();
                InputStream inputStream = null;
                HttpURLConnection httpConn = (HttpURLConnection) ucon;
                httpConn.setRequestMethod( "GET" );
                httpConn.connect();

                if ( httpConn.getResponseCode() == HttpURLConnection.HTTP_OK )
                {
                    inputStream = httpConn.getInputStream();
                }

                FileOutputStream fos = new FileOutputStream( file );
                int totalSize = httpConn.getContentLength();
                int downloadedSize = 0;
                byte[] buffer = new byte[ 1024 ];
                int bufferLength = 0;
                while ( (bufferLength = inputStream.read( buffer )) > 0 )
                {
                    fos.write( buffer, 0, bufferLength );
                    downloadedSize += bufferLength;
                    Log.i( "Progress:", "downloadedSize:" + downloadedSize + "totalSize:" + totalSize );
                }

                fos.close();
                Log.d( "test", "Image Saved in sdcard.." );
            } catch ( IOException io )
            {
                io.printStackTrace();
            } catch ( Exception e )
            {
                e.printStackTrace();
            }


        } else
        {
            Log.i( TAG, "downloadFile: Existe" );
        }

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
                if ( totalDescargado > 0 )
                {


                    Intent i = new Intent( getApplicationContext(), MainActivity.class );
                    i.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
                    startActivity( i );
                }
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
