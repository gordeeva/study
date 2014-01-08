package com.sam.app.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.sam.app.domain.Employee;

public class EntityManagerUtil {

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
