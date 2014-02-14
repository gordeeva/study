package com.sam.app.util;


import com.sam.app.domain.Department;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DepartmentService extends AbstractEntityService<Department> {

    public DepartmentService() {
        super(Department.class);
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
}
