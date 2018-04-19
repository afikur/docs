package com.afikur.main;

import com.afikur.model.Employee;
import com.afikur.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Date;

public class Main {
   public static void main(String[] args) {
       Employee emp = new Employee();
       emp.setFirstName("Afikur Rahman");
       emp.setLastName("Khan");
       emp.setRole("Software Developer");
       emp.setCreatedAt(new Date());

       SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
       Session session = sessionFactory.getCurrentSession();

       session.beginTransaction();
       session.save(emp);
       session.getTransaction().commit();

       sessionFactory.close();
   }
}
