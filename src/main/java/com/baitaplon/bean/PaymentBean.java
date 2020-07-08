/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baitaplon.bean;

import com.baitaplon.pojo.Booking;
import com.baitaplon.pojo.Payment;
import com.baitaplon.pojo.User;
import com.baitaplon.service.PaymentService;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
@Named(value = "paymentBean")
@RequestScoped
public class PaymentBean {

    private Booking booking;
    private final static PaymentService paymentService = new PaymentService();

    /**
     * Creates a new instance of PaymentBean
     */
    public PaymentBean() {
    }

    public List<Payment> getPayments() {
        return paymentService.getPayments();
    }

    public int daysBetween(Date date1, Date date2) {
        return (int) ((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24));
    }

    public String addPayment(Booking b) {
        Date payment_date = new Date();
        Date org_date = b.getOrganization_date();
        float delay = ((payment_date.getTime() - org_date.getTime()) / (1000 * 60 * 60 * 24));
        Payment p = new Payment();
        p.setBooking(b);
        p.setUser((User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user"));
        p.setTemporary_price(b.getPrice());
        p.setPayment_date(payment_date);
        BigDecimal booking_price = new BigDecimal(b.getPrice().toString());
        BigDecimal total;
        if (delay > 0) {
            p.setDelayed_content("Thanh toán muộn");
            BigDecimal delay_percent = new BigDecimal(delay).divide(new BigDecimal(100));
            BigDecimal delay_price = booking_price.multiply(delay_percent);
            p.setDelayed_price(delay_price);
            total = booking_price.add(delay_price);
        } else {
            p.setDelayed_price(BigDecimal.ZERO);
            p.setDelayed_content(null);
            total = booking_price;
        }
        p.setTotal_price(total);
        b.setPayment_status("Đã thanh toán");
        if (paymentService.addPayment(p, b) == true) {
            return "payment-list?faces-redirect=true";
        }
        return "booking-list";
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

}
