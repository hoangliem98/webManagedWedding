/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baitaplon.bean;

import com.baitaplon.pojo.Hall;
import com.baitaplon.service.HallService;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

/**
 *
 * @author Liem Nemo
 */
@ManagedBean
@Named(value = "hallBean")
@RequestScoped
public class HallBean {

    private int haId;
    private String name;
    private String description;
    private String image_name;
    private Part image;
    private int minimum_tables;
    private int maximum_tables;
    private BigDecimal price;
    private static HallService hallService = new HallService();

    private String searchkey;

    /**
     * Creates a new instance of HallBean
     */
    public HallBean() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            String hallId = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getRequestParameterMap().get("hall_id");
            if (hallId != null && !hallId.isEmpty()) {
                Hall h = hallService.getHallById(Integer.parseInt(hallId));
                this.haId = h.getId();
                this.name = h.getName();
                this.description = h.getDescription();
                this.image_name = h.getImage();
                this.minimum_tables = h.getMinimum_tables();
                this.maximum_tables = h.getMaximum_tables();
                this.price = h.getPrice();
            }
        }
    }

    public List<Hall> getHalls() {

        String key = (String) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .get("search_key");
        
        if (key != null) {
            List<Hall> list = hallService.getHalls();
            List<Hall> result = new ArrayList<>();

            for (Hall hall : list) {
                if (hall.getName().toUpperCase().contains(key.toUpperCase())
                        || hall.getName().toUpperCase().contains(key.toUpperCase())
                        || hall.getDescription().toUpperCase().contains(key.toUpperCase())) {
                    result.add(hall);
                }
            }

            return result;
        }

        return hallService.getHalls();
    }

    public String searchHalls() {
        if (searchkey == null) {
            searchkey = "";
        }
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .put("search_key", searchkey);

        return "hall?faces-redirect=true";

    }

    public String addHall() {
//        String hallId = FacesContext.getCurrentInstance()
//                    .getExternalContext().
//                    getRequestParameterMap().get("hall_id");
        Hall h;
        if (this.haId > 0) {
            h = hallService.getHallById(this.haId);
        } else {
            h = new Hall();
        }
        h.setName(this.name);
        h.setDescription(this.description);
        h.setMinimum_tables(this.minimum_tables);
        h.setMaximum_tables(this.maximum_tables);
        h.setPrice(this.price);

        try {
            if (this.image != null) {
                this.uploadFile();
                h.setImage(this.image.getSubmittedFileName());
            }
            if (hallService.addOrSaveHall(h) == true) {
                return "hall-list?faces-redirect=true";
            }
        } catch (IOException ex) {
            Logger.getLogger(HallBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "hall";
    }

    public String deleteHall(Hall h) throws Exception {
        if (hallService.deleteHall(h)) {
            return "successful";
        }

        throw new Exception("Something wrong!!!");
    }

    private void uploadFile() throws IOException {
//        Random rd = new Random();
//        String imgUpload = rd.nextInt() + "_" + this.image.getSubmittedFileName();
        String path = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getInitParameter("uploadPath") + "halls/" + this.image.getSubmittedFileName();
        try ( InputStream in = this.image.getInputStream();  FileOutputStream out = new FileOutputStream(path)) {
            byte[] b = new byte[1024];
            int byteRead;
            while ((byteRead = in.read(b)) != -1) {
                out.write(b, 0, byteRead);
            }
        }
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the minimum_tables
     */
    public int getMinimum_tables() {
        return minimum_tables;
    }

    /**
     * @param minimum_tables the minimum_tables to set
     */
    public void setMinimum_tables(int minimum_tables) {
        this.minimum_tables = minimum_tables;
    }

    /**
     * @return the maximum_tables
     */
    public int getMaximum_tables() {
        return maximum_tables;
    }

    /**
     * @param maximum_tables the maximum_tables to set
     */
    public void setMaximum_tables(int maximum_tables) {
        this.maximum_tables = maximum_tables;
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the image
     */
    public Part getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(Part image) {
        this.image = image;
    }

    /**
     * @return the haId
     */
    public int getHaId() {
        return haId;
    }

    /**
     * @param haId the haId to set
     */
    public void setHaId(int haId) {
        this.haId = haId;
    }

    /**
     * @return the image_name
     */
    public String getImage_name() {
        return image_name;
    }

    /**
     * @param image_name the image_name to set
     */
    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    /**
     * @return the searchkey
     */
    public String getSearchkey() {
        return searchkey;
    }

    /**
     * @param searchkey the searchkey to set
     */
    public void setSearchkey(String searchkey) {
        this.searchkey = searchkey;
    }
}
