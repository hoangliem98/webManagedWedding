/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baitaplon.bean;

import javax.enterprise.context.RequestScoped;

import com.baitaplon.pojo.User;
import com.baitaplon.service.UserService;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.Transient;
import javax.servlet.http.Part;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Liem Nemo
 */
@ManagedBean
@Named(value = "userBean")
@RequestScoped
public class UserBean {

    private int usId;
    private String user_name;
    private String password;
    private String name;
    private String image;
    private Part imageFile;
    private String email;
    @Transient
    private String confirmPassword;
    private String currentPassword;
    private int roles;
    private final static UserService userService = new UserService();

    /**
     * Creates a new instance of UserBean
     */
    public UserBean() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            String userId = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getRequestParameterMap().get("user_id");
            if (userId != null && !userId.isEmpty()) {
                User u = userService.getUserById(Integer.parseInt(userId));
                this.usId = u.getId();
                this.user_name = u.getUser_name();
                this.image = u.getImage();
                this.email = u.getEmail();
                this.roles = u.getRoles();
                this.name = u.getName();
            }
        }
    }

    public String loginAdmin() {
        User u = userService.loginAdmin(user_name, password);
        if (u != null) {
            if (u.getRoles() == 1) {
                FacesContext.getCurrentInstance()
                        .getExternalContext()
                        .getSessionMap().put("user", u);
                return "dashboard?faces-rediect=true";
            } else {
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Tài khoản của bạn không được phép vào trang này", "detail...."));
                return "login";
            }
        }
        return "login";
    }

    public String checkLoginAdmin() {
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user") != null) {
            return "dashboard?faces-rediect=true";
        } else {
            return "login";
        }
    }

    public String logoutAdmin() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("user");
        return "login";
    }

    public List<User> getUsers() {
        return userService.getUsers();
    }

    public String addUsers() {
        User u;
        if (!this.password.isEmpty() && this.password.equals(this.confirmPassword)) {
            if (userService.getAccountByUsername(this.user_name) == null) {
                u = new User();
                u.setUser_name(this.user_name);
                u.setPassword(this.password);
                u.setEmail(this.email);
                u.setName(this.name);
                u.setRoles(this.roles);
                try {
                    if (this.imageFile != null) {
                        this.uploadFile();
                        u.setImage(this.imageFile.getSubmittedFileName());
                    }
                    if (userService.addOrSaveUser(u) == true) {
                        return "user-list?faces-redirect=true";
                    }
                } catch (IOException ex) {
                    Logger.getLogger(HallBean.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Tài khoản đã tồn tại!", "detail...."));
                return null;
            }
        } else {
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mật khẩu không khớp", "detail...."));
            return null;
        }
        return "user";
    }
    
    public String updateUsers() {
        User u = userService.getUserById(this.usId);
        u.setEmail(this.email);
        u.setName(this.name);
        try {
            if (this.imageFile != null) {
                this.uploadFile();
                u.setImage(this.imageFile.getSubmittedFileName());
            }
            if (userService.updateUser(u) == true) {
                return "user-list?faces-redirect=true";
            }
        } catch (IOException ex) {
            Logger.getLogger(HallBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "user";
    }

    public String changePasswordAdmin() {
        User u = userService.getUserById(this.usId);
        if (!this.currentPassword.isEmpty() && DigestUtils.md5Hex(this.currentPassword).equals(u.getPassword())) {
            if (!this.password.isEmpty() && this.password.equals(this.confirmPassword)) {
                u.setPassword(this.password);
                if (userService.addOrSaveUser(u) == true) {
                    return "user-list?faces-redirect=true";
                }
            }
        }
        return "change-password";
    }

    public String login() {
        User u = userService.login(user_name, password);
        if (u != null) {
            if (u.getRoles() == 1) {
                FacesContext.getCurrentInstance()
                        .getExternalContext()
                        .getSessionMap().put("user", u);
            } else {
                FacesContext.getCurrentInstance()
                        .getExternalContext()
                        .getSessionMap().put("user_normal", u);
            }
            return "home?faces-rediect=true";
        }
        return "login";
    }

    public String checkLogin() {
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user") != null
                || FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_normal") != null) {
            return "home?faces-rediect=true";
        }
        return null;
    }

    public String logout() {
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user") != null) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("user");
        }
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_normal") != null) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("user_normal");
        }
        return "home";
    }

    public String addUser() {
        User u;
        if (!this.password.isEmpty() && this.password.equals(this.confirmPassword)) {
            if (userService.getAccountByUsername(this.user_name) == null) {
                u = new User();
                u.setUser_name(this.user_name);
                u.setPassword(this.password);
                u.setEmail(this.email);
                u.setName(this.name);
                u.setRoles(0);
                try {
                    if (this.imageFile != null) {
                        this.uploadFile();
                        u.setImage(this.imageFile.getSubmittedFileName());
                    }
                    if (userService.addOrSaveUser(u) == true) {
                        return "login?faces-redirect=true";
                    }
                } catch (IOException ex) {
                    Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Tài khoản đã tồn tại!", "detail...."));
                return null;
            }
        } else {
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mật khẩu không khớp", "detail...."));
            return null;
        }
        return "user";
    }

    public String updateUser() {
        User u = userService.getUserById(this.usId);
        u.setEmail(this.email);
        u.setName(this.name);
        try {
            if (this.imageFile != null) {
                this.uploadFile();
                u.setImage(this.imageFile.getSubmittedFileName());
            }
            if (userService.updateUser(u) == true) {
                return "home?faces-redirect=true";
            }
        } catch (IOException ex) {
            Logger.getLogger(HallBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "user-info";
    }

    public String changePassword() {
        User u = userService.getUserById(this.usId);
        if (!this.currentPassword.isEmpty() && DigestUtils.md5Hex(this.currentPassword).equals(u.getPassword())) {
            if (!this.password.isEmpty() && this.password.equals(this.confirmPassword)) {
                u.setPassword(this.password);
                if (userService.addOrSaveUser(u) == true) {
                    return "home?faces-redirect=true";
                }
            }
        }
        return "change-password";
    }

    public String deleteUser(User u) throws Exception {
        if (userService.deleteUser(u)) {
            return "successful";
        }

        throw new Exception("Something wrong!!!");
    }

    private void uploadFile() throws IOException {
        String path = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getInitParameter("uploadPath") + "users/" + this.imageFile.getSubmittedFileName();
        try ( InputStream in = this.imageFile.getInputStream();  FileOutputStream out = new FileOutputStream(path)) {
            byte[] b = new byte[1024];
            int byteRead;
            while ((byteRead = in.read(b)) != -1) {
                out.write(b, 0, byteRead);
            }
        }
    }

    /**
     * @return the user_name
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * @param user_name the user_name to set
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
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
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the roles
     */
    public int getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(int roles) {
        this.roles = roles;
    }

    /**
     * @return the confirmPassword
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * @param confirmPassword the confirmPassword to set
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * @return the imageFile
     */
    public Part getImageFile() {
        return imageFile;
    }

    /**
     * @param imageFile the imageFile to set
     */
    public void setImageFile(Part imageFile) {
        this.imageFile = imageFile;
    }

    /**
     * @return the usId
     */
    public int getUsId() {
        return usId;
    }

    /**
     * @param usId the usId to set
     */
    public void setUsId(int usId) {
        this.usId = usId;
    }

    /**
     * @return the currentPassword
     */
    public String getCurrentPassword() {
        return currentPassword;
    }

    /**
     * @param currentPassword the currentPassword to set
     */
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

}
