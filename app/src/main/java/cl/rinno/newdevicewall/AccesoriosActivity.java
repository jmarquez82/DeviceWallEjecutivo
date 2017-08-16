package cl.rinno.newdevicewall;

import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.github.mmin18.widget.RealtimeBlurView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cl.rinno.newdevicewall.adapters.CaracteristicasDestacadoAdapter;
import cl.rinno.newdevicewall.cls.NonSwipeableViewPager;
import cl.rinno.newdevicewall.cls.TimerInactivity;
import cl.rinno.newdevicewall.cls.ViewPagerCarruselAdapter;
import cl.rinno.newdevicewall.models.Global;
import cl.rinno.newdevicewall.models.Producto;
import cl.rinno.newdevicewall.models.Session;
import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.PagerContainer;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AccesoriosActivity extends AppCompatActivity
{

    ArrayList<String> caracteristicasList;
    LinearLayoutManager linearLayoutManagerCaract;


    @BindView(R.id.blur_content)
    RealtimeBlurView blurContent;
    @BindView(R.id.tv_provider_name)
    TextView tvProviderName;
    @BindView(R.id.tv_name_accessory)
    TextView tvNameAccessory;
    @BindView(R.id.image_accented_accessory)
    SimpleDraweeView imageAccentedAccessory;
    @BindView(R.id.rv_caracteristicas)
    RecyclerView rvCaracteristicas;
    @BindView(R.id.button_close_fragment)
    LinearLayout buttonCloseFragment;
    @BindView(R.id.tv_total_a_pagar)
    TextView tvTotalAPagar;
    @BindView(R.id.tv_cae)
    TextView tvCae;
    @BindView(R.id.tv_cuota_mensual)
    TextView tvCuotaMensual;
    @BindView(R.id.tv_precio_venta)
    TextView tvPrecioVenta;
    @BindView(R.id.content_precios)
    ConstraintLayout contentPrecios;
    @BindView(R.id.content_descuento)
    ConstraintLayout contentDescuento;
    @BindView(R.id.content_main)
    ConstraintLayout contentMain;
    @BindView(R.id.textView23)
    TextView textView23;
    @BindView(R.id.textView25)
    TextView textView25;
    @BindView(R.id.textView21)
    TextView textView21;
    @BindView(R.id.textView11)
    TextView textView11;
    @BindView(R.id.textView24)
    TextView textView24;
    @BindView(R.id.linearLayout4)
    LinearLayout linearLayout4;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.textView27)
    TextView textView27;
    @BindView(R.id.textView19)
    TextView textView19;
    @BindView(R.id.textView20)
    TextView textView20;
    @BindView(R.id.textView18)
    TextView textView18;
    @BindView(R.id.textView13)
    TextView textView13;
    @BindView(R.id.vp_carrusel)
    NonSwipeableViewPager vpCarrusel;
    @BindView(R.id.pager_container)
    PagerContainer pagerContainer;
    @BindView(R.id.lineat_back_accesory)
    LinearLayout btnBackAccesory;
    @BindView(R.id.lineat_next_accesory)
    LinearLayout lineatNextAccesory;

    ViewPagerCarruselAdapter viewPagerCarruselAdapter;
    ArrayList<Producto> equiposCompatibles;

    TimerInactivity timerInactivity;


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

        if ( width > 1300 )
        {
            setContentView( R.layout.activity_accesorios_mx );
        } else
        {
            setContentView( R.layout.activity_accesorios );
        }
        ButterKnife.bind( this );
        timerInactivity = new TimerInactivity( 120000, 1000, this );
        timerInactivity.start();
        caracteristicasList = new ArrayList<>();
        equiposCompatibles = new ArrayList<>();
        linearLayoutManagerCaract = new LinearLayoutManager( this );
        linearLayoutManagerCaract.setOrientation( LinearLayoutManager.VERTICAL );
        tvNameAccessory.setText( Global.accesorio.getName() );

        tvTotalAPagar.setText( "$" + Global.accesorio.getTotalCredito() );
        tvCuotaMensual.setText( "$" + Global.accesorio.getCuotaCredito() );
        //TODO AGREGAR PROVIDER NAME
        tvProviderName.setText( Global.accesorio.getProvider_name() );
        imageAccentedAccessory.setImageURI( Uri.fromFile( new File( Global.dirImages + Global.accesorio.getImagenPrimaria() ) ) );


        tvPrecioVenta.setText( getString( R.string.precio_venta, Global.accesorio.getPrecioVenta() ) );
        rvCaracteristicas.setHasFixedSize( true );
        rvCaracteristicas.setLayoutManager( linearLayoutManagerCaract );
        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected Void doInBackground( Void... params )
            {

                caracteristicasList.add( Global.accesorio.getAtributoUno() );
                caracteristicasList.add( Global.accesorio.getAtributoDos() );
                caracteristicasList.add( Global.accesorio.getAtributoTres() );

                for ( int i = 0; i < Session.objData.getProductos().size(); i++ )
                {
                    for ( Producto producto : Session.objData.getProductos().get( i ).getAccesorios() )
                    {
                        if ( producto.getId().equals( Global.accesorio.getId() ) )
                        {
                            equiposCompatibles.add( Session.objData.getProductos().get( i ) );
                            break;
                        }
                    }

                }

                return null;
            }

            @Override
            protected void onPostExecute( Void aVoid )
            {
                CaracteristicasDestacadoAdapter caracteristicasAdapter = new CaracteristicasDestacadoAdapter( caracteristicasList );
                rvCaracteristicas.setAdapter( caracteristicasAdapter );
                if ( equiposCompatibles.size() > 2 )
                {
                    btnBackAccesory.setVisibility( View.VISIBLE );
                    btnBackAccesory.setBackground( getResources().getDrawable( R.drawable.bg_disabled ) );
                    lineatNextAccesory.setVisibility( View.VISIBLE );
                } else
                {
                    btnBackAccesory.setVisibility( View.GONE );
                    lineatNextAccesory.setVisibility( View.GONE );
                }
                viewPagerCarruselAdapter = new ViewPagerCarruselAdapter( getSupportFragmentManager(), equiposCompatibles, 1 );
                vpCarrusel.setAdapter( viewPagerCarruselAdapter );

            }
        }.execute();

        new CoverFlow.Builder()
                .with( vpCarrusel )
                .pagerMargin( 0f )
                .scale( 0.4f )
                .spaceSize( 0f )
                .rotationY( 0f )
                .build();
        pagerContainer.setOverlapEnabled( true );


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
                    btnBackAccesory.setBackground( getResources().getDrawable( R.drawable.bg_disabled ) );
                } else
                {
                    btnBackAccesory.setBackground( getResources().getDrawable( R.drawable.bg_circle_orange ) );
                }
                if ( position == (equiposCompatibles.size() - 1) )
                {
                    lineatNextAccesory.setBackground( getResources().getDrawable( R.drawable.bg_disabled ) );
                } else
                {
                    lineatNextAccesory.setBackground( getResources().getDrawable( R.drawable.bg_circle_orange ) );
                }
            }

            @Override
            public void onPageScrollStateChanged( int state )
            {

            }
        } );

        if ( Global.accesorio.getId().equals( "15" ) )
        {
            LinearLayout ofertaDestacada = (LinearLayout) findViewById( R.id.oferta_destacado );
            ofertaDestacada.setVisibility( View.VISIBLE );
        } else
        {
            LinearLayout ofertaDestacada = (LinearLayout) findViewById( R.id.oferta_destacado );
            ofertaDestacada.setVisibility( View.GONE );

        }
    }

    @OnClick({ R.id.blur_content, R.id.button_close_fragment, R.id.content_precios, R.id.content_descuento, R.id.content_main, R.id.lineat_next_accesory, R.id.lineat_back_accesory })
    public void onClick( View view )
    {
        switch ( view.getId() )
        {
            case R.id.blur_content:
                finish();
                break;
            case R.id.button_close_fragment:
                finish();
                break;
            case R.id.content_precios:
                break;
            case R.id.content_descuento:
                break;
            case R.id.content_main:
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
    protected void onDestroy()
    {
        super.onDestroy();
        timerInactivity.cancel();
    }

    @Override
    public void onUserInteraction()
    {
        super.onUserInteraction();
        timerInactivity.cancel();
        timerInactivity.start();
    }

    @Override
    protected void attachBaseContext( Context newBase )
    {
        super.attachBaseContext( new CalligraphyContextWrapper( newBase, R.attr.fontPath ) );
    }
}
