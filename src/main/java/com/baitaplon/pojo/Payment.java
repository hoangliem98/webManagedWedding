/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baitaplon.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Liem Nemo
 */
@Entity
@Table(name = "payments")
public class Payment implements Serializable{   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private BigDecimal temporary_price;
    private BigDecimal delayed_price;
    private String delayed_content;
    private BigDecimal total_price; 
    @Temporal(TemporalType.DATE)
    @Column(name = "payment_date")
    private Date payment_date; 
    
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
     * @return the booking
     */
    public Booking getBooking() {
        return booking;
    }

    /**
     * @param booking the booking to set
     */
    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the temporary_price
     */
    public BigDecimal getTemporary_price() {
        return temporary_price;
    }

    /**
     * @param temporary_price the temporary_price to set
     */
    public void setTemporary_price(BigDecimal temporary_price) {
        this.temporary_price = temporary_price;
    }

    /**
     * @return the delayed_price
     */
    public BigDecimal getDelayed_price() {
        return delayed_price;
    }

    /**
     * @param delayed_price the delayed_price to set
     */
    public void setDelayed_price(BigDecimal delayed_price) {
        this.delayed_price = delayed_price;
    }

    /**
     * @return the delayed_content
     */
    public String getDelayed_content() {
        return delayed_content;
    }

    /**
     * @param delayed_content the delayed_content to set
     */
    public void setDelayed_content(String delayed_content) {
        this.delayed_content = delayed_content;
    }

    /**
     * @return the total_price
     */
    public BigDecimal getTotal_price() {
        return total_price;
    }

    /**
     * @param total_price the total_price to set
     */
    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
    }

    /**
     * @return the payment_date
     */
    public Date getPayment_date() {
        return payment_date;
    }

    /**
     * @param payment_date the payment_date to set
     */
    public void setPayment_date(Date payment_date) {
        this.payment_date = payment_date;
    }
}
