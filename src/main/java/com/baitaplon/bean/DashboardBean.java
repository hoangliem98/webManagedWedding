/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baitaplon.bean;

import com.baitaplon.pojo.Payment;
import com.baitaplon.service.PaymentService;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Liem Nemo
 */
@ManagedBean
@Named(value = "dashboardBean")
@SessionScoped
public class DashboardBean implements Serializable {

    private final static PaymentService paymentService = new PaymentService();

    private int currentYear;

    private BigDecimal Jan;
    private BigDecimal Feb;
    private BigDecimal Mar;
    private BigDecimal Apr;
    private BigDecimal May;
    private BigDecimal Jun;
    private BigDecimal Jul;
    private BigDecimal Aug;
    private BigDecimal Sep;
    private BigDecimal Oct;
    private BigDecimal Nov;
    private BigDecimal Dec;

    public DashboardBean() {
        currentYear = 2020;
        thongketheothang();
    }

    public List<String> getListYear() {
        List<Payment> jobUsers = paymentService.getPayments();

        Date maxYearDate = jobUsers.stream().map(p -> p.getPayment_date()).max(Date::compareTo).get();
        Date minYearDate = jobUsers.stream().map(p -> p.getPayment_date()).min(Date::compareTo).get();

        Calendar calMaxYear = Calendar.getInstance();
        calMaxYear.setTime(maxYearDate);
        Calendar calMinYear = Calendar.getInstance();
        calMinYear.setTime(minYearDate);

        int minYear = calMinYear.get(Calendar.YEAR);
        int maxYear = calMaxYear.get(Calendar.YEAR);
        List<String> list = new ArrayList<>();

        for (int i = maxYear; i >= minYear; i--) {
            list.add(Integer.toString(i));
        }
        return list;
    }

    public void thongketheothang() {
        List<Payment> list = paymentService.getPaymentByYear(currentYear);

        BigDecimal thang1 = new BigDecimal(0);
        BigDecimal thang2 = new BigDecimal(0);
        BigDecimal thang3 = new BigDecimal(0);
        BigDecimal thang4 = new BigDecimal(0);
        BigDecimal thang5 = new BigDecimal(0);
        BigDecimal thang6 = new BigDecimal(0);
        BigDecimal thang7 = new BigDecimal(0);
        BigDecimal thang8 = new BigDecimal(0);
        BigDecimal thang9 = new BigDecimal(0);
        BigDecimal thang10 = new BigDecimal(0);
        BigDecimal thang11 = new BigDecimal(0);
        BigDecimal thang12 = new BigDecimal(0);

        for (Payment payment : list) {

            Date date = payment.getPayment_date();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int month = calendar.get(Calendar.MONTH);

            month = month + 1;

            switch (month) {
                case 1:
                    thang1 = thang1.add(payment.getTotal_price());
                    break;
                case 2:
                    thang2 = thang2.add(payment.getTotal_price());
                    break;
                case 3:
                    thang3 = thang3.add(payment.getTotal_price());
                    break;
                case 4:
                    thang4 = thang4.add(payment.getTotal_price());
                    break;
                case 5:
                    thang5 = thang5.add(payment.getTotal_price());
                    break;
                case 6:
                    thang6 = thang6.add(payment.getTotal_price());
                    break;
                case 7:
                    thang7 = thang7.add(payment.getTotal_price());
                    break;
                case 8:
                    thang8 = thang8.add(payment.getTotal_price());
                    break;
                case 9:
                    thang9 = thang9.add(payment.getTotal_price());
                    break;
                case 10:
                    thang10 = thang10.add(payment.getTotal_price());
                    break;
                case 11:
                    thang11 = thang11.add(payment.getTotal_price());
                    break;
                case 12:
                    thang12 = thang12.add(payment.getTotal_price());
                    break;
                default:
                    throw new AssertionError();
            }

        }

        this.Jan = thang1;
        this.Feb = thang2;
        this.Mar = thang3;
        this.Apr = thang4;
        this.Mar = thang5;
        this.Jun = thang6;
        this.Jul = thang7;
        this.Aug = thang8;
        this.Sep = thang9;
        this.Oct = thang10;
        this.Nov = thang11;
        this.Dec = thang12;

    }

    /**
     * @return the currentYear
     */
    public int getCurrentYear() {
        return currentYear;
    }

    /**
     * @param currentYear the currentYear to set
     */
    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    /**
     * @return the Jan
     */
    public BigDecimal getJan() {
        return Jan;
    }

    /**
     * @param Jan the Jan to set
     */
    public void setJan(BigDecimal Jan) {
        this.Jan = Jan;
    }

    /**
     * @return the Feb
     */
    public BigDecimal getFeb() {
        return Feb;
    }

    /**
     * @param Feb the Feb to set
     */
    public void setFeb(BigDecimal Feb) {
        this.Feb = Feb;
    }

    /**
     * @return the Mar
     */
    public BigDecimal getMar() {
        return Mar;
    }

    /**
     * @param Mar the Mar to set
     */
    public void setMar(BigDecimal Mar) {
        this.Mar = Mar;
    }

    /**
     * @return the Apr
     */
    public BigDecimal getApr() {
        return Apr;
    }

    /**
     * @param Apr the Apr to set
     */
    public void setApr(BigDecimal Apr) {
        this.Apr = Apr;
    }

    /**
     * @return the May
     */
    public BigDecimal getMay() {
        return May;
    }

    /**
     * @param May the May to set
     */
    public void setMay(BigDecimal May) {
        this.May = May;
    }

    /**
     * @return the Jun
     */
    public BigDecimal getJun() {
        return Jun;
    }

    /**
     * @param Jun the Jun to set
     */
    public void setJun(BigDecimal Jun) {
        this.Jun = Jun;
    }

    /**
     * @return the Jul
     */
    public BigDecimal getJul() {
        return Jul;
    }

    /**
     * @param Jul the Jul to set
     */
    public void setJul(BigDecimal Jul) {
        this.Jul = Jul;
    }

    /**
     * @return the Aug
     */
    public BigDecimal getAug() {
        return Aug;
    }

    /**
     * @param Aug the Aug to set
     */
    public void setAug(BigDecimal Aug) {
        this.Aug = Aug;
    }

    /**
     * @return the Sep
     */
    public BigDecimal getSep() {
        return Sep;
    }

    /**
     * @param Sep the Sep to set
     */
    public void setSep(BigDecimal Sep) {
        this.Sep = Sep;
    }

    /**
     * @return the Oct
     */
    public BigDecimal getOct() {
        return Oct;
    }

    /**
     * @param Oct the Oct to set
     */
    public void setOct(BigDecimal Oct) {
        this.Oct = Oct;
    }

    /**
     * @return the Nov
     */
    public BigDecimal getNov() {
        return Nov;
    }

    /**
     * @param Nov the Nov to set
     */
    public void setNov(BigDecimal Nov) {
        this.Nov = Nov;
    }

    /**
     * @return the Dec
     */
    public BigDecimal getDec() {
        return Dec;
    }

    /**
     * @param Dec the Dec to set
     */
    public void setDec(BigDecimal Dec) {
        this.Dec = Dec;
    }

}
