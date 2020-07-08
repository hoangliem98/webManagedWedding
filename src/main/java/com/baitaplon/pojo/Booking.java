/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baitaplon.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Liem Nemo
 */
@Entity
@Table(name = "bookings")
public class Booking implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hall;
    
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    private int number_of_tables;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "organization_date")
    private Date organization_date;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "booking_date")
    private Date booking_date;
       
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "service_booking",
            joinColumns = {
                @JoinColumn(name = "booking_id")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "service_id")
            }
    ) 
    private Set<Service> service_booking;
    private String season;
    private BigDecimal price;
    private String payment_status;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the hall
     */
    public Hall getHall() {
        return hall;
    }

    /**
     * @param hall the hall to set
     */
    public void setHall(Hall hall) {
        this.hall = hall;
    }

    /**
     * @return the menu
     */
    public Menu getMenu() {
        return menu;
    }

    /**
     * @param menu the menu to set
     */
    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the number_of_tables
     */
    public int getNumber_of_tables() {
        return number_of_tables;
    }

    /**
     * @param number_of_tables the number_of_tables to set
     */
    public void setNumber_of_tables(int number_of_tables) {
        this.number_of_tables = number_of_tables;
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
     * @return the organization_date
     */
    public Date getOrganization_date() {
        return organization_date;
    }

    /**
     * @param organization_date the organization_date to set
     */
    public void setOrganization_date(Date organization_date) {
        this.organization_date = organization_date;
    }

    /**
     * @return the booking_date
     */
    public Date getBooking_date() {
        return booking_date;
    }

    /**
     * @param booking_date the booking_date to set
     */
    public void setBooking_date(Date booking_date) {
        this.booking_date = booking_date;
    }

    /**
     * @return the season
     */
    public String getSeason() {
        return season;
    }

    /**
     * @param season the season to set
     */
    public void setSeason(String season) {
        this.season = season;
    }

    /**
     * @return the service_booking
     */
    public Set<Service> getService_booking() {
        return service_booking;
    }

    /**
     * @param service_booking the service_booking to set
     */
    public void setService_booking(Set<Service> service_booking) {
        this.service_booking = service_booking;
    }

    /**
     * @return the payment_status
     */
    public String getPayment_status() {
        return payment_status;
    }

    /**
     * @param payment_status the payment_status to set
     */
    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }
}
