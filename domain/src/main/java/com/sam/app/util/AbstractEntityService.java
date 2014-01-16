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

import com.sam.app.ICRUD;
import com.sam.app.domain.*;

public class AbstractEntityService<T extends AbstractEntity> implements ICRUD<T> {

	private EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("study");
	private EntityManager em = emf.createEntityManager();

	private Class<T> entityClass;

	public AbstractEntityService(Class<T> entity) {
		entityClass = entity;
	}

	@Override
    public long create(T entity) {
		em.getTransaction().begin();
		em.persist(entity);
		em.getTransaction().commit();
		// em.close();
		// emf.close();
		return entity.getId();
	}

    @Override
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

    @Override
	public T delete(long id) {
		em.getTransaction().begin();
		T entity = em.find(entityClass, id);
		em.remove(entity);
		em.getTransaction().commit();
		return entity;
	}

    @Override
	public T get(Long id) {
		if (id == null) {
			throw new IllegalArgumentException();
		}
		// em.getTransaction().begin();
		return em.find(entityClass, id);
	}

    @Override
	public List<T> getAll() {
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
		return em.createNamedQuery("all_employees", Employee.class)
				.getResultList();
	}

    @SuppressWarnings("unchecked")
	public List<Role> getAllRoles() {
		return em.createNamedQuery("all_roles", Role.class)
				.getResultList();
	}

	public void close() {
		em.close();
		emf.close();
	}

}
