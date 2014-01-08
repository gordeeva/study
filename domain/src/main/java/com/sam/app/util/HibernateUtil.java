package com.sam.app.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;

import com.sam.app.model.Employee;

public class HibernateUtil {
	private static final SessionFactory sessionFactory;
	static {
		try {
			sessionFactory = new Configuration().configure()
					.addAnnotatedClass(Employee.class)
					.buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public static Long saveEmployee(Employee employee) {
		// logger.info("create() start");
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(employee);
			session.getTransaction().commit();
		} catch (Exception e) {
			// logger.error("������ ��� �������", e);
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		// logger.info("create() end");
		return employee.getId();
	}
	
	/**
	 * Транзакция управляется вручную.
	 * 
	 * @param employee
	 * @return
	 */
	public static Long saveEmployeeEM(Employee employee) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("study");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(employee);
		em.flush();
		em.getTransaction().commit();
//		em.getTransaction().
		return employee.getId();
	}
	
	/**
	 * Передается заинъектированный контекст сохранения.
	 * 
	 * @param employee
	 * @param em
	 * @return
	 */
	public static Long saveEmployeeEM(Employee employee, EntityManager em) {
//		em.getTransaction().begin();
		em.persist(employee);
		em.flush();
//		em.getTransaction().commit();
//		em.getTransaction().
		return employee.getId();
	}
}