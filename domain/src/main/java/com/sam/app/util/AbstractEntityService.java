package com.sam.app.util;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sam.app.domain.AbstractEntity;
import com.sam.app.domain.Department;
import com.sam.app.domain.Employee;
import com.sam.app.domain.Role;

public class AbstractEntityService<T extends AbstractEntity> {

    private static final Logger logger = LoggerFactory
            .getLogger(AbstractEntityService.class);

    private static EntityManagerFactory emf;

    private EntityManager em;

    private Class<T> entityClass;

    public static void initEMF() {
        emf = Persistence.createEntityManagerFactory("study");
    }

    public static void closeEMF() {
        emf.close();
    }

    public AbstractEntityService(Class<T> entity) {
        entityClass = entity;
    }

    public Long create(final T entity) {
        new EMUtil() {

            @Override
            T work() {
                em.persist(entity);
                return entity;
            }
        }.doWork();
        return entity.getId();
    }

    public T update(final T entity) {
        @SuppressWarnings("unused")
        T merged = new EMUtil() {

            @Override
            T work() {
                return em.merge(entity);
            }
        }.doWork();

        // return merged;
        return entity;
    }

    public T delete(final T entity) {
        new EMUtil() {

            @Override
            T work() {
                em.remove(entity);
                return entity;
            }
        }.doWork();
        return entity;
    }

    public T delete(final Long id) {
        T deleted = new EMUtil() {

            @Override
            T work() {
                T entity = em.find(entityClass, id);
                em.remove(entity);
                return entity;
            }
        }.doWork();
        return deleted;
    }

    public T get(final Long id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }

        T found = new EMUtil() {

            @Override
            T work() {
                return em.find(entityClass, id);
            }
        }.doWork();
        return found;
    }

    public List<T> getAll() {
        List<T> list = new EMUtilForList<T>() {

            @Override
            List<T> work() {
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
        }.doWork();
        return list;
    }

    public List<Department> getAllDepartments() {
        List<Department> found = new EMUtilForList<Department>() {

            @Override
            List<Department> work() {
                return em.createNamedQuery("all_departments", Department.class)
                        .getResultList();
            }
        }.doWork();
        return found;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> found = new EMUtilForList<Employee>() {

            @Override
            List<Employee> work() {
                return em.createNamedQuery("all_employees", Employee.class)
                        .getResultList();
            }
        }.doWork();
        return found;
    }

    public List<Role> getAllRoles() {
        List<Role> found = new EMUtilForList<Role>() {

            @Override
            List<Role> work() {
                return em.createNamedQuery("all_roles", Role.class)
                        .getResultList();
            }
        }.doWork();
        return found;
    }

    private abstract class EMUtil {

        abstract T work();

        T doWork() {
            em = emf.createEntityManager();
            EntityTransaction tx = null;
            T result = null;
            try {
                tx = em.getTransaction();
                tx.begin();

                result = work();

                tx.commit();
            } catch (RuntimeException e) {
                if (tx != null && tx.isActive())
                    tx.rollback();
                logger.error("problem at doWork " + e.getMessage());
                throw e; // or display error message
            } finally {
                em.close();
            }
            return result;
        }
    }

    private abstract class EMUtilForList<E extends AbstractEntity> {

        abstract List<E> work();

        List<E> doWork() {
            em = emf.createEntityManager();
            EntityTransaction tx = null;
            List<E> result = null;
            try {
                tx = em.getTransaction();
                tx.begin();

                result = work();

                tx.commit();
            } catch (RuntimeException e) {
                if (tx != null && tx.isActive())
                    tx.rollback();
                logger.error("problem at doWorkForList " + e.getMessage());
                throw e; // or display error message
            } finally {
                em.close();
            }
            return result;
        }
    }
}
