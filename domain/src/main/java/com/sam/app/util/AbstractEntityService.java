package com.sam.app.util;

import com.sam.app.domain.AbstractEntity;
import com.sam.app.domain.Department;
import com.sam.app.domain.Employee;
import com.sam.app.domain.Role;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;


public class AbstractEntityService<T extends AbstractEntity> {

    private static final Logger LOGGER = LoggerFactory
      .getLogger(AbstractEntityService.class);

    private Class<T> entityClass;

    private SessionFactory sessionFactory;

    public AbstractEntityService(Class<T> entity) {
        entityClass = entity;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Long create(final T entity) {
        sessionFactory.getCurrentSession().persist(entity);
        return entity.getId();
    }

    @Transactional
    public T update(final T entity) {
        sessionFactory.getCurrentSession().update(entity);
        return entity;
    }

    @Transactional
    public T delete(final T entity) {
        sessionFactory.getCurrentSession().delete(entity);
        return entity;
    }


    @Transactional
    @SuppressWarnings("unchecked")
    public T delete(final Long id) {
        Session session = sessionFactory.getCurrentSession();
        T entity = (T) session.createCriteria(entityClass).
          add(Restrictions.eq("id", id)).uniqueResult();
        session.delete(entity);
        return entity;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<Department> getAllDepartments() {
        List<Department> departments = sessionFactory.getCurrentSession().
          createCriteria(Department.class).list();
        initLazyDepartments(departments);
        return departments;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<Employee> getAllEmployees() {
        List<Employee> employees = sessionFactory.getCurrentSession().
          createCriteria(Employee.class).list();
        initLazyEmployees(employees);
        return employees;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<Role> getAllRoles() {
        List<Role> roles = sessionFactory.getCurrentSession().
          createCriteria(Role.class).list();
        initLazyRoles(roles);
        return roles;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public Employee getEmployee(final long id) {
        Employee employee = (Employee) sessionFactory.getCurrentSession().
          createCriteria(Employee.class).add(Restrictions.eq("id", id)).uniqueResult();
        initLazyEmployees(Collections.singletonList(employee));
        return employee;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public Department getDepartment(final long id) {
        Department department = (Department) sessionFactory.getCurrentSession().
          createCriteria(Department.class).add(Restrictions.eq("id", id)).uniqueResult();
        initLazyDepartments(Collections.singletonList(department));
        return department;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public Role getRole(final long id) {
        Role role = (Role) sessionFactory.getCurrentSession().
          createCriteria(Role.class).add(Restrictions.eq("id", id)).uniqueResult();
        initLazyRoles(Collections.singletonList(role));
        return role;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<Department> getDepartmentsByName(final String name) {
        List<Department> departments = (List<Department>) sessionFactory.getCurrentSession().
          createCriteria(Department.class).add(Restrictions.eq("name", name)).list();
        initLazyDepartments(departments);
        return departments;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<Role> getRolesByName(final String name) {
        List<Role> roles = (List<Role>) sessionFactory.getCurrentSession().
          createCriteria(Role.class).add(Restrictions.eq("name", name)).list();
        initLazyRoles(roles);
        return roles;
    }

    private void initLazyEmployees(List<Employee> employees) {
        for (Employee employee : employees) {
            Hibernate.initialize(employee.getRoles());
        }
    }

    private void initLazyDepartments(List<Department> departments) {
        for (Department department : departments) {
            Hibernate.initialize(department.getEmployees());
        }
    }

    private void initLazyRoles(List<Role> roles) {
        for (Role role : roles) {
            Hibernate.initialize(role.getEmployees());
        }
    }

}
