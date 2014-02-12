package com.sam.app.util;

import com.sam.app.domain.Employee;

public class EmployeeService extends AbstractEntityService<Employee>{

    public EmployeeService() {
        super(Employee.class);
    }
}
