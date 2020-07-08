/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baitaplon.bean;

import com.baitaplon.pojo.Booking;
import com.baitaplon.pojo.Customer;
import com.baitaplon.pojo.Hall;
import com.baitaplon.pojo.Menu;
import com.baitaplon.pojo.Service;
import com.baitaplon.service.BookingService;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Liem Nemo
 */
@ManagedBean
@Named(value = "bookingResultBean")
@Dependent
public class BookingResultBean {

    private int bkId;
    private Hall hall;
    private Menu menu;
    private Customer customer;
    private String season;
    private int number_of_tables;
    private Date organization_date;
    private Date booking_date;
    private BigDecimal price;
    private Set<Service> service_booking;
    private String first_name;
    private String last_name;
    private int phone_number;
    private String email;
    private String address;
    private final static BookingService bookingService = new BookingService();

    /**
     * Creates a new instance of BookingResultBean
     */
    public BookingResultBean() {
        int bookingId = bookingService.getMaxIdBooking();
        Booking b = bookingService.getBookingById(bookingId);
        this.bkId = b.getId();
        this.hall = b.getHall();
        this.menu = b.getMenu();
        this.service_booking = b.getService_booking();
        this.customer = b.getCustomer();
        this.season = b.getSeason();
        this.number_of_tables = b.getNumber_of_tables();
        this.organization_date = b.getOrganization_date();
        this.booking_date = b.getBooking_date();
        this.price = b.getPrice();
    }

    /**
     * @return the bkId
     */
    public int getBkId() {
        return bkId;
    }

    /**
     * @param bkId the bkId to set
     */
    public void setBkId(int bkId) {
        this.bkId = bkId;
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
     * @return the first_name
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * @param first_name the first_name to set
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * @return the last_name
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * @param last_name the last_name to set
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * @return the phone_number
     */
    public int getPhone_number() {
        return phone_number;
    }

    /**
     * @param phone_number the phone_number to set
     */
    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
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
