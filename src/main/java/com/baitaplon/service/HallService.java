/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baitaplon.service;

import com.baitaplon.pojo.Hall;
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
public class HallService {

    private final static SessionFactory factory = HibernateUtil.getFactory();

    public List<Hall> getHalls() {
        try ( Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Hall> query = builder.createQuery(Hall.class);
            Root<Hall> root = query.from(Hall.class);
            query.select(root);

            return session.createQuery(query).getResultList();
        }
    }

    public boolean addOrSaveHall(Hall h) {
        try ( Session session = factory.openSession()) {
            try {
                session.getTransaction().begin();
                session.saveOrUpdate(h);
                session.getTransaction().commit();
            } catch (Exception ex) {
                session.getTransaction().rollback();
                return false;
            }
        }
        return true;
    }

    public boolean deleteHall(Hall h) {
        try ( Session session = factory.openSession()) {
            try {
                session.getTransaction().begin();
                session.delete(h);
                session.getTransaction().commit();
            } catch (Exception ex) {
                session.getTransaction().rollback();
                return false;
            }
        }
        return true;
    }
    public Hall getHallById (int hallId){
        try(Session session = factory.openSession()){
            return session.get(Hall.class, hallId);
        }
    }
}
