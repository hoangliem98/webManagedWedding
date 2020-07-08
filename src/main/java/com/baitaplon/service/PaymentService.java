/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baitaplon.service;

import com.baitaplon.pojo.Payment;
import com.baitaplon.ou.wedding.HibernateUtil;
import com.baitaplon.pojo.Booking;
import com.baitaplon.pojo.Hall;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 *
 * @author Liem Nemo
 */
public class PaymentService {

    private final static SessionFactory factory = HibernateUtil.getFactory();

    public List<Payment> getPayments() {
        try ( Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Payment> query = builder.createQuery(Payment.class);
            Root<Payment> root = query.from(Payment.class);
            query.orderBy(builder.desc(root.get("id")));

            return session.createQuery(query).getResultList();
        }
    }

    public boolean addPayment(Payment p, Booking b) {
        try ( Session session = factory.openSession()) {
            try {
                session.getTransaction().begin();
                session.save(p);
                session.update(b);
                session.getTransaction().commit();

                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                return false;
            }
        }
    }

    public List<Payment> getPaymentByYear(int year) {
        try ( Session session = factory.openSession()) {
            try {
                Query query = session.createQuery("SELECT c FROM Payment c WHERE year(c.payment_date) = " + year, Payment.class);

                List<Payment> list = query.getResultList();

                return list;

            } catch (Exception ex) {
                String er = ex.getMessage();
                session.getTransaction().rollback();
                return null;
            }

        }
    }
}
