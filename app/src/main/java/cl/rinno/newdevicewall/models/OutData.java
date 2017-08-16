package cl.rinno.newdevicewall.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chinodoge on 08-02-2017.
 */

public class OutData
{

    private Catalogo catalogo;
    private List<Producto> productos;
    private List<Producto> accesorios;
    private List<Provider> proveedores;
    private ArrayList<Producto> grupos;
    private ArrayList<String> imagenes;


    public OutData( Catalogo catalogo, List<Producto> productos, List<Producto> accesorios, List<Provider> proveedores, ArrayList<Producto> grupos, ArrayList<String> imagenes )
    {
        this.catalogo = catalogo;
        this.productos = productos;
        this.accesorios = accesorios;
        this.proveedores = proveedores;
        this.grupos = grupos;
        this.imagenes = imagenes;
    }

    public Catalogo getCatalogo()
    {
        return catalogo;
    }

    public void setCatalogo( Catalogo catalogo )
    {
        this.catalogo = catalogo;
    }

    public List<Producto> getProductos()
    {
        return productos;
    }

    public void setProductos( List<Producto> productos )
    {
        this.productos = productos;
    }

    public List<Producto> getAccesorios()
    {
        return accesorios;
    }

    public void setAccesorios( List<Producto> accesorios )
    {
        this.accesorios = accesorios;
    }

    public List<Provider> getProveedores()
    {
        return proveedores;
    }

    public void setProveedores( List<Provider> proveedores )
    {
        this.proveedores = proveedores;
    }

    public ArrayList<Producto> getGrupos()
    {
        return grupos;
    }

    public void setGrupos( ArrayList<Producto> grupos )
    {
        this.grupos = grupos;
    }

    public ArrayList<String> getImagenes()
    {
        return imagenes;
    }

    public void setImagenes( ArrayList<String> imagenes )
    {
        this.imagenes = imagenes;
    }
}
