package cl.rinno.newdevicewall.models;

import java.util.List;

/**
 * Created by chinodoge on 08-02-2017.
 */

public class OutData {

    private Catalogo catalog;
    private List<Producto> destacados;
    private List<Producto> devices;
    private List<Producto> accessories;
    private List<Producto> planes;
    private List<Provider> providers;

    public OutData(Catalogo catalog, List<Producto> destacados, List<Producto> devices, List<Producto> accesories, List<Producto> planes, List<Provider> providers) {
        this.catalog = catalog;
        this.destacados = destacados;
        this.devices = devices;
        this.accessories = accesories;
        this.planes = planes;
        this.providers = providers;
    }

    public Catalogo getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalogo catalog) {
        this.catalog = catalog;
    }

    public List<Producto> getDestacados() {
        return destacados;
    }

    public void setDestacados(List<Producto> destacados) {
        this.destacados = destacados;
    }

    public List<Producto> getDevices() {
        return devices;
    }

    public void setDevices(List<Producto> devices) {
        this.devices = devices;
    }

    public List<Producto> getAccessories() {
        return accessories;
    }

    public void setAccessories(List<Producto> accesories) {
        this.accessories = accesories;
    }

    public List<Producto> getPlanes() {
        return planes;
    }

    public void setPlanes(List<Producto> planes) {
        this.planes = planes;
    }

    public List<Provider> getProviders() {
        return providers;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }
}
