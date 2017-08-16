package cl.rinno.newdevicewall.models;

import java.util.ArrayList;


public class Producto
{
    private String id;
    private String provider_id;
    private String provider_name;
    private String product_type_id;
    private String name;
    private String precioVenta;
    private String precioClienteEntel;
    private String tam;
    private String img;
    private String imagenPrimaria;
    private String procesador;
    private String pantalla;
    private String camaraFrontal;
    private String camaraTrasera;
    private String memoriaInterna;
    private String red;
    private String codigo;
    private String colores;
    private String PM;
    private String VC;

    private String atributoUno;
    private String atributoDos;
    private String atributoTres;

    private String bdg;
    private String cch;
    private String ccv;

    private String bannerPlan;
    private String imagenBase;
    private String equno;
    private String eqdos;
    private String eqtres;
    private String caid;

    private String cuotaCredito;
    private String totalCredito;

    private ArrayList<Producto> hijos;
    private ArrayList<Producto> accesorios;
    private ArrayList<Producto> planes;

    private int estado = 1;


    public Producto()
    {
    }

    public Producto( String id, String provider_id, String provider_name, String product_type_id, String name, String precioVenta, String precioClienteEntel, String tam, String img, String imagenPrimaria, String procesador, String pantalla, String camaraFrontal, String camaraTrasera, String memoriaInterna, String red, String codigo, String colores, String PM, String VC, String atributoUno, String atributoDos, String atributoTres, String bdg, String cch, String ccv, String bannerPlan, String imagenBase, String equno, String eqdos, String eqtres, String caid, String cuotaCredito, String totalCredito, ArrayList<Producto> hijos, ArrayList<Producto> accesorios, ArrayList<Producto> planes, int estado )
    {
        this.id = id;
        this.provider_id = provider_id;
        this.provider_name = provider_name;
        this.product_type_id = product_type_id;
        this.name = name;
        this.precioVenta = precioVenta;
        this.precioClienteEntel = precioClienteEntel;
        this.tam = tam;
        this.img = img;
        this.imagenPrimaria = imagenPrimaria;
        this.procesador = procesador;
        this.pantalla = pantalla;
        this.camaraFrontal = camaraFrontal;
        this.camaraTrasera = camaraTrasera;
        this.memoriaInterna = memoriaInterna;
        this.red = red;
        this.codigo = codigo;
        this.colores = colores;
        this.PM = PM;
        this.VC = VC;
        this.atributoUno = atributoUno;
        this.atributoDos = atributoDos;
        this.atributoTres = atributoTres;
        this.bdg = bdg;
        this.cch = cch;
        this.ccv = ccv;
        this.bannerPlan = bannerPlan;
        this.imagenBase = imagenBase;
        this.equno = equno;
        this.eqdos = eqdos;
        this.eqtres = eqtres;
        this.caid = caid;
        this.cuotaCredito = cuotaCredito;
        this.totalCredito = totalCredito;
        this.hijos = hijos;
        this.accesorios = accesorios;
        this.planes = planes;
        this.estado = estado;
    }


    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public String getProvider_id()
    {
        return provider_id;
    }

    public void setProvider_id( String provider_id )
    {
        this.provider_id = provider_id;
    }

    public String getProvider_name()
    {
        return provider_name;
    }

    public void setProvider_name( String provider_name )
    {
        this.provider_name = provider_name;
    }

    public String getProduct_type_id()
    {
        return product_type_id;
    }

    public void setProduct_type_id( String product_type_id )
    {
        this.product_type_id = product_type_id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getPrecioVenta()
    {
        return precioVenta;
    }

    public void setPrecioVenta( String precioVenta )
    {
        this.precioVenta = precioVenta;
    }

    public String getPrecioClienteEntel()
    {
        return precioClienteEntel;
    }

    public void setPrecioClienteEntel( String precioClienteEntel )
    {
        this.precioClienteEntel = precioClienteEntel;
    }

    public String getTam()
    {
        return tam;
    }

    public void setTam( String tam )
    {
        this.tam = tam;
    }

    public String getImg()
    {
        return img;
    }

    public void setImg( String img )
    {
        this.img = img;
    }

    public String getImagenPrimaria()
    {
        return imagenPrimaria;
    }

    public void setImagenPrimaria( String imagenPrimaria )
    {
        this.imagenPrimaria = imagenPrimaria;
    }

    public String getProcesador()
    {
        return procesador;
    }

    public void setProcesador( String procesador )
    {
        this.procesador = procesador;
    }

    public String getPantalla()
    {
        return pantalla;
    }

    public void setPantalla( String pantalla )
    {
        this.pantalla = pantalla;
    }

    public String getCamaraFrontal()
    {
        return camaraFrontal;
    }

    public void setCamaraFrontal( String camaraFrontal )
    {
        this.camaraFrontal = camaraFrontal;
    }

    public String getCamaraTrasera()
    {
        return camaraTrasera;
    }

    public void setCamaraTrasera( String camaraTrasera )
    {
        this.camaraTrasera = camaraTrasera;
    }

    public String getMemoriaInterna()
    {
        return memoriaInterna;
    }

    public void setMemoriaInterna( String memoriaInterna )
    {
        this.memoriaInterna = memoriaInterna;
    }

    public String getRed()
    {
        return red;
    }

    public void setRed( String red )
    {
        this.red = red;
    }

    public String getCodigo()
    {
        return codigo;
    }

    public void setCodigo( String codigo )
    {
        this.codigo = codigo;
    }

    public String getColores()
    {
        return colores;
    }

    public void setColores( String colores )
    {
        this.colores = colores;
    }

    public String getPM()
    {
        return PM;
    }

    public void setPM( String PM )
    {
        this.PM = PM;
    }

    public String getVC()
    {
        return VC;
    }

    public void setVC( String VC )
    {
        this.VC = VC;
    }

    public String getAtributoUno()
    {
        return atributoUno;
    }

    public void setAtributoUno( String atributoUno )
    {
        this.atributoUno = atributoUno;
    }

    public String getAtributoDos()
    {
        return atributoDos;
    }

    public void setAtributoDos( String atributoDos )
    {
        this.atributoDos = atributoDos;
    }

    public String getAtributoTres()
    {
        return atributoTres;
    }

    public void setAtributoTres( String atributoTres )
    {
        this.atributoTres = atributoTres;
    }

    public String getBdg()
    {
        return bdg;
    }

    public void setBdg( String bdg )
    {
        this.bdg = bdg;
    }

    public String getCch()
    {
        return cch;
    }

    public void setCch( String cch )
    {
        this.cch = cch;
    }

    public String getCcv()
    {
        return ccv;
    }

    public void setCcv( String ccv )
    {
        this.ccv = ccv;
    }

    public String getBannerPlan()
    {
        return bannerPlan;
    }

    public void setBannerPlan( String bannerPlan )
    {
        this.bannerPlan = bannerPlan;
    }

    public String getImagenBase()
    {
        return imagenBase;
    }

    public void setImagenBase( String imagenBase )
    {
        this.imagenBase = imagenBase;
    }

    public String getEquno()
    {
        return equno;
    }

    public void setEquno( String equno )
    {
        this.equno = equno;
    }

    public String getEqdos()
    {
        return eqdos;
    }

    public void setEqdos( String eqdos )
    {
        this.eqdos = eqdos;
    }

    public String getEqtres()
    {
        return eqtres;
    }

    public void setEqtres( String eqtres )
    {
        this.eqtres = eqtres;
    }

    public String getCaid()
    {
        return caid;
    }

    public void setCaid( String caid )
    {
        this.caid = caid;
    }

    public String getCuotaCredito()
    {
        return cuotaCredito;
    }

    public void setCuotaCredito( String cuotaCredito )
    {
        this.cuotaCredito = cuotaCredito;
    }

    public String getTotalCredito()
    {
        return totalCredito;
    }

    public void setTotalCredito( String totalCredito )
    {
        this.totalCredito = totalCredito;
    }

    public ArrayList<Producto> getHijos()
    {
        return hijos;
    }

    public void setHijos( ArrayList<Producto> hijos )
    {
        this.hijos = hijos;
    }

    public ArrayList<Producto> getAccesorios()
    {
        return accesorios;
    }

    public void setAccesorios( ArrayList<Producto> accesorios )
    {
        this.accesorios = accesorios;
    }

    public ArrayList<Producto> getPlanes()
    {
        return planes;
    }

    public void setPlanes( ArrayList<Producto> planes )
    {
        this.planes = planes;
    }

    public int getEstado()
    {
        return estado;
    }

    public void setEstado( int estado )
    {
        this.estado = estado;
    }
}
