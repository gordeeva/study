package com.sam.app;

import com.sam.app.domain.Employee;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.List;

import static junit.framework.Assert.*;

public class HibernateTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static EntityTransaction tx;

    @BeforeClass
    public static void initEntityManager() throws Exception {
        emf = Persistence.createEntityManagerFactory("study");
        em = emf.createEntityManager();
    }

    @AfterClass
    public static void closeEntityManager() throws SQLException {
        em.close();
        emf.close();
    }

    @Before
    public void initTransaction() {
        tx = em.getTransaction();
    }

    @Test
    public void createBook() throws Exception {

        Employee employee = new Employee();
        employee.setName("newEmp1");

        tx.begin();
        em.persist(employee);
        tx.commit();

        assertNotNull("ID should not be null", employee.getId());

        List<Employee> employees = em.createNamedQuery("getAllEmployees").getResultList();
        assertNotNull(employees);
    }
}
