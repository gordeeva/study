package com.sam.app.util;

import com.sam.app.domain.Employee;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class EmployeeService extends AbstractEntityService<Employee>{

    public EmployeeService() {
        super(Employee.class);
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
}
