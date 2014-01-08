package com.sam.app.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sam.app.domain.Employee;

public class EmployeeDAOHibernate {
	private static final Logger logger = LoggerFactory
			.getLogger(EmployeeDAOHibernate.class);

	private final SessionFactory sessionFactory = new Configuration()
			.configure("hibernate.cfg.xml").addAnnotatedClass(Employee.class)
			.buildSessionFactory();

	public Long create(Employee employee) {
		logger.info("create() start");
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(employee);
			session.getTransaction().commit();
		} catch (Exception e) {
			logger.error("smth happened at employee dao create", e);
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		logger.info("create() end");
		return 0l;
	}
}
