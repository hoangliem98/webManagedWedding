/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baitaplon.bean;

import com.baitaplon.pojo.Menu;
import com.baitaplon.service.MenuService;
import com.google.gson.Gson;
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
@Named(value = "menuBean")
@RequestScoped
public class MenuBean {
    private int mnId;
    private String name;
    private String content;
    private String image_name;
    private Part image;
    private BigDecimal price;
    private final static MenuService menuService = new MenuService();
    /**
     * Creates a new instance of HallBean
     */
    public MenuBean() {
        if(!FacesContext.getCurrentInstance().isPostback()){
            String menuId = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getRequestParameterMap().get("menu_id");
            if(menuId != null && !menuId.isEmpty()){
                Menu m = menuService.getMenuById(Integer.parseInt(menuId));
                this.mnId = m.getId();
                this.name = m.getName();
                this.content = m.getContent();
                this.image_name = m.getImage();
                this.price = m.getPrice();
            }
        }
    }
    public List<Menu> getMenus() {
        return menuService.getMenus();
    }
    
    public String addMenu(){
        Menu m;
        if(this.mnId > 0){
            m = menuService.getMenuById(this.mnId);
        }
        else {
            m = new Menu();
        }
        m.setName(this.name);
        m.setContent(this.content);
        m.setPrice(this.price);
        
        try{
            if(this.image != null){
                this.uploadFile();
                m.setImage(this.image.getSubmittedFileName());
            }
            if(menuService.addOrSaveMenu(m)== true)
                return "menu-list?faces-redirect=true";
        } catch(IOException ex){
            Logger.getLogger(MenuBean.class.getName()).log(Level.SEVERE,null,ex);
        }
        return "menu";
    }
    
    public String deleteMenu (Menu m) throws Exception {
        if(menuService.deleteMenu(m))
            return "successful";
        
        throw new Exception("Something wrong!!!");
    }
    
    private void uploadFile() throws IOException {
        String path = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getInitParameter("uploadPath") + "menu/" + this.image.getSubmittedFileName();
        try(InputStream in = this.image.getInputStream();
                FileOutputStream out = new FileOutputStream(path)){
            byte[] b = new byte[1024];
            int byteRead;
            while((byteRead = in.read(b)) != -1)
                out.write(b, 0, byteRead);
        }
    }
    
    public String getJson(){
        String menuId = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getRequestParameterMap().get("menu_id");
        Menu m = menuService.getPriceById(Integer.parseInt(menuId));
        String json = new Gson().toJson(m);
        return json;
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
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
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
     * @return the mnId
     */
    public int getMnId() {
        return mnId;
    }

    /**
     * @param mnId the mnId to set
     */
    public void setMnId(int mnId) {
        this.mnId = mnId;
    }
    
}
