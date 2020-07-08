/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baitaplon.bean;
        
import com.baitaplon.pojo.Service;
import com.baitaplon.service.SvService;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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
 * @author Admin
 */

@ManagedBean
@Named(value = "serviceBean")
@RequestScoped
public class ServiceBean {

    /**
     * @return the svId
     */
    
    private int svId;
    private String name;
    private String image_name;
    private Part image;
    private String description;
    private BigDecimal price;
     private final static SvService svService = new SvService();
    /**
     * Creates a new instance of HallBean
     */
    public ServiceBean() {
        if(!FacesContext.getCurrentInstance().isPostback()){
            String serviceId = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getRequestParameterMap().get("service_id");
            if(serviceId != null && !serviceId.isEmpty()){
                Service sv = svService.getServiceById(Integer.parseInt(serviceId));
                this.svId = sv.getId();
                this.name = sv.getName();
                this.image_name = sv.getImage();
                this.description = sv.getDescription();
                this.price = sv.getPrice();
            }
        }
    }
    public List<Service> getServices() {
        return svService.getServices();
    }
    
    public String addService(){
        Service sv;
        if(this.svId > 0){
            sv = svService.getServiceById(this.svId);
        }
        else{
            sv = new Service();
        }
        sv.setName(this.name);
        sv.setDescription(this.description);
        sv.setPrice(this.price);
        
        try {
            if(this.image != null){
                this.uploadFile();
                sv.setImage(this.image.getSubmittedFileName());
            }
            if(svService.addOrSaveSv(sv) == true)
                return "service-list?faces-redirect=true";
        } catch (IOException ex) {
            Logger.getLogger(ServiceBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "service";
    }
    
    public String deleteService (Service sv) throws Exception {
        if(svService.deleteService(sv))
            return "successful";
        
        throw new Exception("Something wrong!!!");
    }
    
    private void uploadFile() throws IOException {
//        Random rd = new Random();
//        String imgUpload = rd.nextInt() + "_" + this.image.getSubmittedFileName();
        String path = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getInitParameter("uploadPath") + "services/" + this.image.getSubmittedFileName();
        try(InputStream in = this.image.getInputStream();
                FileOutputStream out = new FileOutputStream(path)){
            byte[] b = new byte[1024];
            int byteRead;
            while((byteRead = in.read(b)) != -1)
                out.write(b, 0, byteRead);
        }
    }
    public int getSvId() {
        return svId;
    }

    /**
     * @param svId the svId to set
     */
    public void setSvId(int svId) {
        this.svId = svId;
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
    
}

