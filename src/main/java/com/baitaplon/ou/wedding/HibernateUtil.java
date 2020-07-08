/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baitaplon.ou.wedding;
import com.baitaplon.pojo.Booking;
import com.baitaplon.pojo.Customer;
import com.baitaplon.pojo.Hall;
import com.baitaplon.pojo.Menu;
import com.baitaplon.pojo.Payment;
import com.baitaplon.pojo.Service;
import com.baitaplon.pojo.User;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author Liem Nemo
 */
public class HibernateUtil {
    private final static SessionFactory FACTORY;
    static {
        Configuration conf = new Configuration();
        Properties pros = new Properties();
        pros.put(Environment.DIALECT,"org.hibernate.dialect.MySQL5Dialect");
        pros.put(Environment.DRIVER,"com.mysql.cj.jdbc.Driver");
        pros.put(Environment.URL,"jdbc:mysql://localhost:3306/wedding");
        pros.put(Environment.USER,"root");
        pros.put(Environment.PASS,"liem123");
        conf.setProperties(pros);
        
        conf.addAnnotatedClass(Hall.class);
        conf.addAnnotatedClass(Menu.class);
        conf.addAnnotatedClass(Service.class);
        conf.addAnnotatedClass(Customer.class);
        conf.addAnnotatedClass(User.class);
        conf.addAnnotatedClass(Booking.class);
        conf.addAnnotatedClass(Payment.class);
        
        ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        
        FACTORY = conf.buildSessionFactory(registry);
    }
    public static SessionFactory getFactory(){
        return FACTORY;
    }
}
