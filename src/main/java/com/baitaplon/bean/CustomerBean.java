/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baitaplon.bean;

import com.baitaplon.pojo.Customer;
import com.baitaplon.service.CustomerService;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author Liem Nemo
 */
@ManagedBean
@Named(value = "customerBean")
@RequestScoped
public class CustomerBean {

    
    private int csId;
    private String firstname;
    private String lastname;
    private int phone;
    private String email;
    private String address;
    private final static CustomerService customerService = new CustomerService();
    /**
     * Creates a new instance of HallBean
     */
    public CustomerBean() {
        if(!FacesContext.getCurrentInstance().isPostback()){
            String customerId = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getRequestParameterMap().get("customer_id");
            if(customerId != null && !customerId.isEmpty()){
                Customer c = customerService.getCustomerById(Integer.parseInt(customerId));
                this.csId = c.getId();
                this.firstname = c.getFirst_name();
                this.lastname = c.getLast_name();
                this.phone = c.getPhone_number();
                this.email = c.getEmail();
                this.address = c.getAddress();
            }
        }
    }
    
    public String addCustomer(){
        Customer c = customerService.getCustomerById(this.csId);
        c.setFirst_name(this.firstname);
        c.setLast_name(this.lastname);
        c.setPhone_number(this.phone);
        c.setEmail(this.email);
        c.setAddress(this.address);
        if(customerService.addOrSaveCustomer(c) == true)
            return "customer-list?faces-redirect=true";
        return "customer";
    }
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }
    
    public String deleteCustomer (Customer c) throws Exception {
        if(customerService.deleteCustomer(c))
            return "successful";
        
        throw new Exception("Something wrong!!!");
    }
    
    /**
     * @return the csId
     */
    public int getCsId() {
        return csId;
    }

    /**
     * @param csId the csId to set
     */
    public void setCsId(int csId) {
        this.csId = csId;
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the phone
     */
    public int getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(int phone) {
        this.phone = phone;
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
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
}
