/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baitaplon.service;

import com.baitaplon.pojo.Service;
import com.baitaplon.ou.wedding.HibernateUtil;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Admin
 */
public class SvService {
    
    private final static SessionFactory factory = HibernateUtil.getFactory();
    
    public List<Service> getServices() {
        try (Session session  = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Service> query = builder.createQuery(Service.class);
            Root<Service> root = query.from(Service.class);
            query.select(root);
            
            return session.createQuery(query).getResultList();
        }
    }
    
    public boolean addOrSaveSv (Service sv){
        try(Session session = factory.openSession()){
            try{
                session.getTransaction().begin();
                session.saveOrUpdate(sv);
                session.getTransaction().commit();
            }catch(Exception ex) {
                session.getTransaction().rollback();
                return false;
            }
        }
        return true;
    }
    
        public boolean deleteService(Service sv) {
        try ( Session session = factory.openSession()) {
            try {
                session.getTransaction().begin();
                session.delete(sv);
                session.getTransaction().commit();
            } catch (Exception ex) {
                session.getTransaction().rollback();
                return false;
            }
        }
        return true;
    }
    
    public Service getServiceById (int svId){
        try(Session session = factory.openSession()){
            return session.get(Service.class, svId);
        }
    }
    
}
