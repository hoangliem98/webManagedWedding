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
import com.baitaplon.service.CustomerService;
import com.baitaplon.service.HallService;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author Liem Nemo
 */
@ManagedBean
@Named(value = "bookingBean")
@RequestScoped
public class BookingBean {

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
    private final static HallService hallService = new HallService();
    private final static CustomerService customerService = new CustomerService();

    /**
     * Creates a new instance of BookingBean
     */
    public BookingBean() {
    }

    public int addCustomer() {
        Customer c = new Customer();
        c.setFirst_name(this.first_name);
        c.setLast_name(this.last_name);
        c.setPhone_number(this.phone_number);
        c.setEmail(this.email);
        c.setAddress(this.address);

        if (customerService.addOrSaveCustomer(c)) {
            int id = customerService.getMaxIdCustomer();
            return id;
        }
        return 0;
    }

    public List<Booking> getBookings() {
        return bookingService.getBooking();
    }

    public String add() {
        SimpleDateFormat formatdate = new SimpleDateFormat("ddMMyyyy");
        String date1 = formatdate.format(this.organization_date);
        List<Booking> gethall = bookingService.getBookingByHall(this.hall, this.season);
        Iterator<Booking> iterators = gethall.iterator();
        while (iterators.hasNext()) {
            String date2 = formatdate.format(iterators.next().getOrganization_date());
            if (gethall != null && date1.equals(date2)) {
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sảnh bạn chọn không thể đặt ở thời điểm đó. Vui lòng chọn ngày hoặc buổi khác!", "detail...."));
                return null;
            }
        }
        int idcus = addCustomer();
        if (idcus > 0) {
            Customer cus = customerService.getCustomerById(idcus);
            Booking b = new Booking();
            b.setBooking_date(new Date());
            b.setOrganization_date(this.organization_date);
            b.setHall(this.hall);
            b.setMenu(this.menu);
            b.setCustomer(cus);
            b.setSeason(this.season);
            b.setNumber_of_tables(this.number_of_tables);
            b.setService_booking(this.service_booking);
            b.setPayment_status("Chưa thanh toán");
            Iterator<Service> iterator = this.service_booking.iterator();
            if (this.service_booking.size() > 0) {
                BigDecimal hallprice = new BigDecimal(this.hall.getPrice().toString());
                BigDecimal menuprice = new BigDecimal(this.menu.getPrice().toString());
                BigDecimal numbertable = new BigDecimal(this.number_of_tables);
                BigDecimal total = hallprice.add(menuprice.multiply(numbertable));
                while (iterator.hasNext()) {
                    BigDecimal service = new BigDecimal(iterator.next().getPrice().toString());
                    BigDecimal temp = total;
                    total = temp.add(service);
                }
                b.setPrice(total);
            } else {
                BigDecimal hallprice = new BigDecimal(this.hall.getPrice().toString());
                BigDecimal menuprice = new BigDecimal(this.menu.getPrice().toString());
                BigDecimal numbertable = new BigDecimal(this.number_of_tables);
                BigDecimal total = hallprice.add(menuprice.multiply(numbertable));
                b.setPrice(total);
            }

            if (bookingService.addOrSaveBooking(b) == true) {
                return "booking-result?faces-redirect=true";
            }
        }

        return "booking";
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

}
