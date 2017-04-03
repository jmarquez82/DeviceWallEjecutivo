package cl.rinno.newdevicewall.models;

/**
 * Created by chinodoge on 08-02-2017.
 */

public class Provider {
    private String id;
    private String name;
    private String status;
    private String provider_image;
    private String product_type;

    public Provider(String id, String name, String status, String provider_image, String product_type) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.provider_image = provider_image;
        this.product_type = product_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProvider_image() {
        return provider_image;
    }

    public void setProvider_image(String provider_image) {
        this.provider_image = provider_image;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }
}
