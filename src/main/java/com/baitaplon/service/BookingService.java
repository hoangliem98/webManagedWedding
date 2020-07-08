/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baitaplon.service;

import com.baitaplon.ou.wedding.HibernateUtil;
import com.baitaplon.pojo.Booking;
import com.baitaplon.pojo.Hall;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Liem Nemo
 */
public class BookingService {

    private final static SessionFactory factory = HibernateUtil.getFactory();

    public List<Booking> getBooking() {
        try ( Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Booking> query = builder.createQuery(Booking.class);
            Root<Booking> root = query.from(Booking.class);
            query.orderBy(builder.desc(root.get("id")));

            return session.createQuery(query).getResultList();
        }
    }

    public boolean addOrSaveBooking(Booking b) {
        try ( Session session = factory.openSession()) {
            try {
                session.getTransaction().begin();
                session.save(b);
                session.getTransaction().commit();

                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                return false;
            }
        }
    }

    public List<Booking> getBookingByHall(Hall hall, String season) {
        try ( Session session = factory.openSession()) {

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Booking> query = builder.createQuery(Booking.class);
            Root<Booking> root = query.from(Booking.class);
            query.select(root);
            query.where(builder.and(builder.equal(root.get("hall"), hall),
                    builder.equal(root.get("season").as(String.class), season)));

            List<Booking> b = session.createQuery(query).getResultList();

            return b;

        } catch (Exception e) {
            return null;
        }
    }
    
    public int getMaxIdBooking() {
        try ( Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
            Root<Booking> root = query.from(Booking.class);
            query.select(builder.max(root.get("id").as(Integer.class)));

            int result = (int) session.createQuery(query).getSingleResult();
            return result;

        } catch (Exception e) {
            return 0;
        }
    }
    
    public Booking getBookingById (int bookingId){
        try(Session session = factory.openSession()){
            return session.get(Booking.class, bookingId);
        }
    }
}
