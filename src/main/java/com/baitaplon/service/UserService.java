/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baitaplon.service;

import com.baitaplon.pojo.User;
import com.baitaplon.ou.wedding.HibernateUtil;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Liem Nemo
 */
public class UserService {

    private final static SessionFactory factory = HibernateUtil.getFactory();

    public List<User> getUsers() {
        try ( Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root);

            return session.createQuery(query).getResultList();
        }
    }

    public boolean addOrSaveUser(User u) {
        try ( Session session = factory.openSession()) {
            try {
                session.getTransaction().begin();
                u.setPassword(DigestUtils.md5Hex(u.getPassword()));
                session.saveOrUpdate(u);
                session.getTransaction().commit();
            } catch (Exception ex) {
                session.getTransaction().rollback();
                return false;
            }
        }
        return true;
    }
    
    public boolean updateUser(User u) {
        try ( Session session = factory.openSession()) {
            try {
                session.getTransaction().begin();
                session.update(u);
                session.getTransaction().commit();
            } catch (Exception ex) {
                session.getTransaction().rollback();
                return false;
            }
        }
        return true;
    }

    public boolean deleteUser(User u) {
        try ( Session session = factory.openSession()) {
            try {
                session.getTransaction().begin();
                session.delete(u);
                session.getTransaction().commit();
            } catch (Exception ex) {
                session.getTransaction().rollback();
                return false;
            }
        }
        return true;
    }

    public User getUserById(int userId) {
        try ( Session session = factory.openSession()) {
            return session.get(User.class, userId);
        }
    }

    public User getAccountByUsername(String username) {
        try ( Session session = factory.openSession()) {

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);

            query.select(root).where(builder.equal(root.get("user_name"), username));

            User u = session.createQuery(query).getSingleResult();

            return u;

        } catch (Exception e) {
            return null;
        }
    }

    public User loginAdmin(String user_name, String password) {
        password = DigestUtils.md5Hex(password);
        try ( Session session = factory.openSession()) {
            CriteriaBuilder b = session.getCriteriaBuilder();
            CriteriaQuery<User> q = b.createQuery(User.class);
            Root<User> root = q.from(User.class);
            q.select(root);
            q.where(b.and(b.equal(root.get("user_name").as(String.class), user_name),
                    b.equal(root.get("password").as(String.class), password),
                    b.equal(root.get("roles").as(Integer.class), 1)));

            return session.createQuery(q).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public User login(String user_name, String password) {
        password = DigestUtils.md5Hex(password);
        try ( Session session = factory.openSession()) {
            CriteriaBuilder b = session.getCriteriaBuilder();
            CriteriaQuery<User> q = b.createQuery(User.class);
            Root<User> root = q.from(User.class);
            q.select(root);
            q.where(b.and(b.equal(root.get("user_name").as(String.class), user_name),
                    b.equal(root.get("password").as(String.class), password)));

            return session.createQuery(q).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
