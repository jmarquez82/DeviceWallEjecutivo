package cl.rinno.newdevicewall.models;

/**
 * Created by chinodoge on 08-02-2017.
 */

public class Detalle {
    private String id;
    private String product_id;
    private String key;
    private String value;
    private String title;
    private String status;
    private String created_at;
    private String uploaded_at;

    public Detalle(String id, String product_id, String key, String value, String title, String status, String created_at, String uploaded_at) {
        this.id = id;
        this.product_id = product_id;
        this.key = key;
        this.value = value;
        this.title = title;
        this.status = status;
        this.created_at = created_at;
        this.uploaded_at = uploaded_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
