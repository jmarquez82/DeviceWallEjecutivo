package cl.rinno.newdevicewall;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.github.mmin18.widget.RealtimeBlurView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cl.rinno.newdevicewall.adapters.AlmacenamientoEquipoAdapter;
import cl.rinno.newdevicewall.adapters.CaracteristicasDestacadoAdapter;
import cl.rinno.newdevicewall.adapters.ColorAdapter;
import cl.rinno.newdevicewall.cls.TimerInactivity;
import cl.rinno.newdevicewall.cls.ViewPagerCarruselAdapter;
import cl.rinno.newdevicewall.models.Global;
import cl.rinno.newdevicewall.models.Producto;
import cl.rinno.newdevicewall.models.Session;
import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.PagerContainer;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FichaEquipo extends AppCompatActivity
{

    LinearLayoutManager linearLayoutManagerGB;
    LinearLayoutManager linearLayoutManagerCL;

    ArrayList<Producto> listHijos;

    TimerInactivity timerInactivity;


    @BindView(R.id.image_device)
    SimpleDraweeView imageDevice;
    @BindView(R.id.tv_provider_name)
    TextView tvProviderName;
    @BindView(R.id.tv_name_device)
    TextView tvNameDevice;
    @BindView(R.id.tv_screen_size)
    TextView tvScreenSize;
    @BindView(R.id.tv_back_camera)
    TextView tvBackCamera;
    @BindView(R.id.tv_front_camera)
    TextView tvFrontCamera;
    @BindView(R.id.rv_Almacenamiento)
    RecyclerView rvAlmacenamiento;
    @BindView(R.id.rvColores)
    RecyclerView rvColor;
    @BindView(R.id.llColores)
    LinearLayout llColores;
    @BindView(R.id.tv_precio_venta)
    TextView tvPrecioVenta;
    @BindView(R.id.button_volver_catalogo)
    LinearLayout btnVolverCatalogo;
    @BindView(R.id.button_llevatelo_con_un_plan)
    LinearLayout btnLlevateloConUnPlan;
    @BindView(R.id.image_sticker)
    SimpleDraweeView imgSticker;
    @BindView(R.id.vp_carrusel)
    ViewPager vpCarrusel;
    @BindView(R.id.pager_container)
    PagerContainer pagerContainer;

    ViewPagerCarruselAdapter viewPagerCarruselAdapter;
    ArrayList<Producto> accesoriosCompatibles;
    @BindView(R.id.lineat_next_accesory)
    LinearLayout btnNextAccessory;
    @BindView(R.id.lineat_back_accesory)
    LinearLayout btnBackAccessory;

    int cont;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @BindView(R.id.linearLayout3)
    LinearLayout linearLayout3;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.textView8)
    LinearLayout textView8;
    @BindView(R.id.textView10)
    LinearLayout textView10;
    @BindView(R.id.imageView)
    ImageView imageView;


    @BindView(R.id.textView15)
    TextView textView15;
    @BindView(R.id.textView16)
    TextView textView16;

    @BindView(R.id.tv_total_a_pagar_acc)
    TextView totalCredito;

    @BindView(R.id.tv_cuota_mensual_acc)
    TextView cuotaCredito;

    @BindView(R.id.fragment_accesorio_relacionado)
    RelativeLayout fragmentAccesorioRelacionado;
    @BindView(R.id.tv_precio_venta_acc)
    TextView tvPrecioVentaAcc;
    @BindView(R.id.blur_content_acc)
    RealtimeBlurView blurContentAcc;
    @BindView(R.id.tv_provider_name_acc)
    TextView tvProviderNameAcc;
    @BindView(R.id.tv_name_accessory)
    TextView tvNameAccessory;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.content_precios)
    ConstraintLayout contentPrecios;
    @BindView(R.id.image_accented_accessory)
    SimpleDraweeView imageAccentedAccessory;
    @BindView(R.id.rv_caracteristicas)
    RecyclerView rvCaracteristicas;
    @BindView(R.id.textView27)
    TextView textView27;
    @BindView(R.id.textView19)
    TextView textView19;
    @BindView(R.id.textView20)
    TextView textView20;
    @BindView(R.id.textView18)
    TextView textView18;
    @BindView(R.id.content_descuento)
    ConstraintLayout contentDescuento;
    @BindView(R.id.content_main)
    ConstraintLayout contentMain;
    @BindView(R.id.button_close_fragment)
    LinearLayout buttonCloseFragment;

    ArrayList<String> caracteristicasList;
    LinearLayoutManager linearLayoutManagerCaract;
    ArrayList<String> listColores;
    ColorAdapter colorAdapter;
    private static final String TAG = "FichaEquipo";

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
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
        Point size = new Point();
        Display display = ((WindowManager) getSystemService( Context.WINDOW_SERVICE )).getDefaultDisplay();
        display.getSize( size );
        int width = size.x;

        Log.i( TAG, "onCreate: " + width );
        if ( width > 1300 )
        {

            setContentView( R.layout.activity_ficha_equipo_mx );
        } else
        {
            setContentView( R.layout.activity_ficha_equipo );
        }
        ButterKnife.bind( this );


        listHijos = new ArrayList<>();
        accesoriosCompatibles = new ArrayList<>();

        linearLayoutManagerCL = new LinearLayoutManager( this );
        linearLayoutManagerCL.setOrientation( LinearLayoutManager.HORIZONTAL );
        rvColor.setHasFixedSize( true );
        rvColor.setLayoutManager( linearLayoutManagerCL );

        timerInactivity = new TimerInactivity( 180000, 1000, this );
        timerInactivity.start();

        listColores = new ArrayList<>();
        tvProviderName.setText( Global.producto.getProvider_name() );
        tvNameDevice.setText( Global.producto.getName() );
        imageDevice.setImageURI( Uri.fromFile( new File( Global.dirImages + Global.producto.getImagenPrimaria() ) ) );


        tvPrecioVenta.setText( getString( R.string.precio_venta, Global.producto.getPrecioVenta() ) );


        tvScreenSize.setText( Global.producto.getPantalla() );
        tvBackCamera.setText( Global.producto.getCamaraTrasera() );
        tvFrontCamera.setText( Global.producto.getCamaraFrontal() );

        if ( Global.producto.getColores().length() > 0 )
        {
            listColores.clear();
            String[] items = Global.producto.getColores().split( "," );
            Collections.addAll( listColores, items );
            colorAdapter = new ColorAdapter( listColores, FichaEquipo.this );

        } else
        {
            rvColor.setVisibility( View.INVISIBLE );
            llColores.setVisibility( View.INVISIBLE );
        }


        final Handler handler = new Handler();
        handler.postDelayed( new Runnable()
        {
            @Override
            public void run()
            {
                rvColor.setAdapter( colorAdapter );
                setAnimationRecycler( rvColor );
            }
        }, 700 );

        linearLayoutManagerGB = new LinearLayoutManager( this );
        linearLayoutManagerGB.setOrientation( LinearLayoutManager.HORIZONTAL );
        rvAlmacenamiento.setHasFixedSize( true );
        rvAlmacenamiento.setLayoutManager( linearLayoutManagerGB );

        if ( Global.producto.getHijos() != null )
        {
            for ( int i = 0; i < Global.producto.getHijos().size(); i++ )
            {
                listHijos.add( Global.producto.getHijos().get( i ) );
            }

            rvAlmacenamiento.setAdapter( new AlmacenamientoEquipoAdapter( this, listHijos ) );
            rvAlmacenamiento.setVisibility( View.VISIBLE );
        } else
        {
            rvAlmacenamiento.setVisibility( View.INVISIBLE );
        }

        //TODO ACCESORIOS RELACIONADOS

        Log.i( TAG, Global.producto.getAccesorios().size() + "" );
        for ( int r = 0; r < Global.producto.getAccesorios().size(); r++ )
        {

            for ( Producto producto : Session.objData.getAccesorios() )
            {
                if ( producto.getId().equals( Global.producto.getAccesorios().get( r ).getId() ) )
                {
                    accesoriosCompatibles.add( producto );
                }
            }
        }


        viewPagerCarruselAdapter = new ViewPagerCarruselAdapter( getSupportFragmentManager(), accesoriosCompatibles, 0 );

        vpCarrusel.setAdapter( viewPagerCarruselAdapter );


        new CoverFlow.Builder()
                .with( vpCarrusel )
                .pagerMargin( -20f )
                .scale( 0.4f )
                .spaceSize( 0f )
                .rotationY( 0f )
                .build();

        pagerContainer.setOverlapEnabled( true );

        btnBackAccessory.setBackground( getResources().getDrawable( R.drawable.bg_disabled ) );

        vpCarrusel.addOnPageChangeListener( new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled( int position, float positionOffset, int positionOffsetPixels )
            {

            }

            @Override
            public void onPageSelected( int position )
            {
                if ( position == 0 )
                {
                    btnBackAccessory.setBackground( getResources().getDrawable( R.drawable.bg_disabled ) );
                } else
                {
                    btnBackAccessory.setBackground( getResources().getDrawable( R.drawable.bg_type_filters_device ) );
                }
                if ( position == (accesoriosCompatibles.size() - 1) )
                {
                    btnNextAccessory.setBackground( getResources().getDrawable( R.drawable.bg_disabled ) );
                } else
                {
                    btnNextAccessory.setBackground( getResources().getDrawable( R.drawable.bg_type_filters_device ) );
                }
            }

            @Override
            public void onPageScrollStateChanged( int state )
            {

            }
        } );

        cont = 0;


        vpCarrusel.setOnTouchListener( new View.OnTouchListener()
        {
            @Override
            public boolean onTouch( View v, MotionEvent event )
            {
                vpCarrusel.setCurrentItem( vpCarrusel.getCurrentItem() );
                if ( cont == 0 )
                {

                    Producto pr = accesoriosCompatibles.get( vpCarrusel.getCurrentItem() );
                    Log.d( "POSITION", pr.getName() );

                    showAccessory( pr );
                    fragmentAccesorioRelacionado.setVisibility( View.VISIBLE );
                    cont++;
                }
                return true;
            }
        } );


    }

    @Override
    protected void onPause()
    {
        super.onPause();
        timerInactivity.cancel();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        timerInactivity.cancel();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        timerInactivity.start();
    }

    @Override
    public void onUserInteraction()
    {
        super.onUserInteraction();
        timerInactivity.cancel();
        timerInactivity.start();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        timerInactivity.cancel();
    }

    @OnClick({ R.id.button_volver_catalogo, R.id.button_llevatelo_con_un_plan, R.id.lineat_back_accesory, R.id.lineat_next_accesory })
    public void onClick( View view )
    {
        switch ( view.getId() )
        {
            case R.id.button_volver_catalogo:
                finish();
                break;
            case R.id.button_llevatelo_con_un_plan:
                startActivity( new Intent( FichaEquipo.this, EquipoConPlanActivity.class ) );
                break;
            case R.id.lineat_back_accesory:
                if ( vpCarrusel.getCurrentItem() > 0 )
                {
                    vpCarrusel.setCurrentItem( vpCarrusel.getCurrentItem() - 1 );
                }
                break;
            case R.id.lineat_next_accesory:
                vpCarrusel.setCurrentItem( vpCarrusel.getCurrentItem() + 1 );
                break;
        }
    }

    @Override
    protected void attachBaseContext( Context newBase )
    {
        super.attachBaseContext( new CalligraphyContextWrapper( newBase, R.attr.fontPath ) );
    }

    public void closeBlurAccessories()
    {
        fragmentAccesorioRelacionado.setVisibility( View.GONE );
        cont = 0;
    }

    private void setAnimationRecycler( final RecyclerView recyclerView )
    {
        recyclerView.getViewTreeObserver().addOnPreDrawListener( new ViewTreeObserver.OnPreDrawListener()
        {
            @Override
            public boolean onPreDraw()
            {
                recyclerView.getViewTreeObserver().removeOnPreDrawListener( this );

                for ( int i = 0; i < recyclerView.getChildCount(); i++ )
                {
                    View v = recyclerView.getChildAt( i );

                    v.setScaleY( 0 );
                    v.setAlpha( 0 );
                    v.setScaleX( 0 );

                    v.animate().alpha( 1.0f )
                            .setDuration( 250 )
                            .setStartDelay( i * 50 )
                            .scaleX( 1.0f )
                            .scaleY( 1.0f )
                            .start();
                }
                return true;
            }
        } );
    }


    private void showAccessory( final Producto pr )
    {
        caracteristicasList = new ArrayList<>();

        linearLayoutManagerCaract = new LinearLayoutManager( this );
        linearLayoutManagerCaract.setOrientation( LinearLayoutManager.VERTICAL );
        tvNameAccessory.setText( pr.getName() );
        tvProviderNameAcc.setText( pr.getProvider_name() );
        imageAccentedAccessory.setImageURI( Uri.fromFile( new File( Global.dirImages + pr.getImagenPrimaria() ) )
        );

        Log.i( "cuotaCredito", pr.getCuotaCredito() );
        Log.i( "creditoTotal", pr.getTotalCredito() );

        cuotaCredito.setText( "$" + pr.getCuotaCredito() );
        totalCredito.setText( "$" + pr.getTotalCredito() );
        tvPrecioVentaAcc.setText( getString( R.string.precio_venta, pr.getPrecioVenta() ) );
        rvCaracteristicas.setHasFixedSize( true );
        rvCaracteristicas.setLayoutManager( linearLayoutManagerCaract );

        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected Void doInBackground( Void... params )
            {
                caracteristicasList.add( pr.getAtributoUno() );
                caracteristicasList.add( pr.getAtributoDos() );
                caracteristicasList.add( pr.getAtributoTres() );

                return null;
            }

            @Override
            protected void onPostExecute( Void aVoid )
            {
                CaracteristicasDestacadoAdapter caracteristicasAdapter = new CaracteristicasDestacadoAdapter( caracteristicasList );
                rvCaracteristicas.setAdapter( caracteristicasAdapter );

                if ( pr.getId().equals( "15" ) )
                {
                    LinearLayout productoDestacado = (LinearLayout) findViewById( R.id.oferta_destacado );
                    productoDestacado.setVisibility( View.VISIBLE );
                } else
                {
                    LinearLayout productoDestacado = (LinearLayout) findViewById( R.id.oferta_destacado );
                    productoDestacado.setVisibility( View.GONE );
                }

            }
        }.execute();
    }

    @OnClick({ R.id.blur_content_acc, R.id.button_close_fragment })
    public void onViewClicked( View view )
    {
        switch ( view.getId() )
        {
            case R.id.blur_content_acc:
                closeBlurAccessories();
                break;
            case R.id.button_close_fragment:
                closeBlurAccessories();
                break;
        }
    }
}
