package cl.rinno.newdevicewall.models;

/**
 * Created by chinodoge on 08-02-2017.
 */

public class Catalogo {
    private String id;
    private String name;
    private String status;
    private String  created_at;
    private String uploaded_at;

    public Catalogo(String id, String name, String status, String created_at, String uploaded_at) {
        this.id = id;
        this.name = name;
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
