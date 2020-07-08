/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baitaplon.service;

import com.baitaplon.pojo.Customer;
import com.baitaplon.ou.wedding.HibernateUtil;
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
public class CustomerService {
    private final static SessionFactory factory = HibernateUtil.getFactory();
    
    public List<Customer> getCustomers() {
        try (Session session  = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
            Root<Customer> root = query.from(Customer.class);
            query.select(root);
            
            return session.createQuery(query).getResultList();
        }
    }
    
    public boolean addOrSaveCustomer(Customer c) {
        try ( Session session = factory.openSession()) {
            try {
                session.getTransaction().begin();
                session.saveOrUpdate(c);
                session.getTransaction().commit();
            } catch (Exception ex) {
                session.getTransaction().rollback();
                return false;
            }
        }
        return true;
    }

    public boolean deleteCustomer(Customer c) {
        try ( Session session = factory.openSession()) {
            try {
                session.getTransaction().begin();
                session.delete(c);
                session.getTransaction().commit();
            } catch (Exception ex) {
                session.getTransaction().rollback();
                return false;
            }
        }
        return true;
    }
    public Customer getCustomerById (int customerId){
        try(Session session = factory.openSession()){
            return session.get(Customer.class, customerId);
        }
    }
    
    public int getMaxIdCustomer() {
        try ( Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
            Root<Customer> root = query.from(Customer.class);
            query.select(builder.max(root.get("id").as(Integer.class)));

            int result = (int) session.createQuery(query).getSingleResult();
            return result;

        } catch (Exception e) {
            return 0;
        }
    }
}
