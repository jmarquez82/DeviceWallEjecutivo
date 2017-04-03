package cl.rinno.newdevicewall.models;

import java.util.ArrayList;


public class Catalogo {
    private String id;
    private String name;
    private String status;
    private String created_at;
    private String uploaded_at;
    private String screenOneId;
    private String screenOneImage;
    private String screenTwoId;
    private String screenTwoImage;
    private String screenThreeId;
    private String screenThreeImage;
    private String screenFourId;
    private String screenFourImage;
    private ArrayList<Producto> ofertas;

    public Catalogo(String id, String name, String status, String created_at, String uploaded_at, String screenOneId, String screenOneImage, String screenTwoId, String screenTwoImage, String screenThreeId, String screenThreeImage, String screenFourId, String screenFourImage, ArrayList<Producto> ofertas) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.created_at = created_at;
        this.uploaded_at = uploaded_at;
        this.screenOneId = screenOneId;
        this.screenOneImage = screenOneImage;
        this.screenTwoId = screenTwoId;
        this.screenTwoImage = screenTwoImage;
        this.screenThreeId = screenThreeId;
        this.screenThreeImage = screenThreeImage;
        this.screenFourId = screenFourId;
        this.screenFourImage = screenFourImage;
        this.ofertas = ofertas;
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

    public String getScreenOneId() {
        return screenOneId;
    }

    public void setScreenOneId(String screenOneId) {
        this.screenOneId = screenOneId;
    }

    public String getScreenOneImage() {
        return screenOneImage;
    }

    public void setScreenOneImage(String screenOneImage) {
        this.screenOneImage = screenOneImage;
    }

    public String getScreenTwoId() {
        return screenTwoId;
    }

    public void setScreenTwoId(String screenTwoId) {
        this.screenTwoId = screenTwoId;
    }

    public String getScreenTwoImage() {
        return screenTwoImage;
    }

    public void setScreenTwoImage(String screenTwoImage) {
        this.screenTwoImage = screenTwoImage;
    }

    public String getScreenThreeId() {
        return screenThreeId;
    }

    public void setScreenThreeId(String screenThreeId) {
        this.screenThreeId = screenThreeId;
    }

    public String getScreenThreeImage() {
        return screenThreeImage;
    }

    public void setScreenThreeImage(String screenThreeImage) {
        this.screenThreeImage = screenThreeImage;
    }

    public String getScreenFourId() {
        return screenFourId;
    }

    public void setScreenFourId(String screenFourId) {
        this.screenFourId = screenFourId;
    }

    public String getScreenFourImage() {
        return screenFourImage;
    }

    public void setScreenFourImage(String screenFourImage) {
        this.screenFourImage = screenFourImage;
    }

    public ArrayList<Producto> getOfertas() {
        return ofertas;
    }

    public void setOfertas(ArrayList<Producto> ofertas) {
        this.ofertas = ofertas;
    }
}
