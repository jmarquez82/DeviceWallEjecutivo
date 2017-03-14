package cl.rinno.newdevicewall.models;

/**
 * Created by chinodoge on 08-02-2017.
 */

public class Relation {
    private String id;
    private String product_master_id;
    private String product_slave_id;
    private String catalog_id;
    private String relation_type;
    private String key;
    private String value;
    private String title;
    private String status;
    private String created_at;
    private String uploaded_at;

    public Relation(String id, String product_master_id, String product_slave_id, String catalog_id, String relation_type, String key, String value, String title, String status, String created_at, String uploaded_at) {
        this.id = id;
        this.product_master_id = product_master_id;
        this.product_slave_id = product_slave_id;
        this.catalog_id = catalog_id;
        this.relation_type = relation_type;
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

    public String getProduct_master_id() {
        return product_master_id;
    }

    public void setProduct_master_id(String product_master_id) {
        this.product_master_id = product_master_id;
    }

    public String getProduct_slave_id() {
        return product_slave_id;
    }

    public void setProduct_slave_id(String product_slave_id) {
        this.product_slave_id = product_slave_id;
    }

    public String getCatalog_id() {
        return catalog_id;
    }

    public void setCatalog_id(String catalog_id) {
        this.catalog_id = catalog_id;
    }

    public String getRelation_type() {
        return relation_type;
    }

    public void setRelation_type(String relation_type) {
        this.relation_type = relation_type;
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
