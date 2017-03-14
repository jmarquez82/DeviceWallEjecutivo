package cl.rinno.newdevicewall.models;

/**
 * Created by chinodoge on 08-02-2017.
 */

public class Provider {
    private String id;
    private String name;
    private String provider_image;

    public Provider(String id, String name, String provider_image) {
        this.id = id;
        this.name = name;
        this.provider_image = provider_image;
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

    public String getProvider_image() {
        return provider_image;
    }

    public void setProvider_image(String provider_image) {
        this.provider_image = provider_image;
    }


}
