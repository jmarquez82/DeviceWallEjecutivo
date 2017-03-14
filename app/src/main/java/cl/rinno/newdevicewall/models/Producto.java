package cl.rinno.newdevicewall.models;

import java.util.ArrayList;

/**
 * Created by chinodoge on 08-02-2017.
 */

public class Producto {
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

    public Producto(String id) {
        this.id = id;
    }

    public Producto(String id, String provider_id, String product_type_id, String product_type, String name, String parent, String status, String created_at, String uploaded_at, ArrayList<Detalle> detalles, ArrayList<Precio> precios, ArrayList<Producto> accesorios, Relation relation, ArrayList<Producto> planes, ArrayList<Producto> hijos, ArrayList<Producto> plans, ArrayList<Highlight> highlight, ArrayList<Producto> devices, String provider_name) {
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
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getProduct_type_id() {
        return product_type_id;
    }

    public void setProduct_type_id(String product_type_id) {
        this.product_type_id = product_type_id;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUploaded_at() {
        return uploaded_at;
    }

    public void setUploaded_at(String uploaded_at) {
        this.uploaded_at = uploaded_at;
    }

    public ArrayList<Detalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(ArrayList<Detalle> detalles) {
        this.detalles = detalles;
    }

    public ArrayList<Precio> getPrecios() {
        return precios;
    }

    public void setPrecios(ArrayList<Precio> precios) {
        this.precios = precios;
    }

    public ArrayList<Producto> getAccesorios() {
        return accesorios;
    }

    public void setAccesorios(ArrayList<Producto> accesorios) {
        this.accesorios = accesorios;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    public ArrayList<Producto> getPlanes() {
        return planes;
    }

    public void setPlanes(ArrayList<Producto> planes) {
        this.planes = planes;
    }

    public ArrayList<Producto> getHijos() {
        return hijos;
    }

    public void setHijos(ArrayList<Producto> hijos) {
        this.hijos = hijos;
    }

    public ArrayList<Producto> getPlans() {
        return plans;
    }

    public void setPlans(ArrayList<Producto> plans) {
        this.plans = plans;
    }

    public ArrayList<Highlight> getHighlight() {
        return highlight;
    }

    public void setHighlight(ArrayList<Highlight> highlight) {
        this.highlight = highlight;
    }

    public ArrayList<Producto> getDevices() {
        return devices;
    }

    public void setDevices(ArrayList<Producto> devices) {
        this.devices = devices;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }
}
