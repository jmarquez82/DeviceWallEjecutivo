package cl.rinno.newdevicewall.models;

/**
 * Created by chinodoge on 09-02-2017.
 */

public class Highlight {
    private String id;
    private String product_id;
    private String catalog_id;
    private String key;
    private String value;
    private String title;
    private String status;
    private String created_at;
    private String upload_at;

    public Highlight(String id, String product_id, String catalog_id, String key, String value, String title, String status, String created_at, String upload_at) {
        this.id = id;
        this.product_id = product_id;
        this.catalog_id = catalog_id;
        this.key = key;
        this.value = value;
        this.title = title;
        this.status = status;
        this.created_at = created_at;
        this.upload_at = upload_at;
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

    public String getCatalog_id() {
        return catalog_id;
    }

    public void setCatalog_id(String catalog_id) {
        this.catalog_id = catalog_id;
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

    public String getUpload_at() {
        return upload_at;
    }

    public void setUpload_at(String upload_at) {
        this.upload_at = upload_at;
    }
}