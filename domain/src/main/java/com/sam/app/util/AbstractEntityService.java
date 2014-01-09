package com.sam.app.util;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.sam.app.domain.AbstractEntity;
import com.sam.app.domain.Department;
import com.sam.app.domain.Employee;

public class AbstractEntityService<T extends AbstractEntity> {

	private EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("study");
	private EntityManager em = emf.createEntityManager();

	private Class<T> entityClass;

	public AbstractEntityService(Class<T> entity) {
		entityClass = entity;
	}

	public Long save(T entity) {
		em.getTransaction().begin();
		em.persist(entity);
		em.getTransaction().commit();
		// em.close();
		// emf.close();
		return entity.getId();
	}
	
	public T update(T entity) {
		em.getTransaction().begin();
		em.merge(entity);
		em.getTransaction().commit();
		return entity;
	}
	
	public T delete(T entity) {
		em.getTransaction().begin();
		em.remove(entity);
		em.getTransaction().commit();
		return entity;
	}
	
	public T delete(Long id) {
		em.getTransaction().begin();
		T entity = em.find(entityClass, id);
		em.remove(entity);
		em.getTransaction().commit();
		return entity;
	}

	public T find(Long id) {
		if (id == null) {
			throw new IllegalArgumentException();
		}
		// em.getTransaction().begin();

		return em.find(entityClass, id);
	}

	public List<T> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(entityClass);
		Root<T> root = cq.from(entityClass);
		cq.select(root);
		TypedQuery<T> tq = em.createQuery(cq);
		List<T> result;
		try {
			result = tq.getResultList();
		} catch (NoResultException nre) {
			result = Collections.emptyList();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Department> getAllDepartments() {
		return em.createNamedQuery("all_departments", Department.class)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Employee> getAllEmployees() {
		return em.createNamedQuery("getAllEmployees", Employee.class)
				.getResultList();
	}

	public void close() {
		em.close();
		emf.close();
	}

}
