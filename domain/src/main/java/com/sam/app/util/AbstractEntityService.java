package com.sam.app.util;

import com.sam.app.domain.AbstractEntity;
import com.sam.app.domain.Department;
import com.sam.app.domain.Employee;
import com.sam.app.domain.Role;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;


public class AbstractEntityService<T extends AbstractEntity> {

    private static final Logger LOGGER = LoggerFactory
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
        new EMUtil<T>() {

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
        T merged = new EMUtil<T>() {

            @Override
            T work() {
                return em.merge(entity);
            }
        }.doWork();

        return entity;
    }

    public T delete(final T entity) {
        new EMUtil<T>() {

            @Override
            T work() {
                em.remove(entity);
                return entity;
            }
        }.doWork();
        return entity;
    }

    public T delete(final Long id) {
        T deleted = new EMUtil<T>() {

            @Override
            T work() {
                T entity = em.find(entityClass, id);
                em.remove(entity);
                return entity;
            }
        }.doWork();
        return deleted;
    }

    public List<Department> getAllDepartments() {
        List<Department> found = new EMUtilForList<Department>() {

            @Override
            List<Department> work() {
                List<Department> localFound = em.createNamedQuery("all_departments", Department.class)
                  .getResultList();
                for (Department department : localFound) {
                    Hibernate.initialize(department.getEmployees());
                }
                return localFound;
            }
        }.doWork();

        return found;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> found = new EMUtilForList<Employee>() {

            @Override
            List<Employee> work() {
                List<Employee> localFound = em.createNamedQuery("all_employees", Employee.class)
                  .getResultList();
                for (Employee employee : localFound) {
                    Hibernate.initialize(employee.getRoles());
                }
                return localFound;
            }
        }.doWork();

        return found;
    }

    public List<Role> getAllRoles() {
        List<Role> found = new EMUtilForList<Role>() {

            @Override
            List<Role> work() {
                List<Role> localFound = em.createNamedQuery("all_roles", Role.class)
                  .getResultList();
                for (Role role : localFound) {
                    Hibernate.initialize(role.getEmployees());
                }
                return localFound;
            }
        }.doWork();

        return found;
    }

    public Employee getEmployee(final long id) {
        Employee found = new EMUtil<Employee>() {
            @Override
            Employee work() {
                Employee localFound = em.createNamedQuery("employee_by_id", Employee.class)
                  .setParameter("empId", id).getSingleResult();
                Hibernate.initialize(localFound.getRoles());
                return localFound;
            }
        }.doWork();
        return found;
    }

    public Role getRole(final long id) {
        Role found = new EMUtil<Role>() {
            @Override
            Role work() {
                Role localFound = em.createNamedQuery("role_by_id", Role.class)
                  .setParameter("rId", id).getSingleResult();
                Hibernate.initialize(localFound.getEmployees());
                return localFound;
            }
        }.doWork();
        return found;
    }

    public Department getDepartment(final long id) {
        Department found = new EMUtil<Department>() {
            @Override
            Department work() {
                Department localFound = em.createNamedQuery("department_by_id", Department.class)
                  .setParameter("depId", id).getSingleResult();
                Hibernate.initialize(localFound);
                return localFound;
            }
        }.doWork();
        return found;
    }

    public List<Department> getDepartmentsByName(final String name) {
        List<Department> found = new EMUtilForList<Department>() {
            @Override
            List<Department> work() {
                return em.createNamedQuery("departments_by_name", Department.class)
                  .setParameter("depName", name).getResultList();
            }
        }.doWork();
        return found;
    }

    public List<Role> getRolesByName(final String name) {
        List<Role> found = new EMUtilForList<Role>() {
            @Override
            List<Role> work() {
                return em.createNamedQuery("roles_by_name", Role.class)
                  .setParameter("roleName", name).getResultList();
            }
        }.doWork();
        return found;
    }

    private abstract class EMUtil<E extends AbstractEntity> {

        abstract E work();

        E doWork() {
            em = emf.createEntityManager();
            EntityTransaction tx = null;
            E result = null;
            try {
                tx = em.getTransaction();
                tx.begin();

                result = work();

                tx.commit();
            } catch (RuntimeException e) {
                if (tx != null && tx.isActive())
                    tx.rollback();
                LOGGER.error("problem at doWork ", e);
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
                LOGGER.error("problem at doWorkForList ", e);
            } finally {
                em.close();
            }
            return result;
        }
    }
}
