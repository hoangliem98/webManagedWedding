/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baitaplon.service;


import com.baitaplon.pojo.Menu;
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
public class MenuService {
    
    private final static SessionFactory factory = HibernateUtil.getFactory();
    
    public List<Menu> getMenus() {
        try (Session session  = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Menu> query = builder.createQuery(Menu.class);
            Root<Menu> root = query.from(Menu.class);
            query.select(root);
            
            return session.createQuery(query).getResultList();
        }
    }
    
    public boolean addOrSaveMenu(Menu m) {
        try ( Session session = factory.openSession()) {
            try {
                session.getTransaction().begin();
                session.saveOrUpdate(m);
                session.getTransaction().commit();
            } catch (Exception ex) {
                session.getTransaction().rollback();
                return false;
            }
        }
        return true;
    }
    
    public boolean deleteMenu(Menu m) {
        try ( Session session = factory.openSession()) {
            try {
                session.getTransaction().begin();
                session.delete(m);
                session.getTransaction().commit();
            } catch (Exception ex) {
                session.getTransaction().rollback();
                return false;
            }
        }
        return true;
    }
    
    public Menu getMenuById (int menuId){
        try(Session session = factory.openSession()){
            return session.get(Menu.class, menuId);
        }
    }
    
    public Menu getPriceById (int menuId){
        try ( Session session = factory.openSession()) {

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Menu> query = builder.createQuery(Menu.class);
            Root<Menu> root = query.from(Menu.class);

            query.select(root).where(builder.equal(root.get("price"), menuId));

            Menu m = session.createQuery(query).getSingleResult();

            return m;

        } catch (Exception e) {
            return null;
        }
    }
    
}
