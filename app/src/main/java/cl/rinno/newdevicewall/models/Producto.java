package cl.rinno.newdevicewall.models;

import java.util.ArrayList;

/**
 * Created by chinodoge on 08-02-2017.
 */

public class Producto
{
    private String id;
    private String provider_id;
    private String product_type_id;
    private String product_type;
    private String name;
    private String parent;
    private String status;
    private String created_at;
    private String uploaded_at;
    private ArrayList<Detalle> detalles;
    private ArrayList<Precio> precios;
    private ArrayList<Producto> accesorios;
    private Relation relation;
    private ArrayList<Producto> planes;
    private ArrayList<Producto> hijos;
    private ArrayList<Producto> plans;
    private ArrayList<Highlight> highlight;
    private ArrayList<Producto> devices;
    private String provider_name;
    private String provider_image;
    private String sizes;
    private String imageHigh;
    private ArrayList<Detalle> cae;
    private String primaryImage;
    private String bannerImage;
    private String condicionImage;
    private String condicionImageHorizontal;
    private String equno;
    private String eqdos;
    private String eqtres;
    private String imagen_oferta;
    private String primaryImage_h;

    public Producto()
    {
    }

    public Producto( String id, String provider_id, String product_type_id, String product_type, String name, String parent, String status, String created_at, String uploaded_at, ArrayList<Detalle> detalles, ArrayList<Precio> precios, ArrayList<Producto> accesorios, Relation relation, ArrayList<Producto> planes, ArrayList<Producto> hijos, ArrayList<Producto> plans, ArrayList<Highlight> highlight, ArrayList<Producto> devices, String provider_name, String provider_image, String sizes, String imageHigh, ArrayList<Detalle> cae, String primaryImage, String bannerImage, String condicionImage, String condicionImageHorizontal, String equno, String eqdos, String eqtres, String imagen_oferta, String primaryImage_h )
    {
        this.id = id;
        this.provider_id = provider_id;
        this.product_type_id = product_type_id;
        this.product_type = product_type;
        this.name = name;
        this.parent = parent;
        this.status = status;
        this.created_at = created_at;
        this.uploaded_at = uploaded_at;
        this.detalles = detalles;
        this.precios = precios;
        this.accesorios = accesorios;
        this.relation = relation;
        this.planes = planes;
        this.hijos = hijos;
        this.plans = plans;
        this.highlight = highlight;
        this.devices = devices;
        this.provider_name = provider_name;
        this.provider_image = provider_image;
        this.sizes = sizes;
        this.imageHigh = imageHigh;
        this.cae = cae;
        this.primaryImage = primaryImage;
        this.bannerImage = bannerImage;
        this.condicionImage = condicionImage;
        this.condicionImageHorizontal = condicionImageHorizontal;
        this.equno = equno;
        this.eqdos = eqdos;
        this.eqtres = eqtres;
        this.imagen_oferta = imagen_oferta;
        this.primaryImage_h = primaryImage_h;
    }


    public String getEqtres()
    {
        return eqtres;
    }

    public void setEqtres( String eqtres )
    {
        this.eqtres = eqtres;
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

    public String getProduct_type_id()
    {
        return product_type_id;
    }

    public void setProduct_type_id( String product_type_id )
    {
        this.product_type_id = product_type_id;
    }

    public String getProduct_type()
    {
        return product_type;
    }

    public void setProduct_type( String product_type )
    {
        this.product_type = product_type;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getParent()
    {
        return parent;
    }

    public void setParent( String parent )
    {
        this.parent = parent;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus( String status )
    {
        this.status = status;
    }

    public String getCreated_at()
    {
        return created_at;
    }

    public void setCreated_at( String created_at )
    {
        this.created_at = created_at;
    }

    public String getUploaded_at()
    {
        return uploaded_at;
    }

    public void setUploaded_at( String uploaded_at )
    {
        this.uploaded_at = uploaded_at;
    }

    public ArrayList<Detalle> getDetalles()
    {
        return detalles;
    }

    public void setDetalles( ArrayList<Detalle> detalles )
    {
        this.detalles = detalles;
    }

    public ArrayList<Precio> getPrecios()
    {
        return precios;
    }

    public void setPrecios( ArrayList<Precio> precios )
    {
        this.precios = precios;
    }

    public ArrayList<Producto> getAccesorios()
    {
        return accesorios;
    }

    public void setAccesorios( ArrayList<Producto> accesorios )
    {
        this.accesorios = accesorios;
    }

    public Relation getRelation()
    {
        return relation;
    }

    public void setRelation( Relation relation )
    {
        this.relation = relation;
    }

    public ArrayList<Producto> getPlanes()
    {
        return planes;
    }

    public void setPlanes( ArrayList<Producto> planes )
    {
        this.planes = planes;
    }

    public ArrayList<Producto> getHijos()
    {
        return hijos;
    }

    public void setHijos( ArrayList<Producto> hijos )
    {
        this.hijos = hijos;
    }

    public ArrayList<Producto> getPlans()
    {
        return plans;
    }

    public void setPlans( ArrayList<Producto> plans )
    {
        this.plans = plans;
    }

    public ArrayList<Highlight> getHighlight()
    {
        return highlight;
    }

    public void setHighlight( ArrayList<Highlight> highlight )
    {
        this.highlight = highlight;
    }

    public ArrayList<Producto> getDevices()
    {
        return devices;
    }

    public void setDevices( ArrayList<Producto> devices )
    {
        this.devices = devices;
    }

    public String getProvider_name()
    {
        return provider_name;
    }

    public void setProvider_name( String provider_name )
    {
        this.provider_name = provider_name;
    }

    public String getProvider_image()
    {
        return provider_image;
    }

    public void setProvider_image( String provider_image )
    {
        this.provider_image = provider_image;
    }

    public String getSizes()
    {
        return sizes;
    }

    public void setSizes( String sizes )
    {
        this.sizes = sizes;
    }

    public String getImageHigh()
    {
        return imageHigh;
    }

    public void setImageHigh( String imageHigh )
    {
        this.imageHigh = imageHigh;
    }

    public ArrayList<Detalle> getCae()
    {
        return cae;
    }

    public void setCae( ArrayList<Detalle> cae )
    {
        this.cae = cae;
    }

    public String getPrimaryImage()
    {
        return primaryImage;
    }

    public void setPrimaryImage( String primaryImage )
    {
        this.primaryImage = primaryImage;
    }

    public String getBannerImage()
    {
        return bannerImage;
    }

    public void setBannerImage( String bannerImage )
    {
        this.bannerImage = bannerImage;
    }

    public String getCondicionImage()
    {
        return condicionImage;
    }

    public void setCondicionImage( String condicionImage )
    {
        this.condicionImage = condicionImage;
    }

    public String getCondicionImageHorizontal()
    {
        return condicionImageHorizontal;
    }

    public void setCondicionImageHorizontal( String condicionImageHorizontal )
    {
        this.condicionImageHorizontal = condicionImageHorizontal;
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

    public String getImagen_oferta()
    {
        return imagen_oferta;
    }

    public void setImagen_oferta( String imagen_oferta )
    {
        this.imagen_oferta = imagen_oferta;
    }

    public String getPrimaryImage_h()
    {
        return primaryImage_h;
    }

    public void setPrimaryImage_h( String primaryImage_h )
    {
        this.primaryImage_h = primaryImage_h;
    }

    @Override
    public String toString()
    {
        return "Producto{" +
                "id='" + id + '\'' +
                ", provider_id='" + provider_id + '\'' +
                ", product_type_id='" + product_type_id + '\'' +
                ", product_type='" + product_type + '\'' +
                ", name='" + name + '\'' +
                ", parent='" + parent + '\'' +
                ", status='" + status + '\'' +
                ", created_at='" + created_at + '\'' +
                ", uploaded_at='" + uploaded_at + '\'' +
                ", detalles=" + detalles +
                ", precios=" + precios +
                ", accesorios=" + accesorios +
                ", relation=" + relation +
                ", planes=" + planes +
                ", hijos=" + hijos +
                ", plans=" + plans +
                ", highlight=" + highlight +
                ", devices=" + devices +
                ", provider_name='" + provider_name + '\'' +
                ", provider_image='" + provider_image + '\'' +
                ", sizes='" + sizes + '\'' +
                ", imageHigh='" + imageHigh + '\'' +
                ", cae=" + cae +
                ", primaryImage='" + primaryImage + '\'' +
                ", bannerImage='" + bannerImage + '\'' +
                ", condicionImage='" + condicionImage + '\'' +
                ", condicionImageHorizontal='" + condicionImageHorizontal + '\'' +
                ", equno='" + equno + '\'' +
                ", eqdos='" + eqdos + '\'' +
                ", imagen_oferta='" + imagen_oferta + '\'' +
                ", primaryImage_h='" + primaryImage_h + '\'' +
                '}';
    }
}
